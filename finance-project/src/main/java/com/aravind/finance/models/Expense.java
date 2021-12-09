package com.aravind.finance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
@NoArgsConstructor
public class Expense {

    @NotBlank(message = "user Id is required")
    private String userId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int expenseId;

    @NotBlank(message = "Item Name is required")
    private String expenseName;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "category_id",referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"createdAt","updatedAt"})
    private Category category;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "sub_category_id",referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"createdAt","updatedAt"})
    private SubCategory subCategory;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    @NotNull(message = "Price is required")
    private Double amount;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name="paymentMode_id",referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"createdAt","updatedAt"})
    private Mode paymentMode;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {this.createdAt = new Date();}

    @PreUpdate
    protected void onUpdate() {this.updatedAt = new Date();}

}
