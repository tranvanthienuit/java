package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.BorrowDetail;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BorrowDelist {
    List<BorrowDetail> borrowDelists;
    int count;
}
