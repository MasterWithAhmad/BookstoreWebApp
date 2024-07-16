package BookStore.Controller;

import BookStore.Entitiy.Customer;
import BookStore.Service.BookService;
import BookStore.Service.BorrowRecordService;
import BookStore.Service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/customers")
public class CustomerController {

    private CustomerService customerService;
    private BorrowRecordService borrowRecordService;
    private BookService bookService;

    public void BorrowRecordController(BorrowRecordService borrowRecordService, CustomerService customerService, BookService bookService) {
        this.borrowRecordService = borrowRecordService;
        this.customerService = customerService;
        this.bookService = bookService;
    }

    @GetMapping("/all-borrows")
    public String listBorrowRecords(Model model, @RequestParam(value = "query", required = false) String query) {
        if (query != null) {
            model.addAttribute("borrowRecords", borrowRecordService.searchBorrowRecords(query));
            model.addAttribute("searchQuery", query);
        } else {
            model.addAttribute("borrowRecords", borrowRecordService.findAll());
        }
        return "borrows/list";
    }

    @GetMapping("/search")
    public String searchBorrowRecords(@RequestParam("keyword") String keyword, Model model) {
        model.addAttribute("borrowRecords", borrowRecordService.searchBorrowRecords(keyword));
        return "borrows/list :: borrowRecordsTable";
    }


    public CustomerController(CustomerService customerService, BorrowRecordService borrowRecordService, BookService bookService) {
        this.customerService = customerService;
        this.borrowRecordService = borrowRecordService;
        this.bookService = bookService;
    }

    @GetMapping("/all-customers")
    public String listCustomers(Model model, @RequestParam(value = "query", required = false) String query) {
        if (query != null) {
            model.addAttribute("customers", customerService.searchCustomers(query));
            model.addAttribute("searchQuery", query);
        } else {
            model.addAttribute("customers", customerService.findAll());
        }
        return "customers/list";
    }

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("customer", new Customer());
        return "customers/add";
    }

    @PostMapping("/add")
    public String saveCustomer(@ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        boolean isNew = (customer.getId() == null);
        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", isNew ? "created" : "updated");
        return "redirect:/customers/all-customers";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("customer", customerService.findById(id));
        return "customers/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateCustomer(@PathVariable("id") Long id, @ModelAttribute Customer customer, RedirectAttributes redirectAttributes) {
        customer.setId(id);
        customerService.saveCustomer(customer);
        redirectAttributes.addFlashAttribute("successMessage", "updated");
        return "redirect:/customers/all-customers";
    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        customerService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "deleted");
        return "redirect:/customers/all-customers";
    }
}