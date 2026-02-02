package CoserCreation.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import CoserCreation.DTO.LoginDTO;
import CoserCreation.services.LoginService;

@RestController
@RequestMapping("api/login")
@CrossOrigin
public class LoginController {
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("")
    public ResponseEntity<Void> CheckAuthentification(@RequestBody LoginDTO loginDTO) {
        boolean isAuthenticated = loginService.authenticate(loginDTO.getUsername(), loginDTO.getPassword());

        if (isAuthenticated) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}