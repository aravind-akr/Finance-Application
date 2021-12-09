package com.aravind.finance.controller;

import com.aravind.finance.exceptions.ExpenseException;
import com.aravind.finance.exceptions.UserException;
import com.aravind.finance.models.Expense;
import com.aravind.finance.repositories.ExpenseRepository;
import com.aravind.finance.services.MapValidationErrorService;
import com.aravind.finance.services.ExpenseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/finance/expenses")
@CrossOrigin
@Slf4j
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private ExpenseRepository expenseRepository;

    @PostMapping("/add")
    public ResponseEntity<?> createNewExpense(@Valid @RequestBody Expense expense,
                                              BindingResult result){
        log.info("Triggering the expense POST method");
        ResponseEntity<?> errorMap = mapValidationErrorService.mapValidationErrorService(result);
        if(errorMap!=null) return errorMap;

        Expense expense1 = expenseService.saveOrUpdateExpense(expense);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().
                replacePath("/finance/expenses/get/{expenseId}")
                .buildAndExpand(expense1.getExpenseId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/user-expense/{userId}")
    public RedirectView getExpensesOfUser(@PathVariable String userId, HttpServletResponse response) throws IOException {
        log.info("Getting the user's expense by user ID by redirecting");
        long userCount = expenseRepository.countByUserId(userId);
        RedirectView view = new RedirectView();
        view.setContextRelative(true);
        if(userCount==0){
            log.warn("No user with " + userId + " is found");
            throw new UserException(userId+" Not Found");
        }
        else if(userCount==1){
            log.info("Redirecting to user-single-expense API as only one expense found on the user " +userId);
            view.setUrl("/finance/expenses/user-single-expense/"+userId);
        }
        else{
            log.info("Redirecting to user-expenses API as more than one expense found on the user " +userId);
            view.setUrl("/finance/expenses/user-expenses/"+userId);
        }
        return view;
    }

    /**
     * If the userId has only one purchase
     * @param userId
     * @return
     */
    @GetMapping("/user-single-expense/{userId}")
    public EntityModel<Expense> getSingleExpensesOfUser(@PathVariable String userId){
        log.info("Get the single expense of the user "+userId);
        Expense expense = expenseService.getSingleExpenseOfUser(userId);
        EntityModel<Expense> entityModel = EntityModel.of(expense);
        entityModel.add(linkTo(methodOn(this.getClass()).getExpenseById(expense.getExpenseId())).withRel("expense-details"));
        entityModel.add(linkTo(methodOn(this.getClass()).getAllExpensesOfUser(userId)).withRel("user-expenses"));
        //entityModel.add(linkTo(methodOn(this.getClass()).getAllExpensesByPaymentMode(expense.getPaymentMode())).withRel("expenses-by-payment"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteExpenseById(expense.getExpenseId())).withRel("delete-expense"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteUserExpenses(userId)).withRel("delete-user-expenses"));
        return entityModel;
    }

    /**
     * If the userId has more than one purchases
     * @return
     */
    @GetMapping("/user-expenses/{userId}")
    public CollectionModel<EntityModel<Expense>> getAllExpensesOfUser(@PathVariable String userId){
        log.info("Get all the expenses of the User "+userId);
        List<Expense> allExpensesOfUser = expenseService.getAllExpensesOfUser(userId);
        List<EntityModel<Expense>> collect = allExpensesOfUser.stream().map(expense ->
                EntityModel.of(expense,
                        linkTo(methodOn(this.getClass()).getExpenseById(expense.getExpenseId())).withRel("expense-details"),
                        //linkTo(methodOn(this.getClass()).getAllExpensesByPaymentMode(expense.getPaymentMode())).withRel("expenses-by-mode"),
                        linkTo(methodOn(this.getClass()).deleteExpenseById(expense.getExpenseId())).withRel("delete-expense"),
                        linkTo(methodOn(this.getClass()).deleteUserExpenses(expense.getUserId())).withRel("delete-user-expenses")
                )).collect(Collectors.toList());
        return CollectionModel.of(collect);
    }

//    @GetMapping("/mode-expense/{mode}")
//    public CollectionModel<EntityModel<Expense>> getAllExpensesByPaymentMode(@PathVariable String mode){
//        log.info("Get the expenses by Payment Mode "+mode);
//        List<Expense> allExpensesByMode = expenseService.getAllExpensesByMode(mode);
//        List<EntityModel<Expense>> collect = allExpensesByMode.stream().map(expense ->
//                EntityModel.of(expense,
//                        linkTo(methodOn(this.getClass()).getExpenseById(expense.getExpenseId())).withRel("expense-details"),
//                        linkTo(methodOn(this.getClass()).getAllExpensesOfUser(expense.getUserId())).withRel("user-expenses"),
//                        linkTo(methodOn(this.getClass()).deleteExpenseById(expense.getExpenseId())).withRel("delete-expense"),
//                        linkTo(methodOn(this.getClass()).deleteUserExpenses(expense.getUserId())).withRel("delete-user-expenses")
//                )).collect(Collectors.toList());
//        return CollectionModel.of(collect);
//    }

    @GetMapping("/get/{expenseId}")
    public EntityModel<Expense> getExpenseById(@PathVariable int expenseId){
        log.info("Get the expense by ID "+expenseId);
        Expense expense = expenseService.getExpenseByID(expenseId);
        if(expense == null){
            log.debug("UhOh!! No expenses on "+ expenseId + " found");
            throw new ExpenseException("No expenses found");
        }
        EntityModel<Expense> entityModel = EntityModel.of(expense);
        entityModel.add(linkTo(methodOn(this.getClass()).getAllExpensesOfUser(expense.getUserId())).withRel("user-expenses"));
        //entityModel.add(linkTo(methodOn(this.getClass()).getAllExpensesByPaymentMode(expense.getPaymentMode())).withRel("expenses-by-payment"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteExpenseById(expense.getExpenseId())).withRel("delete-expense"));
        entityModel.add(linkTo(methodOn(this.getClass()).deleteUserExpenses(expense.getUserId())).withRel("delete-user-expenses"));
        return entityModel;
    }

    //Delete the expense with its id
    @DeleteMapping("/deleteEx/{expenseId}")
    public ResponseEntity<?> deleteExpenseById(@PathVariable int expenseId){
        log.info("Delete the expense by ID "+ expenseId);
        expenseService.deleteExpenseByID(expenseId);
        return new ResponseEntity<>("Expense with ID "+ expenseId +" is deleted", HttpStatus.OK);
    }

    //Delete the expenses of the user.
    @DeleteMapping("/deleteUs/{userId}")
    public ResponseEntity<?> deleteUserExpenses(@PathVariable String userId){
        log.info("Deleting all the expense of the user "+ userId);
        List<Integer> expenses = expenseService.deleteUserExpenses(userId);
        return new ResponseEntity<>(String.format("User with ID %s expenses %s are deleted", userId, expenses),HttpStatus.OK);
    }
}
