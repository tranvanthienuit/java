package spring.Entity;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {
    //Books: id, name, categoryId, author, publishYear, nxb, dayAdded, price, status, description
    @Id
    @Column(name = "BookId")
    private String bookId;
    @Column(name = "NameBook")
    private String nameBook;
    @Column(name = "Author")
    private String author;
    @Column(name = "PublishYear")
    private String publishYear;
    @Column(name = "PublishCom")
    private String publishCom;
    @Column(name = "DayAdd")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dayAdd;
    @Column(name = "Price")
    private Integer price;
    @Column(name = "Count")
    private Integer count;
    @Column(name = "Description")
    private String description;
    @Column(name = "image")
    @Lob
    @Basic(fetch = FetchType.LAZY)
    @ToString.Exclude
    private byte[] image;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CategoryId")
    private Categories category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Book book = (Book) o;
        return bookId != null && Objects.equals(bookId, book.bookId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
