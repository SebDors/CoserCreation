package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoserCreation.DAO.creationDAO;
import CoserCreation.models.creationModel;

@Service
public class creationService {
    private final creationDAO creationDAO;

    public creationService(creationDAO creationDAO) {
        this.creationDAO = creationDAO;
    }

    public List<creationModel> getAllCreations() {
        return creationDAO.findAll();
    }

}
