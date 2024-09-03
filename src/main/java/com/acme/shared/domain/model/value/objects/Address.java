package com.acme.shared.domain.model.value.objects;

public class Address {
    private final String street;
    private final String number;
    private final String city;
    private final String state;
    private final String zipCode;
    private final String country;

    public Address(){
        this.street="";
        this.number="";
        this.city="";
        this.state="";
        this.zipCode="";
        this.country="";
    }


    public Address(String street, String number, String city, String state, String zipCode, String country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    /*añadiendo un metodo para juntar en un string los valores de Address*/
    public String getAddressAsString(){
        return String.format("%s, %s,%s,%s,%s,%s", street, number, city, state, zipCode, country);
    }
}
