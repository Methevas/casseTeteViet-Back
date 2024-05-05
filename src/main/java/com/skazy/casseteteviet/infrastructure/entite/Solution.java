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

    @Column(name="statut")
    private boolean statut;

    public String statutToString(){
        if(this.statut){
            return "Correcte";
        }
        else {
            return "Incorrecte";
        }
    }
}
