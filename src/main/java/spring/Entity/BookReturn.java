package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Book;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookReturn {
    private BookList bookList;
    private List<Book> bookOder;
    private List<Book> bookRating;
}
