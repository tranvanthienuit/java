package spring.Controller.Admin_Librarian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import spring.Entity.CateList;
import spring.Entity.Model.Categories;
import spring.Service.CategoryService;

import java.util.List;

@RestController
public class Category {
    @Autowired
    CategoryService categoryService;

    @GetMapping(value = {"/librarian/xem-tat-ca-loai-sach/{page}", "/librarian/xem-tat-ca-loai-sach","/admin/xem-tat-ca-loai-sach/{page}", "/admin/xem-tat-ca-loai-sach"})
    public ResponseEntity<CateList> getAllCate(
            @PathVariable(name = "page", required = false) Integer page) throws Exception {
        CateList cateList = new CateList();
        if (page == null) {
            page = 0;
        }
        Pageable pageable = PageRequest.of(page, 4);
        Page<Categories> categoriesList = categoryService.getAllCate(pageable);
        List<Categories> categoriesListContent = categoriesList.getContent();
        if (categoriesListContent.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            cateList.setCategoriesList(categoriesListContent);
            cateList.setCount(categoryService.getAllCategory().size());
            return new ResponseEntity<>(cateList, HttpStatus.OK);
        }
    }
}
