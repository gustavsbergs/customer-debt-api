package com.intrum.debt.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

//@ApiModel(value = "Dto used for sending/receiving Debt related data")
public class DebtDto {

    @ApiModelProperty(value = "ID of debt entry", name = "id", example = "2", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @ApiModelProperty(value = "Amount of debt", name = "amount", example = "100.50")
    private BigDecimal amount;

    @ApiModelProperty(value = "Currency of debt", name = "currency", example = "USD")
    private String currency;

    @ApiModelProperty(value = "Due date for debt", name = "dueDate", example = "2022-01-01T00:00:00.000")
    private LocalDateTime dueDate;

    @ApiModelProperty(value = "Customer id", name = "customerId", example = "1")
    private Long customerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }


    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }


    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
