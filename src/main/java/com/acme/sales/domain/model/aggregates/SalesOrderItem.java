package com.acme.sales.domain.model.aggregates;

import java.util.UUID;

public class SalesOrderItem {
    private UUID itemId;
    private int quantity;
    private Long productId;
    private double unitPrice;
    private boolean dispatched;

    /*itemId no se considera porque no es proporcionado externamente.
    * Además, dispatched tampoco se procesa porque es un elemento nuevo.
    * SalesOrderItem con los valores predeterminados
    * */

    public SalesOrderItem(int quantity, Long productId, double unitPrice) {
        /*reglas de negocio:
        * quantity, productId y unitPrice no pueden ser 0 o menos*/
        if (quantity==0)
            throw new IllegalArgumentException("Quantity must be greater than zero");
        if (productId ==0)
            throw new IllegalArgumentException("Product Id is never zero");
        if (unitPrice<=0)
            throw new IllegalArgumentException("Unit Price can't be zero or less");
        /*se le asigna un tipo UUID random a itemId*/
        this.itemId=UUID.randomUUID();
        this.quantity = quantity;
        this.productId = productId;
        this.unitPrice = unitPrice;

        this.dispatched= false;
    }

    public boolean isDispatched(){
        return dispatched;
    }

    public void dispatch(){
        this.dispatched=true;
    }

    public double calculatePrice(){
        return unitPrice*quantity;
    }



}
