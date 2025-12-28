package com.app.estore.utility;

import com.app.estore.entity.Vendor;
import com.app.estore.response.VendorProfileDto;
import org.springframework.stereotype.Component;

@Component
public class VendorModelMapper {
    public VendorProfileDto convertToVendorProfileDto(Vendor vendor) {
        VendorProfileDto vendorProfileDto = new VendorProfileDto();
        vendorProfileDto.setEmail(vendor.getEmail());
        vendorProfileDto.setName(vendor.getName());
        vendorProfileDto.setPhone(vendor.getPhone());
        vendorProfileDto.setCreatedAt(vendor.getCreatedAt());
        vendorProfileDto.setProductList(vendor.getProductList());
        return vendorProfileDto;
    }
}
