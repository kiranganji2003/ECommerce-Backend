package com.app.estore.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class Wishlist {
    @Id
    private Integer customerId;
    private Set<Integer> productIdSet = new HashSet<>();
}
