package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductDeliveryService {
    public String getDeliveryInfo(String productId){
        return "Morgen";
    }
    public boolean freeDeliveryCheck(String productId, BigDecimal price, MoneyTypes moneyTypes){
        //TODO:
        return true;
    }
}
