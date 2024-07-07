package BookStore.Controller;

import BookStore.Entitiy.Author;
import BookStore.Service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/authors")
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/all-authors")
    public String getAllAuthors(@RequestParam(name = "query", required = false) String query, Model model) {
        List<Author> authors;
        if (query != null && !query.isEmpty()) {
            authors = authorService.searchByNameOrBio(query);
        } else {
            authors = authorService.findAll();
        }
        model.addAttribute("authors", authors);
        model.addAttribute("query", query); // Add query parameter to retain in the view
        return "authors/list"; // Thymeleaf template name for listing authors
    }

    @GetMapping("/add")
    public String showAddAuthorForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add";
    }

    @PostMapping("/add")
    public String addAuthor(@ModelAttribute("author") Author author) {
        authorService.save(author);
        return "redirect:/authors/all-authors?added=true";
    }

    @GetMapping("/edit/{id}")
    public String showEditAuthorForm(@PathVariable("id") Long id, Model model) {
        Optional<Author> author = authorService.findById(id);
        if (author.isPresent()) {
            model.addAttribute("author", author.get());
            return "authors/edit";
        } else {
            return "redirect:/authors/all-authors?notfound=true";
        }
    }

    @PostMapping("/edit/{id}")
    public String updateAuthor(@PathVariable("id") Long id, @ModelAttribute("author") Author author) {
        author.setId(id);
        authorService.save(author);
        return "redirect:/authors/all-authors?updated=true";
    }

    @GetMapping("/delete/{id}")
    public String deleteAuthor(@PathVariable("id") Long id) {
        authorService.deleteById(id);
        return "redirect:/authors/all-authors?deleted=true";
    }
}
