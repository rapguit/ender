package com.nscorp.cep.service;

import com.nscorp.cep.dto.CepInfo;
import com.nscorp.cep.provider.CepProvider;
import com.nscorp.cep.validator.CepValidator;
import com.nscorp.model.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Created by raphael on 23/03/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class CepServiceTest {

    @Mock private CepValidator validator;
    @Mock private CepProvider provider;
    @InjectMocks private CepService service;

    @Test
    public void returna_endereco_de_cep_valido() throws Exception {
        CepInfo info = CepInfo.builder()
                .bairro("bairro")
                .cep("20000012")
                .localidade("localidade")
                .logradouro("logradouro")
                .uf("UF")
                .build();

        when(provider.find(anyString())).thenReturn(info);

        Address address = service.findAddressByCep("20000012");

        verify(provider, only()).find(anyString());
        verify(validator, only()).validate(anyString());

        assertThat(address.getDistrict(), equalTo("bairro"));
        assertThat(address.getZip(), equalTo("20000012"));
        assertThat(address.getCity(), equalTo("localidade"));
        assertThat(address.getStreet(), equalTo("logradouro"));
        assertThat(address.getState(), equalTo("UF"));
    }
}