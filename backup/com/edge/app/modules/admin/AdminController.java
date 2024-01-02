package com.edge.app.modules.admin;

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.notification.NotificationService;
import com.edge.app.modules.profile.ProfileReadService;
import com.edge.app.modules.profile.ProfileUpdateService;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.app.modules.trackDetails.TrackDetails;
import com.edge.app.modules.wallet.MeetMateWalletService;
import com.edge.app.modules.wallet.ProductInfoEnum;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.common.EdgeResponse;
import com.edge.core.modules.communications.CoreCommunicationSender;
import com.edge.core.security.Encrypter;
import com.edge.core.utils.CoreDateUtils;
import com.edge.core.utils.EdgeUtils;
import com.edge.core.wallet.WalletTransaction;
import com.edge.repositories.TrackDetailsRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class AdminController {

    @Value(value = "${appDomain}")
    private String appDomain;

    @Autowired
    private AdminService adminService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private MeetMateWalletService meetMateWalletService;

    @Autowired
    private TrackDetailsRepository trackDetailsRepository;

    @Autowired
    private SecureProfileDetailsService secureProfileDetailsService;

    @Autowired
    private Encrypter encrypter;

    @Autowired
    private MessageSource messageSource;

    static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd-MMM-YYYY HH:mm");

    @Autowired
    private CoreCommunicationSender coreCommunicationSender;
    
    @Autowired
    private ProfileReadService profileReadService;
    
    @Autowired
    private ProfileUpdateService profileUpdateService;

    @ResponseBody
    @RequestMapping(value = {AppAPIConstants.URL_ADMIN_REGISTERED})
    public String getRegistered() {
        Iterable<ProfileDetails> allProfileDetails = profileReadService.getAllActiveProfileDetails();
        Iterable<TrackDetails> trackDetailsList = trackDetailsRepository.findAll();

        Map<String, TrackDetails> map = new HashMap<>();

        for (TrackDetails trackDetails : trackDetailsList) {
            if (trackDetails != null && trackDetails.getProfileId() != null) {
                map.put(trackDetails.getProfileId(), trackDetails);
            }
        }
        List<String> result = new ArrayList<>();
        int ctr = 1;
        for (ProfileDetails i : allProfileDetails) {

            String profileId = i.getProfileId();

            TrackDetails trackDetails = map.get(profileId);
            if (trackDetails == null) {
                trackDetails = new TrackDetails();
            } else {
                map.remove(profileId);
            }

            String str = "" + ctr++ + " : "
                    + i.getProfileId() + " # "
                    + "<a href='https://" + appDomain + "/#/p/" + i.getProfileId() + "'>" + i.getProfileId() + " </a> - "
                    + " Membership : " + toIST(i.getMembershipValidTill()) + " # "
                    + i.getGender() + " # "
                    + i.getFirstName() + " # "
                    + " Created : " + toIST(i.getCreatedOn()) + " # "
                    + " Phone: " + trackDetails.getCompleteNumber() + " # "
                    + " Birth: " + toIST(i.getBirthDate()) + " # "
                    + " City: " + i.getCurrentCity() + " # "
                    + " Family: " + i.getFamilyCity() + " # ";

            result.add(str);
        }

        return StringUtils.join(result, "<br><br>");
    }

    @ResponseBody
    @RequestMapping(value = {AppAPIConstants.URL_ADMIN_REQUESTED})
    public String getRequested() {
        List<TrackDetails> list = trackDetailsRepository.findByRegisteredOrderByCreatedDateDesc(false);
        List<String> result = new ArrayList<>();
        int ctr = 1;
        for (TrackDetails i : list) {
            String str = ""
                    + ctr++ + " : "
                    + i.getCompleteNumber() + " # "
                    + i.getName() + " # "
                    + i.getGender() + " # "
                    + i.getDegreeType() + " <br> "
                    + toIST(i.getCreatedDate()) + " # "
                    + i.getAnyIssues() + " # ";
            result.add(str);
        }

        return StringUtils.join(result, "<br><br>");

    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ADMIN_SEND_RANDOM_SMS})
    public EdgeResponse<List<String>> sendRandomSms(
            @RequestBody SendSmsRequest sendSmsRequest
    ) {

        try {

            String[] profileIds = null;

            StringBuilder sb = new StringBuilder();
            if (sendSmsRequest.getProfileIds().startsWith("Exp:")) {
                profileIds = profileReadService.evaluateRandomExpression(sendSmsRequest.getProfileIds().replace("Exp:", ""));
            } else {
                profileIds = sendSmsRequest.getProfileIds().split(",");
            }
            Boolean smsMode = false;

            String smsText = sendSmsRequest.getSmsText();
            if (smsText != null && smsText.startsWith("SMS:")) {
                smsMode = true;
                smsText = smsText.replaceAll("SMS:", "");
            }

            int count = 0;
            for (String profileId : profileIds) {
                if (!EdgeUtils.isEmptyString(profileId)) {
                    profileId = profileId.toUpperCase().trim();

                    String result = profileReadService.sendRandomSms(profileId, smsText, smsMode);
                    if (smsMode) {
                        if (result.equals("COUNT")) {
                            count++;
                        }
                    } else {
                        sb.append(profileId + " : " + result);
                        sb.append("; \n");
                    }

                }
            }
            if (smsMode) {
                sb.append(count + " Messages Sent.");
            }

            return EdgeResponse.createDataResponse(
                    null,
                    sb.toString());

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ADMIN_GET_PAYMENT_STATUS})
    public EdgeResponse<List<WalletTransaction>> getPaymentStatus(
            @RequestBody PaymentStatusRequest paymentStatusRequest
    ) {

        try {

            String profileId = paymentStatusRequest.getPaymentStatus();
            List<WalletTransaction> entities;
            if (profileId != null && profileId.trim().length() != 0) {
                ProfileDetails profileByProfileId = profileReadService.getProfileByProfileId(profileId);
                entities = meetMateWalletService.loadWalletTransactionsById(profileByProfileId.getInternalId());
            } else {
                entities = meetMateWalletService.loadSuccessWalletTransactions();
            }

            if (entities != null && entities.size() > 0) {
                return EdgeResponse.createDataResponse(
                        entities,
                        "");
            } else {
                return EdgeResponse.createDataResponse(
                        null,
                        "No Wallet Transactions found for given status. ");
            }

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ADMIN_DELETE_ACCOUNT})
    public EdgeResponse<String> deleteAccount(
            @RequestBody DeleteAccountRequest deleteAccountRequest) {

        try {

            String profileId = "";
            String completeNumber = deleteAccountRequest.getCompleteNumber();
            if (completeNumber.startsWith("ID:")) {
                profileId = profileUpdateService.deleteProfileByProfileId(completeNumber.replace("ID:", ""));
            } else {
                profileId = profileUpdateService.deleteAccount(completeNumber);
            }

            if (profileId != null) {
                return EdgeResponse.createDataResponse(
                        null,
                        "Account Deleted Successfully : " + profileId);
            } else {
                return EdgeResponse.createDataResponse(
                        null,
                        "No account found. ");
            }

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ADMIN_GET_PROFILE_ID})
    public EdgeResponse<String> getProfileId(
            @RequestBody AdminRequest adminRequest) {

        try {

            String profileId = adminRequest.getProfileId();
            String completeNumber = adminRequest.getCompleteNumber();
            String result = "";

            String internalId = null;
            if (profileId != null && profileId.trim().length() != 0) {
                ProfileDetails profileDetailsById = profileReadService.getProfileByProfileId(profileId.toUpperCase());
                SecureProfileDetails byIdEncoded = secureProfileDetailsService.findById(profileDetailsById.getInternalId());
                result = profileId + " -> " + byIdEncoded.getCompleteNumber() + " # " + profileDetailsById.getMembershipValidTill();
                internalId = profileDetailsById.getInternalId();
            } else {
                profileId = profileReadService.getProfileIdByCompleteNumber(completeNumber);
                ProfileDetails profileDetailsById = profileReadService.getProfileByProfileId(profileId.toUpperCase());
                result = completeNumber + " -> " + profileId + " # " + profileDetailsById.getMembershipValidTill();

                internalId = profileDetailsById.getInternalId();
            }

            if (adminRequest.getPromocode() != null && adminRequest.getPromocode().trim().length() != 0) {

                ProductInfoEnum productInfo = ProductInfoEnum.valueOf(adminRequest.getPromocode().trim().toUpperCase());
                ProfileDetails profileDetails = profileReadService.getProfileDetailsById(internalId);

                int daysToAdd = productInfo.getDays();

                Date membershipValidTill = profileDetails.getMembershipValidTill();
                if (!CoreDateUtils.isTodayOrFuture(membershipValidTill)) {
                    membershipValidTill = CoreDateUtils.today();
                }

                Date newMembership = CoreDateUtils.addDays(membershipValidTill, daysToAdd);

                profileDetails.setMembershipValidTill(newMembership);
                profileDetails.setMembershipPlan(productInfo.getMembershipPlan());
                profileDetails.setMembershipRenewDate(new Date());

                String loggedInInternalId = SpringSecurityUtil.getLoggedInInternalId();
                profileDetails.setAdminAction("Admin : " + loggedInInternalId + " Activated " + productInfo.name() +
                        " on " + new Date() +
                        "; " + profileDetails.getAdminAction());
                profileDetails.setProductInfo(productInfo.name());

                profileUpdateService.saveProfileDetails(profileDetails);

                coreCommunicationSender.sendEscalationSms(loggedInInternalId + " recharged for " + profileId + " : " + adminRequest.getPromocode());
                result = result + " # " + " Membership End Date : " + newMembership.toString();
            }

            if (result != null) {
                return EdgeResponse.createDataResponse(
                        null,
                        result);
            } else {
                return EdgeResponse.createDataResponse(
                        null,
                        "No account found. ");
            }

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_ADMIN_DELETE_PIC})
    public EdgeResponse<String> deletePic(
            @RequestBody DeletePicRequest deletePicRequest
    ) {

        try {

            String success = profileUpdateService.deletePic(deletePicRequest.getProfileId());

            if (success != null) {
                return EdgeResponse.createDataResponse(
                        null,
                        "Pic Deleted Successfully : " + success);
            } else {
                return EdgeResponse.createDataResponse(
                        null,
                        "No account found. ");
            }

        } catch (AppRuntimeException ex) {
            return EdgeResponse.createExceptionResponse(ex);
        }
    }


    /* ADMIN HANDLES */

    //    @ResponseBody
//    @PostMapping(value = {AppAPIConstants.URL_WALLET_SUCCESS_ADMIN})
//    public ResponseEntity walletSuccessWebHookAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.info("Inside walletSuccessWebHookAdmin..");
//        return walletSuccessWebHookNew(request, response);
//    }
//
//    @ResponseBody
//    @PostMapping(value = {AppAPIConstants.URL_WALLET_FAILURE_ADMIN})
//    public ResponseEntity walletFailureWebHookAdmin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        logger.info("Inside walletFailureWebHookAdmin..");
//        return walletFailureWebHookNew(request, response);
//    }
//
    private static String toIST(Date date) {
        /*ZonedDateTime zonedDateTime = ZonedDateTime
                .now(ZoneOffset.UTC) // current date/time in India
                .with(date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate())
                .withZoneSameInstant(ZoneId.of("Asia/Calcutta"));
        return zonedDateTime.format(DateTimeFormatter.ofPattern("dd-MMM-YYYY HH:mm"))*/
        if (date == null) return "";
        return dateFormatter.format(date);
    }

    public static void main(String[] args) {
        System.out.println(toIST(new Date()));
    }

}
