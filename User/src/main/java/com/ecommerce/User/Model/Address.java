package com.ecommerce.User.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Address {

    @NotBlank
    @Size(min=5, message= "Street name must be atleast 5 characters")
    private String street;

    @NotBlank
    @Size(min=5, message= "City name must be atleast 5 characters")
    private String city;

    @NotBlank
    @Size(min=2, message= "State Name name must be atleast 2 characters")
    private String state;

    @NotBlank
    @Size(min=2, message= "Country Name name must be atleast 2 characters")
    private String country;

    @NotBlank
    @Size(min=6, message= "Zipcode Name name must be atleast 6 characters")
    private String zipcode;

    private String buildingName;

}