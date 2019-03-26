package cardealer.domain.dtos;

import com.google.gson.annotations.Expose;

public class SalesByCustomerDTO {
    @Expose
    private String fullName;
    @Expose
    private Integer boughtCars;
    @Expose
    private double spentMoney;

    public SalesByCustomerDTO() {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Integer getBoughtCars() {
        return boughtCars;
    }

    public void setBoughtCars(Integer boughtCars) {
        this.boughtCars = boughtCars;
    }

    public double getSpentMoney() {
        return spentMoney;
    }

    public void setSpentMoney(double spentMoney) {
        this.spentMoney = spentMoney;
    }
}

