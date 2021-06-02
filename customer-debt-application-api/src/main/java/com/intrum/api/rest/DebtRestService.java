package com.intrum.api.rest;

import com.intrum.debt.dto.DebtDto;
import org.springframework.http.ResponseEntity;

public interface DebtRestService {

    ResponseEntity<DebtDto> createDebt(DebtDto debtDto);

    ResponseEntity<Void> updateDebt(Long id, DebtDto debtDto);

    ResponseEntity<Void> deleteDebt(Long id);

    ResponseEntity<DebtDto> findDebtById(Long id);
}
