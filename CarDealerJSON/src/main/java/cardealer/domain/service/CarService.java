package cardealer.domain.service;

import cardealer.domain.dtos.CarDetailsDTO;
import cardealer.domain.dtos.CarSeedDTO;
import cardealer.domain.dtos.CarsByToyotaDTO;
import cardealer.domain.entities.Car;

import java.util.List;

public interface CarService {
    void SeedCars(CarSeedDTO[] carSeedDTOS);

    List<CarsByToyotaDTO> carsFromToyota();

    List<CarDetailsDTO> carsWithParts();
}
