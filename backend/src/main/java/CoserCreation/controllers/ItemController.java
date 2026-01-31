package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import CoserCreation.services.ItemService;
import CoserCreation.DTO.ItemCreationDTO;
import CoserCreation.DTO.ItemUpdateDTO;
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
    public void createItem(
            @RequestPart("item") ItemCreationDTO itemCreationDTO,
            @RequestParam(value = "images", required = false) MultipartFile[] images,
            @RequestParam(value = "altTexts", required = false) String[] altTexts) {
        itemService.createItem(itemCreationDTO, images, altTexts);
    }

    @DeleteMapping("/{id}")
    public void deleteItemById(@PathVariable int id) {
        itemService.deleteItemById(id);
    }

    @PutMapping("/{id}")
    public void updateItemById(
            @PathVariable int id,
            @RequestPart("item") ItemUpdateDTO itemUpdateDTO,
            @RequestParam(value = "new_images", required = false) MultipartFile[] newImages,
            @RequestParam(value = "new_image_alts", required = false) String[] newImageAlts) {
        itemService.updateItemById(id, itemUpdateDTO, newImages, newImageAlts);
    }
}
