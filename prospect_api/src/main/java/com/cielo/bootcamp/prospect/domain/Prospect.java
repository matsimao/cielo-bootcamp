package com.cielo.bootcamp.prospect.domain;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotBlank(message = "O campo 'document' não pode estar em branco")
    @Pattern(regexp = "\\d{1,13}")
    private String document;

    @NotBlank(message = "O campo 'name' não pode estar em branco")
    @Size(min = 1, max = 50, message = "O campo 'name' deve ter entre 1 e 50 caracteres")
    private String name;

    @NotBlank(message = "O campo 'MCC' não pode estar em branco")
    @Size(min = 1, max = 4, message = "O campo 'MCC' deve ter entre 1 e 4 caracteres")
    @Pattern(regexp = "\\d{1,4}")
    private String MCC;

    @NotBlank(message = "O campo 'contactDocument' não pode estar em branco")
    @Size(min = 1, max = 11, message = "O campo 'contactDocument' deve ter entre 1 e 11 caracteres")
    @Pattern(regexp = "\\d{1,11}")
    private String contactDocument;

    @NotBlank(message = "O campo 'contactName' não pode estar em branco")
    @Size(min = 1, max = 50, message = "O campo 'contactName' deve ter entre 1 e 50 caracteres")
    private String contactName;

    @NotBlank(message = "O campo 'contactEmail' não pode estar em branco")
    @Email
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$")
    private String contactEmail;

    @Enumerated
    private ClientType clientType;

    public Prospect(ProspectDTO prospectDTO) {
        this.name = prospectDTO.name();
        this.document = prospectDTO.document();
        this.MCC = prospectDTO.MCC();
        this.clientType = prospectDTO.clientType();
        this.contactEmail = prospectDTO.contactEmail();

        if (prospectDTO.clientType() == ClientType.INDIVIDUAL_CUSTOMER) {
            this.contactDocument = prospectDTO.document();
            this.contactName = prospectDTO.name();
        } else {
            this.contactDocument = prospectDTO.contactDocument();
            this.contactName = prospectDTO.contactName();

        }
    }

}
