package CoserCreation.services;

import org.springframework.stereotype.Service;
import CoserCreation.DAO.ClientDAO;
import CoserCreation.DTO.ClientDTO;
import CoserCreation.DTO.ClientMapper;
import CoserCreation.models.ClientModel;

@Service
public class ClientService {
    private final ClientDAO clientDAO;

    public ClientService(ClientDAO clientDAO) {
        this.clientDAO = clientDAO;
    }

    public ClientDTO createClient(ClientDTO clientDTO) {
        ClientModel clientModel = ClientMapper.fromDTO(clientDTO);
        clientModel.setActive(true);
        return ClientMapper.toDTO(clientDAO.save(clientModel));
    }
}
