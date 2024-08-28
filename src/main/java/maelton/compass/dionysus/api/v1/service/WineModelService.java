package maelton.compass.dionysus.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.compass.dionysus.api.v1.exception.wine_model.WineModelAlreadyExistsException;
import maelton.compass.dionysus.api.v1.exception.wine_model.WineModelIdNotFoundException;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.wine_model.WineModelResponseDTO;
import maelton.compass.dionysus.api.v1.model.entity.WineModel;
import maelton.compass.dionysus.api.v1.repository.WineModelRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WineModelService {
    private final WineModelRepository repository;
    public WineModelService(WineModelRepository wineModelRepository) {
        this.repository = wineModelRepository;
    }

    //CREATE
    @Transactional
    public WineModelResponseDTO createWineModel(WineModelRequestDTO wineModelCreateDTO) {
        if(repository.existsByBrandAndNameAndVolumeAndAbvAndType(
                wineModelCreateDTO.brand(),
                wineModelCreateDTO.name(),
                wineModelCreateDTO.volume(),
                wineModelCreateDTO.abv(),
                wineModelCreateDTO.type())
        ) {
            throw new WineModelAlreadyExistsException();
        }

        WineModel wineModel = new WineModel(
                wineModelCreateDTO.brand(),
                wineModelCreateDTO.name(),
                wineModelCreateDTO.volume(),
                wineModelCreateDTO.abv(),
                wineModelCreateDTO.type(),
                wineModelCreateDTO.recommendedPrice()
        );

        return wineModelToResponseDTO(repository.save(wineModel));
    }

    //READ ALL
    @Transactional
    public List<WineModelResponseDTO> getAllWineModels() {
        return repository.findAll()
                         .stream()
                         .map(this::wineModelToResponseDTO)
                         .collect(Collectors.toList());
    }

    //READ BY ID
    @Transactional
    public WineModelResponseDTO getWineModelById(Long id) {
        return wineModelToResponseDTO(
                repository.findById(id).orElseThrow(() -> new WineModelIdNotFoundException(id))
        );
    }

    //UPDATE
    @Transactional
    public WineModelResponseDTO updatedWineModel(Long id, WineModelRequestDTO wineModelUpdateDTO) {
        WineModel wineModel = repository.findById(id).orElseThrow(() -> new WineModelIdNotFoundException(id));

        if(repository.existsByBrandAndNameAndVolumeAndAbvAndType(
                wineModelUpdateDTO.brand(),
                wineModelUpdateDTO.name(),
                wineModelUpdateDTO.volume(),
                wineModelUpdateDTO.abv(),
                wineModelUpdateDTO.type())
        ) {
            throw new WineModelAlreadyExistsException();
        }

            wineModel.setBrand(wineModelUpdateDTO.brand());
            wineModel.setName(wineModelUpdateDTO.name());
            wineModel.setVolume(wineModelUpdateDTO.volume());
            wineModel.setAbv(wineModelUpdateDTO.abv());
            wineModel.setType(wineModelUpdateDTO.type());
            wineModel.setRecommendedPrice(wineModelUpdateDTO.recommendedPrice());

        return wineModelToResponseDTO(repository.save(wineModel));
    }

    //DELETE
    @Transactional
    public void deleteWineModel(Long id) {
        if(!repository.existsById(id))
            throw new WineModelIdNotFoundException(id);
        repository.deleteById(id);
    }

    public WineModelResponseDTO wineModelToResponseDTO(WineModel wineModel) {
        return new WineModelResponseDTO(
                wineModel.getId(),
                wineModel.getBrand(),
                wineModel.getName(),
                wineModel.getVolume(),
                wineModel.getAbv(),
                wineModel.getType(),
                wineModel.getRecommendedPrice()
        );
    }
}
