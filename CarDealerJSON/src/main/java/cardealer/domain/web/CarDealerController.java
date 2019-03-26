package cardealer.domain.web;

import cardealer.domain.dtos.*;
import cardealer.domain.entities.Car;
import cardealer.domain.service.*;
import cardealer.util.FileIOUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class CarDealerController implements CommandLineRunner {

    private final static String SUPPLIER_FILE_PATH = "D:\\Java\\CarDealer\\src\\main\\resources\\files\\suppliers.json";
    private final static String CAR_FILE_PATH = "D:\\Java\\CarDealer\\src\\main\\resources\\files\\cars.json";
    private final static String PART_FILE_PATH = "D:\\Java\\CarDealer\\src\\main\\resources\\files\\parts.json";
    private final static String CUSTOMER_FILE_PATH = "D:\\Java\\CarDealer\\src\\main\\resources\\files\\customers.json";
    private final CustomerService customerService;
    private final PartService partService;
    private final SupplierService supplierService;
    private final SaleService saleService;
    private final CarService carService;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;

    @Autowired
    public CarDealerController(CustomerService customerService, PartService partService, SupplierService supplierService, SaleService saleService, CarService carService, FileIOUtil fileIOUtil, Gson gson) {
        this.customerService = customerService;
        this.partService = partService;
        this.supplierService = supplierService;
        this.saleService = saleService;
        this.carService = carService;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
    }

    @Override
    public void run(String... args) throws Exception {
        // this.SeedSuppliers();
        // this.SeedParts();
        //this.SeedCars();
       // this.SeedCustomers();

        // this.Sales();

        // this.OrderedCustomers(); //Query 1 – Ordered Customers
        // this.CarsFromToyota();  //Query 2 – Cars from make Toyota
        // this.findLocalSuppliers();  //Query 3 – Local Suppliers

        // this.CarsWithTheirParts();   //Query 4 – Cars with Their List of Parts

        //   this.TotalSalesByCustomer(); //Query 5 – Total Sales by Customer
    }


    private void SeedSuppliers() throws IOException {
        String supplierFileContent = this.fileIOUtil.readFile(SUPPLIER_FILE_PATH);
        SupplierSeedDTO[] supplierSeedDTOS = this.gson.fromJson(supplierFileContent,SupplierSeedDTO[].class);

        this.supplierService.SeedSuppliers(supplierSeedDTOS);
    }

    private void SeedParts() throws IOException {
        String partFileContent = this.fileIOUtil.readFile(PART_FILE_PATH);
        PartSeedDTO[] partSeedDTOS = this.gson.fromJson(partFileContent,PartSeedDTO[].class);

        this.partService.SeedParts(partSeedDTOS);
    }

    private void SeedCars() throws IOException {
        String carFileContent = this.fileIOUtil.readFile(CAR_FILE_PATH);
        CarSeedDTO[] carSeedDTOS = this.gson.fromJson(carFileContent,CarSeedDTO[].class);

        this.carService.SeedCars(carSeedDTOS);
    }

    private void SeedCustomers() throws IOException {
        String customerFleContent = this.fileIOUtil.readFile(CUSTOMER_FILE_PATH);
        CustomerSeedDTO[] customerSeedDTOS = this.gson.fromJson(customerFleContent,CustomerSeedDTO[].class);

        this.customerService.SeedCustomers(customerSeedDTOS);
    }

    private void  Sales()
    {
        for (int i = 0; i < 20; i++) {
            this.saleService.SaleSomething();
        }
    }

    private void OrderedCustomers()
    {
        List<OrderedCustomersDTO> customersDTOS = this.customerService.customers();
        ;
        String gson = this.gson.toJson(customersDTOS);

        System.out.println(gson);
    }

    private void CarsFromToyota()
    {
        List<CarsByToyotaDTO> cars = this.carService.carsFromToyota();

        String gson = this.gson.toJson(cars);

        System.out.println(gson);
    }

    private void findLocalSuppliers()
    {
        List<LocalSuppliersDTO> suppliersDTOS = this.supplierService.findLocalSuppliers();

        String gson = this.gson.toJson(suppliersDTOS);

        System.out.println(gson);
    }

    private void CarsWithTheirParts()
    {
        List<CarDetailsDTO> carDetailsDTOS = this.carService.carsWithParts();

        String gson = this.gson.toJson(carDetailsDTOS);

        System.out.println(gson);
    }

    private void TotalSalesByCustomer()
    {
        List<SalesByCustomerDTO> salesByCustomerDTOS = this.customerService.totalSalesByCustomer();
        salesByCustomerDTOS.sort(Comparator.comparing(SalesByCustomerDTO::getSpentMoney).thenComparing(SalesByCustomerDTO::getBoughtCars).reversed());

        String gson = this.gson.toJson(salesByCustomerDTOS);

        System.out.println(gson);

    }
}
