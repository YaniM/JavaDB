package cardealer.domain.service;

import cardealer.domain.dtos.CustomerSeedDTO;
import cardealer.domain.dtos.OrderedCustomersDTO;
import cardealer.domain.dtos.SalesByCustomerDTO;

import java.util.List;
import java.util.Set;

public interface CustomerService {
    void SeedCustomers(CustomerSeedDTO[] customerSeedDTOS);

    List<OrderedCustomersDTO> customers();

    List<SalesByCustomerDTO> totalSalesByCustomer();
}
