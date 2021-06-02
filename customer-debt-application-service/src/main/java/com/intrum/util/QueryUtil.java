package com.intrum.util;

import com.intrum.customer.constants.QueryConstants;
import com.intrum.customer.dto.CustomerDto;
import com.intrum.debt.dto.DebtDto;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryUtil {

    public Map<String, Object> createCustomerQueryParameters(String firstName, String lastName, String country, String username,
                                                             String email, Long id, LocalDateTime dueDate, BigInteger amount, String currency) {
        Map<String, Object> params = new HashMap<>();
        if (firstName != null) {
            params.put("firstName", firstName);
        }
        if (lastName != null) {
            params.put("lastName", lastName);
        }
        if (country != null) {
            params.put("country", country);
        }
        if (username != null) {
            params.put("userName", username);
        }
        if (email != null) {
            params.put("email", email);
        }
        if (id != null) {
            params.put("id", id);
        }
        if (amount != null) {
            params.put("amount", amount);
        }
        if (dueDate != null) {
            params.put("dueDate", dueDate);
        }
        if(currency != null) {
            params.put("currency", currency);
        }
        return params;
    }


    public Map<String, Object> createDebtQueryParameters(Long id, Long userId, String currency, LocalDateTime dueDate, BigInteger amount) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        if (userId != null) {
            params.put("userId", userId);
        }
        if (currency != null) {
            params.put("currency", currency);
        }
        if (amount != null) {
            params.put("amount", amount);
        }
        if (dueDate != null) {
            params.put("dueDate", dueDate);
        }
        return params;
    }

    public String createCustomerUpdateQueryString(CustomerDto customer) {
        List<String> queryElements = new ArrayList<>();
        if (customer.getCountry() != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_COUNTRY);
        }
        if (customer.getEmail() != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_EMAIL);
        }
        if (customer.getUserName() != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_USERNAME);
        }
        if (customer.getFirstName() != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_FIRST_NAME);
        }
        if (customer.getLastName() != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_LAST_NAME);
        }
        return QueryConstants.CUSTOMER_QUERY_UPDATE + String.join(", ", queryElements) + QueryConstants.CUSTOMER_QUERY_WHERE;
    }

    public String createCustomerSearchQueryString(String firstName, String lastName, String country, String email) {
        List<String> queryElements = new ArrayList<>();
        if (firstName != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_FIRST_NAME);
        }
        if (lastName != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_LAST_NAME);
        }
        if (country != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_COUNTRY);
        }
        if (email != null) {
            queryElements.add(QueryConstants.CUSTOMER_QUERY_EMAIL);
        }
        return QueryConstants.CUSTOMER_QUERY_SEARCH + String.join(QueryConstants.AND, queryElements);
    }

    public String createDebtSearchQueryString(LocalDateTime dueDate, BigInteger amount) {
        List<String> queryElements = new ArrayList<>();
        if (dueDate != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_SEARCH_DUE_DATE);
        }
        if (amount != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_SEARCH_AMOUNT);
        }
        return QueryConstants.DEBT_QUERY_SEARCH + String.join(QueryConstants.AND, queryElements);
    }

    public String createDebtUpdateQuery(DebtDto debtDto) {
        List<String> queryElements = new ArrayList<>();
        if (debtDto.getAmount() != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_UPDATE_AMOUNT);
        }
        if (debtDto.getDueDate() != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_UPDATE_DUE_DATE);
        }
        if (debtDto.getCurrency() != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_UPDATE_CURRENCY);
        }
        if (debtDto.getCustomerId() != null) {
            queryElements.add(QueryConstants.DEBT_QUERY_UPDATE_USER_ID);
        }
        return QueryConstants.DEBT_QUERY_UPDATE + String.join(", ", queryElements) + QueryConstants.DEBT_QUERY_WHERE;
    }
}
