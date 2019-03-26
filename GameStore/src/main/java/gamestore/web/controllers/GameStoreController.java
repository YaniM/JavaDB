package gamestore.web.controllers;

import gamestore.domain.dtos.*;
import gamestore.service.GameService;
import gamestore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Controller
public class GameStoreController implements CommandLineRunner {

    private String loggedInUser;

    private final UserService userService;

    private final GameService gameService;

    @Autowired
    public GameStoreController(UserService userService, GameService gameService) {
        this.userService = userService;
        this.gameService = gameService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String inputLine = scanner.nextLine();

            String[] inputParams = inputLine.split("\\|");

            switch (inputParams[0]) {
                case "RegisterUser":
                    UserRegisterDTO userRegisterDTO = new UserRegisterDTO(inputParams[1], inputParams[2], inputParams[3], inputParams[4]);
                    System.out.println(this.userService.registerUser(userRegisterDTO));
                    break;
                case "LoginUser":
                    if (this.loggedInUser == null) {
                        UserLoginDTO userLoginDTO = new UserLoginDTO(inputParams[1], inputParams[2]);

                        String loginResult = this.userService.loginUser(userLoginDTO);

                        if (loginResult.contains("Successfully logged in")) {
                            System.out.println(loginResult);
                            this.loggedInUser = userLoginDTO.getEmail();
                        }


                    } else {
                        System.out.println("Session is taken.");
                    }
                    break;
                case "Logout":
                    if (this.loggedInUser == null) {
                        System.out.println("Cannot log out. No user was logged in.");
                    } else {
                        String logoutResult = this.userService.logoutUser(new UserLogoutDTO(this.loggedInUser));
                        System.out.println(logoutResult);
                        this.loggedInUser = null;
                    }
                    break;
                case "AddGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                            GameAddDTO gameAddDTO = new GameAddDTO(inputParams[1], new BigDecimal(inputParams[2]),
                                    Double.parseDouble(inputParams[3]),
                                    inputParams[4],
                                    inputParams[5],
                                    inputParams[6],
                                    LocalDate.parse(inputParams[7], DateTimeFormatter.ofPattern("dd-MM-yyyy")));
                            System.out.println(this.gameService.AddGame(gameAddDTO));
                    } else {
                        System.out.println("No user was logged in.");
                    }
                    break;
                case "EditGame":
                    if (this.loggedInUser != null && this.userService.isAdmin(this.loggedInUser)) {
                        System.out.println(this.gameService.EditGame(inputParams));
                    } else {
                        System.out.println("No user was logged in.");
                    }
                    break;
                case "DeleteGame":
                    System.out.println(this.gameService.DeleteGame(Long.parseLong(inputParams[1])));

                    break;
                case "AllGame":
                    this.gameService.getAllGames().forEach(s-> System.out.println(s));
                    break;
                case "DetailGame":
                    String game = inputParams[1];
                    System.out.println(this.gameService.getDetailedGame(game));
                    break;
            }
        }
    }
}
