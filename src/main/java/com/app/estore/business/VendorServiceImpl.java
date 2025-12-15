package com.app.estore.business;

import com.app.estore.entity.Product;
import com.app.estore.entity.Vendor;
import com.app.estore.repository.ProductRepository;
import com.app.estore.repository.VendorRepository;
import com.app.estore.request.ProductRequestDto;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.service.VendorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.app.estore.common.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ProductRepository productRepository;

    @Override
    public Status registerVendor(RegistrationDto registrationDto) {
        Vendor vendor = new Vendor();
        vendor.setEmail(registrationDto.getEmail());
        vendor.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        vendor.setName(registrationDto.getName());
        vendor.setPhone(registrationDto.getPhone());
        vendorRepository.save(vendor);
        return new Status(REGISTERED_SUCCESSFULLY);
    }

    @Override
    @Transactional
    public Status addProduct(ProductRequestDto productRequestDto) {
        Product product = new Product();
        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setWeight(productRequestDto.getWeight());
        product.setDimensions(productRequestDto.getDimensions());
        product.setCost(productRequestDto.getCost());
        product.setProductCategory(productRequestDto.getProductCategory());

        Vendor vendor = vendorRepository.findByEmail(getCurrentUsername()).get();
        product.setVendor(vendor);
        vendor.getProductList().add(product);
        productRepository.save(product);
        return new Status("Product Added");
    }

    public String getCurrentUsername() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        return authentication.getName(); // username
    }
}
