package com.nscorp.model;

import com.nscorp.cep.dto.CepInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * Created by raphael on 23/03/17.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    private static final long serialVersionUID = -2436336768868774124L;

    @Id private String id;
    @NotBlank private String zip;
    @NotBlank private String state;
    @NotBlank private String city;
    @NotBlank private String street;
    @NotBlank private String number;
    private String district;
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
