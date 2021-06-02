package com.intrum.util;

import com.intrum.customer.dto.CustomerDto;
import com.intrum.debt.dto.DebtDto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QueryUtilTest {

    private static final String STRING_PARAM = "param";
    private static final LocalDateTime DATE_PARAM = LocalDateTime.now();
    private static final BigInteger CURRENCY_PARAM = BigInteger.valueOf(1000);
    private static final Long ID_PARAM = 1L;

    private static final int EXPECTED_SIZE_ALL_PARAMS = 9;
    private static final int EXPECTED_SIZE_SOME_PARAMS = 4;
    private static final int EXPECTED_SIZE_ALL_PARAMS_DEBT = 5;
    private static final int EXPECTED_SIZE_SOME_PARAMS_DEBT = 3;

    private static final String EXPECTED_UPDATE_QUERY_ALL_FIELDS = "UPDATE Customer c SET c.country = :country, c.email = :email, c.userName = :userName, c.firstName = :firstName, c.lastName = :lastName WHERE c.id = :id";
    private static final String EXPECTED_UPDATE_QUERY_SOME_FIELDS = "UPDATE Customer c SET c.country = :country, c.email = :email, c.firstName = :firstName WHERE c.id = :id";
    private static final String EXPECTED_SEARCH_QUERY_ALL_FIELDS = "SELECT c FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName AND c.country = :country AND c.email = :email";
    private static final String EXPECTED_SEARCH_QUERY_SOME_FIELDS = "SELECT c FROM Customer c WHERE c.firstName = :firstName AND c.lastName = :lastName";
    private static final String EXPECTED_DEBT_SEARCH_QUERY_ALL_FIELDS = "SELECT d FROM Debt d where d.duedate <= :dueDate AND (SELECT SUM(dd.amount) FROM Debt dd WHERE dd.currency = :currency  AND dd.user_id = d.user_id) > :amount";
    private static final String EXPECTED_DEBT_SEARCH_QUERY_SOME_FIELDS = "SELECT d FROM Debt d where d.duedate <= :dueDate";
    private static final String EXPECTED_DEBT_UPDATE_QUERY_ALL_FIELDS = "UPDATE Debt d SET d.amount = :amount, d.dueDate = :dueDate, d.currency = :currency, d.user_id = :userId WHERE d.id = :id";
    private static final String EXPECTED_DEBT_UPDATE_QUERY_SOME_FIELDS = "UPDATE Debt d SET d.amount = :amount, d.dueDate = :dueDate WHERE d.id = :id";


    QueryUtil queryUtil = new QueryUtil();


    @Test
    public void shouldBuildCustomerQueryParams() {
        Map<String, Object> params = queryUtil.createCustomerQueryParameters(STRING_PARAM, STRING_PARAM, STRING_PARAM,
                STRING_PARAM, STRING_PARAM, ID_PARAM, DATE_PARAM, CURRENCY_PARAM, STRING_PARAM);

        assertEquals(EXPECTED_SIZE_ALL_PARAMS, params.size());
    }

    @Test
    public void shouldBuildCorrectCustomerQueryParams_whenSomeParamsNull() {
        Map<String, Object> params = queryUtil.createCustomerQueryParameters(STRING_PARAM, null, null, null, null, ID_PARAM, DATE_PARAM, CURRENCY_PARAM, null);

        assertEquals(EXPECTED_SIZE_SOME_PARAMS, params.size());
        assertTrue(params.containsKey("firstName"));
        assertTrue(params.containsKey("id"));
        assertTrue(params.containsKey("amount"));
        assertTrue(params.containsKey("dueDate"));
    }

    @Test
    public void shouldBuildDebtQueryParams() {
        Map<String, Object> params = queryUtil.createDebtQueryParameters(ID_PARAM, ID_PARAM, STRING_PARAM, DATE_PARAM, CURRENCY_PARAM);

        assertEquals(EXPECTED_SIZE_ALL_PARAMS_DEBT, params.size());
    }

    @Test
    public void shouldBuildCorrectDebtQueryParams_whenSomeParamsNull() {
        Map<String, Object> params = queryUtil.createDebtQueryParameters(ID_PARAM, null, STRING_PARAM, DATE_PARAM, null);

        assertEquals(EXPECTED_SIZE_SOME_PARAMS_DEBT, params.size());
        assertTrue(params.containsKey("id"));
        assertTrue(params.containsKey("currency"));
        assertTrue(params.containsKey("dueDate"));
    }

    @Test
    public void shouldBuildCustomerUpdateQuery() {
        CustomerDto customerDto = createCustomerDto();

        String updateQuery = queryUtil.createCustomerUpdateQueryString(customerDto);

        assertEquals(EXPECTED_UPDATE_QUERY_ALL_FIELDS, updateQuery);
    }

    @Test
    public void shouldBuildCorrectCustomerUpdateQuery_whenSomeFieldsNull() {
        CustomerDto customerDto = createCustomerDto();
        customerDto.setUserName(null);
        customerDto.setLastName(null);

        String updateQuery = queryUtil.createCustomerUpdateQueryString(customerDto);

        assertEquals(EXPECTED_UPDATE_QUERY_SOME_FIELDS, updateQuery);
    }

    @Test
    public void shouldBuildCustomerSearchQuery() {
        String searchQuery = queryUtil.createCustomerSearchQueryString(STRING_PARAM, STRING_PARAM, STRING_PARAM, STRING_PARAM);

        assertEquals(EXPECTED_SEARCH_QUERY_ALL_FIELDS, searchQuery);
    }

    @Test
    public void shouldBuildCorrectCustomerSearchQuery_whenSomeFieldsNull() {
        String searchQuery = queryUtil.createCustomerSearchQueryString(STRING_PARAM, STRING_PARAM, null, null);

        assertEquals(EXPECTED_SEARCH_QUERY_SOME_FIELDS, searchQuery);
    }

    @Test
    public void shouldBuildDebtSearchQuery() {
        String searchQuery = queryUtil.createDebtSearchQueryString(DATE_PARAM, CURRENCY_PARAM);

        assertEquals(EXPECTED_DEBT_SEARCH_QUERY_ALL_FIELDS, searchQuery);
    }

    @Test
    public void shouldBuildCorrectDebtSearchQuery_whenSomeFieldsNull() {
        String searchQuery = queryUtil.createDebtSearchQueryString(DATE_PARAM, null);

        assertEquals(EXPECTED_DEBT_SEARCH_QUERY_SOME_FIELDS, searchQuery);
    }

    @Test
    public void shouldBuildDebtUpdateQuery() {
        DebtDto debtDto = createDebtDto();

        String updateQuery = queryUtil.createDebtUpdateQuery(debtDto);

        assertEquals(EXPECTED_DEBT_UPDATE_QUERY_ALL_FIELDS, updateQuery);
    }

    @Test
    public void shouldBuildCorrectDebtUpdateQuery_whenSomeFieldsNull() {
        DebtDto debtDto = createDebtDto();
        debtDto.setCustomerId(null);
        debtDto.setCurrency(null);

        String updateQuery = queryUtil.createDebtUpdateQuery(debtDto);

        assertEquals(EXPECTED_DEBT_UPDATE_QUERY_SOME_FIELDS, updateQuery);
    }

    private static DebtDto createDebtDto() {
        DebtDto debtDto = new DebtDto();
        debtDto.setAmount(BigDecimal.valueOf(100.00));
        debtDto.setCurrency("USD");
        debtDto.setCustomerId(1L);
        debtDto.setDueDate(LocalDateTime.now());
        return debtDto;
    }

    private static CustomerDto createCustomerDto() {
        CustomerDto customerDto = new CustomerDto();
        customerDto.setCountry("USA");
        customerDto.setEmail("email@email.com");
        customerDto.setId(1L);
        customerDto.setFirstName("John");
        customerDto.setLastName("Smith");
        customerDto.setUserName("JohnSmith");
        return customerDto;
    }
}