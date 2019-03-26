package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AutoServiceImpl implements AuthorService {

    private final static String AUTHORS_FILE_PATH = "D:\\Java\\bookshop\\src\\main\\resources\\files\\authors.txt";

    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;

    @Autowired
    public AutoServiceImpl(AuthorRepository authorRepository, FileUtil fileUtil) {
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
    }

    @Override
    public void seedAuthors() throws IOException {
        if(this.authorRepository.count() !=0)
        {
            return;
        }

        String[] authorFileContent = this.fileUtil.getFileContent(AUTHORS_FILE_PATH);
        for (String line : authorFileContent) {
            String[] names = line.split("\\s+");

            Author author  = new Author();
            author.setFirstName(names[0]);
            author.setLastName(names[1]);

            this.authorRepository.saveAndFlush(author);
        }
     }

    @Override
    public List<String> getAllAuthorsByBooksNumber() {
        List<Author> authors=this.authorRepository.findAllByOrderByBooksDesc();
        return authors.stream().map(a->String.format("%s %s - %d",a.getFirstName(),a.getLastName(),a.getBooks().size())).collect(Collectors.toList());
    }

    @Override
    public String authorSearch(String endsWith) {
        String wildCard = "%" + endsWith;

        List<Author> authors = this.authorRepository.authorsFirstNameEndsWith(wildCard);

        List<String> result = authors.stream().map(b->String.format("%s %s",b.getFirstName(),b.getLastName())).collect(Collectors.toList());

        return String.join(System.lineSeparator(),result);
    }

    @Override
    public List<Object> getAllByTotalBookCopies() {
        List<Object> objects = this.authorRepository.getAllByTotalBookCopies();
        return objects;
    }

}
