package com.edge.repositories;


import com.edge.core.wallet.WalletTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * @author vinpatil
 */
public interface WalletTransactionRepository extends CrudRepository<WalletTransaction, String> {

    List<WalletTransaction> findTop50ByCustomerNameOrderByUpdatedOnDesc(String customerName);

    List<WalletTransaction> findTop50ByOrderByUpdatedOnDesc();

}