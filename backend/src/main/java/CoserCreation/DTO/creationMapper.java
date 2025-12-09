package CoserCreation.DTO;

import java.util.List;
import java.util.stream.Collectors;

import CoserCreation.models.creationModel;

public class creationMapper {
    public static creationDTO toDTO(creationModel model) {
        return creationDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .price(model.getPrice())
                .description(model.getDescription())
                .image(model.getImage())
                .build();
    }

    public static List<creationDTO> toDTO(List<creationModel> models) {
        return models.stream()
                .map(creationMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static creationModel fromDTO(creationDTO dto) {
        creationModel model = new creationModel();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setPrice(dto.getPrice());
        model.setDescription(dto.getDescription());
        model.setImage(dto.getImage());
        return model;
    }
}
