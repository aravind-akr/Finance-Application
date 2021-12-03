package com.aravind.finance.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "SubCategory")
@NoArgsConstructor
public class SubCategory {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Getter @Setter private Integer id;

    @NotBlank(message = "Category is a must")
    @Getter @Setter private String subCategory;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinColumn(name = "category")
    @JsonBackReference
    @Getter @Setter private Category category;

    @OneToOne(mappedBy="subCategory", cascade = CascadeType.ALL)
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

}
