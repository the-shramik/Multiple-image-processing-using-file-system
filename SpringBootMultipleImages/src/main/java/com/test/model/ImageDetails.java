package com.test.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ImageDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long imageDetailsId;

    public String imageName;

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;

}
