package com.edge.core.wallet;

import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.language.CoreMessages;
import com.edge.repositories.WalletRepository;
import com.edge.repositories.WalletTransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class WalletServiceImpl implements WalletService {

    private static final Logger logger = LoggerFactory.getLogger(WalletServiceImpl.class);

    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private WalletTransactionRepository walletTransactionRepo;

    @Autowired
    private CoreMessages coreMessages;

    @Override
    @Transactional
    public Wallet createWallet(String internalId) {
        Optional<Wallet> byId = walletRepo.findById(internalId);
        if (!byId.isPresent()) {
            Wallet wallet = new Wallet();
            wallet.setInternalId(internalId);
            BigDecimal zero = BigDecimal.valueOf(0);
            wallet.setBalance(zero);
            wallet.setTotalRechargeTillDate(zero);
            walletRepo.save(wallet);
            return wallet;
        } else {
            return byId.get();
        }

    }

    @Override
    @Transactional
    public void addNewTransaction(String internalId, BigDecimal amount, String productInfo, WalletTranCatEnum tranCategory) {

        Optional<Wallet> walletOptional = walletRepo.findById(internalId);
        if (!walletOptional.isPresent()) {
            throw new AppRuntimeException(null, coreMessages.msg_walletNotInitialized());
        }
        Wallet wallet = walletOptional.get();

        WalletTransaction walletTransaction = new WalletTransaction();

        // FIX
        walletTransaction.setCustomerName(internalId);
        walletTransaction.setAmount(amount);
        walletTransaction.setTranCategory(tranCategory.name());

        walletTransaction.setCustomerName(internalId);
        walletTransaction.setProductInfo(productInfo);
        walletTransaction.setStatus(tranCategory.name());

        BigDecimal currBalance = wallet.getBalance();
        BigDecimal newBalance = null;
        if (tranCategory == WalletTranCatEnum.Credit) {
            newBalance = currBalance.add(amount);
        } else {
            newBalance = currBalance.subtract(amount);
        }

        wallet.setBalance(newBalance);
        saveWallet(wallet);
        logger.info("Balance Updated TransactionId" + walletTransaction.getMerchantTransactionId() + " :: " + newBalance);

        walletTransaction.setBalanceBefore(currBalance);
        walletTransaction.setBalanceAfter(newBalance);
        saveWalletTransaction(walletTransaction);

        walletTransactionRepo.save(walletTransaction);
    }


    @Override
    @Transactional
    public void initiateWalletRecharge(Map<String, String> values, String internalId) {

        Optional<Wallet> walletOptional = walletRepo.findById(internalId);
        if (!walletOptional.isPresent()) {
            throw new AppRuntimeException(null, coreMessages.msg_walletNotInitialized());
        }
        Wallet wallet = walletOptional.get();

        WalletTransaction walletTransaction = new WalletTransaction();

        // THINGS THAT WOULD CHANGE
        walletTransaction.setStatus("Initiated");
        walletTransaction.setTranStatusLog("Initiated");

        // FIX
        walletTransaction.setCustomerName(internalId);
        walletTransaction.setCustomerPhone(values.get("phone"));
        walletTransaction.setProductInfo(values.get("productinfo"));
        walletTransaction.setCustomerEmail(values.get("email").trim());
        walletTransaction.setMerchantTransactionId(values.get("txnid").trim());
        walletTransaction.setAmount(new BigDecimal(values.get("amount").trim()));
        walletTransaction.setUdf1(values.get("udf1").trim());
        walletTransaction.setUdf2(values.get("udf2").trim());
        walletTransaction.setUdf3(values.get("udf3").trim());
        walletTransaction.setUdf4(values.get("udf4").trim());
        walletTransaction.setUdf5(values.get("udf5").trim());
        walletTransaction.setTranCategory(WalletTranCatEnum.Credit.name());

        walletTransactionRepo.save(walletTransaction);
    }

    @Override
    @Transactional
    public WalletDto updateWalletTransaction(WalletTransaction walletTransactionRec, Boolean rechargeSuccessful) {
        String customerNameRec = walletTransactionRec.getCustomerName();
        Optional<Wallet> walletOptional = walletRepo.findById(customerNameRec);
        if (!walletOptional.isPresent()) {
            throw new AppRuntimeException(null, "Wallet not yet initialized for Profile : " + customerNameRec);
        }
        Wallet wallet = walletOptional.get();
        String txnId = walletTransactionRec.getMerchantTransactionId();

        Optional<WalletTransaction> walletTranOptional = walletTransactionRepo.findById(txnId);
        if (!walletOptional.isPresent()) {
            throw new AppRuntimeException(null, "Wallet TransactionNot Found : " + txnId);
        }

        WalletTransaction walletTransactionDb = walletTranOptional.get();
        String customerNameDb = walletTransactionDb.getCustomerName();

        if (!customerNameRec.equals(customerNameDb)) {
            throw new AppRuntimeException(null, "Customer Name Mismatch for Transaction " + txnId + " - Received : " + customerNameRec + " Db : " + customerNameDb);
        }

        BigDecimal amountRec = walletTransactionRec.getAmount();
        BigDecimal amountDb = walletTransactionDb.getAmount();
        if (amountRec.compareTo(amountDb) != 0) {
            throw new AppRuntimeException(null, "Amount Mismatch for Transaction " + txnId + " - Received : " + amountRec + " Db : " + amountDb);
        }

        if (rechargeSuccessful && walletTransactionDb.getPaymentId().equals(walletTransactionRec.getPaymentId())) {
            // This payment is already processed
            logger.info("Payment Already Processed.. TransactionId" + walletTransactionRec.getMerchantTransactionId());
            logger.info("Payment Already Processed.. PaymentID" + walletTransactionRec.getPaymentId());
            return null;
        }

        if (rechargeSuccessful) {

            BigDecimal currBalance = wallet.getBalance();
            BigDecimal amount = walletTransactionDb.getAmount();
            BigDecimal newBalance = currBalance.add(amount);

            wallet.setBalance(newBalance);
            wallet.setTotalRechargeTillDate(wallet.getTotalRechargeTillDate().add(amount));

            walletRepo.save(wallet);
            logger.info("Balance Updated TransactionId" + walletTransactionRec.getMerchantTransactionId() + " :: " + newBalance);

            walletTransactionDb.setBalanceBefore(currBalance);
            walletTransactionDb.setBalanceAfter(newBalance);

        }

        walletTransactionDb.setStatus(walletTransactionRec.getStatus());
        walletTransactionDb.setError_Message(walletTransactionRec.getError_Message());
        walletTransactionDb.setCustomerPhone(walletTransactionRec.getCustomerPhone());
        walletTransactionDb.setSplit_info(walletTransactionRec.getSplit_info());
        walletTransactionDb.setAdditionalCharges(walletTransactionRec.getAdditionalCharges());
        walletTransactionDb.setPaymentId(walletTransactionRec.getPaymentId());
        walletTransactionDb.setNotificationId(walletTransactionRec.getNotificationId());
        walletTransactionDb.setPaymentMode(walletTransactionRec.getPaymentMode());

        walletTransactionDb.setTranStatusLog(walletTransactionDb.getTranStatusLog() + " | " + walletTransactionRec.getStatus());
        walletTransactionRepo.save(walletTransactionDb);

        logger.info("WalletTransaction Saved :: " + walletTransactionRec.getMerchantTransactionId());

        return new WalletDto(wallet, walletTransactionDb);
    }

    @Override
    public List<WalletTransaction> getWalletTransactions(String internalId) {
        return walletTransactionRepo.findTop50ByCustomerNameOrderByUpdatedOnDesc(internalId);
    }


    @Override
    public List<WalletTransaction> getSuccessWalletTransactions() {
        return walletTransactionRepo.findTop50ByOrderByUpdatedOnDesc();
    }

    @Override
    public Optional<Wallet> getWalletDetails(String internalId) {
        return walletRepo.findById(internalId);
    }

    @Override
    public void saveWallet(Wallet wallet) {
        walletRepo.save(wallet);
    }

    @Override
    public void saveWalletTransaction(WalletTransaction walletTransaction) {
        walletTransactionRepo.save(walletTransaction);
    }

}
