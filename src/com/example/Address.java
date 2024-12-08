package com.example;

public class Address {
    private int addressID;
    private String addressLine0;
    private String addressLine1;
    private String addressLine2;
    private String country;
    private String postCode;


    /**
     *
     * @param addressLine0
     * @param addressLine1
     * @param addressLine2
     * @param country
     * @param postCode
     */
    public Address(String addressLine0, String addressLine1,
                   String addressLine2, String country,
                   String postCode) {
        this.addressLine0 = addressLine0;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.country = country;
        this.postCode = postCode;
    }

    /**
     *
     * @param addressID
     */
    void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    /**
     *
     * @return
     */
    int getAddressID() {
        return addressID;
    }

    /**
     *
     * @return
     */
    String getAddressLine0() {
        return addressLine0;
    }

    /**
     *
     * @return
     */
    String getAddressLine1() {
        return addressLine1;
    }

    /**
     *
     * @return
     */
    String getAddressLine2() {
        return addressLine2;
    }

    /**
     *
     * @return
     */
    String getCountry() {
        return country;
    }

    /**
     *
     * @return
     */
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
