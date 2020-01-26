package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class OrderHistory {
    @SerializedName("productId")
    private String productId;
    @SerializedName("customerId")
    private String customerId;
    @SerializedName("productName")
    private String productName;
    @SerializedName("productImage")
    private String productImage;
    @SerializedName("productPrice")
    private String productPrice;
    @SerializedName("productQuantity")
    private String productQuantity;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("timeInMillis")
    private String timeInMillis;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(String productQuantity) {
        this.productQuantity = productQuantity;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getTimeInMillis() {
        return timeInMillis;
    }

    public void setTimeInMillis(String timeInMillis) {
        this.timeInMillis = timeInMillis;
    }
}
