package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.Product;
import com.yemagci.shoppingapp.product.domain.es.CategoryEs;
import com.yemagci.shoppingapp.product.domain.es.CompanyEs;
import com.yemagci.shoppingapp.product.domain.es.ProductEs;
import com.yemagci.shoppingapp.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductEsService {
    private final ProductEsRepository productEsRepository;

  //  @Async
   public Mono<ProductEs> saveNewProduct(Product product){
       return productEsRepository.save(
          ProductEs.builder().active(product.getActive())
                  .code(product.getCode())
                  .description(product.getDescription())
                  .features(product.getId())
                  .name(product.getName())
                  //TODO get Company name and code
                  .seller(CompanyEs.builder().id(product.getCompanyId()).name("buraya firmaninismini buldurup ekleyecegim").build())//firmanin adiyla search yapabilmek icin bütün arama yapilacak bilgileri kaydetmek gerekir.
                  //TODO get product kategory
                  .category(CategoryEs.builder().id(product.getCategoryId()).name("test").build())
                  .build());
    }

    public Flux<ProductEs>  findAll() {
        return productEsRepository.findAll();
    }
}
