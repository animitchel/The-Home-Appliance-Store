package com.example;

public class Customer {
    private int customerID;
    private Address address;
    private String businessName;
    private String telephoneNumber;

    public Customer(
            String businessName,
            Address address, String telephoneNumber) {

        this.businessName = businessName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }


    void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    int getCustomerID() {
        return customerID;
    }

    String getBusinessName() {
        return businessName;
    }

    Address getAddress() {
        return address;
    }

    String getTelephoneNumber() {
        return telephoneNumber;
    }

    @Override
    public String toString() {
        return "Customer={" +
                "customerID=" + customerID +
                ", businessName='" + businessName + '\'' +
                ", address=" + address +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                '}';
    }
}
