package productshop.domain.dtos;

import com.google.gson.annotations.Expose;
import productshop.domain.entities.Category;
import productshop.domain.entities.Product;
import productshop.service.CategoryService;
import productshop.service.ProductService;

import java.math.BigDecimal;
import java.util.List;

public class CategoryProductsCountDTO {

    @Expose
    private String name;
    @Expose
    private Integer count;
    @Expose
    private BigDecimal price;
    @Expose
    private BigDecimal totalRevenue;

    public CategoryProductsCountDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
      return   count;
    }

    public void setCount(Integer count) {
        this.count=count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }
}
