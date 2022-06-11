package spring.Entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "Books")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Book {
    //Books: id, name, categoryId, author, publishYear, nxb, dayAdded, price, status, description
    @Id
    @GeneratedValue(generator = "uuid", strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "bookId", updatable = false)
    private String bookId;
    @Column(name = "NameBook")
    private String nameBook;
    @Column(name = "Author")
    private String author;
    @Column(name = "PublishYear")
    private String publishYear;
    @Column(name = "PublishCom")
    private String publishCom;
    @Column(name = "DayAdd")
    @Temporal(TemporalType.TIMESTAMP)
    private java.util.Date dayAdd;
    @Column(name = "Price")
    private Integer price;
    @Column(name = "Count")
    private Integer count;
    //    @Column(name = "nameCate")
//    private String nameCate;
    @Column(name = "Description")
    @Lob
    private byte[] description;
    @Column(name = "image")
    @Lob
    private byte[] image;
    @Column(name = "rating")
    private Integer rating = 5;
    @Column(name = "Cmt")
    private Integer Cmt = 0;
    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Categories category;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<OrderssDetail> orderssDetails;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Rating> ratings;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    @JsonIgnore
    @ToString.Exclude
    private List<Comment> comments;

    public void setImage(String image) {
        this.image = image.getBytes();
    }

    public String getImage() {
        if (image == null)
            return null;
        return new String(image);
    }

    public void setDescription(String description) {
        this.description = description.getBytes();
    }

    public String getDescription() {
        if (description == null)
            return null;
        return new String(description);
    }

    //xóa các bảng, thông tin có khóa ngoại liên kết
    @PreRemove
    public void preRemove() {
        this.orderssDetails.remove(this);
        this.ratings.remove(this);
        this.comments.remove(this);
    }

    public void setRating(Integer rating) {
        if (rating != null)
            this.rating = (this.rating + rating) / 2;
    }
    public void setCmt(boolean value){
        if (value)
            this.Cmt = this.Cmt + 1;
        if (value == false)
            this.Cmt = this.Cmt - 1;
    }
}