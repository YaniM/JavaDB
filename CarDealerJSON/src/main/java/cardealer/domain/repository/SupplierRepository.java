package cardealer.domain.repository;

import cardealer.domain.entities.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier,Integer> {

    @Query(value = "SELECT * FROM car_dealer_db.suppliers u JOIN car_dealer_db.parts p ON u.id=p.supplier_id having u.is_imported IS FALSE ",nativeQuery = true)
    Set<Supplier> findLocalSuppliers();
}
