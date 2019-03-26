package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.math.BigDecimal;

public class ProductDetailsDTO {
    @Expose
    private String name;
    @Expose
    private BigDecimal price;
    @Expose
    private String bFirstName;
    @Expose
    private String bLastName;

    public ProductDetailsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getbFirstName() {
        return bFirstName;
    }

    public void setbFirstName(String bFirstName) {
        this.bFirstName = bFirstName;
    }

    public String getbLastName() {
        return bLastName;
    }

    public void setbLastName(String bLastName) {
        this.bLastName = bLastName;
    }
}
