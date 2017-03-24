package com.nscorp.cep.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by raphael on 24/03/17.
 */
@Data
@AllArgsConstructor
public class CepCriteria implements Serializable {
    private static final long serialVersionUID = 4884519743295317501L;

    private String cep;
}
