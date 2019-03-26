package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    @Query(value = "SELECT a From bookshopsystemapp.domain.entities.Author as a ORDER BY size(a.books) DESC")
    List<Author> findAllByOrderByBooksDesc();

    @Query("SELECT a FROM bookshopsystemapp.domain.entities.Author AS a WHERE a.firstName LIKE :wildCard")
    List<Author> authorsFirstNameEndsWith(@Param("wildCard") String wildCard);

    @Query("SELECT CONCAT(a.firstName, ' ', a.lastName, ' ', SUM(b.copies)) FROM bookshopsystemapp.domain.entities.Author AS a " +
            "JOIN bookshopsystemapp.domain.entities.Book AS b ON b.author = a " +
            "GROUP BY a.id ORDER BY SUM(b.copies) DESC")
    List<Object> getAllByTotalBookCopies();
}
