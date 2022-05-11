package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.OrderssDetail;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderssDelist {
    List<OrderssDetail> OrderssDelists;
    int count;
}
