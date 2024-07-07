package BookStore.Repository;

import BookStore.Entitiy.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {

    @Query("SELECT br FROM BorrowRecord br WHERE " +
            "LOWER(br.book.title) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
            "LOWER(br.customer.name) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<BorrowRecord> searchByQuery(String query);
}