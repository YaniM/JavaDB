package productshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import productshop.domain.entities.User;

import java.util.List;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM product_shop_db.users u JOIN product_shop_db.products p ON u.id = p.seller_id HAVING p.buyer_id IS NOT NULL",nativeQuery = true)
    Set<User> getUserBy();

    @Query(value = "SELECT * FROM product_shop_db.users u JOIN product_shop_db.products p ON u.id = p.seller_id HAVING p.buyer_id IS NOT NULL",nativeQuery = true)
    Set<User> getUserBy1productSold();

}

//"select u FROM com.kdd.productshop.domain.entities.User u JOIN com.kdd.productshop.domain.entities.Product p ON u.id = p.seller.id")