package cardealer.domain.repository;

import cardealer.domain.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface CustomerRepository extends JpaRepository<Customer,Integer> {

    Customer findCustomerById(Integer id);

    Set<Customer> getAllByIdIsNotNullOrderByBirthDate();

    @Query(value = "SELECT * FROM car_dealer_db.customers c JOIN car_dealer_db.sales s ON c.id=s.customer_id JOIN car_dealer_db.parts p ON c.id = p.supplier_id",nativeQuery = true)
    Set<Customer> totalSalesByCustomer();


}
