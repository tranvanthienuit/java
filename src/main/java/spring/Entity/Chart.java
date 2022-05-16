package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Chart {
    private Map<Integer,Integer> month_book;
    private Map<Integer,String> book_category;
    private Map<Integer,Integer> month_user;
    private Map<Integer,Double> month_price;
}
