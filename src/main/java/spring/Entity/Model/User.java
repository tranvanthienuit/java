package spring.Entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Users")
@Data
public class User {
//    Users: id, name, email, password, roleId, address, phoneNumber, gender

    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "UserId", updatable = false)
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

//    @Column(name = "nameRole")
//    private String nameRole;
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Comment> comments;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Blog> blogs;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Orderss> ordersses;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Rating> ratings;


    public void setImage(String image) {
        this.image = image.getBytes();
    }

    public String getImage() {
        if (image == null)
            return null;
        return new String(image);
    }
    //xóa các bảng, thông tin có khóa ngoại liên kết
    @PreRemove
    public void preRemove(){
        this.getBlogs().remove(this);
        this.getRatings().remove(this);
        this.getComments().remove(this);
        this.ordersses.remove(this);
    }
}