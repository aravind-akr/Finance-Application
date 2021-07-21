package com.aravind.finance.services;

import com.aravind.finance.exceptions.CategoryException;
import com.aravind.finance.exceptions.ExpenseException;
import com.aravind.finance.exceptions.ModeExcpetion;
import com.aravind.finance.exceptions.UserIdException;
import com.aravind.finance.models.ExpenseModel;
import com.aravind.finance.repositories.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public ExpenseModel saveOrUpdateExpense(ExpenseModel itemModel) {
        try {
            return expenseRepository.save(itemModel);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ExpenseModel getSingleExpenseOfUser(String userId) {
        if (userId.isEmpty()) {
            throw new UserIdException(userId + " Does not exist");
        } else {
            ExpenseModel expenseModel = expenseRepository.findByUserId(userId);
            if (expenseModel == null)
                throw new ExpenseException(userId + "' did not make any payments");
            return expenseModel;
        }
    }

    public Iterable<ExpenseModel> getAllExpensesOfUser(String userId) {
        if (userId.isEmpty()) {
            throw new UserIdException(userId + " Does not exist");
        } else {
            Iterable<ExpenseModel> allByUserId =
                    expenseRepository.findAllByUserId(userId);
            if (allByUserId.spliterator().getExactSizeIfKnown() == 0
                    || allByUserId.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No Expenses Found on the user " + userId);
            }
            return allByUserId;
        }
    }

    public Iterable<ExpenseModel> getAllExpensesByCategory(String category) {
        if (category.isEmpty()) {
            throw new CategoryException(category + "Not Found");
        } else {
            Iterable<ExpenseModel> allByCategory =
                    expenseRepository.findAllByCategoryIgnoreCase(category);
            if (allByCategory.spliterator().getExactSizeIfKnown() == 0
                    || allByCategory.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + category);
            }
            return allByCategory;
        }
    }

    public Iterable<ExpenseModel> getAllExpensesBySubCategory(String subCategory) {
        if (subCategory.isEmpty()) {
            throw new CategoryException(subCategory + "Not Found");
        } else {
            Iterable<ExpenseModel> allBySubCategory =
                    expenseRepository.findAllBySubCategoryIgnoreCase(subCategory);
            if (allBySubCategory.spliterator().getExactSizeIfKnown() == 0
                    || allBySubCategory.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + subCategory);
            }
            return allBySubCategory;
        }
    }

    public Iterable<ExpenseModel> getAllExpensesByMode(String mode) {
        if (mode.isEmpty()) {
            throw new ModeExcpetion(mode + "Not Found");
        } else {
            Iterable<ExpenseModel> allByPaymentMode =
                    expenseRepository.findAllByPaymentModeIgnoreCase(mode);
            if (allByPaymentMode.spliterator().getExactSizeIfKnown() == 0
                    || allByPaymentMode.spliterator().getExactSizeIfKnown() == -1) {
                throw new ExpenseException("No expenses found on " + mode);
            }
            return allByPaymentMode;
        }
    }

}
