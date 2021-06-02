package com.intrum.customer.service;

import com.intrum.customer.CustomerService;
import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import com.intrum.customer.mapper.CustomerMappingProvider;
import com.intrum.domain.dao.CredentialDao;
import com.intrum.domain.dao.CustomerDao;
import com.intrum.domain.dao.DebtDao;
import com.intrum.domain.entity.Credential;
import com.intrum.domain.entity.Customer;
import com.intrum.domain.entity.Debt;
import com.intrum.exception.ProcessingException;
import com.intrum.exception.ValidationException;
import com.intrum.util.CredentialUtil;
import com.intrum.util.CurrencyUtil;
import com.intrum.util.QueryUtil;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomerServiceImpl implements CustomerService {

    private static final String EMAIL_REGEX = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

    private CustomerDao customerDao;
    private DebtDao debtDao;
    private CredentialDao credentialDao;
    private CredentialUtil credentialUtil;
    private CustomerMappingProvider customerMappingProvider;
    private QueryUtil queryUtil;
    private CurrencyUtil currencyUtil;

    @Override
    public CustomerDto createCustomer(CustomerDto customer) {
        validateCustomerInfoCreate(customer);
        Long count = customerDao.checkNewCustomerCredentials(customer.getUserName(), customer.getEmail());
        if (count > 0) {
            throw new ValidationException("Customer with provided username and/ord email already exists.");
        }
        Customer createdCustomer = customerDao.save(customerMappingProvider.toDomain(customer));
        credentialDao.save(new Credential(credentialUtil.hash(customer.getPassword()), createdCustomer.getId()));
        return customerMappingProvider.toDto(createdCustomer);
    }

    @Override
    public void updateCustomer(Long customerId, CustomerDto customer) {
        if (ObjectUtils.isEmpty(customer.getCountry()) && ObjectUtils.isEmpty(customer.getEmail())
                && ObjectUtils.isEmpty(customer.getLastName()) && ObjectUtils.isEmpty(customer.getUserName())
                && ObjectUtils.isEmpty(customer.getFirstName())) {
            throw new ValidationException("At least one field must be provided to update a customer.");
        }
        if (customer.getEmail() != null) {
            validateEmail(customer.getEmail());
        }
        if (customer.getEmail() != null || customer.getUserName() != null) {
            Long count = customerDao.checkNewCustomerCredentials(customer.getUserName(), customer.getEmail());
            if (count > 0) {
                throw new ValidationException("Username or Email already taken.");
            }
        }
        String query = queryUtil.createCustomerUpdateQueryString(customer);
        Map<String, Object> params = queryUtil.createCustomerQueryParameters(customer.getFirstName(), customer.getLastName(),
                customer.getCountry(), customer.getUserName(), customer.getEmail(), customerId, null, null, null);
        customerDao.updateCustomer(query, params);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new ProcessingException("Customer with id: " + customerId + " not found.");
        }
        debtDao.deleteByCustomerId(customerId);
        credentialDao.deleteByCustomerId(customerId);
        customerDao.deleteCustomer(customerId);
    }

    @Override
    public CustomerDto searchCustomerById(Long customerId) {
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            throw new ProcessingException("Customer with id: " + customerId + " not found.");
        }
        return customerMappingProvider.toDto(customer);
    }

    @Override
    public List<CustomerDto> findAllCustomers() {
        return customerDao.findAll().stream()
                .map(customer -> customerMappingProvider.toDto(customer))
                .collect(Collectors.toList());
    }

    @Override
    public CustomerSummaryDto generateCustomerSummaryForSingleCustomer(Long customerId) {
        return customerMappingProvider.toSummary(customerDao.findById(customerId), debtDao.findByUserId(customerId));
    }

    @Override
    public List<CustomerSummaryDto> generateCustomerSummaryBySearchCriteria(LocalDateTime dueDate, BigDecimal debtAmount, String currency) {
        validateSearchCriteria(dueDate, debtAmount, currency);

        String query = queryUtil.createDebtSearchQueryString(dueDate, currencyUtil.convertCurrencyToCents(debtAmount, currency));

        Map<String, Object> params = queryUtil.createCustomerQueryParameters(null, null, null, null,
                null, null, dueDate, debtAmount.toBigInteger(), currency);

        List<Debt> debts = debtDao.findByQuery(query, params);

        List<Long> customerIds = debts.stream()
                .filter(distinctByKey(Debt::getUser_id))
                .map(Debt::getUser_id)
                .collect(Collectors.toList());

        List<Customer> customers = customerDao.findByIds(customerIds);

        return customers.stream()
                .map(customer -> customerMappingProvider.toSummary(customer, debts))
                .collect(Collectors.toList());
    }

    private void validateSearchCriteria(LocalDateTime dueDate, BigDecimal debtAmount, String currency) {
        if (dueDate == null && debtAmount == null) {
            throw new ValidationException("At least one search criteria must be provided.");
        }
        if (debtAmount != null && currency == null) {
            throw new ValidationException("If searching by debt amount, must provide currency.");
        }
    }

    @Override
    public List<CustomerDto> searchCustomersBySearchCriteria(String firstName, String lastName, String country, String email) {
        String query = queryUtil.createCustomerSearchQueryString(firstName, lastName, country, email);
        Map<String, Object> parameters = queryUtil.createCustomerQueryParameters(firstName, lastName, country, null,
                email, null, null, null, null);
        return customerDao.findByQuery(query, parameters).stream()
                .map(customer -> customerMappingProvider.toDto(customer))
                .collect(Collectors.toList());
    }

    private void validateCustomerInfoCreate(CustomerDto customer) {
        if (customer.getUserName() == null) {
            throw new ValidationException("userName", "Required field is missing");
        }
        if (customer.getPassword() == null) {
            throw new ValidationException("password", "Required field is missing");
        }
        if (customer.getFirstName() == null) {
            throw new ValidationException("firstName", "Required field is missing");
        }
        if (customer.getLastName() == null) {
            throw new ValidationException("lastName", "Required field is missing");
        }
        if (customer.getCountry() == null) {
            throw new ValidationException("country", "Required field is missing");
        }
        if (customer.getEmail() == null) {
            throw new ValidationException("email", "Required field is missing");
        }
        validateEmail(customer.getEmail());
    }

    private void validateEmail(String email) {
        Matcher matcher = EMAIL_PATTERN.matcher(email);
        if (!matcher.matches()) {
            throw new ValidationException(email, "Invalid email address");
        }
    }

    public static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Set<Object> seen = ConcurrentHashMap.newKeySet();
        return t -> seen.add(keyExtractor.apply(t));
    }

    public void setCurrencyUtil(CurrencyUtil currencyUtil) {
        this.currencyUtil = currencyUtil;
    }

    public void setQueryUtil(QueryUtil queryUtil) {
        this.queryUtil = queryUtil;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setDebtDao(DebtDao debtDao) {
        this.debtDao = debtDao;
    }

    public void setCredentialDao(CredentialDao credentialDao) {
        this.credentialDao = credentialDao;
    }

    public void setCredentialUtil(CredentialUtil credentialUtil) {
        this.credentialUtil = credentialUtil;
    }

    public void setCustomerMappingProvider(CustomerMappingProvider customerMappingProvider) {
        this.customerMappingProvider = customerMappingProvider;
    }
}
