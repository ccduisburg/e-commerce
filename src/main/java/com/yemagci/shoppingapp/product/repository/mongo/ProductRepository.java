package com.yemagci.shoppingapp.product.repository.mongo;

import com.yemagci.shoppingapp.product.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
//asyncron veri sorgulamasini untershutsen yapan repository reaktiveMongoRepository
public interface ProductRepository extends ReactiveMongoRepository<Product,String> {
}
