package com.intrum.customer.mapper.impl;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import com.intrum.debt.dto.DebtDto;
import com.intrum.debt.mapper.DebtMappingProvider;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.entity.Debt;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomerMappingProviderImplTest {

    private static final String FIRST_NAME = "John";
    private static final String LAST_NAME = "Smith";
    private static final String COUNTRY = "USA";
    private static final String EMAIL = "email@email.com";
    private static final String USER_NAME = "JohnSmith";
    private static final Long ID = 1L;
    private static final BigInteger DECIMAL_AMOUNT = BigInteger.valueOf(10050);
    private static final String CURRENCY = "USD";
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final Long DEBT_ID = 2L;


    @Mock
    DebtMappingProvider debtMappingProvider;

    @InjectMocks
    CustomerMappingProviderImpl customerMappingProvider;

    @Test
    public void shouldMapToDto() {
        Customer customer = createDomain();

        CustomerDto mappedDto = customerMappingProvider.toDto(customer);

        assertEquals(FIRST_NAME, mappedDto.getFirstName());
        assertEquals(LAST_NAME, mappedDto.getLastName());
        assertEquals(COUNTRY, mappedDto.getCountry());
        assertEquals(EMAIL, mappedDto.getEmail());
        assertEquals(USER_NAME, mappedDto.getUserName());
        assertEquals(ID, mappedDto.getId());
    }

    @Test
    public void shouldMapToDomain() {
        CustomerDto customerDto = createDto();

        Customer customer = customerMappingProvider.toDomain(customerDto);

        assertEquals(FIRST_NAME, customer.getFirstName());
        assertEquals(LAST_NAME, customer.getLastName());
        assertEquals(COUNTRY, customer.getCountry());
        assertEquals(EMAIL, customer.getEmail());
        assertEquals(USER_NAME, customer.getUserName());
    }

    @Test
    public void shouldMapToSummary() {
        Customer customer = createDomain();
        Debt debt1 = createDebt();
        Debt debt2 = createDebt();

        when(debtMappingProvider.mapToDto(any(Debt.class))).thenReturn(new DebtDto());
        CustomerSummaryDto summary = customerMappingProvider.toSummary(customer, Arrays.asList(debt1, debt2));

        assertEquals(2, summary.getDebtSummary().size());
        assertEquals(FIRST_NAME, summary.getFirstName());
        assertEquals(LAST_NAME, summary.getLastName());
        assertEquals(ID, summary.getId());
    }

    @Test
    public void shouldFilterDebts_whenIdDoesntMatchUser() {
        Customer customer = createDomain();
        Debt debt1 = createDebt();
        Debt debt2 = createDebt();
        debt2.setUser_id(4L);

        when(debtMappingProvider.mapToDto(any(Debt.class))).thenReturn(new DebtDto());
        CustomerSummaryDto summary = customerMappingProvider.toSummary(customer, Arrays.asList(debt1, debt2));

        assertEquals(1, summary.getDebtSummary().size());
        assertEquals(FIRST_NAME, summary.getFirstName());
        assertEquals(LAST_NAME, summary.getLastName());
        assertEquals(ID, summary.getId());
    }

    private static Customer createDomain() {
        Customer customer = new Customer();
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);
        customer.setCountry(COUNTRY);
        customer.setEmail(EMAIL);
        customer.setUserName(USER_NAME);
        customer.setId(ID);
        return customer;
    }

    private static CustomerDto createDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setLastName(LAST_NAME);
        customerDto.setFirstName(FIRST_NAME);
        customerDto.setCountry(COUNTRY);
        customerDto.setEmail(EMAIL);
        customerDto.setUserName(USER_NAME);
        return customerDto;
    }

    private static Debt createDebt() {
        Debt debt = new Debt();
        debt.setAmount(DECIMAL_AMOUNT);
        debt.setCurrency(CURRENCY);
        debt.setDueDate(DATE);
        debt.setUser_id(ID);
        debt.setId(DEBT_ID);
        return debt;
    }
}