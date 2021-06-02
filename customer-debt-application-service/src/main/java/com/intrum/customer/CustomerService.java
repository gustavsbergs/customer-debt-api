package com.intrum.customer;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public interface CustomerService {

    CustomerDto createCustomer(CustomerDto customer);

    void updateCustomer(Long id, CustomerDto customerDto);

    void deleteCustomer(Long customerId);

    CustomerDto searchCustomerById(Long customerId);

    List<CustomerDto> findAllCustomers();

    CustomerSummaryDto generateCustomerSummaryForSingleCustomer(Long customerId);

    List<CustomerSummaryDto> generateCustomerSummaryBySearchCriteria(LocalDateTime dueDate, BigDecimal debtAmount, String currency);

    List<CustomerDto> searchCustomersBySearchCriteria(String firstName, String lastName, String country, String email);

}
