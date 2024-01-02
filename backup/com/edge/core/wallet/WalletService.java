package com.edge.core.wallet;

import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface WalletService {

    Wallet createWallet(String internalId);

    @Transactional
    void addNewTransaction(String internalId, BigDecimal amount, String productInfo, WalletTranCatEnum tranCategory);

    @Transactional
    void initiateWalletRecharge(Map<String, String> values, String internalId);

    @Transactional
    WalletDto updateWalletTransaction(WalletTransaction walletTransactionRec, Boolean rechargeSuccessful);

    @Transactional
    List<WalletTransaction> getWalletTransactions(String internalId);

    Optional<Wallet> getWalletDetails(String internalId);

    void saveWallet(Wallet wallet);

    void saveWalletTransaction(WalletTransaction walletTransaction);

    List<WalletTransaction> getSuccessWalletTransactions();

}
