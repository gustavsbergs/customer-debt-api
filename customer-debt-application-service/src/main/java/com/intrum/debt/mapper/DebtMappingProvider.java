package com.intrum.debt.mapper;

import com.intrum.debt.dto.DebtDto;
import com.intrum.domain.entity.Debt;

public interface DebtMappingProvider {

    Debt mapToDomain(DebtDto debtDto);

    DebtDto mapToDto(Debt debt);
}
