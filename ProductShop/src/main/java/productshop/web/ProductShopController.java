package productshop.web;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;
import productshop.domain.dtos.*;
import productshop.service.CategoryService;
import productshop.service.ProductService;
import productshop.service.UserService;
import productshop.util.FileIOUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Controller
public class ProductShopController implements CommandLineRunner {

    private final static String USER_FILE_PATH = "D:\\Java\\ProductShop\\src\\main\\resources\\files\\users.json";
    private final static String CATEGORIES_FILE_PATH = "D:\\Java\\ProductShop\\src\\main\\resources\\files\\categories.json";
    private final static String PRODUCTS_FILE_PATH = "D:\\Java\\ProductShop\\src\\main\\resources\\files\\products.json";
    private final UserService userService;
    private final FileIOUtil fileIOUtil;
    private final Gson gson;
    private final CategoryService categoryService;
    private final ProductService productService;

    @Autowired
    public ProductShopController(UserService userService, FileIOUtil fileIOUtil, Gson gson, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.fileIOUtil = fileIOUtil;
        this.gson = gson;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {
     //  this.seedUsers();
       //this.seedCategories();
        //this.seedProducts();

        // this.findUsersWithBuyer(); //2ra
       //this.ProductsCountByCategory(); //3ta

       // this.UsersAndProducts(); //4ta


    }

    private void seedUsers() throws IOException {
        String usersFileContent = this.fileIOUtil.readFile(USER_FILE_PATH);
        UserSeedDTO[] userSeedDTOS = this.gson.fromJson(usersFileContent,UserSeedDTO[].class);

        this.userService.SeedUsers(userSeedDTOS);
    }

    private void seedCategories() throws IOException {
        String categoryFileContent = this.fileIOUtil.readFile(CATEGORIES_FILE_PATH);
        CategorySeedDTO[] categorySeedDTOS = this.gson.fromJson(categoryFileContent,CategorySeedDTO[].class);

        this.categoryService.seedCategories(categorySeedDTOS);
    }

    private void seedProducts() throws IOException {
        String productsFileContent=this.fileIOUtil.readFile(PRODUCTS_FILE_PATH);
        ProductSeedDTO[] productSeedDTOS = this.gson.fromJson(productsFileContent,ProductSeedDTO[].class);

        this.productService.seedProducts(productSeedDTOS);
    }

    private void ProductsInRange() throws IOException {
        List<ProductInRangeDTO> productInRangeDTOS =  this.productService.productsInRange(BigDecimal.valueOf(500),BigDecimal.valueOf(1000));

        String productsInRangeJson = this.gson.toJson(productInRangeDTOS);

        System.out.println(productsInRangeJson);

       /* File file = new File("D:\\Java\\ProductShop\\src\\main\\resources\\files\\output\\products-in-range.json");
        FileWriter writer = new FileWriter(file);
        writer.write(productsInRangeJson);
        writer.close();*/
    }

    private void ProductsCountByCategory()
    {
        List<CategoryProductsCountDTO> categoryProductsCountDTOS = this.categoryService.findAllCategories();
        Collections.sort(categoryProductsCountDTOS,Comparator.comparing(CategoryProductsCountDTO::getCount).reversed());

        String categories = this.gson.toJson(categoryProductsCountDTOS);

        System.out.println(categories);
    }

    private void findUsersWithBuyer()
    {
        List<UserSoldDTO> soldDTOS = this.userService.findUsersWithBuyer();

        String solds = this.gson.toJson(soldDTOS);

        System.out.println(solds);
    }

    private void UsersAndProducts()
    {
        List<UserCountDTO> userCountDTOS = this.userService.usersAndProducts();

        String counts = this.gson.toJson(userCountDTOS);

        System.out.println(counts);
    }
}
