package com.example.inventory;

public class User {
    public String product_name,category,price,itembarcode;

    public User() {
    }

    public User(String product_name, String category, String price,String itembarcode) {

        this.product_name = product_name;
        this.category = category;
        this.price = price;
        this.itembarcode = itembarcode;
    }

    public String getItembarcode() {
        return itembarcode;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getCategory() {
        return category;
    }

    public String getPrice() {
        return price;
    }
}
