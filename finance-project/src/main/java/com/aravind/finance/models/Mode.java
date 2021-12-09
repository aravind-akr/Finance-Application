package com.aravind.finance.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
public class Mode {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Getter @Setter private int id;

    @NotBlank(message = "Mode name is a must")
    @Getter @Setter private String modeName;

    @OneToOne(mappedBy = "paymentMode", cascade = CascadeType.ALL)
    private Expense expense;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column(updatable = false)
    private Date createdAt;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @LastModifiedDate
    private Date updatedAt;

    @PrePersist
    protected void onCreate(){this.createdAt = new Date();}

    @PreUpdate
    protected void onUpdate(){this.updatedAt = new Date();}
}
