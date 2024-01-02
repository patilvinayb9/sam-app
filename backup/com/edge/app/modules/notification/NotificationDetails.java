package com.edge.app.modules.notification;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.core.modules.common.EdgeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "NOTIFICATION_DETAILS")
public class NotificationDetails extends EdgeEntity {

    @Id
    private String notificationId;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String internalId;

    @Size(min = 1, max = 50)
    private String internalOther;

    @Size(min = 1, max = 50)
    private String profileId;

    @Size(min = 1, max = 50)
    private String firstName;

    @Size(min = 1, max = 50)
    private String firstNameOther;

    @Size(min = 1, max = 50)
    private String profileOther;

    @NotNull
    @Size(min = 1, max = 50)
    private String category;

    @NotNull
    @Size(min = 1, max = 500)
    private String notificationText;

    @NotNull
    @Size(min = 1, max = 50)
    private String notificationStatus = NotificationStatusEnum.Unread.name();

    @Size(min = 1, max = 50)
    private String connectionStatus = "";

    @NotNull
    private Date raisedOn = new Date();

    @Size(min = 1, max = 200)
    @Transient
    private String thumbnail;

    private Date actionedOn;

    public void deriveNotificationId() {
        this.notificationId = this.internalId + "_" + this.internalOther;
    }

    public ConnectionStatusEnum getConnectionStatus() {
        return ConnectionStatusEnum.valueOf(connectionStatus);
    }

    public void setConnectionStatus(ConnectionStatusEnum connectionStatus) {
        this.connectionStatus = connectionStatus.name();
    }

    public NotificationStatusEnum getNotificationStatus() {
        return NotificationStatusEnum.valueOf(notificationStatus);
    }

    public void setNotificationStatus(NotificationStatusEnum notificationStatus) {
        this.notificationStatus = notificationStatus.name();
    }

}
