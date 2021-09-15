package com.aravind.finance.controller;

import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import com.aravind.finance.services.CategoryService;
import com.aravind.finance.services.MapValidationErrorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/finance")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MapValidationErrorService errorService;

    @PostMapping("/category/add")
    public ResponseEntity<?> createNewCategory(@Valid @RequestBody Category category,
                                               BindingResult result){
        ResponseEntity<?> errorMap = errorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        Category category1 = categoryService.saveOrUpdateCategory(category);
        return new ResponseEntity<>(category1, HttpStatus.CREATED);

    }

    @PostMapping("/category/{category_id}/subCategory/add")
    public ResponseEntity<?> createNewSubCategory(@Valid @RequestBody SubCategory subCategory,
                                               BindingResult result, @PathVariable int category_id){
        ResponseEntity<?> errorMap = errorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        SubCategory subCategory1 = categoryService.saveOrUpdateSubCategory(subCategory, category_id);
        return new ResponseEntity<>(subCategory1, HttpStatus.CREATED);

    }
}
