package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
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
}
