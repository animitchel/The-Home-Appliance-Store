package com.example;

public class Customer {
    private int customerID;
    private Address address;
    private String businessName;
    private String telephoneNumber;

    /**
     * Customer Class constructor
     *
     * @param businessName
     * @param address
     * @param telephoneNumber
     */
    Customer(
            String businessName,
            Address address, String telephoneNumber) {

        this.businessName = businessName;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
    }

    /**
     *
     * @param customerID
     */
    void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    /**
     *
     * @return
     */
    int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @return
     */
    String getBusinessName() {
        return businessName;
    }

    /**
     *
     * @return
     */
    Address getAddress() {
        return address;
    }

    /**
     *
     * @return
     */
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
