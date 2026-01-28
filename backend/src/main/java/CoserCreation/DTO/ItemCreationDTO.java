package CoserCreation.DTO;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
@ToString
public class ItemCreationDTO {
    private String title;
    private BigDecimal price;
    private String description;
    private List<ItemImageDTO> images;
    private List<Integer> colorsId;
}
