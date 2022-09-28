package com.example.e_commerce;
import java.util.Date;

public class Order {
    private int OrderID,CustID;
    private String Address;
    private Date OrdDate;


    public Order(int CustID, String orderAddress, Date orderDate) {
        this.CustID = CustID;
        this.Address = orderAddress;
        this.OrdDate = orderDate;
    }

    public Order() {
    }

    public int getOrderId() {
        return OrderID;
    }

    public void setOrderId(int orderId) {
        this.OrderID = orderId;
    }

    public int getCustId() {
        return CustID;
    }

    public void setCustId(int custId) {
        this.CustID= custId;
    }

    public String getOrderAddress() {
        return Address;
    }

    public void setOrderAddress(String orderAddress) {
        this.Address = orderAddress;
    }

    public Date getOrderDate() {
        return OrdDate;
    }

    public void setOrderDate(Date orderDate) {
        this.OrdDate = orderDate;
    }
}


