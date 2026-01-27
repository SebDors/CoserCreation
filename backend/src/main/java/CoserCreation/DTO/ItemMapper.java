package CoserCreation.DTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
                .build();
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
        // This is typically handled separately if needed.
        return model;
    }
}
