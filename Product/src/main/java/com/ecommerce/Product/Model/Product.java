package com.ecommerce.Product.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(name= "product_name")
    private String name;

    @NotBlank
    @Column(name= "product_description")
    private String description;

    @NotNull
    @Column(name= "product_price")
    private BigDecimal price;

    @NotNull
    @Column(name= "product_quantity")
    private Integer stockQuantity;

    @NotBlank
    @Size(min = 2, max = 20)
    @Column(name= "product_category")
    private String category;

    @NotBlank
    @Size(min = 2, max = 200)
    @Column(name= "product_url")
    private String imageUrl;

    private Boolean active = true;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
