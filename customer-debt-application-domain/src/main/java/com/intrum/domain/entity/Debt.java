package com.intrum.domain.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "debt")
public class Debt {

    @Id
    @SequenceGenerator(name = "debt_debt_id_seq",
            sequenceName = "debt_debt_id_seq",
            allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "debt_debt_id_seq")
    @Column(name = "debt_id", updatable = false)
    Long id;

    @Column(name = "amount")
    BigInteger amount;

    @Column(name = "duedate")
    LocalDateTime dueDate;

    @Column(name = "customer_id")
    Long user_id;

    @Column(name = "currency")
    String currency;


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
}
