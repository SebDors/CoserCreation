package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoserCreation.DAO.ItemDAO;
import CoserCreation.DTO.ItemDTO;
import CoserCreation.DTO.ItemMapper;

@Service
public class ItemService {
    private final ItemDAO itemDAO;

    public ItemService(ItemDAO itemDAO) {
        this.itemDAO = itemDAO;
    }

    public List<ItemDTO> getAllItems() {
        return ItemMapper.toDTOList(itemDAO.findAll());
    }

}
