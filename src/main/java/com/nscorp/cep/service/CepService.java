package com.nscorp.cep.service;

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
        return Address.from(provider.find(cep));
    }
}
