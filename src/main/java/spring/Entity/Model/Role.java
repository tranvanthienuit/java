package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Roles")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Role {
    //Role: id, name
    @Id
    private String roleId;
    @Column(name = "NameRole")
    private String nameRole;

}
