package BookStore.Repository;

import BookStore.Entitiy.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query("SELECT a FROM Author a WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :query, '%')) OR LOWER(a.bio) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Author> searchByNameOrBio(@Param("query") String query);
}