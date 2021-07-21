package com.aravind.finance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class ExpenseModel {

    @NotBlank(message = "user Id is required")
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expenseId;

    @NotBlank(message = "Item Name is required")
    private String expenseName;

    @NotBlank(message = "Item Category is required")
    private String category;

    private String subCategory;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    @NotNull(message = "Price is required")
    private Double amount;

    @NotBlank(message = "Payment Mode is required")
    private String paymentMode;

    public ExpenseModel(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(int expenseId) {
        this.expenseId = expenseId;
    }

    public String getExpenseName() {
        return expenseName;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getSubCategory() {
        return subCategory;
    }

    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }
}
