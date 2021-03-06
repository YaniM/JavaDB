package gamestore.service;

import gamestore.domain.dtos.GameAddDTO;
import gamestore.domain.dtos.UserLoginDTO;
import gamestore.domain.dtos.UserLogoutDTO;
import gamestore.domain.dtos.UserRegisterDTO;

public interface UserService {

    String registerUser(UserRegisterDTO userRegisterDTO);
    String loginUser(UserLoginDTO userLoginDTO);

    String logoutUser(UserLogoutDTO userLogoutDTO);

    boolean isAdmin(String email);

    String addGame(GameAddDTO gameAddDTO);
}
