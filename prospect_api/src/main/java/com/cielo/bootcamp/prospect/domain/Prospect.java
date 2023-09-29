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

    @NotBlank(message = "This field 'document' is empty")
    @Pattern(regexp = "\\d{1,13}", message = "This field 'document' is invalid")
    private String document;

    @NotBlank(message = "This field 'name' is empty")
    @Size(min = 1, max = 50, message = "This field 'name' must be between 1 and 50 characters")
    private String name;

    @NotBlank(message = "This field 'MCC' is empty")
    @Size(min = 1, max = 4, message = "This field 'MCC' must be between 1 and 4 characters")
    @Pattern(regexp = "\\d{1,4}", message = "This field 'MCC' is invalid")
    private String MCC;

    @NotBlank(message = "This field 'contactDocument' is empty")
    @Size(min = 1, max = 11, message = "This field 'contactDocument' must be between 1 and 11 characters")
    @Pattern(regexp = "\\d{1,11}",  message = "This field 'contactDocument' is invalid")
    private String contactDocument;

    @NotBlank(message = "This field 'contactName' is empty")
    @Size(min = 1, max = 50, message = "This field 'contactName' must be between 1 and 50 characters")
    private String contactName;

    @NotBlank(message = "This field 'contactEmail' is empty")
    @Email(message = "This field 'email' deve ser um email")
    @Pattern(
            regexp = "^([a-zA-Z0-9_\\-\\.]+)@([a-zA-Z0-9_\\-\\.]+)\\.([a-zA-Z]{2,5})$",
            message = "This field 'email' is invalid"
    )
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
