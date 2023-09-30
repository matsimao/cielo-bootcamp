package com.cielo.bootcamp.prospect.application.repositories;

import com.cielo.bootcamp.prospect.domain.prospect.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProspectRepository extends JpaRepository<Prospect, Long> {
    Optional<Prospect> findProspectById(Long id);
}
