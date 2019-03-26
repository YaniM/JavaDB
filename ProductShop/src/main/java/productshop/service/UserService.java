package productshop.service;

import productshop.domain.dtos.UserCountDTO;
import productshop.domain.dtos.UserSeedDTO;
import productshop.domain.dtos.UserSoldDTO;

import java.util.List;

public interface UserService {

    void SeedUsers(UserSeedDTO[] userSeedDTOS);

    List<UserSoldDTO> findUsersWithBuyer();

    List<UserCountDTO> usersAndProducts();
}
