package BookStore.Service;

import BookStore.Entitiy.BorrowRecord;
import BookStore.Repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordService {

    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    public List<BorrowRecord> findAll() {
        return borrowRecordRepository.findAll();
    }

    public BorrowRecord findById(Long id) {
        return borrowRecordRepository.findById(id).orElse(null);
    }

    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    public void deleteById(Long id) {
        borrowRecordRepository.deleteById(id);
    }

    public List<BorrowRecord> searchBorrowRecords(String query) {
        return borrowRecordRepository.searchByQuery(query);
    }
}