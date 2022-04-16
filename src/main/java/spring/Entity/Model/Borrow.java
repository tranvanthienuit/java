package spring.Entity.Model;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Borrow")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Borrow {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "BorrowId", updatable = false, nullable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String borrowId;
    @Column(name = "BorrowDate")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date borrowDate;
    @Column(name = "ReturnDate")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date returnDate;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "UserId")
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Borrow borrow = (Borrow) o;
        return borrowId != null && Objects.equals(borrowId, borrow.borrowId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
