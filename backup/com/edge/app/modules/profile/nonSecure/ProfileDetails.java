package com.edge.app.modules.profile.nonSecure;

import com.edge.core.modules.common.EdgeEntity;
import com.edge.core.utils.CoreDateUtils;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "PROFILE_DETAILS")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProfileDetails extends EdgeEntity {

    @Id
    @NotNull
    @Size(min = 1, max = 50)
    private String internalId;

    @Indexed(unique = true)
    @NotNull
    @Size(min = 1, max = 50)
    private String profileId;

    @NotNull
    @Size(min = 1, max = 10)
    @Indexed
    private String gender;

    @NotBlank
    @NotNull
    private String firstName;

    @NotNull
    @Indexed
    private Date membershipValidTill = CoreDateUtils.yesterday();

    private String about;

    private String expectations;

    @NotNull
    private Integer heightComplete;

    @NotNull
    private Integer weight;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String bodyType;

    @NotNull
    @Size(min = 1, max = 50)
    private String skinColor;

    @NotNull
    @Size(min = 1, max = 50)
    private String diet;

    @NotNull
    @Size(min = 1, max = 50)
    private String smoking;

    @NotNull
    @Size(min = 1, max = 50)
    private String drinking;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String religion;

    @NotNull
    @Size(min = 1, max = 50)
    @Indexed
    private String cast;

    @NotNull
    @Size(min = 1, max = 50)
    private String motherTongue;

    @NotNull
    @Size(min = 1, max = 5)
    private String bloodGroup;

    @NotNull
    private Date birthDate;

    @NotNull
    private String birthDay;

    @NotNull
    private String birthMonth;

    @NotNull
    private String birthYear;

    @NotNull
    @Size(min = 1, max = 50)
    private String maritalStatus;

    @Size(min = 1, max = 50)
    private String manglikStatus;

    @NotNull
    @Size(min = 1, max = 50)
    private String physicalStatus;

    @NotNull
    @Size(min = 1, max = 50)
    private String degreeType;

    @Size(min = 0, max = 100)
    private String professionDetails;

    @Size(min = 0, max = 100)
    private String degreeDetails;

    @NotNull
    @Size(min = 1, max = 100)
    private String professionalType;

    @Size(min = 0, max = 3)
    private String incomeLakhs;

    @NotNull
    private BigDecimal earning;

    @NotNull
    @Size(min = 1, max = 50)
    private String currentCity;

    @NotNull
    @Size(min = 1, max = 50)
    private String currentState;

    @NotNull
    @Size(min = 1, max = 50)
    private String currentCountry;

    //@NotNull @Size(min = 1, max = 50)
    private String familyCity;

    //@NotNull @Size(min = 1, max = 50)
    private String familyState;

    //@NotNull @Size(min = 1, max = 50)
    private String familyCountry;

    @Size(min = 0, max = 30)
    private String kundaliRas;

    @Size(min = 0, max = 30)
    private String kundaliNakshatra;

    @Size(min = 0, max = 10)
    private String kundaliNadi;

    @Size(min = 0, max = 5)
    private String kundaliCharan;

    @Size(min = 0, max = 10)
    private String kundaliGan;

    @Size(min = 0, max = 30)
    private String kundaliGotr;

    @Size(min = 0, max = 30)
    private String kundaliDevak;

    @Size(min = 0, max = 200)
    private String fatherOccupation;

    @Size(min = 0, max = 200)
    private String motherOccupation;

    //@NotNull
    private Integer brothers;

    //@NotNull
    private Integer marriedBrothers;

    //@NotNull
    private Integer sisters;

    private String productInfo;

    //@NotNull
    private Integer marriedSisters;

    private int totalContacts = 0;

    private int usedContacts = 0;

    @NotNull
    @Size(min = 1, max = 200)
    private ShareContactsWithEnum shareContactsWith = ShareContactsWithEnum.ANYONE;

    @NotNull
    @Size(min = 1, max = 200)
    private AllowRequestFromEnum allowRequestFrom = AllowRequestFromEnum.ANYONE;

    @Size(min = 1, max = 200)
    private String wealth;

    @NotNull
    @Size(min = 1, max = 200)
    private String profilePic = "NA";

    @Size(min = 0, max = 200)
    private String albumImg1 = "NA";

    @Size(min = 0, max = 200)
    private String albumImg2 = "NA";

    @Size(min = 0, max = 200)
    private String albumImg3 = "NA";

    private String expectationsSet = "N";

    private String adminAction;

    private String membershipPlan;

    private Date membershipRenewDate;

    @Indexed
    private Date lastLoggedIn;


    public String getProfilePic() {
        if (profilePic != null && !profilePic.equals("NA")) {
            return profilePic;
        } else if (albumImg1 != null && !albumImg1.equals("NA")) {
            return albumImg1;
        } else if (albumImg2 != null && !albumImg2.equals("NA")) {
            return albumImg2;
        } else if (albumImg3 != null && !albumImg3.equals("NA")) {
            return albumImg3;
        } else
            return "NA";
    }


    public String getProfileThumbnail() {
        String thumbnail = getThumbnail();

        if (thumbnail == null || thumbnail.trim().length() == 0) {
            thumbnail = "NA";
        }
        return derivePath(internalId, "profilePic", thumbnail);
    }

    private String getThumbnail() {
        if (profilePic != null && !profilePic.equals("NA")) {
            return profilePic;
        } else if (albumImg1 != null && !albumImg1.equals("NA")) {
            return albumImg1;
        } else if (albumImg2 != null && !albumImg2.equals("NA")) {
            return albumImg2;
        } else if (albumImg3 != null && !albumImg3.equals("NA")) {
            return albumImg3;
        } else
            return "NA";
    }

    private void setProfileThumbnail(String str) {
        // Nothing to do here -- Added for UI
    }

    public List<ImageDetails> getProfileImages() {
        List<ImageDetails> images = new ArrayList<ImageDetails>();
        addImagesPath(images, "Profile Pic", "profilePic", profilePic);
        addImagesPath(images, "Image 1", "albumImg1", albumImg1);
        addImagesPath(images, "Image 2", "albumImg2", albumImg2);
        addImagesPath(images, "Image 3", "albumImg3", albumImg3);
        return images;
    }

    private void addImagesPath(List<ImageDetails> images, String caption, String fileType, String fileName) {
        if (fileName != null && fileName.trim().length() != 0 && !fileName.equals("NA")) {
            images.add(
                    new ImageDetails(
                            derivePath(internalId, fileType, fileName),
                            caption
                    )
            );
        }
    }

    private String derivePath(String entityId, String fileType, String fileName) {
        //return "server/secured/getImage/"+ fileType +"/" + entityId +"/" + fileName;
        return fileName;
    }

    public void deriveValues() {
        //heightEq = heightComplete * 100 + heightInch;
    }

    public void maskImages() {
        profilePic = "NA";
        albumImg1 = "NA";
        albumImg2 = "NA";
        albumImg3 = "NA";
    }


    public String getMembershipPlan() {
        if (isMembershipValid()) {
            return membershipPlan;
        }
        return "";
    }

    public boolean isMembershipValid() {
        return CoreDateUtils.isTodayOrFuture(membershipValidTill);
    }

}

class ImageDetails {
    private String image;
    private String caption;

    public ImageDetails() {
        // Nothing to do here -- Added for UI
    }

    public ImageDetails(String image, String caption) {
        this.image = image;
        this.caption = caption;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }


}

