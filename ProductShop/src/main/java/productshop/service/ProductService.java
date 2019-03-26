package productshop.service;

import productshop.domain.dtos.ProductInRangeDTO;
import productshop.domain.dtos.ProductSeedDTO;
import productshop.domain.entities.Product;
import productshop.domain.entities.User;

import java.math.BigDecimal;
import java.util.List;

public interface ProductService {
    void seedProducts(ProductSeedDTO[] productSeedDTOS);

    List<ProductInRangeDTO> productsInRange(BigDecimal more,BigDecimal less);

}
