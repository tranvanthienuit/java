package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Orderss;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderssList {
    List<Orderss> orderssList;
    int count;
}
