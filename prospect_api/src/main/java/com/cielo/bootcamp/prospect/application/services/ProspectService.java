package com.cielo.bootcamp.prospect.application.services;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.repositories.ProspectRepository;
import com.cielo.bootcamp.prospect.domain.ClientType;
import com.cielo.bootcamp.prospect.domain.Prospect;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.*;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProspectService {

    @Autowired
    private ProspectRepository repository;

    @Autowired
    private Validator validator;

    private final Logger LOG = LoggerFactory.getLogger(ProspectService.class);

    public void validateProspect(Prospect prospect) throws  Exception{
        Errors errors = new BeanPropertyBindingResult(prospect, "prospect");

        validator.validate(prospect, errors);

        if (prospect.getClientType() == ClientType.INDIVIDUAL_CUSTOMER) {
            if (prospect.getDocument().length() > 11) {
                errors.rejectValue("document", "length.exceeded", "This field 'contactDocument' must be between 1 and 11 characters");
            }
        } else {
            if (prospect.getDocument().length() > 13) {
                errors.rejectValue("document", "length.exceeded", "This field 'contactDocument' must be between 1 and 13 characters");
            }
        }

        if (errors.hasErrors()) {
            List<String> listErrors = new ArrayList<>();
            for (ObjectError error : errors.getAllErrors()) {
                listErrors.add(error.getDefaultMessage());
            }
            throw new Exception(String.join(", ", listErrors));
        }
    }

    public Prospect findProspectById(Long id) throws Exception {
        return this
                .repository
                .findProspectById(id)
                .orElseThrow(() -> new EntityNotFoundException("Prospect not found"));
    }

    public List<Prospect> findAll() {
        return this.repository.findAll();
    }

    public Prospect save(ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = new Prospect(prospectDTO);

        if (prospect.getClientType() == ClientType.INDIVIDUAL_CUSTOMER)
            prospect.setDocument(String.format("%011d", Long.parseLong(prospect.getDocument())));
        else
            prospect.setDocument(String.format("%013d", Long.parseLong(prospect.getDocument())));

        prospect.setContactDocument(String.format("%011d", Long.parseLong(prospect.getContactDocument())));

        this.validateProspect(prospect);
        return  this.repository.save(prospect);
    }

    public Prospect update(Long id, ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = this.findProspectById(id);

        if (prospectDTO.name() != null) prospect.setName(prospectDTO.name());
        if (prospectDTO.MCC() != null) prospect.setMCC(prospectDTO.MCC());
        if (prospectDTO.document() != null) prospect.setDocument(prospectDTO.document());
        if (prospectDTO.contactDocument() != null) prospect.setContactDocument(prospectDTO.contactDocument());
        if (prospectDTO.contactName() != null) prospect.setContactName(prospectDTO.contactName());
        if (prospectDTO.contactEmail() != null) prospect.setContactEmail(prospectDTO.contactEmail());
        if (prospectDTO.clientType() != null) prospect.setClientType(prospectDTO.clientType());

        if (prospect.getClientType() == ClientType.INDIVIDUAL_CUSTOMER)
            prospect.setDocument(String.format("%011d", Long.parseLong(prospect.getDocument())));
        else
            prospect.setDocument(String.format("%013d", Long.parseLong(prospect.getDocument())));

        prospect.setContactDocument(String.format("%011d", Long.parseLong(prospect.getContactDocument())));
        this.validateProspect(prospect);
        return this.repository.save(prospect);
    }

    public void delete(Long id) throws Exception {
        Prospect prospect = this.findProspectById(id);

        this.repository.delete(prospect);
    }
}
