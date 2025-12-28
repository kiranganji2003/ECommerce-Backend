package com.app.estore.entity;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
@Entity
public class CustomerCart {
    @Id
    private Integer customerId;
    
    @ElementCollection
    private Map<Integer, Integer> productToQuantityMap = new HashMap<>();
}
