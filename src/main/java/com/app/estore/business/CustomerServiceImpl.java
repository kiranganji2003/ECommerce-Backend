package com.app.estore.business;

import com.app.estore.entity.AllProducts;
import com.app.estore.entity.Customer;
import com.app.estore.entity.Product;
import com.app.estore.repository.AllProductsRepository;
import com.app.estore.repository.CustomerRepository;
import com.app.estore.repository.ProductRepository;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.AllProductsDto;
import com.app.estore.response.Status;
import com.app.estore.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.app.estore.common.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AllProductsRepository allProductsRepository;
    private final ProductRepository productRepository;

    @Override
    public Status registerCustomer(RegistrationDto registrationDto) {
        Customer customer = new Customer();
        customer.setEmail(registrationDto.getEmail());
        customer.setPassword(bCryptPasswordEncoder.encode(registrationDto.getPassword()));
        customer.setName(registrationDto.getName());
        customer.setPhone(registrationDto.getPhone());
        customerRepository.save(customer);
        return new Status(REGISTERED_SUCCESSFULLY);
    }

    @Override
    public List<AllProductsDto> listAllProducts() {

        List<AllProducts> allProductsList = allProductsRepository.findAll();
        List<AllProductsDto> allProductsDtoList = new ArrayList<>();

        for(AllProducts allProducts : allProductsList) {
            Product product = productRepository.getReferenceById(allProducts.getProductId());
            AllProductsDto allProductsDto = new AllProductsDto();

            allProductsDto.setProductId(product.getProductId());
            allProductsDto.setVendorId(allProducts.getVendorId());
            allProductsDto.setTitle(product.getTitle());
            allProductsDto.setDescription(product.getDescription());
            allProductsDto.setWeight(product.getWeight());
            allProductsDto.setDimensions(product.getDimensions());
            allProductsDto.setCost(product.getCost());
            allProductsDto.setProductCategory(product.getProductCategory());

            allProductsDtoList.add(allProductsDto);
        }

        return allProductsDtoList;
    }
}
