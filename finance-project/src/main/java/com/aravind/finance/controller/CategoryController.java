package com.aravind.finance.controller;

import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import com.aravind.finance.services.CategoryService;
import com.aravind.finance.services.MapValidationErrorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/finance")
@CrossOrigin
@Slf4j
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MapValidationErrorService errorService;

    @PostMapping("/category/add")
    public ResponseEntity<?> createNewCategory(@Valid @RequestBody Category category,
                                               BindingResult result){
        log.info("Triggering POST Category Method");
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
        log.info("Triggering POST Sub Category Method");
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
    public EntityModel<Category> getCategoryByID(@PathVariable int categoryId){
        log.info("Getting the category details by an ID");
        Category category = categoryService.getCategoryByCategoryId(categoryId);
        EntityModel<Category> entityModel = EntityModel.of(category);
        entityModel.add(linkTo(methodOn(this.getClass()).getAllCategories()).withRel("all-categories"));
        entityModel.add(linkTo(methodOn(this.getClass()).getAllSubCategories()).withRel("all-sub-categories"));
        entityModel.add(linkTo(methodOn(this.getClass()).getAllSubCategoriesByCategoryId()).withRel("sub-category-list"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteCategoryByID(categoryId)).withRel("delete-category"));
        return entityModel;
    }

    @GetMapping("/subCategory/get/{subCategoryId}")
    public EntityModel<SubCategory> getSubCategoryByID(@PathVariable int subCategoryId){
        log.info("Getting the sub category details by an ID");
        SubCategory subCategory = categoryService.getSubCategoryBySubCategoryId(subCategoryId);
        EntityModel<SubCategory> entityModel = EntityModel.of(subCategory);
        entityModel.add(linkTo(methodOn(this.getClass()).getAllSubCategories()).withRel("all-sub-categories"));
        entityModel.add(linkTo(methodOn(this.getClass()).getAllSubCategoriesByCategoryId()).withRel("sub-category-list"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteSubCategoryByID(subCategoryId)).withRel("delete-sub-category"));
        return entityModel;
    }

    @GetMapping("/subCategory/all")
    public CollectionModel<EntityModel<SubCategory>> getAllSubCategories(){
        log.info("Getting all the sub-categories details");
        List<SubCategory> subCategories = categoryService.findAllSubCategories();
        List<EntityModel<SubCategory>> entityModel = subCategories.stream().map(subCategory->
                EntityModel.of(subCategory,
                        linkTo(methodOn(this.getClass()).getAllSubCategoriesByCategoryId()).withRel("sub-category-list"),
                        linkTo(methodOn(this.getClass()).getSubCategoryByID(subCategory.getId())).withRel("sub-category-details"),
                        linkTo(methodOn(this.getClass()).deleteSubCategoryByID(subCategory.getId())).withRel("delete-sub-category")
                        )).collect(Collectors.toList());
        return CollectionModel.of(entityModel);
    }

    @GetMapping("/subCategory/getCategory/{subCategoryId}")
    public String getParentCategoryBySubCategory(@PathVariable int subCategoryId){
        log.info("Getting the Parent category details by the sub category ID");
        return categoryService.getParentCategoryBySubCategory(subCategoryId);
    }

    @GetMapping("/category/subCategory")
    public ResponseEntity<Object> getAllSubCategoriesByCategoryId(){
        log.info("Getting the sub category mapping with category");
        Map<String, List<String>> subCategoriesByCategoryId = categoryService.getSubCategoriesByCategoryId();
        return new ResponseEntity<>(subCategoriesByCategoryId, HttpStatus.OK);
    }

    @DeleteMapping("/category/delete/{categoryId}")
    public ResponseEntity<?> deleteCategoryByID(@PathVariable int categoryId){
        log.info("Deleting the category details by an ID");
        categoryService.deleteCategoryByID(categoryId);
        return new ResponseEntity<>("Category with ID: "+categoryId+" is deleted", HttpStatus.OK);
    }

    @DeleteMapping("/subCategory/delete/{subCategoryId}")
    public ResponseEntity<?> deleteSubCategoryByID(@PathVariable int subCategoryId){
        log.info("Deleting the sub category details by an ID");
        categoryService.deleteSubCategoryByID(subCategoryId);
        return new ResponseEntity<>("Sub Category with ID: "+subCategoryId+" is deleted", HttpStatus.OK);
    }
}