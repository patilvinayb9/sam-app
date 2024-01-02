package com.edge.app.modules.wallet;

import com.edge.app.modules.communications.CommunicationSender;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.core.utils.CoreDateUtils;
import com.edge.core.wallet.*;
import com.edge.repositories.ProfileDetailsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class MeetMateWalletServiceImpl implements MeetMateWalletService {

    private static final Logger logger = LoggerFactory.getLogger(MeetMateWalletServiceImpl.class);

    @Autowired
    private WalletService walletService;

    @Autowired
    private CommunicationSender meetMateAlertService;

    @Autowired
    private SecureProfileDetailsService secureProfileDetailsService;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Override
    @Transactional
    public Wallet createWallet(String internalId) {
        // Create a new Wallet
        Wallet wallet = walletService.createWallet(internalId);
        return wallet;
    }

    @Override
    @Transactional
    public void initiateWalletRechargeById(Map<String, String> values, String internalId) {
        walletService.initiateWalletRecharge(values, internalId);
    }

    @Override
    @Transactional
    public void updateWalletTransaction(WalletTransaction walletTransactionRec, Boolean rechargeSuccessful) throws Exception {
        try {

            WalletDto resWalletDto = walletService.updateWalletTransaction(walletTransactionRec, rechargeSuccessful);
            if (resWalletDto == null || resWalletDto.getWallet() == null) return;

            Wallet wallet = resWalletDto.getWallet();

            if (rechargeSuccessful) {

                ProfileDetails profileDetails = getProfileDetailsById(wallet.getInternalId());
                SecureProfileDetails secureDetails = secureProfileDetailsService.findById(wallet.getInternalId());

                meetMateAlertService.sendAlertForWalletTransaction(wallet, profileDetails, secureDetails, resWalletDto.getWalletTransaction(), rechargeSuccessful);

                // Extend Membership by an Year
                //BigDecimal annualMembershipRate = walletRateService.getAnnualMembershipRate();

                if (wallet.getBalance().compareTo(BigDecimal.ZERO) >= 0) {

                    // Melava
                    BigInteger amount = walletTransactionRec.getAmount().toBigInteger();
                    WalletTransaction updatedWalletTransaction = resWalletDto.getWalletTransaction();

                    ProductInfoEnum productInfo = ProductInfoEnum.valueOf(updatedWalletTransaction.getProductInfo().trim().toUpperCase());

                    int daysToAdd = productInfo.getDays();

                    walletService.addNewTransaction(wallet.getInternalId(), wallet.getBalance(), productInfo.name(), WalletTranCatEnum.Debit);

                    Date membershipValidTill = profileDetails.getMembershipValidTill();
                    if (!CoreDateUtils.isTodayOrFuture(membershipValidTill)) {
                        membershipValidTill = CoreDateUtils.today();
                    }
                    Date newMembership = CoreDateUtils.addDays(membershipValidTill, daysToAdd);

                    profileDetails.setMembershipValidTill(newMembership);
                    profileDetails.setMembershipPlan(productInfo.getMembershipPlan());
                    profileDetails.setMembershipRenewDate(new Date());
                    profileDetails.setProductInfo(productInfo.name());

                    saveProfileDetails(profileDetails);

                    meetMateAlertService.sendAlertForExtendedMembership(wallet, profileDetails, secureDetails, resWalletDto.getWalletTransaction());
                }


            }

        } catch (Throwable e) {
            String message = e.getMessage();
            meetMateAlertService.rechargeFailedEscalation(message, walletTransactionRec, "updateWalletTransaction", walletTransactionRec.getMerchantTransactionId());
            e.printStackTrace();
            throw e;
        }
    }

    public void saveProfileDetails(ProfileDetails profileDetails) {
        profileDetailsRepo.save(profileDetails);
    }

    @Override
    public Optional<Wallet> loadWalletDetailsById(String internalId) {
        return walletService.getWalletDetails(internalId);
    }


    @Override
    public List<WalletTransaction> loadWalletTransactionsById(String internalId) {
        return walletService.getWalletTransactions(internalId);
    }


    @Override
    public List<WalletTransaction> loadSuccessWalletTransactions() {
        return walletService.getSuccessWalletTransactions();
    }

    public ProfileDetails getProfileDetailsById(String internalId) {
        return profileDetailsRepo.findById(internalId).get();
    }

}
