package com.cielo.bootcamp.prospect.application.services;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.repositories.ProspectRepository;
import com.cielo.bootcamp.prospect.domain.ClientType;
import com.cielo.bootcamp.prospect.domain.Prospect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.*;

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
                errors.rejectValue("document", "length.exceeded", "O campo 'document' deve ter entre 1 e 11 caracteres");
            }
        } else {
            if (prospect.getDocument().length() > 13) {
                errors.rejectValue("document", "length.exceeded", "O campo 'document' deve ter entre 1 e 13 caracteres");
            }
        }

        if (errors.hasErrors()) {
            for (ObjectError error : errors.getAllErrors()) {
                LOG.error(error.getDefaultMessage());
            }
            throw new Exception("A validação falhou.");
        }
    }

    public Prospect findProspectById(Long id) throws Exception {
        return this
                .repository
                .findProspectById(id)
                .orElseThrow(() -> new Exception("Prospect not found"));
    }

    public List<Prospect> findAll() {
        return this.repository.findAll();
    }

    public Prospect save(ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = new Prospect(prospectDTO);

        this.validateProspect(prospect);
        return  this.repository.save(new Prospect(prospectDTO));
    }

    public Prospect update(Long id, ProspectDTO prospectDTO) throws Exception{
        Prospect prospect = this.findProspectById(id);

        prospect.setName(prospectDTO.name());
        prospect.setMCC(prospectDTO.MCC());
        prospect.setDocument(prospectDTO.document());
        prospect.setContactDocument(prospectDTO.contactDocument());
        prospect.setContactName(prospectDTO.contactName());
        prospect.setContactEmail(prospectDTO.contactEmail());
        prospect.setClientType(prospectDTO.clientType());

        this.validateProspect(prospect);
        return this.repository.save(prospect);
    }

    public void delete(Long id) throws Exception {
        Prospect prospect = this.findProspectById(id);

        this.repository.delete(prospect);
    }
}
