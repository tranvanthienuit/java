package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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
    @Column(name = "CategoryId")
    private String categoryId;
    @Column(name = "NameCategory")
    private String nameCate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Categories that = (Categories) o;
        return categoryId != null && Objects.equals(categoryId, that.categoryId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
