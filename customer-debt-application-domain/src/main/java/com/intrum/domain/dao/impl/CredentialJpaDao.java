package com.intrum.domain.dao.impl;

import com.intrum.domain.dao.CredentialDao;
import com.intrum.domain.entity.Credential;
import com.intrum.domain.impl.GenericJpaDao;
import org.springframework.util.Assert;

import javax.persistence.Query;

public class CredentialJpaDao extends GenericJpaDao<Credential, Long> implements CredentialDao {
    private static final String DELETE_CREDENTIAL = "DELETE FROM Credential c WHERE c.userId = :id ";

    public CredentialJpaDao() {
        super(Credential.class);
    }

    @Override
    public void deleteByCustomerId(Long id) {
        Assert.notNull(id, "id cannot be null");
        Query query = getEntityManager().createQuery(DELETE_CREDENTIAL);
        query.setParameter("id", id);
        query.executeUpdate();
    }
}
