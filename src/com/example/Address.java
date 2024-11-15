package com.example;

public class Address {
    private int addressID;
    private String addressLine0;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String postCode;

    public Address(String addressLine0, String addressLine1,
                   String addressLine2, String country,
                   String postCode) {
        this.addressLine0 = addressLine0;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.postCode = postCode;
    }

    void setAddressID(int addressID) {
        this.addressID = addressID;
    }

//    public void setAddressLine0(String addressLine0) {
//        this.addressLine0 = addressLine0;
//    }
//
//    public void setAddressLine1(String addressLine1) {
//        this.addressLine1 = addressLine1;
//    }
//
//    public void setAddressLine2(String addressLine2) {
//        this.addressLine2 = addressLine2;
//    }
//
//    public void setCountry(String country) {
//        this.country = country;
//    }
//
//    public void setPostCode(String postCode) {
//        this.postCode = postCode;
//   }

    int getAddressID() {
        return addressID;
    }

    String getAddressLine0() {
        return addressLine0;
    }

    String getAddressLine1() {
        return addressLine1;
    }

    String getAddressLine2() {
        return addressLine2;
    }

    String getCountry() {
        return country;
    }

    String getPostCode() {
        return postCode;
    }

    @Override
    public String toString() {
        return "Address={" +
                "addressID=" + addressID +
                ", addressLine0='" + addressLine0 + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", country='" + country + '\'' +
                ", postCode='" + postCode + '\'' +
                '}';
    }
}
