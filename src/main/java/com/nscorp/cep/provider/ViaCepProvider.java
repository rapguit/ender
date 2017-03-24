package com.nscorp.cep.provider;

import com.nscorp.cep.dto.CepInfo;
import org.springframework.stereotype.Service;

/**
 * Created by raphael on 23/03/17.
 */
@Service
public class ViaCepProvider implements CepProvider {
    @Override
    public CepInfo find(String cep) {
        return CepInfo.builder().build();
    }
}
