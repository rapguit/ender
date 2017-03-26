package com.nscorp.cep.controller;

import com.nscorp.IntegrationTest;
import com.nscorp.cep.dto.CepCriteria;
import com.nscorp.cep.dto.CepInfo;
import org.junit.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Created by raphael on 24/03/17.
 */
public class CepControllerTest extends IntegrationTest {

    @Test
    public void busca_endereco_por_cep() throws Exception {

        CepInfo response = CepInfo.builder()
                .bairro("bairro")
                .cep("22333111")
                .localidade("localidade")
                .logradouro("logradouro")
                .uf("UF")
                .build();

        String resp = getObjectMapper().writeValueAsString(response);
        getMockServer().expect(requestTo("https://viacep.com.br/ws/22333111/json/"))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(resp, MediaType.APPLICATION_JSON));


        CepCriteria payload = new CepCriteria("22333111");
        getMockMvc().perform(
                post("/ender/ws/cep-address")
                        .contentType(APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(payload)))
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
                                fieldWithPath(".number").description("The address number."),
                                fieldWithPath(".complement").description("The address complement.")
                        )));
    }

}
