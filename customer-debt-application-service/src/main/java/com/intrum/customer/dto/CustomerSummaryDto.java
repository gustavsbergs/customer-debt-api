package com.intrum.customer.dto;

import com.intrum.debt.dto.DebtDto;

import java.util.List;

public class CustomerSummaryDto {
    private Long id;
    private String firstName;
    private String lastName;
    private List<DebtDto> debtSummary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<DebtDto> getDebtSummary() {
        return debtSummary;
    }

    public void setDebtSummary(List<DebtDto> debtSummary) {
        this.debtSummary = debtSummary;
    }

}
