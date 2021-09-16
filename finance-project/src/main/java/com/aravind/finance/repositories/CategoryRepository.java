package com.aravind.finance.repositories;

import com.aravind.finance.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
@Transactional
public interface CategoryRepository extends CrudRepository<Category, Long> {

    Category findById(int category_id);

    @Override
    Iterable<Category> findAll();

}
