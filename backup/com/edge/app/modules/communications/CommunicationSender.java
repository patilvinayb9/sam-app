package com.edge.app.modules.communications;

import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profileConnection.ProfileConnection;
import com.edge.core.wallet.Wallet;
import com.edge.core.wallet.WalletTransaction;

public interface CommunicationSender {

    void sendAlertForConnection(ProfileConnection connection, String iInternalId);

    void sendAlertForWalletTransaction(Wallet wallet, ProfileDetails profileDetails, SecureProfileDetails secureDetails, WalletTransaction walletTransaction, Boolean successful) throws Exception;

    void rechargeFailedEscalation(String escMessage, Object escObject, String methodReference, String txnId) throws Exception;

    void sendAlertForExtendedMembership(Wallet wallet, ProfileDetails profileDetails, SecureProfileDetails secureDetails, WalletTransaction walletTransaction);

    void accountDeleted(String profileId, String completeNumber);

    void sendRandomSms(String smsText, String completeNumber, String profileId, String profileName) throws Exception;

    void registrationSuccess(ProfileDetails profileDetails, SecureProfileDetails secureDetails) throws Exception;

    void sendAlertForContactSharing(String completeNumber, String profileId);
}
