package com.intrum.debt.mapper.impl;

import com.intrum.debt.dto.DebtDto;
import com.intrum.debt.mapper.DebtMappingProvider;
import com.intrum.domain.entity.Debt;
import com.intrum.util.CurrencyUtil;

public class DebtMappingProviderImpl implements DebtMappingProvider {

    CurrencyUtil currencyUtil;

    @Override
    public Debt mapToDomain(DebtDto debtDto) {
        Debt debt = new Debt();
        debt.setAmount(currencyUtil.convertCurrencyToCents(debtDto.getAmount(), debtDto.getCurrency()));
        debt.setDueDate(debtDto.getDueDate());
        debt.setCurrency(debtDto.getCurrency());
        debt.setUser_id(debtDto.getCustomerId());
        return debt;
    }

    @Override
    public DebtDto mapToDto(Debt debt) {
        DebtDto dto = new DebtDto();
        dto.setAmount(currencyUtil.convertCurrencyToDecimal(debt.getAmount(), debt.getCurrency()));
        dto.setCurrency(debt.getCurrency());
        dto.setDueDate(debt.getDueDate());
        dto.setCustomerId(debt.getUser_id());
        dto.setId(debt.getId());
        return dto;
    }

    public void setCurrencyUtil(CurrencyUtil currencyUtil) {
        this.currencyUtil = currencyUtil;
    }
}
