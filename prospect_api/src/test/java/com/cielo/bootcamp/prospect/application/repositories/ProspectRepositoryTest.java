package com.cielo.bootcamp.prospect.application.repositories;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.services.ProspectService;
import com.cielo.bootcamp.prospect.domain.prospect.ClientType;
import com.cielo.bootcamp.prospect.domain.prospect.Prospect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class ProspectRepositoryTest {

    @Autowired
    private ProspectRepository prospectRepository;

    @BeforeEach
    void setUp() throws Exception{
        ProspectDTO prospectDTO = new ProspectDTO(
				"John Doe",
				"12345678901",
				"1234",
				"",
				"",
				"john.doe@teste.com.br",
				ClientType.INDIVIDUAL_CUSTOMER
		);

        Prospect prospect = new Prospect(prospectDTO);

        prospectRepository.save(prospect);
    }

    @Test
    void findProspectByIdIsEmpty() {
        Random random = new Random();

        Long id = random.nextLong();;

        Optional<Prospect> prospect = prospectRepository.findProspectById(id);

        assertEquals(Optional.empty(), prospect);
    }

    @Test
    void findProspectById() {
        Long id = 1L;

        Optional<Prospect> prospect = prospectRepository.findProspectById(id);

        assertNotEquals(Optional.empty(), prospect);
    }
}