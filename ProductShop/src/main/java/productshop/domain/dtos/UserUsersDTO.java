package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class UserUsersDTO {
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private Integer age;
    @Expose
    private ProductSoldDTO productSoldDTOS;

    public UserUsersDTO() {
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public ProductSoldDTO getProductSoldDTOS() {
        return productSoldDTOS;
    }

    public void setProductSoldDTOS(ProductSoldDTO productSoldDTOS) {
        this.productSoldDTOS = productSoldDTOS;
    }
}
