package BookStore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {
    // Handler method to display the home page
    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("message", "Welcome to the Book Store Management System!");
        return "home"; // Thymeleaf template name for the home page
    }
}
