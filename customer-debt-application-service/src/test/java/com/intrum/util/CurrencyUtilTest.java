package com.intrum.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CurrencyUtilTest {

    public static final BigInteger INTEGER_VALUE = BigInteger.valueOf(10050);
    public static final BigDecimal DECIMAL_VALUE = BigDecimal.valueOf(100.50);

    CurrencyUtil currencyUtil = new CurrencyUtil();

    @Test
    public void shouldConvertToCents() {
        BigInteger actualValue = currencyUtil.convertCurrencyToCents(DECIMAL_VALUE, "USD");

        assertEquals(INTEGER_VALUE, actualValue);
    }

    @Test
    public void shouldConvertToDecimal() {
        BigDecimal actualValue = currencyUtil.convertCurrencyToDecimal(INTEGER_VALUE, "USD");
        assertEquals(DECIMAL_VALUE, actualValue);
    }

}