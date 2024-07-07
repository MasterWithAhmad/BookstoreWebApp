package BookStore.Controller;

import BookStore.Entitiy.BorrowRecord;
import BookStore.Service.BookService;
import BookStore.Service.BorrowRecordService;
import BookStore.Service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/borrow-records")
public class BorrowRecordController {

    private final BorrowRecordService borrowRecordService;
    private final CustomerService customerService;
    private final BookService bookService;

    public BorrowRecordController(BorrowRecordService borrowRecordService, CustomerService customerService, BookService bookService) {
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

    @GetMapping("/add")
    public String showCreateForm(Model model) {
        model.addAttribute("borrowRecord", new BorrowRecord());
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("books", bookService.findAll());
        return "borrows/add";
    }

    @PostMapping("/add")
    public String saveBorrowRecord(@ModelAttribute BorrowRecord borrowRecord, RedirectAttributes redirectAttributes) {
        boolean isNew = (borrowRecord.getId() == null);
        borrowRecordService.saveBorrowRecord(borrowRecord);
        redirectAttributes.addFlashAttribute("successMessage", isNew ? "created" : "updated");
        return "redirect:/borrow-records/all-borrows";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("borrowRecord", borrowRecordService.findById(id));
        model.addAttribute("customers", customerService.findAll());
        model.addAttribute("books", bookService.findAll());
        return "borrows/edit";
    }

    @PostMapping("/edit/{id}")
    public String updateBorrow(@PathVariable("id") Long id, @ModelAttribute BorrowRecord borrowRecord, RedirectAttributes redirectAttributes) {
        // Ensure the borrowRecord has the ID for updating
        borrowRecord.setId(id);

        // Handle the relationships
        borrowRecord.setCustomer(customerService.findById(borrowRecord.getCustomer().getId()));

        // Fetch and set the Book properly from Optional
        bookService.findById(borrowRecord.getBook().getId()).ifPresent(borrowRecord::setBook);

        // Save the updated borrow record
        borrowRecordService.saveBorrowRecord(borrowRecord);

        redirectAttributes.addFlashAttribute("successMessage", "updated");
        return "redirect:/borrow-records/all-borrows";
    }

    @GetMapping("/delete/{id}")
    public String deleteBorrowRecord(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        borrowRecordService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "deleted");
        return "redirect:/borrow-records/all-borrows";
    }

}