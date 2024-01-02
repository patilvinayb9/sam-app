package com.edge.app.modules.profileConnection;

import com.edge.app.modules.common.ConnectionStatusEnum;
import lombok.Data;

@Data
public class ConnectionAction {

    private String internalId;

    private String profileId;

    private String connectionStatus;

    public ConnectionStatusEnum getConnectionStatus() {
        return ConnectionStatusEnum.valueOf(connectionStatus);
    }

    public void setConnectionStatus(ConnectionStatusEnum connectionStatus) {
        this.connectionStatus = connectionStatus.name();
    }

}
