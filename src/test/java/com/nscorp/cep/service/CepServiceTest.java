package com.nscorp.cep.service;

import com.nscorp.cep.dto.CepInfo;
import com.nscorp.cep.exception.CepNotFoundException;
import com.nscorp.cep.exception.InvalidCepException;
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
import static org.hamcrest.Matchers.not;
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
    public void retorna_endereco_de_cep_valido() throws Exception {
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

    @Test
    public void retorna_endereco_de_cep_apos_tentativas() throws Exception {
        CepInfo info = CepInfo.builder()
                .bairro("bairro")
                .cep("22330000")
                .localidade("localidade")
                .logradouro("logradouro")
                .uf("UF")
                .build();

        when(provider.find(eq("22330000"))).thenReturn(info);
        when(provider.find(argThat(not(equalTo("22330000"))))).thenReturn(CepInfo.builder().erro(true).build());

        Address address = service.findAddressByCep("22333999");

        verify(provider, times(5)).find(anyString());
        verify(validator, only()).validate(anyString());

        assertThat(address.getDistrict(), equalTo("bairro"));
        assertThat(address.getZip(), equalTo("22330000"));
        assertThat(address.getCity(), equalTo("localidade"));
        assertThat(address.getStreet(), equalTo("logradouro"));
        assertThat(address.getState(), equalTo("UF"));
    }

    @Test(expected = CepNotFoundException.class)
    public void retorna_cep_nao_encontrado() throws Exception {
        CepInfo info = CepInfo.builder()
                .erro(true)
                .build();

        when(provider.find(anyString())).thenReturn(info);

        service.findAddressByCep("22333999");

        verify(provider, times(8)).find(anyString());
        verify(validator, only()).validate(anyString());
    }

    @Test(expected = InvalidCepException.class)
    public void retorna_cep_invalido() throws Exception {
        doThrow(InvalidCepException.class).when(validator).validate(anyString());

        service.findAddressByCep("ABC");

        verify(provider, never()).find(anyString());
        verify(validator, only()).validate(anyString());
    }
}