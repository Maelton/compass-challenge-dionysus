package maelton.compass.dionysus.api.v1.service;

import jakarta.transaction.Transactional;

import maelton.compass.dionysus.api.v1.exception.sale.SaleUUIDNotFoundException;
import maelton.compass.dionysus.api.v1.exception.user.UserUUIDNotFoundException;
import maelton.compass.dionysus.api.v1.exception.wine.WineNotAvailableException;
import maelton.compass.dionysus.api.v1.exception.wine.WineUUIDNotFoundException;
import maelton.compass.dionysus.api.v1.model.dto.sale.SaleRequestDTO;
import maelton.compass.dionysus.api.v1.model.dto.sale.SaleResponseDTO;
import maelton.compass.dionysus.api.v1.model.entity.Sale;
import maelton.compass.dionysus.api.v1.model.entity.User;
import maelton.compass.dionysus.api.v1.model.entity.Wine;
import maelton.compass.dionysus.api.v1.model.enums.ProductStatus;
import maelton.compass.dionysus.api.v1.repository.SaleRepository;
import maelton.compass.dionysus.api.v1.repository.UserRepository;
import maelton.compass.dionysus.api.v1.repository.WineRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class SaleService {

    public final SaleRepository repository;
    public final UserRepository userRepository;
    public final WineRepository wineRepository;
    public SaleService(SaleRepository repository, UserRepository userRepository, WineRepository wineRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.wineRepository = wineRepository;
    }

    //CREATE
    @Transactional
    public SaleResponseDTO createSale(SaleRequestDTO saleCreateDTO) {
        User costumer = userRepository.findById(saleCreateDTO.costumerId()).orElseThrow(
                    () -> new UserUUIDNotFoundException(saleCreateDTO.costumerId())
        );
        Wine product = wineRepository.findById(saleCreateDTO.productId()).orElseThrow(
                () -> new WineUUIDNotFoundException(saleCreateDTO.productId())
        );

        if(product.getStatus() != ProductStatus.AVAILABLE)
            throw new WineNotAvailableException(product);

        Sale sale = repository.save(new Sale(costumer, product));
            product.setStatus(ProductStatus.SOLD);
            wineRepository.save(product);
        return saleToResponseDTO(sale);
    }

    //READ ALL
    @Transactional
    public List<SaleResponseDTO> getAllSales() {
        return repository.findAll()
                .stream()
                .map(this::saleToResponseDTO)
                .collect(Collectors.toList());
    }

    //READ BY ID
    @Transactional
    public SaleResponseDTO getSaleById(UUID id) {
        return saleToResponseDTO(
                repository.findById(id).orElseThrow(() -> new SaleUUIDNotFoundException(id))
        );
    }

    //UPDATE
    @Transactional
    public SaleResponseDTO updateSale(UUID id, SaleRequestDTO saleUpdateDTO) {
        Sale sale = repository.findById(id).orElseThrow(() -> new SaleUUIDNotFoundException(id));

        User costumer = userRepository.findById(saleUpdateDTO.costumerId()).orElseThrow(
                () -> new UserUUIDNotFoundException(saleUpdateDTO.costumerId())
        );
        Wine product = wineRepository.findById(saleUpdateDTO.productId()).orElseThrow(
                () -> new WineUUIDNotFoundException(saleUpdateDTO.productId())
        );
        if(product.getStatus() != ProductStatus.AVAILABLE)
            throw new WineNotAvailableException(product);

        sale.setCostumer(costumer);
        sale.setProduct(product);
        sale.setTotalSaleValue(product.getPrice());

        return saleToResponseDTO(repository.save(sale));
    }

    //DELETE
    @Transactional
    public void deleteSale(UUID id) {
        if(!repository.existsById(id))
            throw new SaleUUIDNotFoundException(id);
        repository.deleteById(id);
    }

    public SaleResponseDTO saleToResponseDTO(Sale sale) {
        return new SaleResponseDTO(
                sale.getId(),
                sale.getCostumer().getId(),
                sale.getCostumer().getName(),
                sale.getCostumer().getEmail(),
                sale.getProduct().getId(),
                sale.getProduct().getModel().getName(),
                sale.getProduct().getPrice()
        );
    }
}
