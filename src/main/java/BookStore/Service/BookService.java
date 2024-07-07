package BookStore.Service;

import BookStore.Entitiy.Book;
import BookStore.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    public List<Book> searchBooks(String query) {
        return bookRepository.searchBooksByTitleOrAuthor(query);
    }
}
//package BookStore.Service;
//
//import BookStore.Entitiy.Book;
//import BookStore.Repository.BookRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class BookService {
//
//    private final BookRepository bookRepository;
//
//    public List<Book> findAll() {
//        return bookRepository.findAll();
//    }
//
//    public Optional<Book> findById(Long id) {
//        return bookRepository.findById(id);
//    }
//
//    public Book save(Book book) {
//        return bookRepository.save(book);
//    }
//
//    public void deleteById(Long id) {
//        bookRepository.deleteById(id);
//    }
//}
