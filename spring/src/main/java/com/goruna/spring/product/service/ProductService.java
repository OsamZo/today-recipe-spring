package com.goruna.spring.product.service;

import com.goruna.spring.common.util.CustomUserUtils;
import com.goruna.spring.product.dto.CreateProductReqDTO;
import com.goruna.spring.product.entity.Product;
import com.goruna.spring.product.repository.ProductRepository;
import com.goruna.spring.shop.entity.Shop;
import com.goruna.spring.shop.repository.ShopRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ShopRepositoryCustom shopRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

    // 상품 등록
    @Transactional
    public void createProductInfo(CreateProductReqDTO createProductReqDTO) {

        Long userSeq = CustomUserUtils.getCurrentUserSeq();   // 토큰에서 추출
        Shop shop = shopRepository.findShopByUserSeq(userSeq);

        createProductReqDTO.setShopSeq(shop.getShopSeq());
        createProductReqDTO.setProductSeq(null);
        Product product = modelMapper.map(createProductReqDTO, Product.class);

        productRepository.save(product);
    }
}
