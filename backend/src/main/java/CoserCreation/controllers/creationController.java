package CoserCreation.controllers;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.services.creationService;

@RestController
@RequestMapping("/creation")
@CrossOrigin
public class creationController {
    private final creationService creationService;

    public creationController(creationService creationService) {
        this.creationService = creationService;
    }

    @GetMapping("")
    public Object getAllCreations() {
        return creationService.getAllCreations();
    }

}
