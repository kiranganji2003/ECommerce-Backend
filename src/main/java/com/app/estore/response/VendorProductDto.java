package com.app.estore.response;

import com.app.estore.entity.Vendor;
import com.app.estore.utility.ProductCategory;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
public class VendorProductDto {
    private Integer productId;
    private String title;
    private String description;
    private String weight;
    private String dimensions;
    private Integer cost;
    private ProductCategory productCategory;
    private LocalDateTime createdAt;
}
