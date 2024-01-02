package com.edge.repositories;


import com.edge.core.modules.security.EdgeUserDetails;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vinpatil
 */
public interface EdgeUserDetailsRepository extends CrudRepository<EdgeUserDetails, String> {

    EdgeUserDetails findByCompleteNumber(String completeNumber);

    EdgeUserDetails findByInternalId(String internalId);

}