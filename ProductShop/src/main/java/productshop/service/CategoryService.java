package productshop.service;

import productshop.domain.dtos.CategoryProductsCountDTO;
import productshop.domain.dtos.CategorySeedDTO;

import java.util.List;

public interface CategoryService {

    void seedCategories(CategorySeedDTO[] categorySeedDTOS);

    List<CategoryProductsCountDTO> findAllCategories();
}
