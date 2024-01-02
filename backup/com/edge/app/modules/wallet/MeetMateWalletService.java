package com.edge.app.modules.wallet;

import com.edge.core.wallet.Wallet;
import com.edge.core.wallet.WalletTransaction;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface MeetMateWalletService {

    Wallet createWallet(String internalId);

    void initiateWalletRechargeById(Map<String, String> values, String internalId);

    void updateWalletTransaction(WalletTransaction walletTransactionRec, Boolean rechargeSuccessful) throws Exception;

    Optional<Wallet> loadWalletDetailsById(String internalId);

    List<WalletTransaction> loadWalletTransactionsById(String internalId);

    List<WalletTransaction> loadSuccessWalletTransactions();

    //BigDecimal updateWalletForConnection(ProfileConnection connection, String internalId);

}