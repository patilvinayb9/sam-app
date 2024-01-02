package com.edge.app.modules.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class LangMessages {

    @Autowired
    MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, "", LocaleContextHolder.getLocale());
    }

    public String msg_one_rechargeFailure() {
        return getMessage("jlg.one_rechargeFailure");
    }

    public String msg_one_registration() {
        return getMessage("jlg.one_registration");
    }

    public String msg_one_accountDeletion() {
        return getMessage("jlg.one_accountDeletion");
    }

    public String msg_one_received(String profileId, String profileName) {
        return getMessage("jlg.one_received").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_one_withdrawn(String profileId, String profileName) {
        return getMessage("jlg.one_withdrawn").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_one_accepted(String profileId, String profileName) {
        return getMessage("jlg.one_accepted").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_one_rejected(String profileId, String profileName) {
        return getMessage("jlg.one_rejected").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_one_rechargeSuccess() {
        return getMessage("jlg.one_rechargeSuccess");
    }

    public String msg_expectationsSaved() {
        return getMessage("jlg.expectationsSaved");
    }

    public String msg_invalidConnection() {
        return getMessage("jlg.invalidConnection");
    }

    public String msg_noNotifications() {
        return getMessage("jlg.noNotifications");
    }

    public String msg_noSuchNotification() {
        return getMessage("jlg.noSuchNotification");
    }

    public String msg_imageUploaded(String imageType) {
        return getMessage("jlg.imageUploaded").replaceAll("\\{#imageType#\\}", imageType);
    }

    public String msg_documentUploaded() {
        return getMessage("jlg.documentUploaded");
    }

    public String msg_profileUpdated() {
        return getMessage("jlg.profileUpdated");
    }

    public String msg_above18Age() {
        return getMessage("jlg.above18Age");
    }

    public String msg_phoneAlreadyRegistered(String completeNumber) {
        return getMessage("jlg.phoneAlreadyRegistered").replaceAll("\\{#completeNumber#\\}", completeNumber);
    }

    public String msg_invalidVerificationCode() {
        return getMessage("jlg.invalidVerificationCode");
    }

    public String msg_errorWhileProcessing() {
        return getMessage("jlg.errorWhileProcessing");
    }

    public String msg_pleaseTryAfterSomeTime() {
        return getMessage("jlg.pleaseTryAfterSomeTime");
    }

    public String msg_invalidProfileId(String profileId) {
        return getMessage("jlg.invalidProfileId").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_errorWhileSendingSms() {
        return getMessage("jlg.errorWhileSendingSms");
    }

    public String msg_notToInactiveProfiles() {
        return getMessage("jlg.notToInactiveProfiles");
    }

    public String msg_renewMembership() {
        return getMessage("jlg.renewMembership");
    }

    public String msg_noActionByInactiveProfile() {
        return getMessage("jlg.noActionByInactiveProfile");
    }

    public String msg_invalidGenderCombination() {
        return getMessage("jlg.invalidGenderCombination");
    }

    public String msg_alreadyConnection() {
        return getMessage("jlg.alreadyConnection");
    }

    public String msg_withdrawNotAllowed() {
        return getMessage("jlg.withdrawNotAllowed");
    }

    public String msg_noSuchConnection() {
        return getMessage("jlg.noSuchConnection");
    }

    public String msg_inactiveCanNotBeAccepted() {
        return getMessage("jlg.inactiveCanNotBeAccepted");
    }

    public String msg_alreadyAccepted() {
        return getMessage("jlg.alreadyAccepted");
    }

    public String msg_uploadIdentityDocument() {
        return getMessage("jlg.uploadIdentityDocument");
    }

    public String msg_onlyAccepetedCanViewContacts() {
        return getMessage("jlg.onlyAccepetedCanViewContacts");
    }

    public String msg_viewContactAfterDays(String days) {
        return getMessage("jlg.viewContactAfterDays").replaceAll("\\{#days#\\}", days);
    }

    public String msg_invalidSecureId() {
        return getMessage("jlg.invalidSecureId");
    }

    public String msg_referralSmsSent() {
        return getMessage("jlg.referralSmsSent");
    }

    public String msg_noPaymentFound(String mobileNumber) {
        return getMessage("jlg.noPaymentFound").replaceAll("\\{#mobileNumber#\\}", mobileNumber);
    }

    public String msg_verificationCodeSent() {
        return getMessage("jlg.verificationCodeSent");
    }

    public String msg_invalidReferralCode() {
        return getMessage("jlg.invalidReferralCode");
    }

    public String msg_invalidDetailsEntered() {
        return getMessage("jlg.invalidDetailsEntered");
    }

    public String msg_invalidPhoneNumber(String phoneNumber) {
        return getMessage("jlg.invalidPhoneNumber").replaceAll("\\{#phoneNumber#\\}", phoneNumber);
    }

    public String msg_requestSuccess() {
        return getMessage("jlg.requestSuccess");
    }

    public String msg_withdrawRequestSucsess(String profileId) {
        return getMessage("jlg.withdrawRequestSucsess").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_undoRemoveFromWallSuccess(String profileId) {
        return getMessage("jlg.undoRemoveFromWallSuccess").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_unreadNotifications(String size) {
        return getMessage("jlg.unreadNotifications").replaceAll("\\{#size#\\}", size);
    }

    public String msg_removeFromWallSuccess(String profileId) {
        return getMessage("jlg.removeFromWallSuccess").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_shortlistProfileSuccess(String profileId) {
        return getMessage("jlg.shortlistProfileSuccess").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_sendConnectionRequestSuccess(String profileId) {
        return getMessage("jlg.sendConnectionRequestSuccess").replaceAll("\\{#profileId#\\}", profileId);
    }

    public String msg_dataSavedSuccessfully() {
        return getMessage("jlg.msg_dataSavedSuccessfully");
    }

    public String msg_languageChanged() {
        return getMessage("jlg.languageChanged");
    }

    public String msg_notToRestrictedProfile() {
        return getMessage("jlg.restrictedProfile");
    }

    public String msg_profileDeleted() {
        return getMessage("jlg.profileDeleted");
    }

    // SMS ::

    public String msg_sms_welcome(String profileId, String profileName) {
        return getMessage("jlg.sms_welcome").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_sms_requestReceived(String profileId, String profileName) {
        return getMessage("jlg.sms_requestReceived").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_sms_requestAccepted(String profileId, String profileName) {
        return getMessage("jlg.sms_requestAccepted").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_sms_requestRejected(String profileId, String profileName) {
        return getMessage("jlg.sms_requestRejected").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_sms_requestWithdrawn(String profileId, String profileName) {
        return getMessage("jlg.sms_requestWithdrawn").replaceAll("\\{#profileId#\\}", profileId).replaceAll("\\{#profileName#\\}", profileName);
    }

    public String msg_sms_accountDeleted() {
        return getMessage("jlg.sms_accountDeleted");
    }


    public String msg_sms_rechargeSuccess(String balance, String amount) {
        String text = getMessage("jlg.sms_rechargeSuccess").replaceAll("\\{#balance#\\}", balance);
        text = text.replaceAll("\\{#amount#\\}", amount);
        return text;
    }


    public String msg_sms_contactShared(String profileId) {
        return getMessage("jlg.sms_contactShared").replaceAll("\\{#profileId#\\}", profileId);
    }
}
