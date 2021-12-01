package com.aravind.finance.repositories;

import com.aravind.finance.models.CatSubCatModel;
import com.aravind.finance.models.Category;
import com.aravind.finance.models.SubCategory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(int category_id);

    @Override
    Iterable<Category> findAll();

    @Query("select new com.aravind.finance.models.CatSubCatModel(s.subCategory, c.categoryName, c.id)"+" from SubCategory s inner join s.category c")
    List<CatSubCatModel> categoriesWithSubCategory();

}
