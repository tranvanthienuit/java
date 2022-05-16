package spring.Entity.Model;


import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Orderss")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Orderss {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "OrderssId", updatable = false, nullable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String OrderssId;
    @Column(name = "OrderssDate")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date OrderssDate;
    @Column(name = "totalBook")
    private Integer totalBook;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "UserId")
    private User user;


}
