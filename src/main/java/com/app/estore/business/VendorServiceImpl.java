package com.app.estore.business;

import com.app.estore.entity.Product;
import com.app.estore.entity.Vendor;
import com.app.estore.repository.ProductRepository;
import com.app.estore.repository.VendorRepository;
import com.app.estore.request.ProductRequestDto;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.response.VendorProfileDto;
import com.app.estore.service.VendorService;
import com.app.estore.utility.CurrentUser;
import com.app.estore.utility.VendorModelMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.app.estore.utility.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final ProductRepository productRepository;
    private final CurrentUser currentUser;
    private final VendorModelMapper vendorModelMapper;

    @Override
    public Status registerVendor(RegistrationDto registrationDto) {
        Vendor vendor = new Vendor();
        vendor.setEmail(registrationDto.getEmail());
        vendor.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        vendor.setName(registrationDto.getName());
        vendor.setPhone(registrationDto.getPhone());
        vendorRepository.save(vendor);
        return new Status(SUCCESS);
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

        Vendor vendor = vendorRepository.findByEmail(currentUser.getCurrentUsername()).get();
        product.setVendor(vendor);
        vendor.getProducts().add(product);
        productRepository.save(product);

        return new Status(SUCCESS);
    }

    @Override
    public VendorProfileDto getVendorProfile() {
        Vendor vendor = vendorRepository.findByEmail(currentUser.getCurrentUsername()).get();
        return vendorModelMapper.convertToVendorProfileDto(vendor);
    }

    @Override
    @Transactional
    public Status deleteProductById(Long productId) {

        Vendor vendor = vendorRepository.findByEmail(currentUser.getCurrentUsername()).get();

        Product product = productRepository
                .findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        if (!product.getVendor().getVendorId().equals(vendor.getVendorId())) {
            throw new RuntimeException("You are not allowed to delete this product");
        }

        productRepository.delete(product);

        return new Status(SUCCESS);
    }

}
