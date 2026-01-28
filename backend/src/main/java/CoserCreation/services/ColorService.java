package CoserCreation.services;

import java.util.List;

import org.springframework.stereotype.Service;

import CoserCreation.DAO.ColorDAO;
import CoserCreation.DTO.ColorDTO;
import CoserCreation.DTO.ColorMapper;

@Service
public class ColorService {
    private final ColorDAO colorDAO;

    public ColorService(ColorDAO colorDAO) {
        this.colorDAO = colorDAO;
    }

    public List<ColorDTO> getAllColors() {
        return ColorMapper.toListDTO(colorDAO.findAll());
    }

    public ColorDTO getColorById(int id) {
        return ColorMapper.toDTO(colorDAO.findById(id).orElseThrow());
    }

    public void createColor(ColorDTO colorDTO) {
        colorDAO.save(ColorMapper.fromDTO(colorDTO));
    }

    public void deleteColorById(int id) {
        colorDAO.deleteById(id);
    }
}
