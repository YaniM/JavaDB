package productshop.domain.dtos;

import com.google.gson.annotations.Expose;
import productshop.domain.entities.Product;

import java.util.List;

public class UserSoldDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private List<ProductDetailsDTO> productDetailsDTOS;

    public UserSoldDTO() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<ProductDetailsDTO> getProductDetailsDTOS() {
        return productDetailsDTOS;
    }

    public void setProductDetailsDTOS(List<ProductDetailsDTO> productDetailsDTOS) {
        this.productDetailsDTOS = productDetailsDTOS;
    }
}
