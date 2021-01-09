package com.yemagci.shoppingapp.product.repository.mongo;

import com.yemagci.shoppingapp.product.domain.ProductPrice;
import com.yemagci.shoppingapp.product.domain.category.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository  extends ReactiveMongoRepository<Category,String> {
}
