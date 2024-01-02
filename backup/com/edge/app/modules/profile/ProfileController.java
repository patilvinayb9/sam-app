package com.edge.app.modules.profile;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.common.EdgeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ProfileController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    @Autowired
    private LangMessages langMessages;

    @Autowired
    private ProfileReadService profileReadService;
    
    @Autowired
    private ProfileUpdateService profileUpdateService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(value = AppAPIConstants.URL_UPLOAD_IMAGE, headers = ("content-type=multipart/form-data"))
    public EdgeResponse<String> uploadImage(
            MultipartHttpServletRequest request) throws Exception {

        try {
            MultipartFile file = request.getFile("file");
            String imageType = (String) request.getParameter("imageType");
            String internalId = SpringSecurityUtil.getLoggedInInternalId();
            profileUpdateService.uploadImage(file, imageType, internalId);

            return EdgeResponse.createDataResponse("", langMessages.msg_imageUploaded(""));
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @GetMapping(value = AppAPIConstants.URL_GET_IMAGE)
    public FileSystemResource getImage(
            @PathVariable("entityId") String entityId,
            @PathVariable("fileType") String fileType,
            @PathVariable("fileName") String fileName) {
        return profileReadService.getImage(entityId, fileType, fileName);
    }

    @ResponseBody
    @GetMapping(value = AppAPIConstants.URL_GET_THUMBNAIL)
    public FileSystemResource getThumbnail(
            @PathVariable("entityId") String entityId) {
        String fileType = "profilePic";
        String fileName = (entityId + fileType) + ".jpg";
        return profileReadService.getImage(entityId, fileType, fileName);
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_GET_LOGGED_IN_PROFILE})
    public EdgeResponse<ProfileDetailsDto> getLoggedInProfile() {
        if (SpringSecurityUtil.getLoggedInInternalId() != null) {
            String internalId = SpringSecurityUtil.getLoggedInInternalId();
            ProfileDetailsDto profileDetailsDto = profileReadService.getProfileDetails(internalId);
            return EdgeResponse.createDataResponse(profileDetailsDto, null);
        } else {
            return EdgeResponse.createDataResponse(null, null);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_UPDATE_MY_PROFILE})
    public EdgeResponse<String> updateMyProfile(@RequestBody ProfileDetailsDto profileDetailsDto) throws Exception {
        try {
            profileUpdateService.updateMyProfile(profileDetailsDto);
            return EdgeResponse.createDataResponse("", langMessages.msg_profileUpdated());
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }

    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_DELETE_MY_PROFILE})
    public EdgeResponse<String> deleteMyProfile() {
        try {
            profileUpdateService.deleteProfileById(SpringSecurityUtil.getLoggedInInternalId());
            return EdgeResponse.createDataResponse("", langMessages.msg_profileDeleted());
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }

    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_SEARCH_BY_ID})
    public EdgeResponse<List<ProfileDetailsDto>> searchById(
            @RequestBody String profileId
    ) {
        List<ProfileDetailsDto> profileDetailsDtoList = profileReadService.searchDtoById(profileId);
        return EdgeResponse.createDataResponse(profileDetailsDtoList, "");
    }

}
