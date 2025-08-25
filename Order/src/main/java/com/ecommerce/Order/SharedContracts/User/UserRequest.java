package com.ecommerce.Order.SharedContracts.User;

import lombok.Data;

@Data
public class UserRequest {
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String phone;
    private String zipcode;
    private AddressDTO address;
}
