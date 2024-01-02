package com.edge.app.modules.profileConnection;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.core.modules.common.EdgeEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

// import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "PROFILE_CONNECTION")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileConnection extends EdgeEntity {

    @Id
    @NotNull
    @Size(min = 1, max = 200)
    private String connectionId;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String internalFrom;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String internalTo;


    @NotNull
    @Size(min = 1, max = 50)
    private String profileFrom;

    @NotNull
    @Size(min = 1, max = 50)
    private String profileTo;


    @NotNull
    @Size(min = 1, max = 50)
    private String firstNameFrom;

    @NotNull
    @Size(min = 1, max = 50)
    private String firstNameTo;

    //@NotNull
    //private BigDecimal amount;

    @NotNull
    @Indexed
    private Date requestedOn;

    @Indexed
    private Date actionedOn;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String connectionStatus = ConnectionStatusEnum.Requested.name();

    public String getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(String connectionId) {
        this.connectionId = connectionId;
    }

    public Date getRequestedOn() {
        return requestedOn;
    }

    public void setRequestedOn(Date requestedOn) {
        this.requestedOn = requestedOn;
    }

    public ConnectionStatusEnum getConnectionStatus() {
        return ConnectionStatusEnum.valueOf(connectionStatus);
    }

    public void setConnectionStatus(ConnectionStatusEnum connectionStatus) {
        this.connectionStatus = connectionStatus.name();
    }

    public String getInternalFrom() {
        return internalFrom;
    }

    public void setInternalFrom(String internalFrom) {
        this.internalFrom = internalFrom;
    }

    public String getInternalTo() {
        return internalTo;
    }

    public void setInternalTo(String internalTo) {
        this.internalTo = internalTo;
    }

    public Date getActionedOn() {
        return actionedOn;
    }

    public void setActionedOn(Date actionedOn) {
        this.actionedOn = actionedOn;
    }

	/*public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}*/

    public String getProfileFrom() {
        return profileFrom;
    }

    public void setProfileFrom(String profileFrom) {
        this.profileFrom = profileFrom;
    }

    public String getProfileTo() {
        return profileTo;
    }

    public void setProfileTo(String profileTo) {
        this.profileTo = profileTo;
    }

    public void setConnectionStatus(String connectionStatus) {
        this.connectionStatus = connectionStatus;
    }

    public String getFirstNameFrom() {
        return firstNameFrom;
    }

    public void setFirstNameFrom(String firstNameFrom) {
        this.firstNameFrom = firstNameFrom;
    }

    public String getFirstNameTo() {
        return firstNameTo;
    }

    public void setFirstNameTo(String firstNameTo) {
        this.firstNameTo = firstNameTo;
    }
}
