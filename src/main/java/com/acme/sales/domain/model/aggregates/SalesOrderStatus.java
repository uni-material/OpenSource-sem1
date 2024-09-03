package com.acme.sales.domain.model.aggregates;

/*enum: clase con valores fijos e inmutables*/
public enum SalesOrderStatus {
    CREATED,
    APPROVED,
    IN_PROGRESS,
    CANCELLED,
    SHIPPED,
    DELIVERED
}
