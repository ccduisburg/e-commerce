package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import com.yemagci.shoppingapp.product.domain.es.ProductEs;
import com.yemagci.shoppingapp.product.model.ProductResponse;
import com.yemagci.shoppingapp.product.model.ProductSaveRequest;
import com.yemagci.shoppingapp.product.model.ProductSellerResponse;
import com.yemagci.shoppingapp.product.repository.mongo.ProductRepository;
import com.yemagci.shoppingapp.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.math.BigDecimal;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductEsRepository productEsRepository;
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;
    public Flux<ProductResponse> getAll(){
       return productEsRepository.findAll().map(this::mapToDto);
    }


    ProductResponse save(ProductSaveRequest productSaveRequest){
        //1- mongoya yaz
        //2- Es den güncelle
        //3- redisten güncelle (stok miktarlarini orada tutacagiz)
        //4- Es den cevap dön
        //5- response nesnesine dönüstür
        return null;
    }
/**
    1- Es den sorgula
    2- Cacl fiealdlari iste
    3- redisten ihtiyac alanlari getir
    4- response nesnesine dönüstür
 @param productEs
 @return
 */
    private ProductResponse mapToDto(ProductEs item){
        BigDecimal productPrice=productPriceService.getByMoneyType(item.getId(), MoneyTypes.EUR);
        return ProductResponse.builder()
            .price(productPrice)
            .name(item.getName())
            .features(item.getFeatures())
            .id(item.getId())
            .description(item.getDescription())
            .deliveryIn(productDeliveryService.getDeliveryInfo(item.getId()))
            .categoryId(item.getCategory().getId())
            .available(productAmountService.getByProductId(item.getId()))
            .freeDelivery(productDeliveryService.freeDeliveryCheck(item.getId(),productPrice))
            .moneyType(MoneyTypes.EUR)
            .image(productImageService.getProductMainImage(item.getId()))
            .seller(ProductSellerResponse.builder().id(item.getSeller().getId()).name(item.getSeller().getName()).build())
            .build();
    }
}
