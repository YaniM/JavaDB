package gamestore.repository;


import gamestore.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GameRepository extends JpaRepository<Game,Long> {


    Game findById(Integer id);
    Game findByTitle(String title);
    void deleteById(Long id);
    Set<Game> getAllByIdIsNotNull();
}
