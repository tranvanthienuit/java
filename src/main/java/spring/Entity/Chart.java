package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private List<month_book> month_book;
    private List<book_category> book_category;
    private List<month_user> month_user;
    private List<month_price> month_price;
}
