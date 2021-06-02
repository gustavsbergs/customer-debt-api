package com.intrum.domain.dao.impl;

import com.intrum.domain.dao.CustomerDao;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.impl.GenericJpaDao;
import org.springframework.util.Assert;

import javax.persistence.Query;
import java.util.List;
import java.util.Map;

public class CustomerJpaDao extends GenericJpaDao<Customer, Long> implements CustomerDao {

    public CustomerJpaDao() {
        super(Customer.class);
    }

    private static final String QUERY_FIND_BY_EMAIL = "SELECT c FROM Customer c WHERE c.email = :email ";

    private static final String QUERY_FIND_BY_USERNAME = "Select c FROM Customer c WHERE c.username = :username ";

    private static final String QUERY_FIND_BY_IDS = "SELECT c FROM  Customer c WHERE c.id IN(:ids) ";

    private static final String QUERY_CHECK_USERNAME_EMAIL = "SELECT COUNT( DISTINCT c ) FROM Customer c " +
            "WHERE c.userName = :userName OR c.email = :email ";

    private static final String QUERY_DELETE_CUSTOMER = "DELETE FROM Customer c WHERE c.id = :id";

    @Override
    public void updateCustomer(String updateQuery, Map<String, Object> parameters) {
        Assert.notNull(updateQuery, "query cannot be null");
        Assert.notNull(parameters, "parameters cannot be null");
        Query query = getEntityManager().createQuery(updateQuery);
        parameters.forEach(query::setParameter);
        query.executeUpdate();
    }

    @Override
    public void deleteCustomer(Long id) {
        Assert.notNull(id, "id cannot be null");
        Query query = getEntityManager().createQuery(QUERY_DELETE_CUSTOMER);
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public Customer findByEmail(String email) {
        Assert.notNull(email, "email cannot be null");
        Query query = getEntityManager().createQuery(QUERY_FIND_BY_EMAIL);
        query.setParameter("email", email);
        return (Customer) query.getSingleResult();
    }

    @Override
    public List<Customer> findByQuery(String searchQuery, Map<String, Object> parameters) {
        Assert.notNull(searchQuery, "query cannot be null");
        Assert.notNull(parameters, "parameters cannot be null");
        Query query = getEntityManager().createQuery(searchQuery);
        parameters.forEach(query::setParameter);
        return query.getResultList();
    }

    @Override
    public Long checkNewCustomerCredentials(String username, String email) {
        Query query = getEntityManager().createQuery(QUERY_CHECK_USERNAME_EMAIL);
        String user = "";
        String mail = "";
        if (username != null) {
            user = username;
        }
        if (email != null) {
            mail = email;
        }
        query.setParameter("userName", user);
        query.setParameter("email", mail);
        return (Long) query.getSingleResult();
    }

    @Override
    public List<Customer> findByIds(List<Long> ids) {
        Assert.notNull(ids, "ids cannot be null");
        Query query = getEntityManager().createQuery(QUERY_FIND_BY_IDS);
        query.setParameter("ids", ids);
        return query.getResultList();
    }

}
