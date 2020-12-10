package com.yemagci.shoppingapp.product.startup;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import com.yemagci.shoppingapp.product.model.ProductSaveRequest;
import com.yemagci.shoppingapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

import java.util.UUID;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ProductDemoDate {
 private final ProductService productService;

// @PostConstruct
// init(){
//
// }

 @EventListener(ApplicationReadyEvent.class)//api nin redy olmasini beklemesi icin yaptik
 public void migrate(){
    Long countData=productService.count().block();
   if(countData.equals(0L)){
       IntStream.range(0,20).forEach(item->{
           productService.save(
               ProductSaveRequest.builder()
                       .sellerId(UUID.randomUUID().toString())
                       .id(UUID.randomUUID().toString())
                       .description("Product "+item)
                       .money(MoneyTypes.EUR)
                       .categoryId(UUID.randomUUID().toString())
                       .name("Product name "+item)
                       .features("<li>Black_Color</li><li>Aluminyum case</li><li>2 year Warranty</li> <li>5 Inch(35x55mm)</li>")
                       .price(BigDecimal.TEN)
                       .images(List.of("https://productimages.hepsiburada.net/s/32/500/10352568139826.jpg"))
                       .build());
               }
       );// 0dan 20 ye kadar data olusturacak
   }
 }
}