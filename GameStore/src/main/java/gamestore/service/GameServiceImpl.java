package gamestore.service;

import gamestore.domain.dtos.GameAddDTO;
import gamestore.domain.dtos.GameEditDTO;
import gamestore.domain.dtos.GameSearch;
import gamestore.domain.entities.Game;
import gamestore.repository.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {
    private final GameRepository gameRepository;
    private ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        this.modelMapper = new ModelMapper();
    }


    @Override
    public String AddGame(GameAddDTO gameAddDTO) {
        Game game;
        game = this.modelMapper.map(gameAddDTO,Game.class);

        this.gameRepository.saveAndFlush(game);
        return String.format("Added %s",game.getTitle());
    }

    @Override
    public String EditGame(String[] arguments) {
        StringBuilder sb = new StringBuilder();
        String id = arguments[1];

        Game game = this.gameRepository.findById(Long.parseLong(id)).orElse(null);
        if(game==null)
        {
            return "Game not found!";
        }

        GameEditDTO gameToEdit = modelMapper.map(game,GameEditDTO.class);
        gameToEdit.setId(Long.parseLong(id));

        for (int i = 2; i <arguments.length ; i++) {
            String[] tokens = arguments[i].split("=");
            String valueName = tokens[0];
            String value = tokens[1];
            switch (valueName)
            {
                case "title":
                    return "Cannot edit title!";
                case "price":
                    gameToEdit.setPrice(new BigDecimal(value));
                    break;
                case "size":
                    gameToEdit.setSize(Double.parseDouble(value));
                    break;
                case "trailer":
                    gameToEdit.setTrailer(value);
                    break;
                case "thumbnailUrl":
                    gameToEdit.setImageThumbnail(value);
                    break;
                case "description":
                    gameToEdit.setDescription(value);
                    break;
            }
        }

        Validator validator = Validation.byDefaultProvider().configure().buildValidatorFactory().getValidator();
        Set<ConstraintViolation<GameEditDTO>> violations = validator.validate(gameToEdit);

        if(violations.size()>0)
        {
            for (ConstraintViolation<GameEditDTO> violation : violations) {
                sb.append(violation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString();
        }else
        {
            Game editedGame = this.modelMapper.map(gameToEdit,Game.class);
            if(editedGame!=null)
            {
                Game savedGame  = this.gameRepository.saveAndFlush(editedGame);
                if(savedGame!=null)
                {
                    return String.format("Edited %s",savedGame.getTitle());
                }
            }
        }
        return null;
    }


    @Override
    public String DeleteGame(Long id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if(game==null)
        {
            return "Id is not valid";
        }

        this.gameRepository.deleteById(id);

        return String.format("Deleted %s",game.getTitle());
    }

    @Override
    public Set<String> getAllGames() {
        Set<Game> games = this.gameRepository.getAllByIdIsNotNull();


        return games.stream().map(g->
        {
            GameSearch gameSearch = this.modelMapper.map(g,GameSearch.class);
            return  String.format("%s %.2f",g.getTitle(),g.getPrice());

        }).collect(Collectors.toSet());
    }

    @Override
    public String getDetailedGame(String title) {
        Game game  = this.gameRepository.findByTitle(title);

        return String.format("Title: %s%nPrice: %.2f%nDescription: %s%nRelease date: %s",game.getTitle(),game.getPrice(),game.getDescription(),game.getReleaseDate());
    }

}
