package cardealer.domain.repository;

import cardealer.domain.entities.Car;
import cardealer.domain.entities.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part,Integer> {

    List<Part> findPartBySupplierId(Integer id);

    List<Part> findPartByCars(Car car);

    @Query(value = "SELECT sum(p.price) as price FROM parts p where p.supplier_id  = :id",nativeQuery = true)
    Double findBySupplier(@Param("id") Integer id);
}
