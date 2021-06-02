package com.intrum.customer.mapper.impl;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import com.intrum.customer.mapper.CustomerMappingProvider;
import com.intrum.debt.mapper.DebtMappingProvider;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.entity.Debt;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMappingProviderImpl implements CustomerMappingProvider {

    private DebtMappingProvider debtMappingProvider;

    @Override
    public CustomerDto toDto(Customer customer) {
        CustomerDto dto = new CustomerDto();
        dto.setCountry(customer.getCountry());
        dto.setFirstName(customer.getFirstName());
        dto.setLastName(customer.getLastName());
        dto.setEmail(customer.getEmail());
        dto.setUserName(customer.getUserName());
        dto.setId(customer.getId());
        return dto;
    }

    @Override
    public Customer toDomain(CustomerDto customerDto) {
        Customer customer = new Customer();
        customer.setFirstName(customerDto.getFirstName());
        customer.setLastName(customerDto.getLastName());
        customer.setUserName(customerDto.getUserName());
        customer.setCountry(customerDto.getCountry());
        customer.setEmail(customerDto.getEmail());
        return customer;
    }

    @Override
    public CustomerSummaryDto toSummary(Customer customer, List<Debt> debtList) {
        CustomerSummaryDto summaryDto = new CustomerSummaryDto();
        summaryDto.setFirstName(customer.getFirstName());
        summaryDto.setLastName(customer.getLastName());
        summaryDto.setId(customer.getId());
        summaryDto.setDebtSummary(debtList.stream()
                .filter(debt -> debt.getUser_id().equals(customer.getId()))
                .map(debt -> debtMappingProvider.mapToDto(debt))
                .collect(Collectors.toList()));
        return summaryDto;
    }

    public void setDebtMappingProvider(DebtMappingProvider debtMappingProvider) {
        this.debtMappingProvider = debtMappingProvider;
    }
}
