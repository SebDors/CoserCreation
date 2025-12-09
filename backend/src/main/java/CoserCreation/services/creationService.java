package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoserCreation.DAO.creationDAO;
import CoserCreation.DTO.creationDTO;
import CoserCreation.DTO.creationMapper;

@Service
public class creationService {
    private final creationDAO creationDAO;

    public creationService(creationDAO creationDAO) {
        this.creationDAO = creationDAO;
    }

    public List<creationDTO> getAllCreations() {
        return creationMapper.toDTO(creationDAO.findAll());
    }

}
