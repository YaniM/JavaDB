package bookshopsystemapp.service;


import java.io.IOException;
import java.util.List;

public interface AuthorService {

    void seedAuthors() throws IOException;
    List<String> getAllAuthorsByBooksNumber();
    String authorSearch(String endsWith);
    List<Object> getAllByTotalBookCopies();
}
