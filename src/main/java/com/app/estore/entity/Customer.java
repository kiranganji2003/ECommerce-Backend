package com.app.estore.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customers")
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

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

    @OneToOne(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orderList = new ArrayList<>();

    public void createWishlistAndCart() {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(this);
        this.wishlist = wishlist;

        Cart cart = new Cart();
        cart.setCustomer(this);
        this.cart = cart;
    }

    public Customer() {
        Wishlist wishlist = new Wishlist();
        wishlist.setCustomer(this);
        this.wishlist = wishlist;

        Cart cart = new Cart();
        cart.setCustomer(this);
        this.cart = cart;
    }
}

