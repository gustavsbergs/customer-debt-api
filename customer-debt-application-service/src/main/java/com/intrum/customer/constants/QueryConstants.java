package com.intrum.customer.constants;

public class QueryConstants {

    //Customer related query elements
    public static final String CUSTOMER_QUERY_SEARCH = "SELECT c FROM Customer c WHERE ";
    public static final String CUSTOMER_QUERY_UPDATE = "UPDATE Customer c SET ";
    public static final String CUSTOMER_QUERY_FIRST_NAME = "c.firstName = :firstName";
    public static final String CUSTOMER_QUERY_LAST_NAME = "c.lastName = :lastName";
    public static final String CUSTOMER_QUERY_COUNTRY = "c.country = :country";
    public static final String CUSTOMER_QUERY_USERNAME = "c.userName = :userName";
    public static final String CUSTOMER_QUERY_EMAIL = "c.email = :email";
    public static final String CUSTOMER_QUERY_WHERE = " WHERE c.id = :id";

    //Debt related query elements
    public static final String DEBT_QUERY_SEARCH = "SELECT d FROM Debt d where ";
    public static final String DEBT_QUERY_SEARCH_AMOUNT = "(SELECT SUM(dd.amount) FROM Debt dd WHERE dd.currency = :currency  AND dd.user_id = d.user_id) > :amount";
    public static final String DEBT_QUERY_SEARCH_DUE_DATE = "d.duedate <= :dueDate";
    public static final String DEBT_QUERY_UPDATE = "UPDATE Debt d SET ";
    public static final String DEBT_QUERY_UPDATE_AMOUNT = "d.amount = :amount";
    public static final String DEBT_QUERY_UPDATE_CURRENCY = "d.currency = :currency";
    public static final String DEBT_QUERY_UPDATE_DUE_DATE = "d.dueDate = :dueDate";
    public static final String DEBT_QUERY_UPDATE_USER_ID = "d.user_id = :userId";
    public static final String DEBT_QUERY_WHERE = " WHERE d.id = :id";

    //Other query elements
    public static final String AND = " AND ";
}
