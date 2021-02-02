package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.Product;
import com.yemagci.shoppingapp.product.domain.category.Category;
import com.yemagci.shoppingapp.product.domain.es.CategoryEs;
import com.yemagci.shoppingapp.product.domain.es.CompanyEs;
import com.yemagci.shoppingapp.product.domain.es.ProductEs;
import com.yemagci.shoppingapp.product.service.category.CategoryService;
import com.yemagci.shoppingapp.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@RequiredArgsConstructor
public class ProductEsService {
    private final ProductEsRepository productEsRepository;
    private final CategoryService categoryService;
  //  @Async
   public Mono<ProductEs> saveNewProduct(Product product){
       return productEsRepository.save(
          ProductEs.builder().active(product.getActive())
                  .code(product.getCode())
                  .description(product.getDescription())
                  .features(product.getId())
                  .price(product.getPrice())//ordering siralama yapilabilmesi icin ekledik
                  .name(product.getName())
                  //TODO get Company name and code
                  .seller(CompanyEs.builder().id(product.getCompanyId()).name("buraya firmaninismini buldurup ekleyecegim").build())//firmanin adiyla search yapabilmek icin bütün arama yapilacak bilgileri kaydetmek gerekir.
                  .category(getProductCategory(product.getCategoryId()))
                  .build());
    }

    private CategoryEs getProductCategory(String categoryId) {
       Category category=categoryService.getById(categoryId);
       return CategoryEs.builder().name(category.getName()).id(category.getId()).code(category.getCode()).build();
    }

    public Flux<ProductEs>  findAll() {
        return productEsRepository.findAll();
    }
}
