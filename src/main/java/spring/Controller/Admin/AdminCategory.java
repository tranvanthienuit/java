package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.Model.Category;
import spring.Service.BookService;
import spring.Service.CategoryService;


@RestController
public class AdminCategory {
    @Autowired
    CategoryService categoryService;
    @Autowired
    BookService bookService;

    @PostMapping(value = "/admin/luu-loai-sach")
    public ResponseEntity<String> saveCategory(@RequestBody Category category) throws Exception {
        categoryService.saveCategory(category);
        return new ResponseEntity<>("successful",HttpStatus.OK);
    }

    @DeleteMapping(value = {"/admin/xoa-loai-sach/{categoryId}", "/admin/xoa-loai-sach"})
    public ResponseEntity<String> removeCategory(@PathVariable(value = "categoryId", required = false) String categoryId) throws Exception {
        if (categoryService.findByCategoryId(categoryId) != null) {
//            bookService.removeBookByCategory(categoryId);
            categoryService.removeByCategoryId(categoryId);
            return new ResponseEntity<>("successful",HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/admin/sua-loai-sach")
    public ResponseEntity<?> editeCategory(@RequestBody Category category){
        Category categories = categoryService.findByCategoryId(category.getCategoryId());
        categories.setNameCate(category.getNameCate());
        categoryService.saveCategory(categories);
        return new ResponseEntity<>("successful",HttpStatus.OK);
    }

}
