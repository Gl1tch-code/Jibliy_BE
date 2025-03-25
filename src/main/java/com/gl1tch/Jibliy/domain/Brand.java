package com.gl1tch.Jibliy.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String logo;

    @ManyToMany(mappedBy = "brands")
    private List<Product> products;
}