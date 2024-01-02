package com.edge.core.wallet;

public class WalletDto {

    private Wallet wallet;

    private WalletTransaction walletTransaction;

    public WalletDto(Wallet wallet, WalletTransaction walletTransaction) {
        this.wallet = wallet;
        this.walletTransaction = walletTransaction;
    }

    public Wallet getWallet() {
        return wallet;
    }

    public void setWallet(Wallet wallet) {
        this.wallet = wallet;
    }

    public WalletTransaction getWalletTransaction() {
        return walletTransaction;
    }

    public void setWalletTransaction(WalletTransaction walletTransaction) {
        this.walletTransaction = walletTransaction;
    }
}
