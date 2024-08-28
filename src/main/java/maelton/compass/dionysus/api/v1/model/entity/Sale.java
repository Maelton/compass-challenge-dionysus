package maelton.compass.dionysus.api.v1.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import maelton.compass.dionysus.api.v1.model.enums.SaleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne
    @JoinColumn(name = "product_id")
    private Wine product;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User costumer;

    private LocalDateTime localDateTime;
    private Double totalSaleValue;
    private SaleStatus status;
}
