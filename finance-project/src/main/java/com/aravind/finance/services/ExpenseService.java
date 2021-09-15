package com.aravind.finance.services;

import com.aravind.finance.exceptions.CategoryException;
import com.aravind.finance.exceptions.ExpenseException;
import com.aravind.finance.exceptions.ModeExcpetion;
import com.aravind.finance.exceptions.UserIdException;
import com.aravind.finance.models.Expense;
import com.aravind.finance.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveOrUpdateExpense(Expense itemModel) {
        try {
            return expenseRepository.save(itemModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public Expense getSingleExpenseOfUser(String userId) {
        if (userId.isEmpty()) {
            throw new UserIdException(userId + " Does not exist");
        } else {
            Expense expense = expenseRepository.findByUserId(userId);
            if (expense == null)
                throw new ExpenseException(userId + "' did not make any payments");
            return expense;
        }
    }

    public Iterable<Expense> getAllExpensesOfUser(String userId) {
        if (userId.isEmpty()) {
            throw new UserIdException(userId + " Does not exist");
        } else {
            Iterable<Expense> allByUserId =
                    expenseRepository.findAllByUserId(userId);
            if (allByUserId.spliterator().getExactSizeIfKnown() == 0
                    || allByUserId.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No Expenses Found on the user " + userId);
            }
            return allByUserId;
        }
    }

    public Iterable<Expense> getAllExpensesByCategory(String category) {
        if (category.isEmpty()) {
            throw new CategoryException(category + "Not Found");
        } else {
            Iterable<Expense> allByCategory =
                    expenseRepository.findAllByCategoryIgnoreCase(category);
            if (allByCategory.spliterator().getExactSizeIfKnown() == 0
                    || allByCategory.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + category);
            }
            return allByCategory;
        }
    }

    public Iterable<Expense> getAllExpensesBySubCategory(String subCategory) {
        if (subCategory.isEmpty()) {
            throw new CategoryException(subCategory + "Not Found");
        } else {
            Iterable<Expense> allBySubCategory =
                    expenseRepository.findAllBySubCategoryIgnoreCase(subCategory);
            if (allBySubCategory.spliterator().getExactSizeIfKnown() == 0
                    || allBySubCategory.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + subCategory);
            }
            return allBySubCategory;
        }
    }

    public Iterable<Expense> getAllExpensesByMode(String mode) {
        if (mode.isEmpty()) {
            throw new ModeExcpetion(mode + "Not Found");
        } else {
            Iterable<Expense> allByPaymentMode =
                    expenseRepository.findAllByPaymentModeIgnoreCase(mode);
            if (allByPaymentMode.spliterator().getExactSizeIfKnown() == 0
                    || allByPaymentMode.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + mode);
            }
            return allByPaymentMode;
        }
    }
    public Expense getExpenseByID(int expenseId){
        Expense expense = expenseRepository.findByExpenseId(expenseId);
        if(expense == null) {
            throw new ExpenseException("There is no exception with the ID "+ expenseId);
        }
        return expense;
    }

    public void deleteExpenseByID(int expenseId) {
        Expense expense = expenseRepository.findByExpenseId(expenseId);
        if(expense == null){
            throw new ExpenseException("There is no expense with "+ expenseId +" Hence cannot delete it");
        }
        expenseRepository.delete(expense);
    }

    public List<Integer> deleteUserExpenses(String userId) {
        Iterable<Expense> allByUserId = expenseRepository.findAllByUserId(userId);
        if(allByUserId.spliterator().getExactSizeIfKnown() == 0
                || allByUserId.spliterator().getExactSizeIfKnown() == -1){
            throw new ExpenseException("No Expenses Found on the user " + userId);
        }
        List<Expense> expenses = expenseRepository.deleteAllByUserId(userId);
        return expenses.stream().map(expense -> expense.getExpenseId()).collect(Collectors.toList());

    }

}
