package com.aravind.finance.repositories;

import com.aravind.finance.models.Expense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ExpenseRepository extends CrudRepository<Expense,Long> {

    List<Expense> findAllByUserId(String userId);

    Expense findByUserId(String userId);

    long countByUserId(String userId);

    //List<Expense> findAllByPaymentModeIgnoreCase(String paymentMode);

    Expense findByExpenseId(int expenseId);

    List<Expense> deleteAllByUserId(String userId);

}
