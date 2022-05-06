package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "BorrowDetail")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class BorrowDetail {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "BorrowDeId", updatable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String borrowDeId;
    @Column(name = "Status")
    private String status;
    @Column(name = "count")
    private Integer count;
    @Column(name = "total_Price")
    private Double total;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BorrowId")
    private Borrow borrow;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BooksId")
    private Book book;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BorrowDetail that = (BorrowDetail) o;
        return borrowDeId != null && Objects.equals(borrowDeId, that.borrowDeId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
