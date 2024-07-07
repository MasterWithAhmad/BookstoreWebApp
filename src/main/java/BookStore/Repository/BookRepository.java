package BookStore.Repository;

import BookStore.Entitiy.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE lower(b.title) LIKE lower(concat('%', :query, '%')) OR lower(b.author.name) LIKE lower(concat('%', :query, '%'))")
    List<Book> searchBooksByTitleOrAuthor(@Param("query") String query);
}