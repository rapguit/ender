package com.nscorp.cep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by raphael on 24/03/17.
 */
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason="CEP not found.")
public class CepNotFoundException extends RuntimeException {
}
