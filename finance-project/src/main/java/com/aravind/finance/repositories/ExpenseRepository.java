package com.aravind.finance.repositories;

import com.aravind.finance.models.ExpenseModel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<ExpenseModel,Long> {

    Iterable<ExpenseModel> findAllByUserId(String userId);

    ExpenseModel findByUserId(String userId);

    long countByUserId(String userId);

    Iterable<ExpenseModel> findAllByCategoryIgnoreCase(String category);

    Iterable<ExpenseModel> findAllBySubCategoryIgnoreCase(String subCategory);

    Iterable<ExpenseModel> findAllByPaymentModeIgnoreCase(String paymentMode);

    ExpenseModel findByExpenseId(int expenseId);

    List<ExpenseModel> deleteAllByUserId(String userId);



}
