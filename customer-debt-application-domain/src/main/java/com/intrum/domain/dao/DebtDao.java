package com.intrum.domain.dao;

import com.intrum.domain.GenericDao;
import com.intrum.domain.entity.Debt;

import java.util.List;
import java.util.Map;

public interface DebtDao extends GenericDao<Debt, Long> {

    void updateDebt(String query, Map<String, Object> params);

    List<Debt> findByUserId(Long id);

    List<Debt> findByQuery(String query, Map<String, Object> parameters);

    void deleteByCustomerId(Long customerId);
}
