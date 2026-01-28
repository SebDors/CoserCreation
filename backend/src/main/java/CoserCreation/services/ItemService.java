package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoserCreation.DAO.ColorDAO;
import CoserCreation.DAO.ItemDAO;
import CoserCreation.DTO.ItemCreationDTO;
import CoserCreation.DTO.ItemDTO;
import CoserCreation.DTO.ItemMapper;
import CoserCreation.DTO.ItemShortDTO;
import CoserCreation.models.ColorModel;
import CoserCreation.models.ItemModel;

@Service
public class ItemService {
    private final ItemDAO itemDAO;
    private final ColorDAO colorDAO;

    public ItemService(ItemDAO itemDAO, ColorDAO colorDAO) {
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

        List<Integer> colorIds = itemCreationDTO.getColorsId();
        if (colorIds != null && !colorIds.isEmpty()) {
            List<ColorModel> existingColors = colorDAO.findAllById(colorIds);
            newItem.setColors(existingColors);
        }

        itemDAO.save(newItem);
    }
}
