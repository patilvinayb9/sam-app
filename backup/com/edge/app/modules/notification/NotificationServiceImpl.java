package com.edge.app.modules.notification;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.edge.app.modules.profileConnection.ProfileConnectionUtil;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.helper.DbHelper;
import com.edge.repositories.NotificationDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationServiceImpl.class);

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("mongoDbHelper")
    private DbHelper dbHelper;

    @Autowired
    private LangMessages langMessages;

    @Autowired
    private NotificationDetailsRepository notificationRepo;

    @Autowired
    private ProfileConnectionUtil profileConnectionUtil;

    @Override
    @Transactional
    public void addNotificationForAction(ProfileConnection connection) {
        NotificationDetails notification = new NotificationDetails();
        notification.setConnectionStatus(connection.getConnectionStatus());
        notification.setCategory(connection.getConnectionStatus().getCategory());
        notification.setNotificationStatus(NotificationStatusEnum.Unread);
        notification.setInternalId(connection.getInternalFrom());
        notification.setInternalOther(connection.getInternalTo());

        notification.setProfileId(connection.getProfileFrom());
        notification.setProfileOther(connection.getProfileTo());

        notification.setFirstName(connection.getFirstNameFrom());
        notification.setFirstNameOther(connection.getFirstNameTo());

        notification.setNotificationText(profileConnectionUtil.getText(connection));
        notification.deriveNotificationId();

        notificationRepo.save(notification);
    }

    @Override
    @Transactional
    public void addNotificationForWithdrawal(ProfileConnection connection) {
        NotificationDetails notification = new NotificationDetails();
        notification.setConnectionStatus(connection.getConnectionStatus());
        notification.setCategory(connection.getConnectionStatus().getCategory());
        notification.setNotificationStatus(NotificationStatusEnum.Unread);
        notification.setInternalId(connection.getInternalTo());
        notification.setInternalOther(connection.getInternalFrom());

        notification.setProfileId(connection.getProfileTo());
        notification.setProfileOther(connection.getProfileFrom());

        notification.setFirstName(connection.getFirstNameTo());
        notification.setFirstNameOther(connection.getFirstNameFrom());

        notification.setNotificationText(profileConnectionUtil.getText(connection));
        notification.deriveNotificationId();

        notificationRepo.save(notification);

		/*Notification notification2 = new Notification();
		notification2.setConnectionStatus(connection.getConnectionStatus());
		notification2.setCategory(connection.getConnectionStatus().getCategory());
		notification2.setNotificationStatus(NotificationStatusEnum.Unread);
		notification2.setInternalId(connection.getInternalFrom());
		notification2.setInternalOther(connection.getInternalTo());
		notification2.setNotificationText(connection.getText());

		notificationRepo.save(notification2);*/
    }

    @Override
    @Transactional
    public void addNotificationForRequest(ProfileConnection connection) {
        NotificationDetails notification = new NotificationDetails();
        notification.setConnectionStatus(connection.getConnectionStatus());
        notification.setCategory(connection.getConnectionStatus().getCategory());
        notification.setNotificationStatus(NotificationStatusEnum.Unread);
        notification.setInternalId(connection.getInternalTo());
        notification.setInternalOther(connection.getInternalFrom());

        notification.setProfileId(connection.getProfileTo());
        notification.setProfileOther(connection.getProfileFrom());

        notification.setFirstName(connection.getFirstNameTo());
        notification.setFirstNameOther(connection.getFirstNameFrom());

        notification.setNotificationText(profileConnectionUtil.getText(connection));
        notification.deriveNotificationId();

        notificationRepo.save(notification);
    }

    @Override
    public List<NotificationDetails> loadUnreadNotificationsById(String internalId) {
        return notificationRepo.findByInternalIdAndNotificationStatus(internalId, NotificationStatusEnum.Unread.name());
    }

    @Override
    public List<NotificationDetails> loadNotificationsById(String internalId) {

		/*HibernateTemplate hibernateTemplate = mongoTemplate.getHibernateTemplate();
		hibernateTemplate.setMaxResults(AppConstants.MAX_NOTIFICATIONS_SIZE);*/

        List<NotificationDetails> notifications = notificationRepo.findTop50ByInternalIdOrderByNotificationStatusDescRaisedOnDesc(internalId);

        Set<String> profileIds = notifications.stream().map(NotificationDetails::getInternalOther).collect(Collectors.toSet());
        Query queryProfile = new BasicQuery(
                "{ internalId: { $in: [ " + dbHelper.addINClause(profileIds, null) + " ] } }");
        List<ProfileDetails> profileList = mongoTemplate.find(queryProfile, ProfileDetails.class);

        Map<String, String> thumbnailMap = profileList.stream().collect(Collectors.toMap(ProfileDetails::getInternalId, ProfileDetails::getProfilePic));

        notifications.stream().forEach(e -> e.setThumbnail(thumbnailMap.get(e.getInternalOther())));

        return notifications;
        // hibernateTemplate.find(" from Notification where internalId = '" + internalId + "' order by notificationStatus desc, raisedOn desc ");

    }

    @Override
    @Transactional
    public void markNotificationAsReadForId(String internalId, String notificationId) {

        Optional<NotificationDetails> notificationOptional = notificationRepo.findById(notificationId);

        if (notificationOptional.isPresent()) {
            NotificationDetails notification = notificationOptional.get();
            if (notification.getInternalId().equals(internalId) && notification.getNotificationStatus() != NotificationStatusEnum.Read) {
                notification.setActionedOn(new Date());
                notification.setNotificationStatus(NotificationStatusEnum.Read);
                notification.deriveNotificationId();

                notificationRepo.save(notification);
            } else {
                throw new AppRuntimeException(null, langMessages.msg_noSuchNotification());
            }
        } else {
            throw new AppRuntimeException(null, langMessages.msg_noSuchNotification());
        }
    }

    @Override
    @Transactional
    public void markNotificationAsReadOnAction(String iInternalId, String otherInternalId, ConnectionStatusEnum connectionStatus) {
        Optional<NotificationDetails> byInternalIdAndInternalOther = notificationRepo.findById(iInternalId + "_" + otherInternalId);
        if (byInternalIdAndInternalOther.isPresent()) {
            NotificationDetails notification = byInternalIdAndInternalOther.get();
            notification.setConnectionStatus(connectionStatus);
            notification.setActionedOn(new Date());
            notification.setNotificationStatus(NotificationStatusEnum.Read);
            notification.deriveNotificationId();

            notificationRepo.save(notification);
        }
    }

    @Override
    public void addNotificationForContactSharing(ProfileDetails profileDetails, ProfileDetails loggedProfileDetails) {
        NotificationDetails notification = new NotificationDetails();
        notification.setConnectionStatus(ConnectionStatusEnum.ContactViewed);
        notification.setCategory(ConnectionStatusEnum.ContactViewed.getCategory());
        notification.setNotificationStatus(NotificationStatusEnum.Unread);
        notification.setInternalId(profileDetails.getInternalId());
        notification.setInternalOther(loggedProfileDetails.getInternalId());

        notification.setProfileId(profileDetails.getProfileId());
        notification.setProfileOther(loggedProfileDetails.getProfileId());

        notification.setFirstName(profileDetails.getFirstName());
        notification.setFirstNameOther(loggedProfileDetails.getFirstName());

        notification.setNotificationText(loggedProfileDetails.getProfileId() + " just requested for your contact details.");
        notification.deriveNotificationId();

        notificationRepo.save(notification);

    }

}
