package com.example.myapplication.pojo;

public class AddToCartResponseBody {

    String name;
    Double price;
    String image;
    int quantity;
    String merchantId;
    String productId;
    String merchantName;

    public AddToCartResponseBody(String name, Double price, String image, int quantity, String merchantId, String productId, String merchantName) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.quantity = quantity;
        this.merchantId = merchantId;
        this.productId = productId;
        this.merchantName = merchantName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public void setMerchantName(String merchantName) {
        this.merchantName = merchantName;
    }
}



