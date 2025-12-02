package CoserCreation.models;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor // Génère un constructeur avec tous les arguments
@Getter // Génère les getters
@Setter // Génère les setters
@Builder // Génère le builder pour plus tard (il parait que c'est utile)
@NoArgsConstructor // Génère un constructeur sans arguments

@Entity
@Table(name = "creation")
public class creationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String title;
    private BigDecimal price;
    private String description;
    private String image;

}