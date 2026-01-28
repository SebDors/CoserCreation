package CoserCreation.controllers;

import org.springframework.web.bind.annotation.*;
import CoserCreation.DTO.ClientDTO;
import CoserCreation.services.ClientService;

@RestController
@RequestMapping("api/clients")
@CrossOrigin
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("")
    public ClientDTO createClient(@RequestBody ClientDTO clientDTO) {
        return clientService.createClient(clientDTO);
    }
}
