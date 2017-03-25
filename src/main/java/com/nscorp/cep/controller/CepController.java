package com.nscorp.cep.controller;

import com.nscorp.cep.dto.CepCriteria;
import com.nscorp.cep.service.CepService;
import com.nscorp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by raphael on 24/03/17.
 */
@RestController
@RequestMapping("ender/ws/cep-address")
public class CepController {

    @Autowired
    private CepService service;

    @RequestMapping
    public Address findCep(@RequestBody CepCriteria criteria){
        return service.findAddressByCep(criteria.getCep());
    }
}
