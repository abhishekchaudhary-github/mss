package model;

import java.util.List;

public class InvoiceData {
    private Integer orderId;
    private String time;
    private List<OrderItemData> orderItemDataList;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public List<OrderItemData> getOrderItemDataList() {
        return orderItemDataList;
    }

    public void setOrderItemDataList(List<OrderItemData> orderItemDataList) {
        this.orderItemDataList = orderItemDataList;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
