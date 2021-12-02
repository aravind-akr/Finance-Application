package com.aravind.finance.repositories;

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
public interface SubCategoryRepository extends CrudRepository<SubCategory, Long> {

    SubCategory findById(int id);

    @Override
    Iterable<SubCategory> findAll();

}
