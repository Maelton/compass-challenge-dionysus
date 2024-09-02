package maelton.compass.dionysus.api.v1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import maelton.compass.dionysus.api.v1.model.enums.WineType;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "tab_wine_model",
        uniqueConstraints = {
                @UniqueConstraint(
                    name = "tab_wine_model_un_model",
                    columnNames = {"brand", "name", "volume", "abv", "type"}
                )
        }
)
public class WineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "wineModelSequenceGenerator")
    @SequenceGenerator(name = "wineModelSequenceGenerator", sequenceName = "seq_wine_model", allocationSize = 1)
    private Long id;

    @Setter
    private String brand;

    @Setter
    private String name;

    @Setter
    private double volume;

    @Setter
    private double abv;

    @Enumerated(EnumType.STRING)
    @Setter
    private WineType type;

    @Setter
    private double recommendedPrice;

    @JsonIgnore
    @OneToMany(mappedBy = "model", fetch = FetchType.LAZY)
    Set<Wine> wines = new HashSet<>();

    public WineModel(String brand, String name, double volume, double abv, WineType type, double recommendedPrice) {
        this.brand = brand;
        this.name = name;
        this.volume = volume;
        this.abv = abv;
        this.type = type;
        this.recommendedPrice = recommendedPrice;
    }
}
