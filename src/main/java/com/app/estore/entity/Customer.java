package com.app.estore.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;

    @Column(unique = true)
    private String email;
    private String password;
    private String name;
    private String phone;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToOne(
            mappedBy = "customer",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Wishlist wishlist;

    // helper method
    public void createWishlist() {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(this);
        this.wishlist = wishlist;
    }
}

