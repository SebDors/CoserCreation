package CoserCreation.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import CoserCreation.DAO.ColorDAO;
import CoserCreation.DAO.ItemDAO;
import CoserCreation.DAO.ItemImageDAO;
import CoserCreation.DTO.ItemCreationDTO;
import CoserCreation.DTO.ItemDTO;
import CoserCreation.DTO.ItemImageUpdateDTO;
import CoserCreation.DTO.ItemMapper;
import CoserCreation.DTO.ItemShortDTO;
import CoserCreation.DTO.ItemUpdateDTO;
import CoserCreation.models.ColorModel;
import CoserCreation.models.ItemImageModel;
import CoserCreation.models.ItemModel;
import jakarta.transaction.Transactional;

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

    public List<ItemShortDTO> getAllItems(String sortBy, String sortDirection, Integer limit) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);

        if (limit != null && limit > 0) {
            Pageable pageable = PageRequest.of(0, limit, sort);
            return ItemMapper.toShortDTOList(itemDAO.findAll(pageable).getContent());
        } else {
            return ItemMapper.toShortDTOList(itemDAO.findAll(sort));
        }
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
            saveNewImages(savedItem, images, altTexts);
        }

        emailService.sendNewItemNotification(savedItem);
    }

    public void deleteItemById(int id) {
        // Before deleting the item, delete its images from the filesystem
        ItemModel item = itemDAO.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));
        if (item.getImages() != null) {
            for (ItemImageModel image : item.getImages()) {
                try {
                    String fileName = image.getImageUrl().replace(webPath, "");
                    Files.deleteIfExists(Paths.get(fileSystemUploadDir + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        itemDAO.deleteById(id);
    }

    @Transactional
    public void updateItemById(int id, ItemUpdateDTO itemUpdateDTO, MultipartFile[] newImages, String[] newImageAlts) {
        ItemModel existingItem = itemDAO.findById(id).orElseThrow(() -> new RuntimeException("Item not found"));

        // Update textual data and colors
        if (itemUpdateDTO.getTitle() != null && !itemUpdateDTO.getTitle().isEmpty()) {
            existingItem.setTitle(itemUpdateDTO.getTitle());
        }
        if (itemUpdateDTO.getDescription() != null && !itemUpdateDTO.getDescription().isEmpty()) {
            existingItem.setDescription(itemUpdateDTO.getDescription());
        }
        if (itemUpdateDTO.getPrice() != null) {
            existingItem.setPrice(itemUpdateDTO.getPrice());
        }
        if (itemUpdateDTO.getColorsId() != null) {
            List<ColorModel> colors = colorDAO.findAllById(itemUpdateDTO.getColorsId());
            existingItem.setColors(colors);
        }

        // Update existing image alt texts
        if (itemUpdateDTO.getExistingImages() != null) {
            for (ItemImageUpdateDTO imageUpdate : itemUpdateDTO.getExistingImages()) {
                ItemImageModel imageModel = itemImageDAO.findById(imageUpdate.getId())
                        .orElseThrow(() -> new RuntimeException("Image not found"));
                imageModel.setAltText(imageUpdate.getAltText());
                itemImageDAO.save(imageModel);
            }
        }

        // Delete images marked for deletion
        if (itemUpdateDTO.getDeletedImageIds() != null) {
            for (Integer imageId : itemUpdateDTO.getDeletedImageIds()) {
                ItemImageModel imageModel = itemImageDAO.findById(imageId)
                        .orElseThrow(() -> new RuntimeException("Image to delete not found"));
                
                try {
                    String fileName = imageModel.getImageUrl().replace(webPath, "");
                    Files.deleteIfExists(Paths.get(fileSystemUploadDir + fileName));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                existingItem.getImages().remove(imageModel);
                itemImageDAO.deleteById(imageId);
            }
        }

        // Save new images
        if (newImages != null && newImages.length > 0) {
            saveNewImages(existingItem, newImages, newImageAlts);
        }
        
        itemDAO.save(existingItem);
    }

    private void saveNewImages(ItemModel item, MultipartFile[] images, String[] altTexts) {
        File directory = new File(fileSystemUploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        int existingImageCount = item.getImages() != null ? item.getImages().size() : 0;
        List<ItemImageModel> newImageModels = new ArrayList<>();

        for (int i = 0; i < images.length; i++) {
            MultipartFile image = images[i];
            String fileExtension = getFileExtension(image.getOriginalFilename());
            String newFileName = item.getTitle().replaceAll("\\s+", "_") + "_" + (existingImageCount + i + 1) + "_" + System.currentTimeMillis() + fileExtension;
            Path filePath = Paths.get(fileSystemUploadDir + newFileName);

            try {
                Files.write(filePath, image.getBytes());
                ItemImageModel itemImage = new ItemImageModel();
                itemImage.setImageUrl(webPath + newFileName);
                if (altTexts != null && i < altTexts.length) {
                    itemImage.setAltText(altTexts[i]);
                }
                itemImage.setItem(item);
                newImageModels.add(itemImage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        item.getImages().addAll(newImageModels);
        itemImageDAO.saveAll(newImageModels);
    }

    private String getFileExtension(String fileName) {
        if (fileName == null || fileName.lastIndexOf('.') == -1) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.'));
    }
}