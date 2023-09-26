package com.cielo.bootcamp.prospect.application.repositories;

import com.cielo.bootcamp.prospect.domain.Prospect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProspectRepository extends JpaRepository<Prospect, Long> {
}
