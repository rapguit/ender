package com.nscorp.cep.service;

import com.nscorp.cep.dto.CepInfo;
import com.nscorp.cep.exception.CepNotFoundException;
import com.nscorp.cep.provider.CepProvider;
import com.nscorp.cep.validator.CepValidator;
import com.nscorp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by raphael on 23/03/17.
 */

@Service
public class CepService {

    @Autowired
    private CepProvider provider;

    @Autowired
    private CepValidator validator;

    public Address findAddressByCep(String cep){
        validator.validate(cep);
        CepInfo cepInfo = tryToFind(cep, 0);
        return Address.from(cepInfo);
    }

    private CepInfo tryToFind(String cep, int retryCounter) {
        String cepTmp = transformCep(cep, retryCounter);
        CepInfo cepInfo = provider.find(cepTmp);
        if(cepInfo.isErro()) cepInfo = tryToFind(cep, retryCounter + 1);
        return cepInfo;
    }

    private String transformCep(String cep, int rindex) {
        if(rindex == cep.length()) throw new CepNotFoundException();

        StringBuilder sb = new StringBuilder();
        String partial = cep.substring(0, cep.length() - rindex);

        sb.append(partial);
        if(rindex > 0){
            for (int i = 0; i < rindex; i++) {
                sb.append(0);
            }
        }

        return sb.toString();
    }
}
