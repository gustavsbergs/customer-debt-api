package com.intrum.domain.dao.impl;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class CustomerJpaDaoTest {

    private static final String USERNAME = "username";
    private static final String EMAIL = "email";
    private static final String EMPTY_STRING = "";

    @Mock
    Query query;

    @Mock
    EntityManager entityManager;

    @InjectMocks
    CustomerJpaDao customerJpaDao;

    @Test
    public void shouldGenerateCorrectQueryParameters_whenCheckingCustomerCredentials(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(entityManager.createQuery(any(String.class))).thenReturn(query);
        customerJpaDao.checkNewCustomerCredentials(USERNAME, EMAIL);

        verify(query, times(2)).setParameter(any(String.class), captor.capture());
        List<String> actualResults = captor.getAllValues();
        assertEquals(USERNAME, actualResults.get(0));
        assertEquals(EMAIL, actualResults.get(1));
    }

    @Test
    public void shouldGenerateCorrectQueryParameters_whenCheckingCustomerCredentials_andUsernameNull(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(entityManager.createQuery(any(String.class))).thenReturn(query);
        customerJpaDao.checkNewCustomerCredentials(null, EMAIL);

        verify(query, times(2)).setParameter(any(String.class), captor.capture());
        List<String> actualResults = captor.getAllValues();
        assertEquals(EMPTY_STRING, actualResults.get(0));
        assertEquals(EMAIL, actualResults.get(1));
    }

    @Test
    public void shouldGenerateCorrectQueryParameters_whenCheckingCustomerCredentials_andEmailNull(){
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        when(entityManager.createQuery(any(String.class))).thenReturn(query);
        customerJpaDao.checkNewCustomerCredentials(USERNAME, null);

        verify(query, times(2)).setParameter(any(String.class), captor.capture());
        List<String> actualResults = captor.getAllValues();
        assertEquals(USERNAME, actualResults.get(0));
        assertEquals(EMPTY_STRING, actualResults.get(1));
    }

}