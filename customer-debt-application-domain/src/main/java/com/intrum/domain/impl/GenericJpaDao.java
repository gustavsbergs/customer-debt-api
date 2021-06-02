package com.intrum.domain.impl;

import com.intrum.domain.GenericDao;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.util.List;

@Transactional
public abstract class GenericJpaDao<T, ID extends Serializable> implements GenericDao<T, ID> {

    private Class<T> persistenceClass;

    @PersistenceContext
    private EntityManager entityManager;

    public GenericJpaDao(Class<T> persistenceClass) {
        this.persistenceClass = persistenceClass;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public Class<T> getPersistenceClass() {
        return persistenceClass;
    }

    public T findById(ID id) {
        T entity = getEntityManager().find(getPersistenceClass(), id);
        return entity;
    }

    public List<T> findAll() {
        return getEntityManager().createQuery(
                "select x from " + getPersistenceClass().getSimpleName() + " x").getResultList();
    }

    public T save(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public T update(T entity) {
        return getEntityManager().merge(entity);
    }

    public void delete(T entity) {
        getEntityManager().remove(entity);
    }

}
