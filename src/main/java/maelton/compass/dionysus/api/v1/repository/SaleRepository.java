package maelton.compass.dionysus.api.v1.repository;

import maelton.compass.dionysus.api.v1.model.entity.Sale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SaleRepository extends JpaRepository<Sale, UUID> {
    Sale save(Sale sale);
    Optional<Sale> findById(UUID id);
    List<Sale> findAll();
    void deleteById(UUID id);

    boolean existsById(UUID uuid);
}
