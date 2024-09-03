package com.acme.sales.domain.model.aggregates;

import com.acme.shared.domain.model.value.objects.Address;

import java.util.List;
import java.util.SplittableRandom;
import java.util.UUID;

public class SalesOrder {
    private final UUID internalId;
    private Address shippingAddress;
    private SalesOrderStatus status;
    private List<SalesOrderItem> items;
    private double paymentAmmout;

    /*constructor*/
    public SalesOrder(){
        this.internalId = UUID.randomUUID();
        this.status = SalesOrderStatus.CREATED;
        paymentAmmout = 0.0;
    }

    /*el unico responsable de pasar items es el propio aggregate*/
    public void addItem(int quantity, double unitPrice, Long productId){
        if (this.status== SalesOrderStatus.APPROVED)
            throw new IllegalStateException("Cannot add items to and aproved order");
        this.items.add(new SalesOrderItem(quantity, productId, unitPrice));
    }

    public double calculateTotalPrice(){
        return items.stream().mapToDouble(SalesOrderItem::calculatePrice).sum();
    }

    public void addPayment(double amount){
        if (amount<=0)
            throw new IllegalArgumentException("Payment must be greater than 0");

        if (amount> calculateTotalPrice()-paymentAmmout)
            throw new IllegalArgumentException("Payment amout exceeds the total price");

        this.paymentAmmout+=amount;
        verifyReadyForDispatch();
    }

    public void dispatch(String street, String number, String city, String status,String zipCode, String country){
        verifyReadyForDispatch();
        this.shippingAddress = new Address(street, number, city, status, zipCode, country);
        this.status = SalesOrderStatus.IN_PROGRESS;
        this.items.forEach(SalesOrderItem::dispatch);

    }

    public boolean isDispatched(){
        return this.items.stream().allMatch(SalesOrderItem::isDispatched);
    }

    private UUID getInternalId(){
        return internalId;
    }

    private void verifyReadyForDispatch(){
        if (this.status == SalesOrderStatus.APPROVED) return;
        if (paymentAmmout == calculateTotalPrice()) this.status= SalesOrderStatus.APPROVED;
    }

    public void verifyIfItemsAreDispatched(){
        if (isDispatched()) this.status = SalesOrderStatus.SHIPPED;
    }

    public void completeDelivery(){
        verifyReadyForDispatch();
        if (status == SalesOrderStatus.SHIPPED) status = SalesOrderStatus.DELIVERED;
    }

    /*orden cancelada*/
    public void cancel(){
        clearItems();
        status = SalesOrderStatus.CANCELLED;
    }
    /*metodo para eliminar los items*/
    public void clearItems(){
        this.items.clear();
    }
}
