package com.example;

public class HomeAppliance {
    private int id;
    private String sku; // Stock keeping unit (a unique code for each product)
    private String description;
    private String category;
    private int price;

    public HomeAppliance(String sku, String description, String category, int price) {
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    int setId(int id){
        return this.id = id;
    }

    int getId(){
        return this.id;
    }

    String getSku(){
        return sku;
    }
    String getDescription(){
        return description;
    }
    String getCategory(){
        return category;
    }
    int getPrice(){
        return price;
    }

    @Override
    public String toString() {
        return "Product={" +
                "id=" + id +
                ", sku='" + sku + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                '}';
    }
}
