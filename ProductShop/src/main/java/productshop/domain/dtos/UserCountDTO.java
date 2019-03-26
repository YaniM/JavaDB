package productshop.domain.dtos;

import com.google.gson.annotations.Expose;

import java.util.List;
import java.util.Set;

public class UserCountDTO {

    @Expose
    private Integer countUsers;
    @Expose
    private Set<UserUsersDTO> userUsersDTOS;

    public UserCountDTO() {
    }

    public Integer getCountUsers() {
        return countUsers;
    }

    public void setCountUsers(Integer countUsers) {
        this.countUsers = countUsers;
    }

    public Set<UserUsersDTO> getUserUsersDTOS() {
        return userUsersDTOS;
    }

    public void setUserUsersDTOS(Set<UserUsersDTO> userUsersDTOS) {
        this.userUsersDTOS = userUsersDTOS;
    }
}
