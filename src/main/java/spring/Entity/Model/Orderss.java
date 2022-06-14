package spring.Entity.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Orderss")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Orderss {

    @Id
    @GeneratedValue(generator = "uuid",strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "OrderssId", updatable = false)
//    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private String orderssId;
    @Column(name = "OrderssDate")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date orderssDate;
    @Column(name = "totalBook")
    private Integer totalBook;
    @Column(name = "totalPrice")
    private Double totalPrice;
    @Column(name = "Telephone")
    private String telephone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Status")
    private String status;
    @Column(name = "pay")
    private String pay;
    @Column(name = "nameUser")
    private String nameUser;
    @Column(name = "fullName")
    private String fullName;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @OneToMany(mappedBy = "orderss", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<OrderssDetail> orderssDetails;
    //xóa các bảng, thông tin có khóa ngoại liên kết
    @PreRemove
    public void preRemove(){
        this.getOrderssDetails().remove(this);
    }
}