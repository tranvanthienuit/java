package spring.Entity.Model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "Rating")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Rating {
    @Id
    @GeneratedValue
    @Column(name = "RatingId", updatable = false, nullable = false)
    //    @org.hibernate.annotations.Type(type="org.hibernate.type.PostgresUUIDType")
    private Long ratingId;
    @ManyToOne
    @JoinColumn(name = "user")
    private User user;
    @ManyToOne
    @JoinColumn(name = "book")
    private Book book;
    private int rating;
}
