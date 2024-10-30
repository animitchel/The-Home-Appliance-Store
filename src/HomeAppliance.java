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

    public int setId(int id){
        return this.id = id;
    }

//    public String setSku(String sku){
//        return this.sku = sku;
//    }
//
//    public String setDescription(String description){
//        return this.description = description;
//    }
//
//    public String setCategory(String category){
//        return this.category = category;
//    }
//
//    public int setPrice(int price){
//        return this.price = price;
//    }

    public int getId(){
        return this.id;
    }

    public String getSku(){
        return this.sku;
    }
    public String getDescription(){
        return this.description;
    }
    public String getCategory(){
        return this.category;
    }
    public int getPrice(){
        return this.price;
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
