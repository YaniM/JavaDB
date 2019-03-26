package cardealer.domain.repository;

import cardealer.domain.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends JpaRepository<Car,Integer> {

    Car findCarById(Integer id);

    List<Car> findByMakeOrderByModelAsc(String make);

    List<Car> findCarsByIdIsNotNull();
}
