package com.intrum.api.rest;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerRestService {

    ResponseEntity<CustomerDto> createCustomer(CustomerDto customerDto);

    ResponseEntity<Void> updateCustomer(Long id, CustomerDto customerDto);

    ResponseEntity<Void> deleteCustomer(Long id);

    ResponseEntity<CustomerDto> getCustomer(Long id);

    ResponseEntity<List<CustomerDto>> getCustomers(String firstName, String lastName, String country, String email);

    ResponseEntity<CustomerSummaryDto> getCustomerSummary(Long id);

    ResponseEntity<List<CustomerSummaryDto>> getCustomerSummaryBySearchCriteria(LocalDateTime dueDate, BigDecimal amount, String currency);
}
