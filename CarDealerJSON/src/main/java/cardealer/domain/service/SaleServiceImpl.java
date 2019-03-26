package cardealer.domain.service;

import cardealer.domain.entities.Car;
import cardealer.domain.entities.Customer;
import cardealer.domain.entities.Sale;
import cardealer.domain.repository.CarRepository;
import cardealer.domain.repository.CustomerRepository;
import cardealer.domain.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SaleServiceImpl implements SaleService {

    private final SaleRepository saleRepository;
    private final CarRepository carRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    public SaleServiceImpl(SaleRepository saleRepository, CarRepository carRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.saleRepository = saleRepository;
        this.carRepository = carRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void SaleSomething() {
        Sale sale = new Sale();
        Car car = this.getRandomCar();
        Customer customer = this.getRandomCustomer();

        List<Integer> discounts = new ArrayList<>();
        discounts.add(0);
        discounts.add(5);
        discounts.add(10);
        discounts.add(15);
        discounts.add(20);
        discounts.add(30);
        discounts.add(40);
        discounts.add(50);

        Random random  = new Random();
        int result = random.nextInt(discounts.size());

        double discountPercentage = discounts.get(result);

        sale.setCar(car);
        sale.setCustomer(customer);

        if(customer.isYoungDriver())
        {
            discountPercentage+=5;
        }

        sale.setDiscountPercentage(discountPercentage);

        this.saleRepository.saveAndFlush(sale);
    }

    @Override
    public Car getRandomCar() {
        Random random = new Random();

        Car car  = new Car();

        int result = random.nextInt((int) (this.carRepository.count()-1))+1;
        car = carRepository.findCarById(result);

        return car;
    }

    @Override
    public Customer getRandomCustomer() {
        Random random = new Random();

        Customer customer = new Customer();

        int result = random.nextInt((int) (this.customerRepository.count()-1))+1;

        customer=this.customerRepository.findCustomerById(result);

        return customer;
    }


}
