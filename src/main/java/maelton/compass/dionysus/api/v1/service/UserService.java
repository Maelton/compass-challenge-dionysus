package maelton.compass.dionysus.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.compass.dionysus.api.v1.exception.user.UserEmailNotFoundException;
import maelton.compass.dionysus.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.compass.dionysus.api.v1.model.dto.user.UserRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.user.UserResponseDTO;
import maelton.compass.dionysus.api.v1.model.entity.User;
import maelton.compass.dionysus.api.v1.repository.UserRepository;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    public final UserRepository repository;
    public final PasswordEncoder passwordEncoder;
    public UserService(UserRepository repository) {
        this.repository = repository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    //CREATE
    @Transactional
    public UserResponseDTO createUser(UserRequestDTO userCreateDTO) {
        User user = new User(
                userCreateDTO.name(),
                userCreateDTO.birthDate(),
                userCreateDTO.email(),
                passwordEncoder.encode(userCreateDTO.password()),
                userCreateDTO.role()
        );

        return userToResponseDTO(repository.save(user));
    }

    //READ ALL
    @Transactional
    public List<UserResponseDTO> getAllUsers() {
        return repository.findAll()
                .stream()
                .map(this::userToResponseDTO)
                .collect(Collectors.toList());
    }

    //READ BY ID
    @Transactional
    public UserResponseDTO getUserById(UUID id) {
        return userToResponseDTO(
                repository.findById(id).orElseThrow(() -> new UserUUIDNotFoundException(id))
        );
    }

    //READ BY EMAIL
    @Transactional
    public UserResponseDTO getUserById(String email) {
        return userToResponseDTO(
                repository.findByEmail(email).orElseThrow(() -> new UserEmailNotFoundException(email))
        );
    }

    //UPDATE
    @Transactional
    public UserResponseDTO updatedUser(UUID id, UserRequestDTO userUpdateDTO) {
        User user = repository.findById(id).orElseThrow(() -> new UserUUIDNotFoundException(id));

        user.setName(userUpdateDTO.name());
        user.setBirthDate(userUpdateDTO.birthDate());
        user.setEmail(userUpdateDTO.email());
        user.setPassword(passwordEncoder.encode(userUpdateDTO.password()));
        user.setRole(userUpdateDTO.role());

        return userToResponseDTO(repository.save(user));
    }

    //DELETE
    @Transactional
    public void deleteUser(UUID id) {
        if(!repository.existsById(id))
            throw new UserUUIDNotFoundException(id);
        repository.deleteById(id);
    }

    public UserResponseDTO userToResponseDTO(User user) {
        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getBirthDate(),
                user.getEmail(),
                user.getRole()
        );
    }
}
