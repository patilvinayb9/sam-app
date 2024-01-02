package com.edge.repositories;


import com.edge.app.modules.trackDetails.TrackDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author vinpatil
 */
public interface TrackDetailsRepository extends CrudRepository<TrackDetails, String> {

    List<TrackDetails> findByRegisteredOrderByCreatedDateDesc(boolean b);

    List<TrackDetails> findByRegisteredOrderByRegisteredDateDesc(boolean b);
}