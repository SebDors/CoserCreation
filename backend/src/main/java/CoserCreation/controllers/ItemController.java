package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.services.ItemService;
import CoserCreation.DTO.ItemDTO;

@RestController
@RequestMapping("api/items")
@CrossOrigin
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("")
    public List<ItemDTO> getAllItems() {
        return itemService.getAllItems();
    }

}
