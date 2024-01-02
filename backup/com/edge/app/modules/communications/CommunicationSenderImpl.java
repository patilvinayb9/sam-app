package com.edge.app.modules.communications;

import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.communications.CoreCommunicationSender;
import com.edge.core.modules.communications.EventDetails;
import com.edge.core.wallet.Wallet;
import com.edge.core.wallet.WalletTransaction;
import com.edge.repositories.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
public class CommunicationSenderImpl implements CommunicationSender {

    @Value(value = "${appDomain}")
    private String appDomain;

    private static final Logger logger = LoggerFactory.getLogger(CommunicationSenderImpl.class);

    @Autowired
    private LangSmsTemplates langSmsTemplates;

    @Autowired
    private CoreCommunicationSender coreCommunicationSender;

    @Autowired
    SecureProfileDetailsService secureProfileDetailsService;

    @Autowired
    private LangMessages langMessages;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Override
    public void sendAlertForConnection(ProfileConnection connection, String iProfileId) {

        try {

            String internalFrom = connection.getInternalFrom();
            ProfileDetails profileFrom = getProfileDetailsById(internalFrom);
            SecureProfileDetails secureFrom = secureProfileDetailsService.findById(internalFrom);

            String internalTo = connection.getInternalTo();
            ProfileDetails profileTo = getProfileDetailsById(internalTo);
            SecureProfileDetails secureTo = secureProfileDetailsService.findById(internalTo);

            ConnectionStatusEnum connectionStatus = connection.getConnectionStatus();

            String identifier;
            EventDetails eventDetails;
            switch (connectionStatus) {
                case ConnectionStatusEnum.Requested:
                    identifier = secureFrom.getProfileId();
                    coreCommunicationSender.sendSms(langSmsTemplates.requestReceived(profileFrom.getProfileId(), profileFrom.getFirstName()), secureTo.getCompleteNumber());
                    break;

                case ConnectionStatusEnum.Accepted:
                    identifier = secureTo.getProfileId();
                    coreCommunicationSender.sendSms(langSmsTemplates.requestAccepted(profileTo.getProfileId(), profileTo.getFirstName()), secureFrom.getCompleteNumber());
                    break;

                case ConnectionStatusEnum.Rejected:
                    identifier = secureTo.getProfileId();
                    coreCommunicationSender.sendSms(langSmsTemplates.requestRejected(profileTo.getProfileId(), profileTo.getFirstName()), secureFrom.getCompleteNumber());
                    break;

                case ConnectionStatusEnum.Withdrawn:
                    identifier = secureFrom.getProfileId();
                    coreCommunicationSender.sendSms(langSmsTemplates.requestWithdrawn(profileFrom.getProfileId(), profileFrom.getFirstName()), secureTo.getCompleteNumber());
                    break;

                default:
                    throw new AppRuntimeException(null, langMessages.msg_invalidConnection());
            }
        } catch (Exception e) {
            // Swollowed
            logger.error("Error while sending communications, e");
        }
    }

    private ProfileDetails getProfileDetailsById(String internalId) {
        return profileDetailsRepo.findById(internalId).get();
    }

    @Override
    public void sendAlertForWalletTransaction(
            Wallet wallet, ProfileDetails profileDetails, SecureProfileDetails secureDetails, WalletTransaction walletTransaction, Boolean rechargeSuccessful) throws Exception {

        if (rechargeSuccessful && walletTransaction.getBalanceBefore() != null && walletTransaction.getBalanceAfter() != null
                && !walletTransaction.getBalanceAfter().equals(walletTransaction.getBalanceBefore())) {

            // Sending SMS
            BigDecimal balance = wallet.getBalance();
            if (balance != null && balance.compareTo(BigDecimal.ZERO) > 0) {
                if (wallet.getBalance() != null && walletTransaction.getAmount() != null) {
                    BigInteger amount = walletTransaction.getAmount().toBigInteger();
                    coreCommunicationSender.sendSms(langSmsTemplates.rechargeSuccess(wallet.getBalance().toString(), walletTransaction.getAmount().toString()), secureDetails.getCompleteNumber());
                }
            }
        }

        // Sending SMS To Admin
        coreCommunicationSender.sendEscalationSms("R" + secureDetails.getProfileId());
        coreCommunicationSender.sendEscalationSms("R" + walletTransaction.getAmount().toBigInteger());


    }

    @Override
    public void rechargeFailedEscalation(String escMessage, Object escObject, String methodReference, String txnId) throws Exception {

        coreCommunicationSender.sendEscalationSms("F" + txnId);

    }

    @Override
    public void sendAlertForExtendedMembership(Wallet wallet, ProfileDetails profileDetails, SecureProfileDetails secureDetails, WalletTransaction walletTransaction) {
        // TODO VINAYPA
    }

    @Override
    public void accountDeleted(String profileId, String completeNumber) {

        coreCommunicationSender.sendSms(langSmsTemplates.accountDeleted(), completeNumber);
        coreCommunicationSender.sendEscalationSms("D" + profileId);
    }

    @Override
    public void sendRandomSms(String smsText, String completeNumber, String profileId, String profileName) throws Exception {
        coreCommunicationSender.sendSmsWithoutTemplate(smsText, completeNumber, profileId, profileName);
    }

    @Override
    public void registrationSuccess(ProfileDetails profileDetails, SecureProfileDetails secureDetails) throws Exception {
        coreCommunicationSender.sendSms(langSmsTemplates.welcome(profileDetails.getProfileId(), profileDetails.getFirstName()), secureDetails.getCompleteNumber());
        coreCommunicationSender.sendEscalationSms("N" + profileDetails.getProfileId());
    }

    @Override
    public void sendAlertForContactSharing(String completeNumber, String profileId) {
        coreCommunicationSender.sendSms(langSmsTemplates.contactShared(profileId), completeNumber);
    }


}
