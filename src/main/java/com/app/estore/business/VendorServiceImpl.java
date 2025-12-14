package com.app.estore.business;

import com.app.estore.entity.Vendor;
import com.app.estore.repository.VendorRepository;
import com.app.estore.request.RegistrationDto;
import com.app.estore.response.Status;
import com.app.estore.service.VendorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import static com.app.estore.common.EStoreConstants.*;

@Service
@RequiredArgsConstructor
public class VendorServiceImpl implements VendorService {

    private final VendorRepository vendorRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

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
}
