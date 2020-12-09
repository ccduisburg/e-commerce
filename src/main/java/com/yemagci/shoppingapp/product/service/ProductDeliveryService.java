package com.yemagci.shoppingapp.product.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDeliveryService {
    public String getDeliveryInfo(String productId){
        return "Morgen";
    }
    public boolean freeDeliveryCheck(String productId, BigDecimal price){
        return price.compareTo(BigDecimal.ONE)>=0;
    }
}
