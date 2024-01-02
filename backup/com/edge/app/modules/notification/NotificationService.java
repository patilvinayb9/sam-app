package com.edge.app.modules.notification;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profileConnection.ProfileConnection;

import java.util.List;

public interface NotificationService {

    void addNotificationForAction(ProfileConnection connection);

    void addNotificationForWithdrawal(ProfileConnection connection);

    void addNotificationForRequest(ProfileConnection connection);

    List<NotificationDetails> loadUnreadNotificationsById(String internalId);

    List<NotificationDetails> loadNotificationsById(String internalId);

    void markNotificationAsReadForId(String internalId, String notificationId);

    void markNotificationAsReadOnAction(String iInternalId, String otherInternalId, ConnectionStatusEnum connectionStatus);

    void addNotificationForContactSharing(ProfileDetails otherProfileDetails, ProfileDetails loggedProfileDetails);

}