package com.example;

public class HomeAppliance {
    private int id;
    private String sku; // Stock keeping unit (a unique code for each product)
    private String description;
    private String category;
    private int price;

    /**
     *
     * @param sku
     * @param description
     * @param category
     * @param price
     */
    public HomeAppliance(String sku, String description, String category, int price) {
        this.sku = sku;
        this.description = description;
        this.category = category;
        this.price = price;
    }

    /**
     *
     * @param id
     * @return
     */
    int setId(int id){
        return this.id = id;
    }

    /**
     *
     * @return
     */
    int getId(){
        return this.id;
    }

    /**
     *
     * @return
     */
    String getSku(){
        return sku;
    }

    /**
     *
     * @return
     */
    String getDescription(){
        return description;
    }

    /**
     *
     * @return
     */
    String getCategory(){
        return category;
    }

    /**
     *
     * @return
     */
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
