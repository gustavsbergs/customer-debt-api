package com.intrum.debt;

import com.intrum.debt.dto.DebtDto;

public interface DebtService {

    DebtDto createDebt(DebtDto debtDto);

    void updateDebt(DebtDto debtDto, Long id);

    void deleteDebt(Long id);

    DebtDto findDebtById(Long id);
}
