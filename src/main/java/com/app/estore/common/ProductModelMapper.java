package com.app.estore.common;

import com.app.estore.entity.Product;
import com.app.estore.response.AllProductsDto;
import org.springframework.stereotype.Component;

@Component
public class ProductModelMapper {

    public AllProductsDto convert(Product product) {
        AllProductsDto allProductsDto = new AllProductsDto();

        allProductsDto.setProductId(product.getProductId());
        allProductsDto.setVendorId(product.getVendor().getVendorId());
        allProductsDto.setTitle(product.getTitle());
        allProductsDto.setDescription(product.getDescription());
        allProductsDto.setWeight(product.getWeight());
        allProductsDto.setDimensions(product.getDimensions());
        allProductsDto.setCost(product.getCost());
        allProductsDto.setProductCategory(product.getProductCategory());

        return allProductsDto;
    }

}
