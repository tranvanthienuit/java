package spring.Entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "OrderssDetail")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class OrderssDetail {

    @Id
    @GeneratedValue(generator = "uuid",strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "OrderssDeId")
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String OrderssDeId;
    @Column(name = "count")
    private Integer count;
    @Column(name = "total_Price")
    private Double total;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @JoinColumn(name = "OrderssId")
    private Orderss orderss;
    @ManyToOne
    @JoinColumn(name = "BooksId")
    private Book book;


}