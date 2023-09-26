package com.cielo.bootcamp.prospect.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Prospect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String document;

    private String MCC;

    private String contactDocument;

    private String contactName;

    private String contactEmail;

    @Enumerated
    private ProspectType prospectType;

}
