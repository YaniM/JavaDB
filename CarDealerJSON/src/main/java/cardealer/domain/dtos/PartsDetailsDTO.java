package cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class PartsDetailsDTO {
    @Expose
    private String name;
    @Expose
    private double price;

    public PartsDetailsDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
