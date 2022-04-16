package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Borrow;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowList {
    List<Borrow> borrowList;
    int count;
}
