package maelton.compass.dionysus.api.v1.repository;

import maelton.compass.dionysus.api.v1.model.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User save(User user);
    Optional<User> findById(UUID id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    void deleteById(UUID id);

    boolean existsById(UUID id);
    boolean existsByEmail(String email);
}
