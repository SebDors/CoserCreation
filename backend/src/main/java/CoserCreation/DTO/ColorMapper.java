package CoserCreation.DTO;

import java.util.List;
import java.util.stream.Collectors;

import CoserCreation.models.ColorModel;

public class ColorMapper {

    public static ColorDTO toDTO(ColorModel model) {
        return ColorDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .image(model.getImage())
                .build();
    }

    public static List<ColorDTO> toListDTO(List<ColorModel> models) {
        return models.stream()
                .map(ColorMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static ColorModel fromDTO(ColorDTO dto) {
        return ColorModel.builder()
                .id(dto.getId())
                .name(dto.getName())
                .image(dto.getImage())
                .build();
    }

    public static List<ColorModel> fromListDTO(List<ColorDTO> dto) {
        return dto.stream()
                .map(ColorMapper::fromDTO)
                .collect(Collectors.toList());
    }

}
