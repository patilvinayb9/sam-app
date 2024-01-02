package com.edge.app.modules.trackDetails;

import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.core.modules.common.EdgeEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "TRACK_DETAILS")
public class TrackDetails extends EdgeEntity {

    @Id
    @NotNull
    @Size(min = 1, max = 50)
    private String completeNumber;

    @NotNull
    private boolean registered = false;

    @NotNull
    private Date createdDate = new Date();

    private Date registeredDate = null;

    private String profileId;
    private String name;
    private String gender;
    private String degreeType;

    private String marketingRef;

    private String marketingOther;
    private String anyIssues;

    private String profileDetailsDto;

    public void setProfileDetailsDtoAsString(ProfileDetailsDto profileDetailsDto) {
        if (profileDetailsDto != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                this.profileDetailsDto = mapper.writeValueAsString(profileDetailsDto);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

    }

    public ProfileDetailsDto getProfileDetailsDtoAsPojo() {
        if (profileDetailsDto != null) {
            try {
                ObjectMapper mapper = new ObjectMapper();
                return mapper.readValue(profileDetailsDto, ProfileDetailsDto.class);
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }

        return null;
    }

}

