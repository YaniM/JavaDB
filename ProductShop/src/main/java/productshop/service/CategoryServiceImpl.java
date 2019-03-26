package productshop.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import productshop.domain.dtos.CategoryProductsCountDTO;
import productshop.domain.dtos.CategorySeedDTO;
import productshop.domain.entities.Category;
import productshop.domain.entities.Product;
import productshop.repository.CategoryRepository;
import productshop.util.ValidatorUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategories(CategorySeedDTO[] categorySeedDTOS) {
        for (CategorySeedDTO categorySeedDTO : categorySeedDTOS) {
                if(!this.validatorUtil.isValid(categorySeedDTO))
                {
                    this.validatorUtil.violations(categorySeedDTO).forEach(v-> System.out.println(v.getMessage()));
                    continue;
                }

            Category entity = this.modelMapper.map(categorySeedDTO,Category.class);

                this.categoryRepository.saveAndFlush(entity);
        }
    }

    @Override
    public List<CategoryProductsCountDTO> findAllCategories() {
        Set<Category> categories= this.categoryRepository.getCategoryByIdNotNull();

        List<CategoryProductsCountDTO> categoryProductsCountDTOS = new ArrayList<>();

        for (Category category : categories) {

            List<Product> products=category.getProducts();
            int countProducts = products.size();

            CategoryProductsCountDTO categoryProductsCountDTO = this.modelMapper.map(category,CategoryProductsCountDTO.class);


            categoryProductsCountDTO.setCount(countProducts);

            BigDecimal sum = BigDecimal.valueOf(0.0);

            for (Product product : products) {
                sum= product.getPrice().add(sum);
            }

            if(sum.signum()>0)
            {
                categoryProductsCountDTO.setPrice(sum.divide(BigDecimal.valueOf(countProducts),BigDecimal.ROUND_HALF_UP));
            }else
            {
                categoryProductsCountDTO.setPrice(new BigDecimal(0.0));
            }

            categoryProductsCountDTO.setTotalRevenue(sum);
            categoryProductsCountDTOS.add(categoryProductsCountDTO);
        }

        System.out.println();
        return categoryProductsCountDTOS;
    }
}
