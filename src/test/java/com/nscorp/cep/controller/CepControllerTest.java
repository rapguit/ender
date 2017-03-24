package com.nscorp.cep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nscorp.EnderApplication;
import com.nscorp.cep.dto.CepCriteria;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;


/**
 * Created by raphael on 24/03/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = EnderApplication.class)
@WebAppConfiguration
public class CepControllerTest {

    @Rule
    public final JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    @Test
    public void busca_endereco_por_cep() throws Exception {
        CepCriteria payload = new CepCriteria("22333111");
        this.mockMvc.perform(
                post("/ender/address")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andDo(document("find-address-by-cep",
                        requestFields(
                                fieldWithPath("cep").description("The cep search criteria.")
                        ),
                        responseFields(
                                fieldWithPath(".id").description("The address id."),
                                fieldWithPath(".zip").description("The address zip code."),
                                fieldWithPath(".state").description("The address state."),
                                fieldWithPath(".city").description("The address city."),
                                fieldWithPath(".district").description("The address district."),
                                fieldWithPath(".street").description("The address street."),
                                fieldWithPath(".complement").description("The address complement.")
                        )));
    }

}
