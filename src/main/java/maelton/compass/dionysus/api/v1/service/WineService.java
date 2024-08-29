package maelton.compass.dionysus.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.compass.dionysus.api.v1.exception.wine.WineUUIDNotFoundException;
import maelton.compass.dionysus.api.v1.exception.wine_model.WineModelIdNotFoundException;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine.WineResponseDTO;
import maelton.compass.dionysus.api.v1.model.entity.Wine;
import maelton.compass.dionysus.api.v1.model.entity.WineModel;
import maelton.compass.dionysus.api.v1.repository.WineModelRepository;
import maelton.compass.dionysus.api.v1.repository.WineRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class WineService {
    public final WineRepository repository;
    public final WineModelRepository wineModelRepository;
    public WineService(WineRepository wineRepository, WineModelRepository wineModelRepository) {
        this.repository = wineRepository;
        this.wineModelRepository = wineModelRepository;
    }

    //CREATE
    @Transactional
    public WineResponseDTO createWine(WineRequestDTO wineCreateDTO) {
        WineModel wineModel = wineModelRepository.findById(wineCreateDTO.modelId()).orElseThrow(
                    () -> new WineModelIdNotFoundException(wineCreateDTO.modelId())
        );
        Wine wine = new Wine(wineModel, wineCreateDTO.price(), wineCreateDTO.status());

        return wineToResponseDTO(repository.save(wine));
    }

    //READ ALL
    @Transactional
    public List<WineResponseDTO> getAllWines() {
        return repository.findAll()
                .stream()
                .map(this::wineToResponseDTO)
                .collect(Collectors.toList());
    }

    //READ BY ID
    @Transactional
    public WineResponseDTO getWineById(UUID id) {
        return wineToResponseDTO(
                repository.findById(id).orElseThrow(() -> new WineUUIDNotFoundException(id))
        );
    }

    //UPDATE
    @Transactional
    public WineResponseDTO updatedWine(UUID id, WineRequestDTO wineUpdateDTO) {
        Wine wine = repository.findById(id).orElseThrow(() -> new WineUUIDNotFoundException(id));

        WineModel wineModel = wineModelRepository.findById(wineUpdateDTO.modelId()).orElseThrow(
                () -> new WineModelIdNotFoundException(wineUpdateDTO.modelId())
        );

        wine.setModel(wineModel);
        wine.setPrice(wineUpdateDTO.price());
        wine.setStatus(wineUpdateDTO.status());

        return wineToResponseDTO(repository.save(wine));
    }

    //DELETE
    @Transactional
    public void deleteWine(UUID id) {
        if(!repository.existsById(id))
            throw new WineUUIDNotFoundException(id);
        repository.deleteById(id);
    }

    public WineResponseDTO wineToResponseDTO(Wine wine) {
        return new WineResponseDTO(
                wine.getId(),
                wine.getModel().getId(),
                wine.getPrice(),
                wine.getInsertionDateTime(),
                wine.getStatus()
        );
    }
}
