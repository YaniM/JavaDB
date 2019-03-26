package bookshopsystemapp.repository;

import bookshopsystemapp.domain.entities.AgeRestriction;
import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.domain.entities.EditionType;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.print.attribute.standard.Copies;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Repository
public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findAllByReleaseDateAfter(LocalDate date);

    List<Book> findAllByReleaseDateBefore(LocalDate date);

    List<Book> findAllByAuthorIsOrderByReleaseDateDescTitleAsc(Optional<Author> author);

    List<Book> findAllByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByPriceLessThanOrPriceGreaterThan(BigDecimal less,BigDecimal greater);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before,LocalDate after);

    List<Book> findAllByCopiesLessThanAndEditionType(Integer copies, EditionType editionType);

    List<Book> findAllByTitleContaining(String searchedString);

    @Query("SELECT b FROM bookshopsystemapp.domain.entities.Book AS b WHERE LENGTH(b.title) >=:number")
    List<Book> findAllByTitle(@Param("number") Integer number);

    List<Book> findAllByAuthor(Author author);

    @Query(value = "SELECT * FROM book_shop_db.books AS b JOIN book_shop_db.authors AS a ON b.author_id = a.id HAVING lower(a.last_name) LIKE :pattern",nativeQuery = true)
    List<Book> getAllByAuthorStartsWith(@Param("pattern")String pattern);
}
