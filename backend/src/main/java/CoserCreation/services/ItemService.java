package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

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

    public ItemService(ItemDAO itemDAO, ColorDAO colorDAO, ItemImageDAO itemImageDAO) {
        this.itemDAO = itemDAO;
        this.colorDAO = colorDAO;
    }

    public List<ItemShortDTO> getAllItems() {
        return ItemMapper.toShortDTOList(itemDAO.findAll());
    }

    public ItemDTO getItemById(int id) {
        return ItemMapper.toDTO(itemDAO.findById(id).orElseThrow());
    }

    public void createItem(ItemCreationDTO itemCreationDTO) {
        ItemModel newItem = ItemMapper.fromDTO(itemCreationDTO);

        List<ItemImageModel> itemImageModels = ItemMapper.fromImageDTOList(itemCreationDTO.getImages());
        if (itemImageModels != null && !itemImageModels.isEmpty()) {
            for (ItemImageModel image : itemImageModels) {
                image.setItem(newItem);
            }
            newItem.setImages(itemImageModels);
        }

        List<Integer> colorIds = itemCreationDTO.getColorsId();
        if (colorIds != null && !colorIds.isEmpty()) {
            List<ColorModel> existingColors = colorDAO.findAllById(colorIds);
            newItem.setColors(existingColors);
        }

        newItem = itemDAO.save(newItem);
    }

    public void deleteItemById(int id) {
        itemDAO.deleteById(id);
    }

    public void updateItemById(int id, ItemCreationDTO itemCreationDTO) {
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

        if (itemCreationDTO.getImages() != null) {
            List<ItemImageModel> newImages = ItemMapper.fromImageDTOList(itemCreationDTO.getImages());
            existingItem.getImages().clear();
            for (ItemImageModel image : newImages) {
                image.setItem(existingItem);
            }
            existingItem.getImages().addAll(newImages);
        }

        if (itemCreationDTO.getColorsId() != null) {
            List<Integer> colorIds = itemCreationDTO.getColorsId();
            List<ColorModel> colors = colorDAO.findAllById(colorIds);
            existingItem.setColors(colors);
        }

        itemDAO.save(existingItem);
    }
}
