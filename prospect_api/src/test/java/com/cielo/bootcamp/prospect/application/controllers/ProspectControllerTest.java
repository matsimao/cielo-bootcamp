package com.cielo.bootcamp.prospect.application.controllers;

import com.cielo.bootcamp.prospect.application.dtos.ProspectDTO;
import com.cielo.bootcamp.prospect.application.repositories.ProspectRepository;
import com.cielo.bootcamp.prospect.domain.prospect.ClientType;
import com.cielo.bootcamp.prospect.domain.prospect.Prospect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProspectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProspectRepository prospectRepository;

    @Autowired
    private JacksonTester<ProspectDTO> prospectDTOJacksonTester;

    @Autowired
    private JacksonTester<Prospect> prospectJacksonTester;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getAllCode200() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/prospects"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }

    @Test
    void getCode400() throws Exception{
        Random random = new Random();

        Long id = random.nextLong();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/prospects/"+id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getCode200() throws Exception{
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

        Prospect prospectSaved = prospectRepository.save(prospect);

        Long id = prospectSaved.getId();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/prospects/"+id))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"id\":1,\"document\":\"12345678901\",\"name\":\"John Doe\",\"contactDocument\":\"12345678901\",\"contactName\":\"John Doe\",\"contactEmail\":\"john.doe@teste.com.br\",\"clientType\":\"INDIVIDUAL_CUSTOMER\",\"mcc\":\"1234\"}");
    }

    @Test
    void createProspectCreated() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "John Doe",
                "99999999999",
                "1234",
                "",
                "",
                "john.doe@teste.com.br",
                ClientType.INDIVIDUAL_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        Prospect prospect = new Prospect(prospectDTO);
        prospect.setId(1L);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                prospectJacksonTester.write(prospect).getJson()
        );
    }

    @Test
    void createProspectDuplicated() throws Exception {
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

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"Duplicate prospect\",\"statusCode\":409}");
    }

    @Test
    void createProspectBusinessCreated() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "John Doe",
                "9999999999999",
                "1234",
                "99999999999",
                "John Doe",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        Prospect prospect = new Prospect(prospectDTO);
        prospect.setId(1L);

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(
                prospectJacksonTester.write(prospect).getJson()
        );
    }

    @Test
    void createProspectNameEmptyAndInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "",
                "9999999999999",
                "1234",
                "99999999999",
                "John Doe",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'name' is empty, This field 'name' must be between 1 and 50 characters\",\"statusCode\":500}");
    }

    @Test
    void createProspectDocumentInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "Jane Doe",
                "99999999999999999",
                "1234",
                "99999999999",
                "John Doe",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'contactDocument' must be between 1 and 13 characters, This field 'document' is invalid\",\"statusCode\":500}");
    }

    @Test
    void createProspectMCCEmptyAndInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "Jane Doe",
                "9999999999999",
                "",
                "99999999999",
                "John Doe",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'MCC' is empty, This field 'MCC' is invalid, This field 'MCC' must be between 1 and 4 characters\",\"statusCode\":500}");
    }

    @Test
    void createProspectContactDocumentInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "Jane Doe",
                "9999999999999",
                "1234",
                "999999999999999",
                "John Doe",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'contactDocument' is invalid, This field 'contactDocument' must be between 1 and 11 characters\",\"statusCode\":500}");
    }

    @Test
    void createProspectContactNameEmptyAndInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "Jane Doe",
                "9999999999999",
                "1111",
                "99999999999",
                "",
                "john.doe@teste.com.br",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'contactName' is empty, This field 'contactName' must be between 1 and 50 characters\",\"statusCode\":500}");
    }

    @Test
    void createProspectEmailEmptyAndInvalid() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "Jane Doe",
                "9999999999999",
                "1111",
                "99999999999",
                "John Doe",
                "",
                ClientType.BUSINESS_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"message\":\"This field 'contactEmail' is empty, This field 'email' is invalid\",\"statusCode\":500}");
    }

    @Test
    void update() throws Exception {
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

        ProspectDTO prospectUpdatedDTO = new ProspectDTO(
                "Jane Doe",
                "12345678901",
                "1234",
                "",
                "",
                "john.doe@teste.com.br",
                ClientType.INDIVIDUAL_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/prospects/"+prospect.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectUpdatedDTO).getJson())

                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"id\":1,\"document\":\"12345678901\",\"name\":\"Jane Doe\",\"contactDocument\":\"12345678901\",\"contactName\":\"Jane Doe\",\"contactEmail\":\"john.doe@teste.com.br\",\"clientType\":\"INDIVIDUAL_CUSTOMER\",\"mcc\":\"1234\"}");
    }

    @Test
    void updateCode404() throws Exception {
        Random random = new Random();

        Long id = random.nextLong();

        ProspectDTO prospectUpdatedDTO = new ProspectDTO(
                "Jane Doe",
                "12345678901",
                "1234",
                "",
                "",
                "john.doe@teste.com.br",
                ClientType.INDIVIDUAL_CUSTOMER
        );

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/api/prospects/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectUpdatedDTO).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void delete() throws Exception {
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

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/prospects/"+prospect.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void deleteCode404() throws Exception {
        Random random = new Random();

        Long id = random.nextLong();

        MockHttpServletResponse response = mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/api/prospects/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }

    @Test
    void getQueueProspect() throws Exception {
        ProspectDTO prospectDTO = new ProspectDTO(
                "John Doe",
                "99999999999",
                "1234",
                "",
                "",
                "john.doe@teste.com.br",
                ClientType.INDIVIDUAL_CUSTOMER
        );

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/api/prospects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(prospectDTOJacksonTester.write(prospectDTO).getJson())

                )
                .andReturn().getResponse();

        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/prospects/queues/retrieve"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"id\":1,\"document\":\"99999999999\",\"name\":\"John Doe\",\"contactDocument\":\"99999999999\",\"contactName\":\"John Doe\",\"contactEmail\":\"john.doe@teste.com.br\",\"clientType\":\"INDIVIDUAL_CUSTOMER\",\"mcc\":\"1234\"}");
    }

    @Test
    void getQueueProspectEmpty() throws Exception {
        MockHttpServletResponse response = mockMvc.perform(MockMvcRequestBuilders.get("/api/prospects/queues/retrieve"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }
}