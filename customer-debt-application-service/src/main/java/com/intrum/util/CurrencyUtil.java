package com.intrum.util;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class CurrencyUtil {

    public static final Map<String, BigDecimal> currencyConversion = new HashMap<String, BigDecimal>() {{
        put("USD", BigDecimal.valueOf(100));
        put("EUR", BigDecimal.valueOf(100));
    }};

    public BigInteger convertCurrencyToCents(BigDecimal value, String currency) {
        return value.multiply(currencyConversion.get(currency)).toBigInteger();
    }

    public BigDecimal convertCurrencyToDecimal(BigInteger value, String currency) {
        if(value != null) {
            BigDecimal decimalValue = new BigDecimal(value);
            return decimalValue.divide(currencyConversion.get(currency));
        }
        return null;
    }
}
