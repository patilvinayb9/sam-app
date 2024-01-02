package com.edge.app.modules.wall;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.expectations.Expectations;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class WallController {

    private static final Logger logger = LoggerFactory.getLogger(WallController.class);

    @Autowired
    private WallService wallService;

    @Autowired
    private LangMessages langMessages;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_NEW_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> loadNewProfiles() {
        List<ProfileDetailsDto> searchedProfiles = wallService.loadNewProfiles();

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            return EdgeResponse.createDataResponse(null, "");
        } else {
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_WALL_PROFILES})
    public EdgeResponse<List<ProfileDetailsDto>> loadWallProfiles() {
        List<ProfileDetailsDto> searchedProfiles = wallService.loadWallProfilesById(SpringSecurityUtil.getLoggedInInternalId(), null);

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            //return EdgeResponse.createErrorResponse(null,"No Profile Found As Per Filter!", null, null);
            return EdgeResponse.createDataResponse(null, "");
        } else {
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ONE_TIME_SEARCH})
    public EdgeResponse<List<ProfileDetailsDto>> oneTimeSearch(
            @RequestBody(required = false) Expectations expectations
    ) throws Exception {
        try {
            if (expectations == null) expectations = new Expectations();
            List<ProfileDetailsDto> searchedProfiles = wallService.oneTimeSearchById(expectations, SpringSecurityUtil.getLoggedInInternalId());
            if (searchedProfiles == null || searchedProfiles.size() == 0) {
                return EdgeResponse.createDataResponse(null, "");
            } else {
                return EdgeResponse.createDataResponse(searchedProfiles, "");
            }
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_GUEST_SEARCH})
    public EdgeResponse<List<ProfileDetailsDto>> guestSearch(
            @RequestBody(required = false) Expectations expectations
    ) throws Exception {
        try {
            if (expectations == null) expectations = new Expectations();
            List<ProfileDetailsDto> searchedProfiles = wallService.guestSearch(expectations);
            if (searchedProfiles == null || searchedProfiles.size() == 0) {
                return EdgeResponse.createDataResponse(null, "");
            } else {
                return EdgeResponse.createDataResponse(searchedProfiles, "");
            }
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_BRIDES})
    public EdgeResponse<List<ProfileDetailsDto>> loadBrides(
            @RequestBody String searchType) throws ParseException {
        List<ProfileDetailsDto> searchedProfiles = wallService.loadBrides(searchType);

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            return EdgeResponse.createDataResponse(null, "");
        } else {
            String internalId = SpringSecurityUtil.getLoggedInInternalId();
            if (internalId == null) mask(searchedProfiles);
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }

    private void mask(List<ProfileDetailsDto> searchedProfiles) {
        searchedProfiles.stream().forEach(p -> p.mask());
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_GROOMS})
    public EdgeResponse<List<ProfileDetailsDto>> loadGrooms(
            @RequestBody String searchType) throws ParseException {
        List<ProfileDetailsDto> searchedProfiles = wallService.loadGrooms(searchType);

        if (searchedProfiles == null || searchedProfiles.size() == 0) {
            return EdgeResponse.createDataResponse(null, "");
        } else {
            String internalId = SpringSecurityUtil.getLoggedInInternalId();
            if (internalId == null) mask(searchedProfiles);
            return EdgeResponse.createDataResponse(searchedProfiles, "");
        }
    }

}
