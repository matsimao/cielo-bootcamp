package com.cielo.bootcamp.prospect.application.services;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.repositories.ProspectRepository;
import com.cielo.bootcamp.prospect.domain.Prospect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProspectService {

    @Autowired
    private ProspectRepository repository;

    public Prospect findProspectById(Long id) throws Exception {
        return this
                .repository
                .findProspectById(id)
                .orElseThrow(() -> new Exception("Prospect not found"));
    }

    public List<Prospect> findAll() {
        return this.repository.findAll();
    }

    public Prospect save(ProspectDTO prospectDTO) {
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

        this.repository.save(prospect);

        return prospect;
    }

    public void delete(Long id) throws Exception {
        Prospect prospect = this.findProspectById(id);

        this.repository.delete(prospect);
    }
}
