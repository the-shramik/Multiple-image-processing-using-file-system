package com.test.model.dto;

import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long productId;

    private String productName;

    private Double productPrice;

    @Transient
    private List<String> imageBase64;

}
