package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductPriceService {
    public BigDecimal getByMoneyType(String id, MoneyTypes eur) {
        return BigDecimal.TEN;
    }
}
