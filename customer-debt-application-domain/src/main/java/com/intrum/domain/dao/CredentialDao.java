package com.intrum.domain.dao;

import com.intrum.domain.GenericDao;
import com.intrum.domain.entity.Credential;

public interface CredentialDao extends GenericDao<Credential, Long> {

    void deleteByCustomerId(Long id);
}
