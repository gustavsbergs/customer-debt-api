package com.intrum.api.rest.controllers;

import com.intrum.api.rest.CustomerRestService;
import com.intrum.customer.CustomerService;
import com.intrum.customer.dto.CustomerDto;
import com.intrum.customer.dto.CustomerSummaryDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Api(value = "Customer API", tags = {"Customer API"}, description = "/customer-debt-rs/v1")
@RequestMapping(path = "/customer-debt-rs/v1")
public class CustomerRestServiceImpl implements CustomerRestService {

    private CustomerService customerService;

    @Override
    @ResponseBody
    @ApiOperation(value = "Creates a new customer", response = CustomerDto.class)
    @PostMapping(path = "/customers")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerDto customerDto) {
        return ResponseEntity.ok(customerService.createCustomer(customerDto));
    }

    @Override
    @ResponseBody
    @ApiOperation(value = "Updates an existing customer", response = CustomerDto.class)
    @PutMapping(value = "/customers/{id}")
    public ResponseEntity<Void> updateCustomer(@PathVariable(value = "id") Long id, @RequestBody CustomerDto customerDto) {
        customerService.updateCustomer(id, customerDto);
        return ResponseEntity.ok().build();
    }

    @Override
    @ApiOperation(value = "Deletes an existing customer", response = CustomerDto.class)
    @DeleteMapping(value = "/customers/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable(value = "id") Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.ok().build();
    }

    @Override
    @ResponseBody
    @ApiOperation(value = "Search a single customer by customerId", response = CustomerDto.class)
    @GetMapping(value = "/customers/{id}")
    public ResponseEntity<CustomerDto> getCustomer(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(customerService.searchCustomerById(id));
    }

    @Override
    @ResponseBody
    @ApiOperation(value = "Returns existing customers by search criteria", response = CustomerDto.class, responseContainer = "List")
    @GetMapping(value = "/customers")
    public ResponseEntity<List<CustomerDto>> getCustomers(@RequestParam(value = "First name", required = false) String firstName,
                                                          @RequestParam(value = "Last name", required = false) String lastName,
                                                          @RequestParam(value = "Country", required = false) String country,
                                                          @RequestParam(value = "Email", required = false) String email) {
        List<CustomerDto> customers;
        if (firstName == null && lastName == null && country == null && email == null) {
            customers = customerService.findAllCustomers();
        } else {
            customers = customerService.searchCustomersBySearchCriteria(firstName, lastName, country, email);
        }
        return ResponseEntity.ok(customers);
    }

    @Override
    @ResponseBody
    @ApiOperation(value = "Returns a single customers debt summary", response = CustomerSummaryDto.class)
    @GetMapping(value = "/customers/summary/{id}")
    public ResponseEntity<CustomerSummaryDto> getCustomerSummary(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(customerService.generateCustomerSummaryForSingleCustomer(id));
    }

    @Override
    @ResponseBody
    @ApiOperation(value = "Returns a single customers debt summary", response = CustomerSummaryDto.class)
    @GetMapping(value = "/customers/summary/")
    public ResponseEntity<List<CustomerSummaryDto>> getCustomerSummaryBySearchCriteria(@RequestParam(value = "Due date", required = false) LocalDateTime dueDate, @RequestParam(value = "Total amount", required = false) BigDecimal amount, @RequestParam(value = "currency", required = false) String currency) {
        return ResponseEntity.ok(customerService.generateCustomerSummaryBySearchCriteria(dueDate, amount, currency));
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
