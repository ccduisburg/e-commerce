package com.yemagci.shoppingapp.product.startup;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import com.yemagci.shoppingapp.product.model.category.CategorySaveRequest;
import com.yemagci.shoppingapp.product.model.category.CategoryResponse;
import com.yemagci.shoppingapp.product.model.product.ProductSaveRequest;
import com.yemagci.shoppingapp.product.service.ProductService;
import com.yemagci.shoppingapp.product.service.category.CategoryService;
import com.yemagci.shoppingapp.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import java.util.UUID;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ProductDemoDate {
 private final ProductService productService;
 private final ProductEsRepository productEsRepository;
 private final CategoryService categoryService;

// @PostConstruct
// init(){
//
// }

 @EventListener(ApplicationReadyEvent.class)//api nin redy olmasini beklemesi icin yaptik
 public void migrate(){
    Long countData=productService.count().block();
   if(countData.equals(0L)){
       productEsRepository.deleteAll().block();// Ürün eklemeden önce elastigi temizliyoruz
       CategoryResponse elektronik=categoryService.save(CategorySaveRequest.builder().name("Elektronik").build());
       CategoryResponse telefon=categoryService.save(CategorySaveRequest.builder().name("Mobile").build());


       IntStream.range(0,20).forEach(item->{
           HashMap<MoneyTypes,BigDecimal> price=new HashMap<>(){{
               put(MoneyTypes.EUR,BigDecimal.valueOf((item+1)*5));
               put(MoneyTypes.USD,BigDecimal.valueOf((item+1)*4));
           }};
           productService.save(
               ProductSaveRequest.builder()
                       .sellerId(UUID.randomUUID().toString())
                       .id(UUID.randomUUID().toString())
                       .description("Product "+item)
                       .price(price)
                       .categoryId(telefon.getId())
                       .name("Product name "+item)
                       .features("<li>Black_Color</li><li>Aluminyum case</li><li>2 year Warranty</li> <li>5 Inch(35x55mm)</li>")
                       .images(List.of("https://productimages.hepsiburada.net/s/32/500/10352568139826.jpg"))
                       .build());
               }
       );// 0dan 20 ye kadar data olusturacak
   }
 }
}
