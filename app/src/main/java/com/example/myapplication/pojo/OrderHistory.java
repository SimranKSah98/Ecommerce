package com.example.myapplication.pojo;

import com.google.gson.annotations.SerializedName;

public class OrderHistory {
    @SerializedName("historyId")
    private String historyId;
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
    private int productQuantity;
    @SerializedName("orderId")
    private String orderId;
    @SerializedName("orderDate")
    private String orderDate;
    @SerializedName("timeInMillis")
    private long timeInMillis;

    public int getProductQuantity() {
        return productQuantity;
    }

    public long getTimeInMillis() {
        return timeInMillis;
    }

    public String getHistoryId() {
        return historyId;
    }

    public void setHistoryId(String historyId) {
        this.historyId = historyId;
    }


    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public void setTimeInMillis(long timeInMillis) {
        this.timeInMillis = timeInMillis;
    }

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
}
