package com.edge.app.modules.expectations;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.language.LangMessages;
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

@Controller
public class ExpectationsController {

    private static final Logger logger = LoggerFactory.getLogger(ExpectationsController.class);

    @Autowired
    private ExpectationsService expectationsService;

    @Autowired
    private LangMessages langMessages;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_EXPECTATIONS})
    public EdgeResponse<Expectations> loadExpectations() {
        Expectations expectations = expectationsService.loadExpectationsById(SpringSecurityUtil.getLoggedInInternalId());
        return EdgeResponse.createDataResponse(expectations, "");
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_SET_EXPECTATIONS})
    public EdgeResponse<Expectations> setExpectations(
            @RequestBody(required = false) Expectations expectations
    ) throws Exception {
        try {
            if (expectations == null){
                expectations = new Expectations();
            }
            expectations.setInternalId(SpringSecurityUtil.getLoggedInInternalId());
            expectations = expectationsService.setExpectations(expectations);

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
        return EdgeResponse.createDataResponse(
                expectations,
                langMessages.msg_expectationsSaved());

    }
}
