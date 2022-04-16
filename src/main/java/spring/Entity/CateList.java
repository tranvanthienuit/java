package spring.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import spring.Entity.Model.Categories;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CateList {
    List<Categories> categoriesList;
    int count;
}
