package cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class PartSeedDTO {
    @Expose
    private String name;
    @Expose
    private Double price;
    @Expose
    private Integer quantity;

    public PartSeedDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
