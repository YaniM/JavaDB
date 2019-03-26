package gamestore.service;

import gamestore.domain.dtos.GameAddDTO;

import java.util.Set;

public interface GameService {

    String AddGame(GameAddDTO gameAddDTO);

    String EditGame(String[] arguments);

    String DeleteGame(Long id);

    Set<String> getAllGames();

    String getDetailedGame(String title);
}
