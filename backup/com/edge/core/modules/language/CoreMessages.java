package com.edge.core.modules.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class CoreMessages {

    @Autowired
    MessageSource messageSource;

    private String getMessage(String key) {
        return messageSource.getMessage(key, null, "", LocaleContextHolder.getLocale());
    }

    public String msg_above18Age() {
        return getMessage("jlg.above18Age");
    }

    public String msg_phoneAlreadyRegistered(String completeNumber) {
        return getMessage("jlg.phoneAlreadyRegistered").replaceAll("\\{#completeNumber#\\}", completeNumber);
    }

    public String msg_fillTheForm() {
        return getMessage("jlg.fillTheForm");
    }

    public String msg_invalidPhoneNumber(String phoneNumber) {
        return getMessage("jlg.invalidPhoneNumber").replaceAll("\\{#phoneNumber#\\}", phoneNumber);
    }

    public String msg_pwdNotEmpty() {
        return getMessage("jlg.pwdNotEmpty");
    }

    public String msg_selectGender() {
        return getMessage("jlg.selectGender");
    }

    public String msg_pwdConfPwdNoMatch() {
        return getMessage("jlg.pwdConfPwdNoMatch");
    }

    public String msg_errorWhileProcessing() {
        return getMessage("jlg.errorWhileProcessing");
    }

    public String msg_invalidVerificationCode() {
        return getMessage("jlg.invalidVerificationCode");
    }

    public String msg_walletNotInitialized() {
        return getMessage("jlg.walletNotInitialized");
    }

    public String msg_verificationCodeSent() {
        return getMessage("jlg.verificationCodeSent");
    }

    public String msg_pwdUpdated() {
        return getMessage("jlg.pwdUpdated");
    }

    // SMS
    public String msg_sms_verificationCode(String verificationCode) {
        return getMessage("jlg.sms_verificationCode").replaceAll("\\{#verificationCode#\\}", verificationCode);
    }

}
