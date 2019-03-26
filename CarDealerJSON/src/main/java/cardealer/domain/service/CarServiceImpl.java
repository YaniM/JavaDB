package cardealer.domain.service;

import cardealer.domain.dtos.CarDetailsDTO;
import cardealer.domain.dtos.CarSeedDTO;
import cardealer.domain.dtos.CarsByToyotaDTO;
import cardealer.domain.dtos.PartsDetailsDTO;
import cardealer.domain.entities.Car;
import cardealer.domain.entities.Part;
import cardealer.domain.repository.CarRepository;
import cardealer.domain.repository.PartRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

@Service
public class CarServiceImpl implements CarService {

    private final PartRepository partRepository;
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    public CarServiceImpl(PartRepository partRepository, CarRepository carRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void SeedCars(CarSeedDTO[] carSeedDTOS) {
        for (CarSeedDTO carSeedDTO : carSeedDTOS) {
            Car entity = this.modelMapper.map(carSeedDTO,Car.class);
            entity.setParts(this.getRandomParts());
            this.carRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<CarsByToyotaDTO> carsFromToyota() {
        List<Car> cars = this.carRepository.findByMakeOrderByModelAsc("Toyota");

        List<CarsByToyotaDTO> carsByToyotaDTOS = new ArrayList<>();

        for (Car car : cars) {
            CarsByToyotaDTO carsByToyotaDTO = this.modelMapper.map(car,CarsByToyotaDTO.class);
            carsByToyotaDTOS.add(carsByToyotaDTO);
        }
        carsByToyotaDTOS.sort(Comparator.comparing(CarsByToyotaDTO::getModel));
        carsByToyotaDTOS.sort(Comparator.comparing(CarsByToyotaDTO::getTravelledDistance).reversed());
        return carsByToyotaDTOS;
    }

    @Override
    public List<CarDetailsDTO> carsWithParts() {
       List<Car> cars = this.carRepository.findCarsByIdIsNotNull();

       List<CarDetailsDTO> carDetailsDTOS = new ArrayList<>();

        for (Car car : cars) {
            CarDetailsDTO carDetailsDTO = this.modelMapper.map(car,CarDetailsDTO.class);

            List<Part> parts = this.partRepository.findPartByCars(car);

            List<PartsDetailsDTO> partsDTO = new ArrayList<>();

            for (Part part : parts) {
                PartsDetailsDTO partsDetailsDTO = this.modelMapper.map(part,PartsDetailsDTO.class);
                partsDTO.add(partsDetailsDTO);
            }
            carDetailsDTO.setPartsDetails(partsDTO);
            carDetailsDTOS.add(carDetailsDTO);
        }

        System.out.println();
        return carDetailsDTOS;
    }

    private List<Part> getRandomParts()
    {
        Random random = new Random();
        List<Part> parts = new ArrayList<>();


        int result = random.nextInt((int) (10)+10);

        for (int i = 0; i < result; i++) {
            parts.add(this.partRepository.getOne(random.nextInt((int) (this.partRepository.count()-1))+1));
        }

        return parts;
    }
}
