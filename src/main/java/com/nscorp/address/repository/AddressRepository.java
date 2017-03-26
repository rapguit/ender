package com.nscorp.address.repository;

import com.nscorp.model.Address;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by raphael on 25/03/17.
 */
@Repository
public interface AddressRepository extends MongoRepository<Address, String>{
}
