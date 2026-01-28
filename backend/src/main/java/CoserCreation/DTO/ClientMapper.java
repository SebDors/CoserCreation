package CoserCreation.DTO;

import CoserCreation.models.ClientModel;

public class ClientMapper {

    public static ClientDTO toDTO(ClientModel model) {
        return ClientDTO.builder()
                .id(model.getId())
                .email(model.getEmail())
                .isActive(model.isActive())
                .build();
    }

    public static ClientModel fromDTO(ClientDTO dto) {
        return ClientModel.builder()
                .id(dto.getId())
                .email(dto.getEmail())
                .isActive(dto.isActive())
                .build();
    }
}
