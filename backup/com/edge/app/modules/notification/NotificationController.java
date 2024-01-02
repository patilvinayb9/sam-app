package com.edge.app.modules.notification;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.common.ConnectionStatusEnum;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class NotificationController {

    private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private LangMessages langMessages;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_NOTIFICATIONS})
    public EdgeResponse<List<NotificationDetails>> loadNotifications() {

        List<NotificationDetails> notifications = notificationService.loadNotificationsById(SpringSecurityUtil.getLoggedInInternalId());

        if (notifications == null || notifications.size() == 0) {
            return EdgeResponse.createErrorResponse(null, langMessages.msg_noNotifications(), null, null);
        } else {
            return EdgeResponse.createDataResponse(notifications, "");
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_UNREAD_NOTIFICATIONS})
    public EdgeResponse<Map<ConnectionStatusEnum, Long>> loadUnreadNotifications() {

        List<NotificationDetails> notifications = notificationService.loadUnreadNotificationsById(SpringSecurityUtil.getLoggedInInternalId());

        EdgeResponse<Map<ConnectionStatusEnum, Long>> edgeResponse;
        if (notifications == null || notifications.size() == 0) {
            edgeResponse = EdgeResponse.createDataResponse(null, null);
        } else {

            Map<ConnectionStatusEnum, Long> counted = notifications.stream().collect(
                    Collectors.groupingBy(NotificationDetails::getConnectionStatus, Collectors.counting())
            );

            counted.put(ConnectionStatusEnum.All, Long.valueOf(notifications.size()));

            edgeResponse = EdgeResponse.createDataResponse(counted, null);

        }
        return edgeResponse;
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_MARK_NOTIFICATIONS_READ})
    public EdgeResponse<String> markNotificationAsRead(@RequestBody String notificationId) {
        try {
            notificationService.markNotificationAsReadForId(SpringSecurityUtil.getLoggedInInternalId(), notificationId);

            return EdgeResponse.createDataResponse("", "");
        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }


    }

}
