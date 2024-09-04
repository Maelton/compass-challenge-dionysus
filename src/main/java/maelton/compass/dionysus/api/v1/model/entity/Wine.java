package maelton.compass.dionysus.api.v1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tab_wine")
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne
    @JoinColumn(name = "model_id")
    WineModel model;

    @Setter
    private Double price;
    private LocalDateTime insertionDateTime;

    @Enumerated(EnumType.STRING)
    @Setter
    private ProductStatus status;

    public Wine(WineModel model, Double price, ProductStatus status) {
        this.model = model;
        this.price = price;
        //TODO: Use date convention
        this.insertionDateTime = LocalDateTime.now();
        this.status = status;
    }
}
