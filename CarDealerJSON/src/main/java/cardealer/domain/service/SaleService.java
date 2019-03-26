package cardealer.domain.service;

import cardealer.domain.entities.Car;
import cardealer.domain.entities.Customer;

public interface SaleService {

    void SaleSomething();

    Car getRandomCar();

    Customer getRandomCustomer();

}
