package maelton.compass.dionysus.api.v1.model.entity;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;

import lombok.NoArgsConstructor;

import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@Entity
public class Wine {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "model_id")
    WineModel model;

    private Double price;
    private LocalDateTime insertionDateTime;
    private ProductStatus status;

    @OneToOne(mappedBy = "product")
    Sale sale;
}
