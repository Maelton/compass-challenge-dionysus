package maelton.compass.dionysus.api.v1.repository;

import maelton.compass.dionysus.api.v1.model.entity.Wine;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface WineRepository extends JpaRepository<Wine, UUID> {
    Wine save(Wine wine);
    Optional<Wine> findById(UUID id);
    List<Wine> findAll();
    void deleteById(UUID id);

    boolean existsById(UUID id);
}
