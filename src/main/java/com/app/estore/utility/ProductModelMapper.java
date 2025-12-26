package com.app.estore.utility;

import com.app.estore.entity.Product;
import com.app.estore.response.CustomerProductDto;
import org.springframework.stereotype.Component;

@Component
public class ProductModelMapper {

    public CustomerProductDto convert(Product product) {
        CustomerProductDto customerProductDto = new CustomerProductDto();

        customerProductDto.setProductId(product.getProductId());
        customerProductDto.setVendorId(product.getVendor().getVendorId());
        customerProductDto.setTitle(product.getTitle());
        customerProductDto.setDescription(product.getDescription());
        customerProductDto.setWeight(product.getWeight());
        customerProductDto.setDimensions(product.getDimensions());
        customerProductDto.setCost(product.getCost());
        customerProductDto.setProductCategory(product.getProductCategory());

        return customerProductDto;
    }

}
