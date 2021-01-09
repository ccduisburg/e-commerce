package com.yemagci.shoppingapp.product.model.category;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class CategorySaveRequest {
   private String name;
}
