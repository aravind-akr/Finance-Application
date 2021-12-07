package com.aravind.finance.services;

import com.aravind.finance.exceptions.ExpenseException;
import com.aravind.finance.exceptions.ModeException;
import com.aravind.finance.exceptions.UserException;
import com.aravind.finance.models.Expense;
import com.aravind.finance.repositories.ExpenseRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    public Expense saveOrUpdateExpense(Expense expense) {
        log.info("Inside saveOrUpdateExpense method");
        try {
            log.info("Trying to save the expense");
            return expenseRepository.save(expense);
        } catch (Exception ex) {
            log.debug("There's some issue in saving the expense:"+ex.getMessage());
            ex.printStackTrace();
        }
        return null;
    }

    public Expense getSingleExpenseOfUser(String userId) {
        log.info("Inside getSingleExpenseOfUser method");
        if (userId.isEmpty()) {
            log.warn("The user with ID " + userId + " does not exist");
            throw new UserException(userId + " Does not exist");
        } else {
            Expense expense = expenseRepository.findByUserId(userId);
            if (expense == null) {
                log.debug(userId + " Did not make any payments");
                throw new ExpenseException(userId + "' did not make any payments");
            }
            log.info("The expense of the user " + userId + " is "+expense);
            return expense;
        }
    }

    public List<Expense> getAllExpensesOfUser(String userId) {
        log.info("Inside getAllExpensesOfUser method");
        if (userId.isEmpty()) {
            log.warn("The user with ID " + userId + " does not exist");
            throw new UserException(userId + " Does not exist");
        } else {
            List<Expense> allByUserId =
                    expenseRepository.findAllByUserId(userId);
            if (allByUserId.spliterator().getExactSizeIfKnown() == 0
                    || allByUserId.spliterator().getExactSizeIfKnown() == -1) {
                log.debug(userId + " Did not make any expenses");
                throw new ExpenseException("No Expenses Found on the user " + userId);
            }
            log.info("The list of expenses for the user " + userId + " are " + allByUserId);
            return allByUserId;
        }
    }

    public List<Expense> getAllExpensesByMode(String mode) {
        log.info("Inside getAllExpensesByMode method");
        if (mode.isEmpty()) {
            log.warn(mode+" not found");
            throw new ModeException(mode + "Not Found");
        } else {
            List<Expense> allByPaymentMode =
                    expenseRepository.findAllByPaymentModeIgnoreCase(mode);
            if (allByPaymentMode.spliterator().getExactSizeIfKnown() == 0
                    || allByPaymentMode.spliterator().getExactSizeIfKnown() == -1) {
                log.debug("No expenses found on " + mode + " found");
                throw new ExpenseException("No expenses found on " + mode);
            }
            log.info("The list of expenses on" + mode +" mode are " + allByPaymentMode);
            return allByPaymentMode;
        }
    }
    public Expense getExpenseByID(int expenseId){
        log.info("Inside getExpenseByID method");
        Expense expense = expenseRepository.findByExpenseId(expenseId);
        if(expense == null) {
            log.debug("No expenses found with " + expenseId);
            throw new ExpenseException("There is no exception with the ID "+ expenseId);
        }
        log.info("The expense details are " + expense);
        return expense;
    }

    public void deleteExpenseByID(int expenseId) {
        log.info("Inside deleteExpenseByID method");
        Expense expense = expenseRepository.findByExpenseId(expenseId);
        if(expense == null){
            log.debug("No expenses found with " + expenseId);
            throw new ExpenseException("There is no expense with "+ expenseId +" Hence cannot delete it");
        }
        expenseRepository.delete(expense);
        log.info("The expense with "+ expenseId + "is deleted");
    }

    public List<Integer> deleteUserExpenses(String userId) {
        log.info("Inside deleteUserExpenses method");
        Iterable<Expense> allByUserId = expenseRepository.findAllByUserId(userId);
        if(allByUserId.spliterator().getExactSizeIfKnown() == 0
                || allByUserId.spliterator().getExactSizeIfKnown() == -1){
            log.debug(userId + " Did not make any expenses");
            throw new ExpenseException("No Expenses Found on the user " + userId);
        }
        List<Expense> expenses = expenseRepository.deleteAllByUserId(userId);
        log.info("The list of expenses made by " + userId + " are deleted");
        return expenses.stream().map(Expense::getExpenseId).collect(Collectors.toList());
    }
}
