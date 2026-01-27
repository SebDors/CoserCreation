package CoserCreation.DTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import CoserCreation.models.ColorModel;
import CoserCreation.models.ItemImageModel;
import CoserCreation.models.ItemModel;

public class ItemMapper {

    public static ItemImageDTO toDTO(ItemImageModel model) {
        if (model == null) {
            return null;
        }
        return ItemImageDTO.builder()
                .id(model.getId())
                .imageUrl(model.getImageUrl())
                .altText(model.getAltText())
                .build();
    }

    public static List<ItemImageDTO> toImageDTOList(List<ItemImageModel> models) {
        if (models == null) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static ColorDTO toDTO(ColorModel model) {
        if (model == null) {
            return null;
        }
        return ColorDTO.builder()
                .id(model.getId())
                .name(model.getName())
                .image(model.getImage())
                .build();
    }

    public static List<ColorDTO> toColorDTOList(List<ColorModel> models) {
        if (models == null) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static ItemDTO toDTO(ItemModel model) {
        if (model == null) {
            return null;
        }
        return ItemDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .price(model.getPrice())
                .description(model.getDescription())
                .images(toImageDTOList(model.getImages()))
                .colors(toColorDTOList(model.getColors()))
                .build();
    }

    public static ItemShortDTO toShortDTO(ItemModel model) {
        if (model == null) {
            return null;
        }
        return ItemShortDTO.builder()
                .id(model.getId())
                .title(model.getTitle())
                .price(model.getPrice())
                .description(model.getDescription())
                .images(toImageDTOList(model.getImages()))
                .build();
    }

    public static List<ItemShortDTO> toShortDTOList(List<ItemModel> models) {
        if (models == null) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(ItemMapper::toShortDTO)
                .collect(Collectors.toList());
    }

    public static List<ItemDTO> toDTOList(List<ItemModel> models) {
        if (models == null) {
            return Collections.emptyList();
        }
        return models.stream()
                .map(ItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public static ItemModel fromDTO(ItemDTO dto) {
        if (dto == null) {
            return null;
        }
        ItemModel model = new ItemModel();
        model.setId(dto.getId());
        model.setTitle(dto.getTitle());
        model.setPrice(dto.getPrice());
        model.setDescription(dto.getDescription());
        // We don't map images back from DTO to model to avoid complexities.
        // We also don't map colors back. This is typically handled separately if
        // needed.
        return model;
    }
}
