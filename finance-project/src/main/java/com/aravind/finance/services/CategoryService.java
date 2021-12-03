package com.aravind.finance.services;

import com.aravind.finance.exceptions.CategoryException;
import com.aravind.finance.models.CatSubCatModel;
import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import com.aravind.finance.repositories.CategoryRepository;
import com.aravind.finance.repositories.SubCategoryRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public Category saveOrUpdateCategory(Category category){
        try{
            return categoryRepository.save(category);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public SubCategory saveOrUpdateSubCategory(SubCategory subCategory, int categoryId){
        try{
            Category category = categoryRepository.findById(categoryId);
            if(category == null){
                throw new CategoryException("Category"+categoryId+" Not Found ");
            }
            category.addSubCategory(subCategory);
            subCategory.setCategory(category);
            return subCategoryRepository.save(subCategory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Iterable<Category> findAllCategories(){
        return categoryRepository.findAll();
    }

    public Iterable<SubCategory> findAllSubCategories(){
        return subCategoryRepository.findAll();
    }

    public String getParentCategoryBySubCategory(int subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId);
        Category category = subCategory.getCategory();
        String categoryName = category.getCategoryName();
        return categoryName;
    }

    public Iterable<SubCategory> getSubCategoryListForCategory(int categoryId){
        Iterable<SubCategory> subCategories;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select s from Category c join c.subCategories s where c.id=:id");
        query.setParameter("id",categoryId);
        subCategories = query.list();
        return subCategories;
    }

    public void deleteCategoryByID(int categoryId) {
        Category category = categoryRepository.findById(categoryId);
        if(category == null){
            throw new CategoryException("Category with ID " + categoryId + " Not Found");
        }
        categoryRepository.delete(category);
    }

    public void deleteSubCategoryByID(int subCategoryId) {
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId);
        if(subCategory == null){
            throw new CategoryException("Sub Category with ID " + subCategoryId + " Not Found");
        }
        subCategoryRepository.delete(subCategory);
    }

    public Map<String, List<String>> getSubCategoriesByCategoryId() {
        List<CatSubCatModel> catSubCatModels = categoryRepository.categoriesWithSubCategory();
        Map<String, List<String>> collect = catSubCatModels.stream().collect(Collectors.groupingBy(CatSubCatModel::getCategoryName,
                Collectors.mapping(CatSubCatModel::getSubCategory, Collectors.toList())));
        return collect;
    }
}
