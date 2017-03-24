package com.nscorp.cep.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * Created by raphael on 23/03/17.
 */
@Data
@Builder
@AllArgsConstructor
public class CepInfo implements Serializable {

    private static final long serialVersionUID = 7141863836156686817L;

    private String cep;
    private String logradouro;
    private String complemento;
    private String bairro;
    private String localidade;
    private String uf;
    private String unidade;
    private String ibge;
    private String gia;

    private boolean erro;

}
