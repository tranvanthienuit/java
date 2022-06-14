package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Category;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateList {
    List<Category> categoryList;
    int count;
}
