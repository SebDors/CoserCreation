package CoserCreation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CoserCreation.DAO.ColorDAO;
import CoserCreation.DAO.ItemDAO;
import CoserCreation.DAO.ItemImageDAO;
import CoserCreation.DTO.ItemCreationDTO;
import CoserCreation.DTO.ItemDTO;
import CoserCreation.DTO.ItemMapper;
import CoserCreation.DTO.ItemShortDTO;
import CoserCreation.models.ColorModel;
import CoserCreation.models.ItemImageModel;
import CoserCreation.models.ItemModel;

@Service
public class ItemService {
    private final ItemDAO itemDAO;
    private final ColorDAO colorDAO;
    private final ItemImageDAO itemImageDAO;
    private final EmailService emailService;

    private final String fileSystemUploadDir = "/app/media/items/";
    private final String webPath = "media/items/";

    public ItemService(ItemDAO itemDAO, ColorDAO colorDAO, ItemImageDAO itemImageDAO, EmailService emailService) {
        this.itemDAO = itemDAO;
        this.colorDAO = colorDAO;
        this.itemImageDAO = itemImageDAO;
        this.emailService = emailService;
    }

    public List<ItemShortDTO> getAllItems() {
        return ItemMapper.toShortDTOList(itemDAO.findAll());
    }

    public ItemDTO getItemById(int id) {
        return ItemMapper.toDTO(itemDAO.findById(id).orElseThrow());
    }

    public void createItem(ItemCreationDTO itemCreationDTO, MultipartFile[] images, String[] altTexts) {
        ItemModel newItem = ItemMapper.fromDTO(itemCreationDTO);

        List<Integer> colorIds = itemCreationDTO.getColorsId();
        if (colorIds != null && !colorIds.isEmpty()) {
            List<ColorModel> existingColors = colorDAO.findAllById(colorIds);
            newItem.setColors(existingColors);
        }

        ItemModel savedItem = itemDAO.save(newItem);

        if (images != null && images.length > 0) {
            saveImages(savedItem, images, altTexts);
        }

        emailService.sendNewItemNotification(savedItem);
    }

    public void deleteItemById(int id) {
        itemDAO.deleteById(id);
    }

    public void updateItemById(int id, ItemCreationDTO itemCreationDTO, MultipartFile[] images, String[] altTexts) {
        ItemModel existingItem = itemDAO.findById(id).orElseThrow();
        if (itemCreationDTO.getTitle() != null && !itemCreationDTO.getTitle().isEmpty()) {
            existingItem.setTitle(itemCreationDTO.getTitle());
        }
        if (itemCreationDTO.getDescription() != null && !itemCreationDTO.getDescription().isEmpty()) {
            existingItem.setDescription(itemCreationDTO.getDescription());
        }

        if (itemCreationDTO.getPrice() != null) {
            existingItem.setPrice(itemCreationDTO.getPrice());
        }

        if (itemCreationDTO.getColorsId() != null) {
            List<Integer> colorIds = itemCreationDTO.getColorsId();
            List<ColorModel> colors = colorDAO.findAllById(colorIds);
            existingItem.setColors(colors);
        }
        
        ItemModel savedItem = itemDAO.save(existingItem);

        if (images != null && images.length > 0) {
            // Here you might want to delete old images first
            saveImages(savedItem, images, altTexts);
        }
    }

    private void saveImages(ItemModel item, MultipartFile[] images, String[] altTexts) {
        File directory = new File(fileSystemUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        List<ItemImageModel> itemImages = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            String fileExtension = getFileExtension(image.getOriginalFilename());
            String newFileName = item.getTitle().replaceAll("\\s+", "_") + "_" + (i + 1) + fileExtension;
            Path filePath = Paths.get(fileSystemUploadDir + newFileName);

            try {
                Files.write(filePath, image.getBytes());
                ItemImageModel itemImage = new ItemImageModel();
                itemImage.setImageUrl(webPath + newFileName);
                if (altTexts != null && i < altTexts.length) {
                    itemImage.setAltText(altTexts[i]);
                }
                itemImage.setItem(item);
                itemImageDAO.save(itemImage);
                itemImages.add(itemImage);
            } catch (IOException e) {
                // Handle exception
                e.printStackTrace();
            }
        }
        item.setImages(itemImages);
        itemDAO.save(item);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}
