package spring.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import spring.Entity.Categories;
import spring.Repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    public void saveCategory(Categories categories) {
        categoryRepository.save(categories);
    }

    public void removeCategoriesByCategoryId(String categoryId) {
        categoryRepository.removeCategoriesByCategoryId(categoryId);
    }

    public Categories findByCategoryId(String categoryid) {
        return categoryRepository.findByCategoryId(categoryid);
    }

    public Page<Categories> getAllCate(Pageable pageable) {
        return  categoryRepository.findAll(pageable);
    }
}
