package com.edge.app.modules.profileConnection;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.common.RequestModel;
import com.edge.app.modules.profile.ProfileReadService;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.common.EdgeResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class ProfileConnectionController {

    private static final Logger logger = LoggerFactory.getLogger(ProfileConnectionController.class);

    @Autowired
    private ProfileConnectionService profileConnectionService;

    @Autowired
    private ProfileReadService profileReadService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_WITHDRAW_REQUEST})
    public EdgeResponse<String> withdrawRequest
            (@RequestBody RequestModel requestModel) {
        try {
            profileConnectionService.withdrawRequestById(SpringSecurityUtil.getLoggedInInternalId(), requestModel.getInternalId());
            return EdgeResponse.createDataResponse("", "");
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ACTION_REQUEST})
    public EdgeResponse<String> actionRequest(@RequestBody ConnectionAction connectionAction) {
        try {
            ProfileConnection profileConnection = profileConnectionService.actionRequestById(SpringSecurityUtil.getLoggedInInternalId(), connectionAction);
            return EdgeResponse.createDataResponse("", ""); //ttttttlangMessages.msg_requestSuccess());
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_GET_SECURE_DETAILS})
    public EdgeResponse<ProfileDetailsDto> getSecureDetails(@RequestBody String internalId) {
        try {
            ProfileDetails profileDetails = profileReadService.getProfileDetailsById(internalId);
            SecureProfileDetails secureDetails = profileConnectionService.getSecureDetailsById(SpringSecurityUtil.getLoggedInInternalId(), profileDetails.getInternalId());

            ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
            profileDetailsDto.setProfileDetails(profileDetails);
            profileDetailsDto.setSecureDetails(secureDetails);

            return EdgeResponse.createDataResponse(profileDetailsDto, "");
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_BUY_SECURE_DETAILS})
    public EdgeResponse<ProfileDetailsDto> buySecureDetails(@RequestBody String internalId) {
        try {
            ProfileDetails profileDetails = profileReadService.getProfileDetailsById(internalId);
            SecureProfileDetails secureDetails = profileConnectionService.buySecureDetailsById(SpringSecurityUtil.getLoggedInInternalId(), profileDetails);

            ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
            profileDetailsDto.setProfileDetails(profileDetails);
            profileDetailsDto.setSecureDetails(secureDetails);

            return EdgeResponse.createDataResponse(profileDetailsDto, "");
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_SEARCH_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> searchProfiles(@RequestBody String searchType) {
        try {
            List<ProfileDetailsDto> searchedProfiles = profileConnectionService.searchProfilesById(SpringSecurityUtil.getLoggedInInternalId(), searchType);

            if (searchedProfiles == null || searchedProfiles.size() == 0) {
                //return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Filter!", null, null);
                return EdgeResponse.createDataResponse(null, "");
            } else {
                return EdgeResponse.createDataResponse(searchedProfiles, "");
            }

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_SEND_CONNECTION_REQUEST})
    public EdgeResponse<String> sendConnectionRequest(@RequestBody String internalId) {
        try {
            ProfileDetails profileDetails = profileConnectionService.sendConnectionRequestById(SpringSecurityUtil.getLoggedInInternalId(), internalId);
            return EdgeResponse.createDataResponse("", ""); //ttttttlangMessages.msg_sendConnectionRequestSuccess(profileDetails.getProfileId()));

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

}
