package maelton.compass.dionysus.api.v1.repository;

import maelton.compass.dionysus.api.v1.model.entity.WineModel;
import maelton.compass.dionysus.api.v1.model.enums.WineType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WineModelRepository extends JpaRepository<WineModel, Long> {
    WineModel save(WineModel wineModel);
    Optional<WineModel> findById(Long id);
    List<WineModel> findAll();
    void deleteById(Long id);

    boolean existsById(Long id);
    boolean existsByBrandAndNameAndVolumeAndAbvAndType(String brand, String name, double volume, double abv, WineType type);
}
