package com.edge.core.modules.common;

import lombok.Data;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

// @MappedSuperclass
@Data
public abstract class EdgeEntity implements Serializable {

    protected static final long serialVersionUID = 1L;

    public EdgeEntity() {
        setLogData();
    }

    protected void setLogData() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = "System";
        if (authentication != null && authentication.getName() != null) {
            currentPrincipalName = authentication.getName();
        }
        Date now = new Date();

        // These are non update-able fields
        this.createdBy = currentPrincipalName;
        this.createdOn = now;

        this.updatedOn = now;
        this.updatedBy = currentPrincipalName;
        this.updatedOn = now;
    }

    @NotNull
    protected String createdBy = "DB_SYSTEM";

    @NotNull
    protected String updatedBy = "DB_SYSTEM";

    @NotNull
    @Indexed
    protected Date createdOn;

    @NotNull
    @Indexed
    protected Date updatedOn;

    public List<String> validate() {
        return null;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
        this.createdOn = new Date();
        this.updatedOn = new Date();
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
        this.updatedOn = new Date();
    }

}
