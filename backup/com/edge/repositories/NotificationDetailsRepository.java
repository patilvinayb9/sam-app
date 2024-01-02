package com.edge.repositories;


import com.edge.app.modules.notification.NotificationDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author vinpatil
 */
public interface NotificationDetailsRepository extends CrudRepository<NotificationDetails, String> {

    List<NotificationDetails> findByInternalIdAndNotificationStatus(String internalId, String notificationStatus);

    List<NotificationDetails> findTop50ByInternalIdOrderByNotificationStatusDescRaisedOnDesc(String internalId);

}