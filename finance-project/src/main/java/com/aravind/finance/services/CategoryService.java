package com.aravind.finance.services;

import com.aravind.finance.exceptions.CategoryException;
import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import com.aravind.finance.repositories.CategoryRepository;
import com.aravind.finance.repositories.SubCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    public Category saveOrUpdateCategory(Category category){
        try{
            return categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SubCategory saveOrUpdateSubCategory(SubCategory subCategory, int category_id){

        try{
            Category category = categoryRepository.findById(category_id);
            if(category == null){
                throw new CategoryException("Category"+category_id+" Not Found ");
            }
            subCategory.setCategory(category);
            return subCategoryRepository.save(subCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
