package com.intrum.customer.mapper;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.entity.Debt;

import java.util.List;

public interface CustomerMappingProvider {

    CustomerDto toDto(Customer customer);

    Customer toDomain(CustomerDto customerDto);

    CustomerSummaryDto toSummary(Customer customer, List<Debt> debtList);
}
