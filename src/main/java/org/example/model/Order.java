package org.example.model;

import org.example.config.AppConfig;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private final String customerName;
    private final List<OrderItem> items;
    private OrderStatus status;
    private Discount discount = new NoDiscount();

    public Order(Builder builder) {
        this.customerName = builder.customerName;
        this.items = builder.items;
        this.status = OrderStatus.NEW;
    }

    public void addItem(OrderItem item){
        if (status == OrderStatus.PAID) {
            System.out.println("Item already paid");
            return;
        }
        items.add(item);
    }

    public double calculateTotal(){
        // TODO: calculate total from all order items (including discounts)
        System.out.println("Looking for discounts...");

        this.applyDiscount(new FixedAmountDiscount("save10", 10));
        System.out.println("Discount found: " + discount.getCode());

        double sum = 0;
        for (OrderItem item: items){
            sum += item.calculateTotal();
        }

        double taxRate = AppConfig.getInstance().getTaxRate();
        double afterDiscount = discount.apply(sum);

        return afterDiscount * (1 + taxRate);
    }

    public void markAsPaid(){
        if (items.isEmpty()) {
            System.out.println("No items in the order.");
            return;
        }
        this.status = OrderStatus.PAID;
    }

    public void applyDiscount(Discount discount){
        this.discount = discount;
    }

    public boolean isPaid(){
        return this.status == OrderStatus.PAID;
    }

    public List<OrderItem> getItems() {
        return items;
    }
    public String getCustomerName() {
        return customerName;
    }
    public OrderStatus getStatus() {
        return status;
    }
    public static Builder builder(){
        return new Builder();
    }
    public static class Builder{
        private String customerName;
        private List<OrderItem> items = new ArrayList<>();
        public Builder customerName(String customerName){
            this.customerName = customerName;
            return this;
        }
        public Builder addItem(OrderItem item){
            this.items.add(item);
            return this;
        }
        public Order build(){
            if (this.customerName.isEmpty()) throw new IllegalStateException("Customer name cannot be empty.");
            if (this.customerName.length() < 2) throw new IllegalStateException("Customer name too short.");
            return new Order(this);
        }
    }
}
