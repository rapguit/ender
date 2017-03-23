package com.nscorp.model;

import com.nscorp.cep.dto.CepInfo;
import lombok.Builder;
import lombok.Data;

/**
 * Created by raphael on 23/03/17.
 */
@Data
@Builder
public class Address {
    private String id;
    private String zip;
    private String state;
    private String city;
    private String district;
    private String street;
    private String complement;

    public static Address from(CepInfo cepInfo) {
        return Address.builder()
                .city(cepInfo.getLocalidade())
                .state(cepInfo.getUf())
                .street(cepInfo.getLogradouro())
                .district(cepInfo.getBairro())
                .zip(cepInfo.getCep())
                .build();
    }
}
