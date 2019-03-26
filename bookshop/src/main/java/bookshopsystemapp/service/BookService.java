package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface BookService {
    void seedBooks() throws IOException;

    List<String> getAllBooksTitlesAfter();
    Set<String> getAllAuthorsWithBookBefore();
    List<String> getBooksByAuthor();
    List<String>  getBooksTitlesByAgeRestriction(String ageRestriction);
    String booksByPrice();
    String notReleasedBooks(String year);
    String releasedBeforeDate(String date);
    List<String> goldenBooks();
    List<String> booksSearch(String searchedString);
    List<Book> booksCount(Integer number);
    Map<String,Integer> totalBooksCopies();
    List<Book> findBooksByAuthor(Author author);
    List<String> bookTitlesSearch(String pattern);
}
