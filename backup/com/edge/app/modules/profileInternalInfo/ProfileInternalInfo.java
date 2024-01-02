package com.edge.app.modules.profileInternalInfo;

import com.edge.core.modules.common.EdgeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

// import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = false)
@Document(collection = "PROFILE_INTERNAL_INFO")
public class ProfileInternalInfo extends EdgeEntity {

    @Id
    @NotNull
    @Size(min = 1, max = 50)
    private String internalId;

    private String shortlistedProfiles = "'1'";

    private String removedProfiles = "'1'";

    private String readProfiles = "'1'";

    private String buyContactsProfiles = "'1'";

}
