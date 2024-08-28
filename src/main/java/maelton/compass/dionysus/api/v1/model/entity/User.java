package maelton.compass.dionysus.api.v1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;

import maelton.compass.dionysus.api.v1.model.enums.UserRole;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Table(name = "tab_user")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Date birthDate;
    private String email;
    private String password;
    private UserRole role;

    @JsonIgnore
    @OneToMany(mappedBy = "costumer", fetch = FetchType.LAZY)
    Set<Sale> purchases = new HashSet<>();
}
