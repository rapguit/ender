package com.nscorp.cep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nscorp.EnderApplication;
import com.nscorp.cep.dto.CepCriteria;
import com.nscorp.cep.dto.CepInfo;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
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

    @Autowired private WebApplicationContext context;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private RestTemplate restTemplate;

    private MockMvc mockMvc;
    private MockRestServiceServer mockServer;

    @Before
    public void setUp() {
        this.mockMvc = webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();

        mockServer = MockRestServiceServer.createServer(restTemplate);
    }

    @Test
    public void busca_endereco_por_cep() throws Exception {

        CepInfo response = CepInfo.builder()
                .bairro("bairro")
                .cep("22333111")
                .localidade("localidade")
                .logradouro("logradouro")
                .uf("UF")
                .build();

        String resp = objectMapper.writeValueAsString(response);
        mockServer.expect(requestTo("https://viacep.com.br/ws/22333111/json/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(resp, MediaType.APPLICATION_JSON));


        CepCriteria payload = new CepCriteria("22333111");
        this.mockMvc.perform(
                post("/ender/ws/cep-address")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk())
                .andDo(document("cep-address",
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
