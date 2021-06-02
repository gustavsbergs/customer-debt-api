package com.intrum.customer.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "Dto used for sending and receiving Customer data")
public class CustomerDto {

    @ApiModelProperty(value = "Id of the Customer", name = "id", example = "1", accessMode = ApiModelProperty.AccessMode.READ_ONLY)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;
    @ApiModelProperty(value = "Username of the Customer", name = "userName", example = "JohnSmith")
    private String userName;
    @ApiModelProperty(value = "First name of the Customer", name = "firstName", example = "John")
    private String firstName;
    @ApiModelProperty(value = "Last name of the Customer", name = "lastName", example = "Smith")
    private String lastName;
    @ApiModelProperty(value = "Country of the Customer", name = "country", example = "USA")
    private String country;
    @ApiModelProperty(value = "Email of the Customer", name = "email", example = "johnsmith@email.com")
    private String email;
    @ApiModelProperty(value = "Password. Used only fro user creation", name = "password", example = "password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
