package com.intrum.debt.service;

import com.intrum.debt.DebtService;
import com.intrum.debt.dto.DebtDto;
import com.intrum.debt.mapper.DebtMappingProvider;
import com.intrum.domain.dao.CustomerDao;
import com.intrum.domain.dao.DebtDao;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.entity.Debt;
import com.intrum.exception.ProcessingException;
import com.intrum.exception.ValidationException;
import com.intrum.util.CurrencyUtil;
import com.intrum.util.QueryUtil;

import java.math.BigDecimal;
import java.util.Map;

public class DebtServiceImpl implements DebtService {

    private DebtDao debtDao;

    private CustomerDao customerDao;

    private DebtMappingProvider debtMappingProvider;

    private CurrencyUtil currencyUtil;

    private QueryUtil queryUtil;

    @Override
    public DebtDto createDebt(DebtDto debtDto) {
        validateDebtInfoCreate(debtDto);
        Customer customer = customerDao.findById(debtDto.getCustomerId());
        if (customer == null) {
            throw new ProcessingException("Customer with id: " + debtDto.getCustomerId() + " not found.");
        }
        return debtMappingProvider.mapToDto(debtDao.save(debtMappingProvider.mapToDomain(debtDto)));
    }

    @Override
    public void updateDebt(DebtDto debtDto, Long id) {
        validateDebtInfoUpdate(debtDto, id);

        if (debtDto.getCustomerId() != null) {
            Customer customer = customerDao.findById(debtDto.getCustomerId());
            if (customer == null) {
                throw new ProcessingException("Customer with id: " + debtDto.getCustomerId() + " not found.");
            }
        }
        Debt debt = debtDao.findById(id);
        if (debt == null) {
            throw new ProcessingException("Debt with id : " + id + " not found.");
        }
        BigDecimal debtAmount = null;
        if (debtDto.getAmount() != null) {
            debtAmount = debtDto.getAmount();
        }
        String query = queryUtil.createDebtUpdateQuery(debtDto);
        Map<String, Object> params = queryUtil.createDebtQueryParameters(id, debtDto.getCustomerId(), debtDto.getCurrency(), debtDto.getDueDate(), currencyUtil.convertCurrencyToCents(debtAmount, debtDto.getCurrency()));
        debtDao.updateDebt(query, params);
    }


    @Override
    public void deleteDebt(Long id) {
        Debt debt = debtDao.findById(id);
        if (debt == null) {
            throw new ProcessingException("Debt with id: " + id + " not found.");
        }
        debtDao.delete(debt);
    }

    @Override
    public DebtDto findDebtById(Long id) {
        Debt debt = debtDao.findById(id);
        if (debt == null) {
            throw new ProcessingException("Debt with id: " + id + " not found.");
        }
        return debtMappingProvider.mapToDto(debt);
    }

    private void validateDebtInfoCreate(DebtDto debtDto) {
        if (debtDto.getAmount() == null) {
            throw new ValidationException("amount", "Missing field");
        }
        if (validateAmount(debtDto.getAmount())) {
            throw new ValidationException(debtDto.getAmount().toPlainString(), "Debt amount cannot be negative or 0:");
        }
        if (debtDto.getCurrency() == null) {
            throw new ValidationException("currency", "Missing field");
        } else {
            if (!validateCurrency(debtDto.getCurrency())) {
                throw new ValidationException(debtDto.getCurrency(), "Unrecognized/unsupported currency:");
            }
        }
        if (debtDto.getCustomerId() == null) {
            throw new ValidationException("userId", "Missing field");
        }
        if (debtDto.getDueDate() == null) {
            throw new ValidationException("dueDate", "Missing field");
        }
    }

    private void validateDebtInfoUpdate(DebtDto debtDto, Long id) {
        if (id == null) {
            throw new ValidationException("id", "Missing field");
        }
        if (debtDto.getDueDate() == null && debtDto.getCustomerId() == null &&
                debtDto.getAmount() == null && debtDto.getCurrency() == null) {
            throw new ValidationException("At least one field must be provided to update debt.");
        }
        if (debtDto.getAmount() != null && debtDto.getCurrency() != null && validateAmount(debtDto.getAmount()) && validateCurrency(debtDto.getCurrency())) {
            throw new ValidationException(debtDto.getAmount().toPlainString(), "Currency missing or invalid. Debt amount cannot be negative or 0:");
        }
        if (debtDto.getCurrency() != null && !validateCurrency(debtDto.getCurrency())) {
            throw new ValidationException(debtDto.getCurrency(), "Unrecognized/unsupported currency:");
        }
    }

    private boolean validateAmount(BigDecimal amount) {
        return amount.compareTo(BigDecimal.valueOf(0)) <= 0;
    }

    private boolean validateCurrency(String currency) {
        return CurrencyUtil.currencyConversion.containsKey(currency);
    }

    public void setDebtDao(DebtDao debtDao) {
        this.debtDao = debtDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setDebtMappingProvider(DebtMappingProvider debtMappingProvider) {
        this.debtMappingProvider = debtMappingProvider;
    }


    public void setCurrencyUtil(CurrencyUtil currencyUtil) {
        this.currencyUtil = currencyUtil;
    }

    public void setQueryUtil(QueryUtil queryUtil) {
        this.queryUtil = queryUtil;
    }
}
