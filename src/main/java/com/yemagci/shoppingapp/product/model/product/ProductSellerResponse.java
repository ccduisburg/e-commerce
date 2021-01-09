package com.yemagci.shoppingapp.product.model.product;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductSellerResponse {
    private String id;
    private String name;


}
