package com.nscorp.cep.provider;

import com.nscorp.cep.dto.CepInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Created by raphael on 23/03/17.
 */
@Service
public class ViaCepProvider implements CepProvider {

    @Autowired
    private RestTemplate consumer;

    @Override
    public CepInfo find(String cep) {
        String url = String.format("https://viacep.com.br/ws/%s/json/", cep);
        return consumer.getForObject(url, CepInfo.class);
    }
}
