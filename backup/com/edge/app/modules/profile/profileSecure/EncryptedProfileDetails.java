package com.edge.app.modules.profile.profileSecure;

import com.edge.core.modules.common.EdgeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "PROFILE_ENCRYPTED")
public class EncryptedProfileDetails extends EdgeEntity {

    @Id
    @NotNull
    private String internalId;

    @NotNull
    @Size(min = 1, max = 200)
    private String profileId;

    @NotNull
    @Size(min = 1, max = 200)
    private String lastName;

    @NotNull
    @Size(min = 10, max = 200)
    private String cell;

    @NotNull
    @Size(min = 1, max = 200)
    private String cellCountry;

    @NotNull
    @Size(min = 12, max = 200)
    @Indexed(unique = true)
    private String completeNumber;

    @Size(min = 1, max = 200)
    private String cellOthers;

}
