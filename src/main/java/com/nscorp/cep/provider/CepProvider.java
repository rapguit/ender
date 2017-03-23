package com.nscorp.cep.provider;

import com.nscorp.cep.dto.CepInfo;

/**
 * Created by raphael on 23/03/17.
 */

public interface CepProvider {
   CepInfo find(String cep);
}
