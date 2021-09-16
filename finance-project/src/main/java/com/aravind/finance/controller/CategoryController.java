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
import java.util.List;

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

    @PostMapping("/category/{categoryId}/subCategory/add")
    public ResponseEntity<?> createNewSubCategory(@Valid @RequestBody SubCategory subCategory,
                                               BindingResult result, @PathVariable int categoryId){
        ResponseEntity<?> errorMap = errorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        SubCategory subCategory1 = categoryService.saveOrUpdateSubCategory(subCategory, categoryId);
        return new ResponseEntity<>(subCategory1, HttpStatus.CREATED);

    }

    @GetMapping("/category/all")
    public Iterable<Category> getAllCategories(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/subCategory/all")
    public Iterable<SubCategory> getAllSubCategories(){
        return categoryService.findAllSubCategories();
    }

    @GetMapping("/subCategory/get/{subCategoryId}")
    public String getParentCategoryBySubCategory(@PathVariable int subCategoryId){
        return categoryService.getParentCategoryBySubCategory(subCategoryId);
    }

    @GetMapping("/category/subCategory/{categoryId}")
    public List<String> getAllSubCategoriesByCategoryId(@PathVariable int categoryId){
        return categoryService.getSubCategoryListForCategory(categoryId);
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<?> deleteCategoryByID(@PathVariable int categoryId){
        categoryService.deleteCategoryByID(categoryId);
        return new ResponseEntity<>("Category with ID: "+categoryId+" is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/subCategory/delete/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategoryByID(@PathVariable int subCategoryId){
        categoryService.deleteSubCategoryByID(subCategoryId);
        return new ResponseEntity<>("Sub Category with ID: "+subCategoryId+" is deleted", HttpStatus.OK);
    }


}
