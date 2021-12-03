package com.aravind.finance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter @Setter private int id;

    @NotBlank(message = "Category is a must")
    @Getter @Setter private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "category", orphanRemoval = true)
    @JsonIgnore
    @Getter @Setter private List<SubCategory> subCategories = new ArrayList<>();

    @OneToOne(mappedBy="category", cascade = CascadeType.ALL)
    private Expense expense;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {this.createdAt = new Date();}

    @PreUpdate
    protected void onUpdate() {this.updatedAt = new Date();}

    public void addSubCategory(SubCategory subCategory) {
        subCategories.add(subCategory);
        subCategory.setCategory(this);
    }

    public void removeSubCategory(SubCategory subCategory) {
        subCategories.remove(subCategory);
        subCategory.setCategory(null);
    }
}
