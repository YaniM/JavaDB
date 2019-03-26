package bookshopsystemapp.controller;

import bookshopsystemapp.domain.entities.Author;
import bookshopsystemapp.domain.entities.Book;
import bookshopsystemapp.service.AuthorService;
import bookshopsystemapp.service.BookService;
import bookshopsystemapp.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class BookshopController implements CommandLineRunner {

    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final BookService bookService;

    @Autowired
    public BookshopController(AuthorService authorService, CategoryService categoryService, BookService bookService) {
        this.authorService = authorService;
        this.categoryService = categoryService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        /*this.authorService.seedAuthors();
        this.categoryService.seedCategories();
        this.bookService.seedBooks();
*/
        //1.     this.bookTitlesByAgeRestriction();
        //2.     this.goldenBooks();
        //3.     this.booksByPrice();
        //4.     this.notReleasedBooks();
        //5.     this.booksReleasedBeforeDay();
        //6.     this.authorsSearch();
        //7.     this.bookSearch();
        //8.     this.bookTitlesSearch();
        //9.     this.booksCount();
        //10.    this.totalBooksCountv2();

    }

    private void bookTitles() {
        List<String> bookTitles = this.bookService.getAllBooksTitlesAfter();

        System.out.println(String.join("\r\n", bookTitles));
    }

    private void authorNames() {
        this.bookService.getAllAuthorsWithBookBefore().stream().forEach(a -> System.out.println(a));
    }

    private void authorsByBookDesc() {
        this.authorService.getAllAuthorsByBooksNumber().stream().forEach(a -> System.out.println(a));
    }


    private void authorByName() {
        this.bookService.getBooksByAuthor().stream().forEach(a -> System.out.println(a));
    }

    private void  bookTitlesByAgeRestriction()
    {
        Scanner scanner = new Scanner(System.in);

        String ageRestriction = scanner.nextLine();
        this.bookService.getBooksTitlesByAgeRestriction(ageRestriction).forEach(b-> System.out.println(b));
    }

    private void booksByPrice()
    {
        System.out.println(this.bookService.booksByPrice());
    }

    private void notReleasedBooks()
    {
        Scanner scanner = new Scanner(System.in);

        String year = scanner.nextLine();

        String result = this.bookService.notReleasedBooks(year);

        System.out.println(result);
    }

    private void booksReleasedBeforeDay()
    {
        Scanner scanner = new Scanner(System.in);

        String date = scanner.nextLine();

        System.out.println(this.bookService.releasedBeforeDate(date));
    }

    private void  authorsSearch()
    {
        Scanner scanner = new Scanner(System.in);

        String endsWith  = scanner.nextLine();

        String result = this.authorService.authorSearch(endsWith);

        System.out.println(result);

    }

    private void goldenBooks()
    {
       this.bookService.goldenBooks().forEach(b-> System.out.println(b));
    }

    private void bookSearch()
    {
        Scanner scanner = new Scanner(System.in);

        String searchedString = scanner.nextLine().toLowerCase();

        this.bookService.booksSearch(searchedString).forEach(b-> System.out.println(b));
    }

    private void  booksCount()
    {
        Scanner scanner = new Scanner(System.in);

        Integer number = Integer.parseInt(scanner.nextLine());

        System.out.println(this.bookService.booksCount(number).size());
    }

    private void totalBooksCount()
    {
        Map<String,Integer> set = this.bookService.totalBooksCopies();

        set.entrySet().stream().sorted(Map.Entry.comparingByValue()).map(Map.Entry::getKey).collect(Collectors.toList());

        for (Map.Entry<String, Integer> a : set.entrySet()) {
            System.out.println(String.format("%s %d",a.getKey(),a.getValue()));
        }
    }

    private void totalBooksCountv2()
    {
        this.authorService.getAllByTotalBookCopies().forEach(a-> System.out.println(a));
    }

    private void  bookTitlesSearch()
    {
        Scanner scanner = new Scanner(System.in);

        String pattern = scanner.nextLine();

        this.bookService.bookTitlesSearch(pattern).forEach(a-> System.out.println(a));
    }

}
