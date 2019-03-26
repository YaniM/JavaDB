package cardealer.domain.repository;

import cardealer.domain.entities.Customer;
import cardealer.domain.entities.Sale;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SaleRepository extends JpaRepository<Sale,Integer> {

    List<Sale> findSaleByCustomerId(Integer id);


}
