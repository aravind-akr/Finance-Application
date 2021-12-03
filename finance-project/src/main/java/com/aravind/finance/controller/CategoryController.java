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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;

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
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/finance/category/get/{categoryId}")
                .buildAndExpand(category1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PostMapping("/category/{categoryId}/subCategory/add")
    public ResponseEntity<?> createNewSubCategory(@Valid @RequestBody SubCategory subCategory,
                                               BindingResult result, @PathVariable int categoryId){
        ResponseEntity<?> errorMap = errorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        SubCategory subCategory1 = categoryService.saveOrUpdateSubCategory(subCategory, categoryId);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/finance/subCategory/get/{subCategoryId}")
                .buildAndExpand(subCategory1.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/category/all")
    public Iterable<Category> getAllCategories(){
        return categoryService.findAllCategories();
    }

    @GetMapping("/category/get/{categoryId}")
    public Category getCategoryByID(@PathVariable int categoryId){
        return categoryService.getCategoryByCategoryId(categoryId);
    }

    @GetMapping("/subCategory/get/{subCategoryId}")
    public SubCategory getSubCategoryByID(@PathVariable int subCategoryId){
        return categoryService.getSubCategoryBySubCategoryId(subCategoryId);
    }

    @GetMapping("/subCategory/all")
    public Iterable<SubCategory> getAllSubCategories(){
        return categoryService.findAllSubCategories();
    }

    @GetMapping("/subCategory/getCategory/{subCategoryId}")
    public String getParentCategoryBySubCategory(@PathVariable int subCategoryId){
        return categoryService.getParentCategoryBySubCategory(subCategoryId);
    }

    @GetMapping("/category/subCategory")
    public ResponseEntity<Object> getAllSubCategoriesByCategoryId(){
        Map<String, List<String>> subCategoriesByCategoryId = categoryService.getSubCategoriesByCategoryId();
        return new ResponseEntity<Object>(subCategoriesByCategoryId, HttpStatus.OK);
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