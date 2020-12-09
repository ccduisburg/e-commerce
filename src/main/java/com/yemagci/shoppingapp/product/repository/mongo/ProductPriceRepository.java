package com.yemagci.shoppingapp.product.repository.mongo;

import com.yemagci.shoppingapp.product.domain.Product;
import com.yemagci.shoppingapp.product.domain.ProductPrice;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductPriceRepository extends ReactiveMongoRepository<ProductPrice,String> {
}
