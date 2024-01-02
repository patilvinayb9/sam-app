package com.edge.app.modules.profileInternalInfo;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.common.RequestModel;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
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
public class ProfileInternalInfoController {


    private static final Logger logger = LoggerFactory.getLogger(ProfileInternalInfoController.class);

    @Autowired
    private ProfileInternalInfoService profileInternalInfoService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_SHORTLIST_PROFILE})
    public EdgeResponse<String> shortlistProfile(  @RequestBody RequestModel requestModel) {
        try {
            profileInternalInfoService.shortlistProfileById(SpringSecurityUtil.getLoggedInInternalId(), requestModel.getInternalId());
            return EdgeResponse.createDataResponse("", ""); //ttttttlangMessages.msg_shortlistProfileSuccess(requestModel.getProfileId()));
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_SHORTLISTED_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> loadShortlistedProfiles() {
        List<ProfileDetailsDto> searchedProfiles = profileInternalInfoService.loadShortlistedProfilesById(SpringSecurityUtil.getLoggedInInternalId());

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            //return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Filter!", null, null);
            return EdgeResponse.createDataResponse(null, "");
        } else {
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_BUY_CONTACTS_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> loadBuyContactsProfiles() {
        List<ProfileDetailsDto> searchedProfiles = profileInternalInfoService.loadBuyContactsProfilesById(SpringSecurityUtil.getLoggedInInternalId());

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            return EdgeResponse.createDataResponse(null, "");
        } else {
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_REMOVED_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> loadRemovedProfiles() {
        List<ProfileDetailsDto> searchedProfiles = profileInternalInfoService.loadRemovedProfilesById(SpringSecurityUtil.getLoggedInInternalId());

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            //return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Filter!", null, null);
            return EdgeResponse.createDataResponse(null, "");
        } else {
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_UNDO_REMOVE_FROM_WALL})
    public EdgeResponse<String> undoRemoveFromWall(@RequestBody RequestModel requestModel) {
        try {
            profileInternalInfoService.undoRemoveFromWall(SpringSecurityUtil.getLoggedInInternalId(), requestModel.getInternalId());
            return EdgeResponse.createDataResponse("", "");
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_REMOVE_FROM_WALL})
    public EdgeResponse<String> removeFromWall(@RequestBody RequestModel requestModel) {
        try {
            profileInternalInfoService.removeFromWallById(SpringSecurityUtil.getLoggedInInternalId(), requestModel.getInternalId());
            return EdgeResponse.createDataResponse("", ""); //ttttttlangMessages.msg_removeFromWallSuccess(requestModel.getProfileId()));
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }




}
