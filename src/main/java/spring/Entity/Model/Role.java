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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Role role = (Role) o;
        return roleId != null && Objects.equals(roleId, role.roleId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
