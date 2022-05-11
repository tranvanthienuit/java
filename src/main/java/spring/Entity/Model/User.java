package spring.Entity.Model;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Users")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class User {
//    Users: id, name, email, password, roleId, address, phoneNumber, gender

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "UserId", updatable = false, nullable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String userId;
    @Column(name = "fullName")
    private String fullName;
    @Column(name = "NameUser")
    private String nameUser;
    @Column(name = "Password")
    private String password;
    @Column(name = "Email")
    private String email;
    @Column(name = "Address")
    private String address;
    @Column(name = "DayAdd")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dayAdd;
    @Column(name = "Telephone")
    private String telephone;
    @Column(name = "Sex")
    private String sex;
    @Column(name = "image")
    @Lob
    private byte[] image;
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "RoleId")
    private Role role;


    public void setImage(String image) {
        this.image = image.getBytes();
    }

    public String getImage() {
        if (image==null)
            return null;
        return new String(image);
    }
}
