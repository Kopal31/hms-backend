package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "paymentId", scope = Payment.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    private Double amount;
    private String paymentMode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "bill_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Billing billing;
}
