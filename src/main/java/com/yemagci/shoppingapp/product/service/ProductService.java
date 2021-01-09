package com.yemagci.shoppingapp.product.service;

import com.yemagci.shoppingapp.product.domain.MoneyTypes;
import com.yemagci.shoppingapp.product.domain.Product;
import com.yemagci.shoppingapp.product.domain.ProductImage;
import com.yemagci.shoppingapp.product.domain.es.ProductEs;
import com.yemagci.shoppingapp.product.model.product.ProductResponse;
import com.yemagci.shoppingapp.product.model.product.ProductSaveRequest;
import com.yemagci.shoppingapp.product.model.product.ProductSellerResponse;
import com.yemagci.shoppingapp.product.repository.mongo.ProductRepository;
import com.yemagci.shoppingapp.repository.es.ProductEsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import java.math.BigDecimal;
import java.util.stream.Collectors;
import static com.yemagci.shoppingapp.product.domain.ProductImage.ImageType.FEATURE;


@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductEsRepository productEsRepository;
    private final ProductRepository productRepository;
    private final ProductPriceService productPriceService;
    private final ProductDeliveryService productDeliveryService;
    private final ProductAmountService productAmountService;
    private final ProductImageService productImageService;
    private final ProductEsService productEsService;

    public Flux<ProductResponse> getAll(){
       return productEsService.findAll().map(this::mapToDto);
    }


  public  ProductResponse save(ProductSaveRequest request){
       Product product= Product.builder()
                .active(Boolean.TRUE)
                .code("Pr001")//bunabir sistem ayarlanacak. kode systemi
                .categoryId(request.getCategoryId())
                .companyId(request.getSellerId())
                .description(request.getDescription())
                .features(request.getFeatures())
                .name(request.getName())
                .productImages(request.getImages().stream().map(item-> new ProductImage(FEATURE,item)).collect(Collectors.toList()))
                .build();
       product =productRepository.save(product).block();//MongoDB ye yazdik
            return this.mapToDto(productEsService.saveNewProduct(product).block());// elastic serach e kaydediyor mongodb ile beraber
        //1- mongoya yaz
        //2- Es den güncelle
        //3- redisten güncelle (stok miktarlarini orada tutacagiz)
        //4- Es den cevap dön
        //5- response nesnesine dönüstür

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
        if(item==null){
            return null;
        }
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

    public Mono<Long> count() {
        return productRepository.count();
    }
}
