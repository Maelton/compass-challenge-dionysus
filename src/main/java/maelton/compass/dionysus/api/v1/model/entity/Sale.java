package maelton.compass.dionysus.api.v1.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Enumerated;
import jakarta.persistence.EnumType;
import jakarta.persistence.CascadeType;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import maelton.compass.dionysus.api.v1.model.enums.SaleStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "tab_sale")
public class Sale {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    @ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "user_id")
    private User costumer;

    @Setter
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    @JoinColumn(name = "product_id")
    private Wine product;

    @Setter
    private LocalDateTime saleDateTime;

    @Setter
    private Double totalSaleValue;

    @Enumerated(EnumType.STRING)
    @Setter
    private SaleStatus status;

    public Sale(User costumer, Wine product) {
        this.costumer = costumer;
        this.product = product;
        this.saleDateTime = LocalDateTime.now();
        this.totalSaleValue = product.getPrice();
        this.status = SaleStatus.CLOSED;
    }
}
