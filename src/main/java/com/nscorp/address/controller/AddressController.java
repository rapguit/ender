package com.nscorp.address.controller;

import com.nscorp.address.dto.Result;
import com.nscorp.address.repository.AddressRepository;
import com.nscorp.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by raphael on 25/03/17.
 */
@RestController
@RequestMapping("ender/ws/address")
public class AddressController {

    @Autowired
    private AddressRepository repo;

    @RequestMapping
    public List<Address> findAll() {
        return repo.findAll();
    }

    @RequestMapping("{id}")
    public Address findOne(@PathVariable String id) {
        return repo.findOne(id);
    }

    @PostMapping
    public ResponseEntity save(@Valid @RequestBody Address address, Errors errors, UriComponentsBuilder uriBuilder) {
        if(errors.hasErrors()){
            return getErrorResponse(errors);
        }

        Address saved = repo.save(address);
        return ResponseEntity
                .created(uriBuilder.path("/ender/ws/address/{id}")
                        .buildAndExpand(saved.getId())
                        .toUri())
                .build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable String id) {
        repo.delete(id);
        return ResponseEntity.noContent().build();
    }

    private ResponseEntity getErrorResponse(Errors errors) {
        Result r = Result.builder().message(
                errors.getAllErrors()
                    .stream()
                    .map(x -> String.format("%s: %s", ((FieldError) x).getField(), x.getDefaultMessage()))
                    .collect(Collectors.joining(", "))
                )
                .status(400)
                .build();

        return ResponseEntity.badRequest().body(r);
    }
}
