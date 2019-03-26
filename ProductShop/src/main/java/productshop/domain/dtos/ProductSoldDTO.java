package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class ProductSoldDTO {

    @Expose
    private Integer count;
    @Expose
    private Set<ProductNamePriceDTO> productNamePriceDTOS;

    public ProductSoldDTO() {
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Set<ProductNamePriceDTO> getProductNamePriceDTOS() {
        return productNamePriceDTOS;
    }

    public void setProductNamePriceDTOS(Set<ProductNamePriceDTO> productNamePriceDTOS) {
        this.productNamePriceDTOS = productNamePriceDTOS;
    }
}
