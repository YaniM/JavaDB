package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ProductSeedDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;

    public ProductSeedDTO() {
    }

    @NotNull(message = "Name cannot be null.")
    @Size(min = 3,message = "Name must be at least 3 symbols long.")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Price cannot be null.")
    @Positive(message = "Price must be possitive number.")
    @DecimalMin("0.01")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
