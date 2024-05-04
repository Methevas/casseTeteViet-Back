package com.skazy.casseteteviet.infrastructure.entite;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name="solutions")
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="valeurs")
    private String valeurs;
}
