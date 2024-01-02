package com.edge.repositories;


import com.edge.core.wallet.Wallet;
import org.springframework.data.repository.CrudRepository;

/**
 * @author vinpatil
 */
public interface WalletRepository extends CrudRepository<Wallet, String> {


}