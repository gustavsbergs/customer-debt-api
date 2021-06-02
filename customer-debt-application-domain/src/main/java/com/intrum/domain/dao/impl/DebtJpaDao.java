package com.intrum.domain.dao.impl;

import com.intrum.domain.dao.DebtDao;
import com.intrum.domain.entity.Debt;
import com.intrum.domain.impl.GenericJpaDao;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class DebtJpaDao extends GenericJpaDao<Debt, Long> implements DebtDao {

    public DebtJpaDao() {
        super(Debt.class);
    }

    private static final String QUERY_BY_USER_ID = "SELECT d FROM Debt d where d.user_id = :id";

    private static final String QUERY_DELETE_BY_CUSTOMER_ID = "DELETE FROM Debt d where d.user_id = :id";

    @Override
    public void updateDebt(String updateQuery, Map<String, Object> params) {
        Assert.notNull(updateQuery, "updateQuery cannot be null");
        Assert.notNull(params, "params cannot be null");

        Query query = getEntityManager().createQuery(updateQuery);
        params.forEach(query::setParameter);
        query.executeUpdate();
    }

    @Override
    public List<Debt> findByUserId(Long id) {
        Assert.notNull(id, "id cannot be null");
        Query query = getEntityManager().createQuery(QUERY_BY_USER_ID);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Override
    public List<Debt> findByQuery(String searchQuery, Map<String, Object> params) {
        Assert.notNull(searchQuery, "query cannot be null");
        Assert.notNull(params, "params cannot be null");
        Query query = getEntityManager().createQuery(searchQuery);
        params.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public void deleteByCustomerId(Long customerId) {
        Assert.notNull(customerId, "id cannot be null");
        Query query = getEntityManager().createQuery(QUERY_DELETE_BY_CUSTOMER_ID);
        query.setParameter("id", customerId);
        query.executeUpdate();
    }
}
