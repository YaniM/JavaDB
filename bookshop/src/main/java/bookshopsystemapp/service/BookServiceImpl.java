package bookshopsystemapp.service;

import bookshopsystemapp.domain.entities.*;
import bookshopsystemapp.repository.AuthorRepository;
import bookshopsystemapp.repository.BookRepository;
import bookshopsystemapp.repository.CategoryRepository;
import bookshopsystemapp.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    private final static String BOOKS_FILE_PATH = "D:\\Java\\bookshop\\src\\main\\resources\\files\\books.txt";
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final FileUtil fileUtil;
    private final CategoryRepository categoryRepository;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, AuthorRepository authorRepository, FileUtil fileUtil, CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.fileUtil = fileUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void seedBooks() throws IOException {
        if(this.bookRepository.count() != 0)
        {
            return;
        }
        String[] booksFileContent = this.fileUtil.getFileContent(BOOKS_FILE_PATH);

        for (String line : booksFileContent) {
                String[] lineParams = line.split("\\s+");

            Book book = new Book();
            book.setAuthor(this.getRandomAuthor());

            EditionType editionType = EditionType.values()[Integer.parseInt(lineParams[0])];
            book.setEditionType(editionType);

            LocalDate releaseDate = LocalDate.parse(lineParams[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
            book.setReleaseDate(releaseDate);

            int copies = Integer.parseInt(lineParams[2]);
            book.setCopies(copies);

            BigDecimal price = new BigDecimal(lineParams[3]);
            book.setPrice(price);

            AgeRestriction ageRestriction = AgeRestriction.values()[Integer.parseInt(lineParams[4])];
            book.setAgeRestriction(ageRestriction);

            StringBuilder title = new StringBuilder();
            for (int i = 5; i <lineParams.length ; i++) {
                title.append(lineParams[i]).append(" ");
            }

            book.setTitle(title.toString().trim());

            Set<Category> categories = this.getRandomCategories();

            book.setCategories(categories);

            this.bookRepository.saveAndFlush(book);

        }
    }

    @Override
    public List<String> getAllBooksTitlesAfter() {
        List<Book> books =this.bookRepository.findAllByReleaseDateAfter(LocalDate.parse("2000-12-31"));
        return books.stream().map(b->b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public Set<String> getAllAuthorsWithBookBefore() {
        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(LocalDate.parse("1990-01-01"));
        return books.stream().map(b->String.format("%s %s",b.getAuthor().getFirstName(),b.getAuthor().getLastName())).collect(Collectors.toSet());
    }


    @Override
    public List<String> getBooksByAuthor() {
        Optional<Author> author = this.authorRepository.findById(4);
        List<Book> books = this.bookRepository.findAllByAuthorIsOrderByReleaseDateDescTitleAsc(author);
        return books.stream().map(b->String.format("%s %s %d",b.getTitle(),b.getReleaseDate(),b.getCopies())).collect(Collectors.toList());
    }

    @Override
    public List<String> getBooksTitlesByAgeRestriction(String ageRestriction) {
        AgeRestriction ageRestriction1 = AgeRestriction.valueOf(ageRestriction.toUpperCase());

        List<Book> books = this.bookRepository.findAllByAgeRestriction(ageRestriction1);

        return books.stream().map(b->b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public String booksByPrice() {
        List<Book> books = this.bookRepository.findAllByPriceLessThanOrPriceGreaterThan(BigDecimal.valueOf(4),BigDecimal.valueOf(40));

        return String.join(System.lineSeparator(),books.stream().map(b->String.format("%s - $%.2f",b.getTitle(),b.getPrice())).collect(Collectors.toList()));
    }

    @Override
    public String notReleasedBooks(String year) {
        LocalDate before = LocalDate.parse(year + "-01-01");
        LocalDate after = LocalDate.parse(year + "-12-31");

        List<Book> books = this.bookRepository.findAllByReleaseDateBeforeOrReleaseDateAfter(before,after);

        List<String> bookTitles = books.stream().map(b->b.getTitle()).collect(Collectors.toList());

        return String.join(System.lineSeparator(),bookTitles);
    }

    @Override
    public String releasedBeforeDate(String date) {
        LocalDate date1 = LocalDate.parse(date,DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Book> books = this.bookRepository.findAllByReleaseDateBefore(date1);

        List<String> bookTitles = books.stream().map(b->b.getTitle()).collect(Collectors.toList());

        return String.join(System.lineSeparator(),bookTitles);
    }

    @Override
    public List<String> goldenBooks() {
        EditionType editionType = EditionType.GOLD;
       List<Book> books = this.bookRepository.findAllByCopiesLessThanAndEditionType(5000,editionType);

       return books.stream().map(b->b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public List<String> booksSearch(String searchedString) {
       List<Book> books = this.bookRepository.findAllByTitleContaining(searchedString);

       return books.stream().map(b->b.getTitle()).collect(Collectors.toList());
    }

    @Override
    public List<Book> booksCount(Integer number) {

        List<Book> books = this.bookRepository.findAllByTitle(number);

        return books;
    }

    @Override
    public Map<String, Integer> totalBooksCopies() {
        List<Author> authors = this.authorRepository.findAll();

        Map<String, Integer> set = new HashMap<>();

        for (Author author : authors) {
            int sum=0;
            List<Book> booksByAuthor = this.bookRepository.findAllByAuthor(author);
            for (Book book : booksByAuthor) {
                sum+=book.getCopies();
            }
            String authorName  = author.getFirstName()+" "+author.getLastName();
            set.put(authorName,sum);
        }

        return set;
    }


    @Override
    public List<Book> findBooksByAuthor(Author author) {
        List<Book> books = this.bookRepository.findAllByAuthor(author);

        return books;
    }

    @Override
    public List<String> bookTitlesSearch(String pattern) {
        String wildcard = pattern.toLowerCase()+"%";
        List<Book> books = this.bookRepository.getAllByAuthorStartsWith(wildcard);

        return books.stream().map(a->String.format("%s (%s %s)",a.getTitle(),a.getAuthor().getFirstName(),a.getAuthor().getLastName())).collect(Collectors.toList());
    }


    private Author getRandomAuthor() {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.authorRepository.count() +1));

        return this.authorRepository.findById(randomId).orElse(null);
    }

    private Category getRandomCategory()
    {
        Random random = new Random();

        int randomId = random.nextInt((int) (this.categoryRepository.count() + 1));

        return this.categoryRepository.findById(randomId).orElse(null);
    }

    private Set<Category> getRandomCategories()
    {
        Set<Category> categories= new LinkedHashSet<>();

        Random random = new Random();
        int length = random.nextInt(5);

        for (int i = 0; i < length; i++) {
            Category category = this.getRandomCategory();

            categories.add(category);
        }
        return categories;
    }


}
