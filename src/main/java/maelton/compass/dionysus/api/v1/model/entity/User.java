package maelton.compass.dionysus.api.v1.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import maelton.compass.dionysus.api.v1.model.enums.UserRole;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;

import java.util.UUID;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity
@Table(
        name = "tab_user",
        uniqueConstraints = {
                @UniqueConstraint(name="tab_user_un_email", columnNames = "email")
        }
)
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Setter
    private String name;

    @Setter
    private LocalDate birthDate;

    @Setter
    private String email;

    @Setter
    private String password;

    @Setter
    private UserRole role;

    @JsonIgnore
    @OneToMany(mappedBy = "costumer", fetch = FetchType.LAZY)
    Set<Sale> purchases = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.getRole()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    public User(String name, LocalDate birthDate, String email, String password, UserRole role) {
        this.name = name;
        this.birthDate = birthDate;
        this.email = email;
        this.password = password;
        this.role = role;
    }
}
