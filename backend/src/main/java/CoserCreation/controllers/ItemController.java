package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.services.ItemService;
import CoserCreation.DTO.ItemCreationDTO;
import CoserCreation.DTO.ItemDTO;
import CoserCreation.DTO.ItemShortDTO;

@RestController
@RequestMapping("api/items")
@CrossOrigin
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public List<ItemShortDTO> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/{id}")
    public ItemDTO getItemById(@PathVariable int id) {
        return itemService.getItemById(id);
    }

    @PostMapping("")
    public void createItem(@RequestBody ItemCreationDTO itemCreationDTO) {
        itemService.createItem(itemCreationDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable int id) {
        itemService.deleteItemById(id);
    }
}
