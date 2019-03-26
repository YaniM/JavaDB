package cardealer.domain.service;

import cardealer.domain.dtos.CustomerSeedDTO;
import cardealer.domain.dtos.OrderedCustomersDTO;
import cardealer.domain.dtos.SalesByCustomerDTO;
import cardealer.domain.entities.Customer;
import cardealer.domain.repository.CustomerRepository;
import cardealer.domain.repository.PartRepository;
import cardealer.domain.repository.SaleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final PartRepository partRepository;
    private final SaleRepository saleRepository;
    private final CustomerRepository customerRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CustomerServiceImpl(PartRepository partRepository, SaleRepository saleRepository, CustomerRepository customerRepository, ModelMapper modelMapper) {
        this.partRepository = partRepository;
        this.saleRepository = saleRepository;
        this.customerRepository = customerRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void SeedCustomers(CustomerSeedDTO[] customerSeedDTOS) {
        for (CustomerSeedDTO customerSeedDTO : customerSeedDTOS) {
            Customer entity = this.modelMapper.map(customerSeedDTO,Customer.class);
            this.customerRepository.saveAndFlush(entity);
        }

    }

    @Override
    public List<OrderedCustomersDTO> customers() {
        Set<Customer> customers = this.customerRepository.getAllByIdIsNotNullOrderByBirthDate();

        List<OrderedCustomersDTO> orderedCustomersDTOS = new ArrayList<>();
        for (Customer customer : customers) {
            OrderedCustomersDTO orderedCustomersDTO = this.modelMapper.map(customer,OrderedCustomersDTO.class);
            orderedCustomersDTOS.add(orderedCustomersDTO);
        }

        return orderedCustomersDTOS;
    }

    @Override
    public List<SalesByCustomerDTO> totalSalesByCustomer() {
        Set<Customer> customers = this.customerRepository.totalSalesByCustomer();

        List<SalesByCustomerDTO> salesByCustomerDTOS = new ArrayList<>();

        for (Customer customer : customers) {
                SalesByCustomerDTO customerDTO = this.modelMapper.map(customer,SalesByCustomerDTO.class);
                customerDTO.setFullName(customer.getName());
                customerDTO.setBoughtCars(this.saleRepository.findSaleByCustomerId(customer.getId()).size());
                customerDTO.setSpentMoney(this.partRepository.findBySupplier(customer.getId()));
                salesByCustomerDTOS.add(customerDTO);
        }
        return salesByCustomerDTOS;
    }
}
