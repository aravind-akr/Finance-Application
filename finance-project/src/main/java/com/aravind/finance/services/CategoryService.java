package com.aravind.finance.services;

import com.aravind.finance.exceptions.CategoryException;
import com.aravind.finance.models.CatSubCatModel;
import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import com.aravind.finance.repositories.CategoryRepository;
import com.aravind.finance.repositories.SubCategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SubCategoryRepository subCategoryRepository;

    @Autowired
    private SessionFactory sessionFactory;

    public Category saveOrUpdateCategory(Category category){
        log.info("Inside the saveOrUpdateCategory method");
        try{
            log.info("Trying to save the category");
            return categoryRepository.save(category);
        } catch (Exception e) {
            log.debug("There's some issue in saving the category:"+e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public SubCategory saveOrUpdateSubCategory(SubCategory subCategory, int categoryId){
        log.info("Inside the saveOrUpdateSubCategory method");
        try{
            log.info("Trying to save the sub category");
            Category category = categoryRepository.findById(categoryId);
            if(category == null){
                log.warn("There's no category with the ID "+categoryId);
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
        log.info("Inside the findAllCategories method");
        return categoryRepository.findAll();
    }

    public List<SubCategory> findAllSubCategories(){
        log.info("Inside the findAllSubCategories method");
        return subCategoryRepository.findAll();
    }

    public String getParentCategoryBySubCategory(int subCategoryId) {
        log.info("Inside the getParentCategoryBySubCategory method");
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId);
        Category category = subCategory.getCategory();
        String categoryName = category.getCategoryName();
        log.info("The Parent category is "+subCategoryId);
        return categoryName;
    }

    public List<SubCategory> getSubCategoryListForCategory(int categoryId){
        log.info("Inside the getSubCategoryListForCategory method");
        List<SubCategory> subCategories;
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("select s from Category c join c.subCategories s where c.id=:id");
        query.setParameter("id",categoryId);
        subCategories = query.list();
        log.info("The sub category list is "+subCategories);
        return subCategories;
    }

    public void deleteCategoryByID(int categoryId) {
        log.info("Inside the deleteCategoryByID method");
        Category category = categoryRepository.findById(categoryId);
        if(category == null){
            log.debug("There is no category with ID "+ categoryId);
            throw new CategoryException("Category with ID " + categoryId + " Not Found");
        }
        categoryRepository.delete(category);
    }

    public void deleteSubCategoryByID(int subCategoryId) {
        log.info("Inside the deleteSubCategoryByID method");
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId);
        if(subCategory == null){
            log.debug("There is no Sub category with ID "+ subCategoryId);
            throw new CategoryException("Sub Category with ID " + subCategoryId + " Not Found");
        }
        subCategoryRepository.delete(subCategory);
    }

    public Map<String, List<String>> getSubCategoriesByCategoryId() {
        log.info("Inside the getSubCategoriesByCategoryId method");
        List<CatSubCatModel> catSubCatModels = categoryRepository.categoriesWithSubCategory();
        Map<String, List<String>> collect = catSubCatModels.stream().collect(Collectors.groupingBy(CatSubCatModel::getCategoryName,
                Collectors.mapping(CatSubCatModel::getSubCategory, Collectors.toList())));
        log.info("Sub Category Map by Category ID "+collect);
        return collect;
    }

    public Category getCategoryByCategoryId(int categoryId) {
        log.info("Inside the getCategoryByCategoryId method");
        Category category = categoryRepository.findById(categoryId);
        if(category == null){
            log.debug("There is no category with ID "+ categoryId);
            throw new CategoryException("Category with ID " + categoryId + " Does not Exist");
        }
        log.info("The category found is "+category);
        return category;
    }


    public SubCategory getSubCategoryBySubCategoryId(int subCategoryId) {
        log.info("Inside the getSubCategoryBySubCategoryId method");
        SubCategory subCategory = subCategoryRepository.findById(subCategoryId);
        if(subCategory == null){
            log.debug("There is no Sub category with ID "+ subCategoryId);
            throw new CategoryException("Category with ID " + subCategoryId + " Does not Exist");
        }
        log.info("The Sub category found is "+subCategory);
        return subCategory;
    }
}
