package com.aravind.finance.repositories;

import com.aravind.finance.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExpenseRepository extends CrudRepository<Expense,Long> {

    Iterable<Expense> findAllByUserId(String userId);

    Expense findByUserId(String userId);

    long countByUserId(String userId);

    Iterable<Expense> findAllByCategoryIgnoreCase(String category);

    Iterable<Expense> findAllBySubCategoryIgnoreCase(String subCategory);

    Iterable<Expense> findAllByPaymentModeIgnoreCase(String paymentMode);

    Expense findByExpenseId(int expenseId);

    List<Expense> deleteAllByUserId(String userId);



}
