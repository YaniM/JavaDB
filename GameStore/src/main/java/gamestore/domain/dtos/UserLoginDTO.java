package gamestore.domain.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UserLoginDTO {

    private String email;
    private String password;

    public UserLoginDTO() {
    }

    public UserLoginDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    @NotNull @NotNull(message = "Email cannot be null.")
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+\\.[a-z]{2,4}",message = "Invalid Email.")
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull @NotNull(message = "Password cannot be null.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?!.*[&%$]).{6,}$",message = "Password must contain at least 1 uppercase, 1 lowercase letter and 1 digit.")
    @Size(min = 6,message = "Password must be at least 6 symbols long.")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
