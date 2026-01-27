package CoserCreation.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
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
@Builder // Génère le builder
@NoArgsConstructor // Génère un constructeur sans arguments

@Entity
@Table(name = "creation")
public class creationModel {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column
    private String title;
    @Column
    private BigDecimal price;
    @Column
    private String description;
    @Column
    private String image;

}