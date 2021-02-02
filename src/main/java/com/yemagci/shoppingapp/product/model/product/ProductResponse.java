package com.yemagci.shoppingapp.product.model.product;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductResponse {
    //String productCode;//name+model
    private String id;
    private String image;
    private String name;
    private BigDecimal price;
    private String description;
    private ProductSellerResponse seller;
    private String features;
    private int available;
    private boolean freeDelivery;
    private String deliveryIn;
    private String categoryId;
    private String moneySymbol;
}
