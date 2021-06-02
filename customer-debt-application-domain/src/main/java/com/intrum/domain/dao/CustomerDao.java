package com.intrum.domain.dao;

import com.intrum.domain.GenericDao;
import com.intrum.domain.entity.Customer;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface CustomerDao extends GenericDao<Customer, Long> {

    void updateCustomer(String query, Map<String, Object> parameters);

    void deleteCustomer(Long id);

    Customer findByEmail(String email);

    List<Customer> findByQuery(String query, Map<String, Object> parameters);

    Long checkNewCustomerCredentials(String username, String email);

    List<Customer> findByIds(List<Long> ids);

}
