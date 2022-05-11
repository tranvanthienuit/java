package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Book;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookRating {
    private Book book;
    private Double rating;
}