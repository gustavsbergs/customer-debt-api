package com.intrum.debt.mapper.impl;

import com.intrum.debt.dto.DebtDto;
import com.intrum.domain.entity.Debt;
import com.intrum.util.CurrencyUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class DebtMappingProviderImplTest {

    private static final String CURRENCY = "USD";
    private static final long USER_ID = 1L;
    private static final LocalDateTime DATE = LocalDateTime.now();
    private static final BigDecimal DECIMAL_AMOUNT = BigDecimal.valueOf(100.50);
    private static final BigInteger INTEGER_VALUE = BigInteger.valueOf(10050);
    private static final long ID = 3L;

    @Mock
    CurrencyUtil currencyUtil;

    @InjectMocks
    DebtMappingProviderImpl debtMappingProvider;

    @Test
    public void shouldMapToDomain() {
        DebtDto debtDto = createDto();
        when(currencyUtil.convertCurrencyToCents(eq(DECIMAL_AMOUNT), eq(CURRENCY))).thenReturn(INTEGER_VALUE);

        Debt debt = debtMappingProvider.mapToDomain(debtDto);

        assertEquals(INTEGER_VALUE, debt.getAmount());
        assertEquals(CURRENCY, debt.getCurrency());
        assertEquals(DATE, debt.getDueDate());
        assertEquals(USER_ID, debt.getUser_id());
    }

    @Test
    public void shouldMapToDto() {
        Debt debt = createDomain();
        when(currencyUtil.convertCurrencyToDecimal(eq(INTEGER_VALUE), eq(CURRENCY))).thenReturn(DECIMAL_AMOUNT);

        DebtDto debtDto = debtMappingProvider.mapToDto(debt);

        assertEquals(DECIMAL_AMOUNT, debtDto.getAmount());
        assertEquals(CURRENCY, debtDto.getCurrency());
        assertEquals(DATE, debtDto.getDueDate());
        assertEquals(USER_ID, debtDto.getCustomerId());
        assertEquals(ID, debtDto.getId());
    }

    private static Debt createDomain() {
        Debt debt = new Debt();
        debt.setAmount(INTEGER_VALUE);
        debt.setCurrency(CURRENCY);
        debt.setDueDate(DATE);
        debt.setUser_id(USER_ID);
        debt.setId(ID);
        return debt;
    }

    private static DebtDto createDto() {
        DebtDto debtDto = new DebtDto();
        debtDto.setCurrency(CURRENCY);
        debtDto.setCustomerId(USER_ID);
        debtDto.setDueDate(DATE);
        debtDto.setAmount(DECIMAL_AMOUNT);
        return debtDto;
    }
}