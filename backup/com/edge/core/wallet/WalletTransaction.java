package com.edge.core.wallet;

import com.edge.core.modules.common.EdgeEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Random;

@Data
@EqualsAndHashCode(callSuper=false)
@Document(collection = "WALLET_TRANSACTION")
public class WalletTransaction extends EdgeEntity {

    @Id
    private String merchantTransactionId;

    @Indexed
    @NotNull
    @Size(min = 0, max = 100)
    private String customerName = "";

    @NotNull
    @Size(min = 0, max = 100)
    private String productInfo = "";

    @NotNull
    @Size(min = 0, max = 200)
    private String customerEmail = "";

    @NotNull
    private BigDecimal amount;

    private BigDecimal onHoldAmount;

    private BigDecimal totalSpent;

    @NotNull
    @Size(min = 0, max = 50)
    private String tranCategory = WalletTranCatEnum.Credit.name();

    // UPDATE FIELDS

    private BigDecimal balanceBefore;

    private BigDecimal balanceAfter;

    @NotNull
    @Size(min = 0, max = 900)
    private String tranStatusLog = "";

    // ################################ AUTO SET FIELDS ############################ //

    //EXTRA RESPONSE FIELDS -

    @NotNull
    @Size(min = 0, max = 200)
    private String status = "";

    @NotNull
    @Size(min = 0, max = 200)
    private String error_Message = "";

    @NotNull
    @Size(min = 0, max = 50)
    private String customerPhone = "";

    @NotNull
    @Size(min = 0, max = 200)
    private String split_info = "";

    @NotNull
    @Size(min = 0, max = 100)
    private String additionalCharges = "";

    @NotNull
    @Size(min = 0, max = 200)
    private String paymentId = "";

    @NotNull
    @Size(min = 0, max = 50)
    private String notificationId = "";

    @NotNull
    @Size(min = 0, max = 50)
    private String paymentMode = "";

    // TRANSIENT FIELDS

    @NotNull
    @Size(min = 0, max = 200)
    @Transient
    private String hash = "";

    @NotNull
    @Size(min = 0, max = 50)
    @Transient
    private String udf1 = "";

    @NotNull
    @Size(min = 0, max = 50)
    @Transient
    private String udf2 = "";

    @NotNull
    @Size(min = 0, max = 50)
    @Transient
    private String udf3 = "";

    @NotNull
    @Size(min = 0, max = 50)
    @Transient
    private String udf4 = "";

    @NotNull
    @Size(min = 0, max = 50)
    @Transient
    private String udf5 = "";

    public String getMerchantTransactionId() {
        return merchantTransactionId;
    }

    public void setMerchantTransactionId() {
        Random rand = new Random();
        String rndm = Integer.toString(rand.nextInt()) + (System.currentTimeMillis() / 1000L);
        this.merchantTransactionId = rndm;
    }


    public void setMerchantTransactionId(String merchantTransactionId) {
        this.merchantTransactionId = merchantTransactionId;
    }

}
