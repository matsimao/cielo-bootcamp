package com.cielo.bootcamp.prospect.application.dtos;

import com.cielo.bootcamp.prospect.domain.ProspectType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

public record ProspectDTO(
    String document,
    String MCC,
    String contactDocument,
    String contactName,
    String contactEmail,
    @Enumerated(EnumType.STRING)
    ProspectType prospectType
) {}
