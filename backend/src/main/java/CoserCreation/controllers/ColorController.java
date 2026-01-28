package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.DTO.ColorDTO;
import CoserCreation.services.ColorService;

@RestController
@RequestMapping("api/colors")
@CrossOrigin
public class ColorController {
    private final ColorService colorService;

    public ColorController(ColorService colorService) {
        this.colorService = colorService;
    }

    @GetMapping("")
    public List<ColorDTO> getAllColors() {
        return colorService.getAllColors();
    }

    @GetMapping("/{id}")
    public ColorDTO getColorById(@PathVariable int id) {
        return colorService.getColorById(id);
    }

    @PostMapping("")
    public void createColor(@RequestBody ColorDTO colorDTO) {
        colorService.createColor(colorDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteColorById(@PathVariable int id) {
        colorService.deleteColorById(id);
    }

    @PutMapping("/{id}")
    public ColorDTO updateColor(@PathVariable int id, @RequestBody ColorDTO colorDTO) {
        return colorService.updateColor(id, colorDTO);
    }
}
