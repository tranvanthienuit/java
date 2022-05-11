package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Categories")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Categories {
    //Categories: id, name
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "categoryId", updatable = false, nullable = false)
    private String categoryId;
    @Column(name = "NameCategory")
    private String nameCate;


}
