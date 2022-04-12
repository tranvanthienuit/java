package spring.Controller.Admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.Entity.CateList;
import spring.Entity.Categories;
import spring.Service.CategoryService;

import java.util.List;


@RestController
public class AdminCategory {
    @Autowired
    CategoryService categoryService;

    @PostMapping(value = "/admin/luu-loai-sach")
    public ResponseEntity<Categories> saveCategory(@RequestBody Categories categories) throws Exception {
        categoryService.saveCategory(categories);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = {"/admin/xoa-loai-sach/{idCategory}", "/admin/xoa-loai-sach"})
    public ResponseEntity<Categories> removeCategory(@PathVariable(value = "idCategory", required = false) String categoryId) throws Exception {
        if (categoryService.findByCategoryId(categoryId) != null) {
            categoryService.removeCategoriesByCategoryId(categoryId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping(value = {"/admin/xem-tat-ca-loai-sach/{page}", "/admin/xem-tat-ca-loai-sach"})
    public ResponseEntity<CateList> getAllCate(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        CateList cateList = new CateList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 16);
        Page<Categories> categoriesList = categoryService.getAllCate(pageable);
        List<Categories> categoriesListContent = categoriesList.getContent();
        if (categoriesListContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            cateList.setCategoriesList(categoriesListContent);
            cateList.setCount(categoriesListContent.size());
            return new ResponseEntity<>(cateList, HttpStatus.OK);
        }
    }

}
