package com.app.estore.utility;

import com.app.estore.entity.Product;
import com.app.estore.entity.Vendor;
import com.app.estore.response.VendorProductDto;
import com.app.estore.response.VendorProfileDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class VendorModelMapper {
    public VendorProfileDto convertToVendorProfileDto(Vendor vendor) {
        VendorProfileDto vendorProfileDto = new VendorProfileDto();
        vendorProfileDto.setEmail(vendor.getEmail());
        vendorProfileDto.setName(vendor.getName());
        vendorProfileDto.setPhone(vendor.getPhone());
        vendorProfileDto.setCreatedAt(vendor.getCreatedAt());

        List<VendorProductDto> productList = new ArrayList<>();

        for(Product product : vendor.getProducts()) {
            productList.add(convertToVendorProductDto(product));
        }

        vendorProfileDto.setProductList(productList);
        return vendorProfileDto;
    }

    public VendorProductDto convertToVendorProductDto(Product product) {
        VendorProductDto vendorProductDto = new VendorProductDto();
        vendorProductDto.setProductId(product.getProductId());
        vendorProductDto.setTitle(product.getTitle());
        vendorProductDto.setDescription(product.getDescription());
        vendorProductDto.setWeight(product.getWeight());
        vendorProductDto.setDimensions(product.getDimensions());
        vendorProductDto.setCost(product.getCost());
        vendorProductDto.setProductCategory(product.getProductCategory());
        vendorProductDto.setCreatedAt(product.getCreatedAt());
        return vendorProductDto;
    }
}
