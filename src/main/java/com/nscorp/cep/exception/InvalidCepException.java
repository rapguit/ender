package com.nscorp.cep.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by raphael on 23/03/17.
 */
@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason="Invalid CEP.")
public class InvalidCepException extends RuntimeException {
}
