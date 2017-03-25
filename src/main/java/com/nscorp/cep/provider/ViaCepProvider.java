package com.nscorp.cep.provider;

import com.nscorp.cep.dto.CepInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

/**
 * Created by raphael on 23/03/17.
 */
@Service
public class ViaCepProvider implements CepProvider {

    @Autowired
    private RestTemplate consumer;

    @Override
    public CepInfo find(String cep) {
        URI uri = URI.create(String.format("https://viacep.com.br/ws/%s/json/", cep));
        return consumer.getForObject(uri, CepInfo.class);
    }
}
