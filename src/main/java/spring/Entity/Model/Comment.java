package spring.Entity.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "Comment")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(generator = "uuid",strategy = GenerationType.IDENTITY)
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "CommentId", updatable = false, nullable = false)
    private String commentId;
    @Column(name = "content")
    private String content;
//    @Column(name = "nameUser")
//    private String nameUser;
    @ManyToOne
    @JoinColumn(name = "User")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Book")
    @JsonIgnore
    private Book book;

}