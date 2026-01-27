package CoserCreation.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.services.creationService;
import CoserCreation.DTO.creationDTO;

@RestController
@RequestMapping("api/creation")
@CrossOrigin
public class creationController {
    private final creationService creationService;

    public creationController(creationService creationService) {
        this.creationService = creationService;
    }

    @GetMapping("")
    public List<creationDTO> getAllCreations() {
        return creationService.getAllCreations();
    }

}
