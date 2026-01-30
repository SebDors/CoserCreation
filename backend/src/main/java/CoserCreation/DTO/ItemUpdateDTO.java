package CoserCreation.DTO;

import java.math.BigDecimal;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemUpdateDTO {
    private String title;
    private BigDecimal price;
    private String description;
    private List<Integer> colorsId;
    private List<ItemImageUpdateDTO> existingImages;
    private List<Integer> deletedImageIds;
}
