package BookStore.Controller;

import BookStore.Entitiy.Book;
import BookStore.Service.AuthorService;
import BookStore.Service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    // Handler method to display all books
    @GetMapping("/all-books")
    public String getAllBooks(Model model) {
        List<Book> books = bookService.findAll();
        model.addAttribute("books", books);
        model.addAttribute("query", ""); // Initialize query parameter
        return "books/list"; // Thymeleaf template name for listing books
    }

    // Handler method to search books
    @GetMapping("/search")
    public String searchBooks(@RequestParam(name = "query") String query, Model model) {
        List<Book> books = bookService.searchBooks(query);
        model.addAttribute("books", books);
        model.addAttribute("query", query); // Add query parameter to retain in the view
        return "books/list"; // Thymeleaf template name for listing books
    }

    // Handler method to show form for adding new book
    @GetMapping("/add")
    public String showAddBookForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "books/add"; // Thymeleaf template name for adding new book
    }

    // Handler method to process form submission for adding new book
    @PostMapping("/add")
    public String addBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        return "redirect:/books/all-books";
    }

    // Handler method to show form for editing a book
    @GetMapping("/edit/{id}")
    public String showEditBookForm(@PathVariable("id") Long id, Model model) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (bookOptional.isPresent()) {
            model.addAttribute("book", bookOptional.get());
            model.addAttribute("authors", authorService.findAll());
            return "books/edit"; // Thymeleaf template name for editing book details
        } else {
            return "redirect:/books/all-books?notfound=true";
        }
    }

    // Handler method to process form submission for editing a book
    @PostMapping("/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, @ModelAttribute("book") Book book) {
        book.setId(id); // Ensure the book ID is set for updating existing record
        bookService.save(book);
        return "redirect:/books/all-books";
    }

    // Handler method to delete a book
    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/books/all-books";
    }
}