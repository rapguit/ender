package com.nscorp.cep.validator;

import com.nscorp.cep.exception.InvalidCepException;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.isNumeric;

/**
 * Created by raphael on 23/03/17.
 */
@Service
public class CepValidator {

    public boolean isValid(String cep) {
        return isNotBlank(cep) &&
                isNumeric(cep) &&
                cep.trim().length() == 8;
    }

    public void validate(String cep) {
        if (!isValid(cep))
            throw new InvalidCepException();
    }
}
