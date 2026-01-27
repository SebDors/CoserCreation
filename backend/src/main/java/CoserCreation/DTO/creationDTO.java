package CoserCreation.DTO;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Getter // Génère les getters
@Setter // Génère les setters
@Builder // Génère le builder
@NoArgsConstructor // Génère un constructeur sans arguments
@ToString
public class creationDTO {
    private int id;
    private String title;
    private BigDecimal price;
    private String description;
    private String image;
}
