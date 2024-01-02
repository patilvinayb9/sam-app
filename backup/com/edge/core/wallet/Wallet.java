package com.edge.core.wallet;

import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.common.EdgeEntity;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

// import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "WALLET")
public class Wallet extends EdgeEntity {

    @Id
    @NotNull
    @Size(min = 1, max = 50)
    private String internalId;

    @NotNull // @Size(min = 1, max = 50)
    private BigDecimal balance;

    @NotNull // @Size(min = 1, max = 50)
    private BigDecimal totalRechargeTillDate;

    public String getInternalId() {
        return internalId;
    }

    public void setInternalId(String internalId) {
        this.internalId = internalId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        if (balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new AppRuntimeException(null, "Insufficient Balance. Please Recharge with amount greater than " + balance.negate() + ". Thank You!");
        }
        this.balance = balance;
    }

    public BigDecimal getTotalRechargeTillDate() {
        return totalRechargeTillDate;
    }

    public void setTotalRechargeTillDate(BigDecimal totalRechargeTillDate) {
        this.totalRechargeTillDate = totalRechargeTillDate;
    }

}
