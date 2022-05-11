package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "OrderssDetail")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderssDetail {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "OrderssDeId", updatable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String OrderssDeId;
    @Column(name = "Status")
    private String status;
    @Column(name = "count")
    private Integer count;
    @Column(name = "total_Price")
    private Double total;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "OrderssId")
    private Orderss orderss;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "BooksId")
    private Book book;


}
