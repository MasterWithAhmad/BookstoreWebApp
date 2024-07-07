package BookStore.Entitiy;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @Column(name = "published_date")
    private LocalDate publishedDate;

    private String category;

    private int quantity;

    private BigDecimal price;

    // Constructors, getters, and setters
    // Ensure to generate constructors, getters, setters, and toString() methods
    // Omitting for brevity
}
