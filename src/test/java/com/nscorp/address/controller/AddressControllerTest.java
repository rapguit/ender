package com.nscorp.address.controller;

import com.nscorp.IntegrationTest;
import com.nscorp.address.repository.AddressRepository;
import com.nscorp.model.Address;
import org.junit.After;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.http.HttpHeaders.LOCATION;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.responseHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by raphael on 25/03/17.
 */
public class AddressControllerTest extends IntegrationTest {

    @Autowired
    private AddressRepository repo;

    @After
    public void tearDown() throws Exception {
        repo.deleteAll();
    }

    @Test
    public void busca_todos() throws Exception {
        repo.save(Address.builder()
                .city("localidade")
                .state("UF")
                .street("rua")
                .district("bairro")
                .zip("99999999")
                .number("1")
                .complement("apto 1")
                .build()
        );

        getMockMvc().perform(
                get("/ender/ws/address"))
                .andExpect(status().isOk())
                .andDo(document("address-find-all",
                        responseFields(
                                fieldWithPath("[].id").description("The address id."),
                                fieldWithPath("[].zip").description("The address zip code."),
                                fieldWithPath("[].state").description("The address state."),
                                fieldWithPath("[].city").description("The address city."),
                                fieldWithPath("[].district").description("The address district."),
                                fieldWithPath("[].street").description("The address street."),
                                fieldWithPath("[].number").description("The address number."),
                                fieldWithPath("[].complement").description("The address complement.")
                        )
                ));
    }

    @Test
    public void busca_endereco() throws Exception {
        Address saved = repo.save(Address.builder()
                .city("localidade")
                .state("UF")
                .street("rua")
                .district("bairro")
                .zip("99999999")
                .number("1")
                .complement("apto 1")
                .build()
        );

        getMockMvc().perform(
                get("/ender/ws/address/{id}", saved.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document("address-find-one",
                        pathParameters(
                                parameterWithName("id").description("The requested address id.")
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
                        )
                ));
    }

    @Test
    public void salva_endereco() throws Exception {
        Address payload = Address.builder()
                .city("localidade")
                .state("UF")
                .street("rua")
                .district("bairro")
                .zip("99999999")
                .number("1")
                .complement("apto 1")
                .build();

        getMockMvc().perform(
                post("/ender/ws/address")
                        .contentType(APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(payload)))
                .andExpect(status().isCreated())
                .andDo(document("address-save",
                        requestFields(
                                fieldWithPath(".id").description("The address id."),
                                fieldWithPath(".zip").description("The address zip code."),
                                fieldWithPath(".state").description("The address state."),
                                fieldWithPath(".city").description("The address city."),
                                fieldWithPath(".district").description("The address district."),
                                fieldWithPath(".street").description("The address street."),
                                fieldWithPath(".number").description("The address number."),
                                fieldWithPath(".complement").description("The address complement.")
                        ),
                        responseHeaders(headerWithName(LOCATION).description("The path of new resource created."))
                ));
    }

    @Test
    public void deleta_endereco() throws Exception {
        Address saved = repo.save(Address.builder()
                .city("localidade")
                .state("UF")
                .street("rua")
                .district("bairro")
                .zip("99999999")
                .number("1")
                .complement("apto 1")
                .build());

        getMockMvc().perform(
                delete("/ender/ws/address/{id}", saved.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(document("address-delete",
                        pathParameters(
                                parameterWithName("id").description("The requested address id.")
                        ))
                );
    }

    @Test
    public void valida_endereco_antes_de_salvar() throws Exception {
        Address payload = Address.builder().build();

        getMockMvc().perform(
                post("/ender/ws/address")
                        .contentType(APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(payload)))
                .andExpect(status().isBadRequest())
                .andDo(document("address-save-validation-error",
                        responseFields(
                                fieldWithPath(".status").description("Error status code."),
                                fieldWithPath(".message").description("Error description")
                        ))
                );
    }

}