package com.cielo.bootcamp.prospect.domain;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
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

    private String name;

    private String MCC;

    private String contactDocument;

    private String contactName;

    private String contactEmail;

    @Enumerated
    private ClientType clientType;

    public Prospect(ProspectDTO prospectDTO) {
        this.name = prospectDTO.name();
        this.document = prospectDTO.document();
        this.MCC = prospectDTO.MCC();
        this.contactDocument = prospectDTO.contactDocument();
        this.contactName = prospectDTO.contactName();
        this.contactEmail = prospectDTO.contactEmail();
        this.clientType = prospectDTO.clientType();
    }

}
