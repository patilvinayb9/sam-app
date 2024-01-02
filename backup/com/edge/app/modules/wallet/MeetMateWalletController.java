package com.edge.app.modules.wallet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.edge.app.modules.api.AppAPIConstants;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.core.config.CoreConstants;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.modules.auth.SpringSecurityUtil;
import com.edge.core.modules.common.EdgeResponse;
import com.edge.core.wallet.PaymentGateway;
import com.edge.core.wallet.Wallet;
import com.edge.core.wallet.WalletTransaction;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class MeetMateWalletController {

    private static final Logger logger = LoggerFactory.getLogger(MeetMateWalletController.class);

    @Value(value = "${appContextRoot}")
    private String appContextRoot;

    @Value(value = "${appEmailId}")
    private String appEmailId;

    @Autowired
    private PaymentGateway paymentGateway;

    @Autowired
    private MeetMateWalletService meetMateWalletService;

    @Autowired
    private SecureProfileDetailsService secureProfileDetailsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(CoreConstants.DEFAULT_DATE_FORMAT);
        dateFormat.setLenient(true);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_WALLET_DETAILS})
    public EdgeResponse<Wallet> loadWalletDetails() {

        Wallet wallet = null;
        Optional<Wallet> walletOptional = meetMateWalletService.loadWalletDetailsById(SpringSecurityUtil.getLoggedInInternalId());

        if (walletOptional.isPresent()) {
            wallet = walletOptional.get();
        } else {
            String internalId = SpringSecurityUtil.getLoggedInInternalId();
            wallet = meetMateWalletService.createWallet(internalId);
        }

        return EdgeResponse.createDataResponse(wallet, "");
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_LOAD_WALLET_TRANSACTIONS})
    public EdgeResponse<List<WalletTransaction>> loadWalletTransactions() {

        List<WalletTransaction> walletTransactions = meetMateWalletService.loadWalletTransactionsById(SpringSecurityUtil.getLoggedInInternalId());

        if (walletTransactions == null || walletTransactions.size() == 0) {
            //return EdgeResponse.createErrorResponse(null,"No Wallet Transactions Yet.!", null, null);
            return EdgeResponse.createDataResponse(walletTransactions, "");
        } else {
            return EdgeResponse.createDataResponse(walletTransactions, "");
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_RECHARGE_WALLET})
    public void rechargeWallet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.info("Inside rechargeWallet..");
        paymentGateway.printParams(request);

        String internalId = SpringSecurityUtil.getLoggedInInternalId();

        SecureProfileDetails secureProfileDetails = secureProfileDetailsService.findById(internalId);
        Map<String, String> extractParamMap = paymentGateway.extractParamMap(request);

        ProductInfoEnum productinfo = null;
        try {
            String productinfoStr = extractParamMap.get("productinfo");
            productinfo = ProductInfoEnum.valueOf(productinfoStr.trim().toUpperCase());
        } catch (Exception e) {
            throw new AppRuntimeException(null, "Invalid Promocode");
        }

        extractParamMap.put("amount", String.valueOf(productinfo.getAmount()));
        String emailId = appEmailId.replace("@", "." + secureProfileDetails.getProfileId() + "@");

        Map<String, String> values = paymentGateway.mapRequest(extractParamMap, emailId, secureProfileDetails.getCell(), internalId, secureProfileDetails.getProfileId());

        logger.info("Calculated rechargeWallet..");
        //values.entrySet().forEach(entry -> logger.info(entry.getKey() + " ::: " + entry.getValue()));

        meetMateWalletService.initiateWalletRechargeById(values, SpringSecurityUtil.getLoggedInInternalId());

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter writer = response.getWriter();

        // build HTML code
        String htmlResponse = paymentGateway.getHtml(values);
        // return response
        writer.println(htmlResponse);
    }

    /*UI WEB HOOK*/
    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_WALLET_SUCCESS})
    public void walletSuccess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        logger.info("Inside walletSuccess..");
        paymentGateway.printParams(request);
        //response.sendRedirect(appContextRoot + "#/pageRedirection=walletsView&messageType=success&showMessage=Wallet Recharge Successful.");
        response.sendRedirect(appContextRoot + "/#/page/walletsView");
    }

    /* UI WEB HOOK */
    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_WALLET_FAILURE})
    public void walletFailure(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //meetMateWalletService.updateWalletTransaction(request);
        logger.info("Inside walletFailure..");
        paymentGateway.printParams(request);
        //response.sendRedirect(appContextRoot + "#/?pageRedirection=walletsView&messageType=danger&showMessage=Oops! Wallet Recharge Failed.");
        response.sendRedirect(appContextRoot + "#/page/walletsView");
    }

    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_WALLET_SUCCESS_WEBHOOK})
    public ResponseEntity walletSuccessWebHookNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("Inside walletSuccessWebHookNew..");
            //integrationKit.printParams(request);

            ObjectMapper mapper = new ObjectMapper();
            WalletTransaction walletTransaction = mapper.readValue(request.getInputStream(), WalletTransaction.class);
            meetMateWalletService.updateWalletTransaction(walletTransaction, true);

            logger.info("Returning OK walletSuccessWebHookNew..");
            return new ResponseEntity(HttpStatus.OK);
        } catch (Throwable e) {
            logger.info("Inside Exception walletSuccessWebHookNew..");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.OK);  // For Configuring Webhook
        }
    }


    @ResponseBody
    @PostMapping(value = {AppAPIConstants.URL_WALLET_FAILURE_WEBHOOK})
    public ResponseEntity walletFailureWebHookNew(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            logger.info("Inside walletFailureWebHookNew..");

            ObjectMapper mapper = new ObjectMapper();
            WalletTransaction walletTransaction = mapper.readValue(request.getInputStream(), WalletTransaction.class);
            meetMateWalletService.updateWalletTransaction(walletTransaction, false);

            logger.info("Returning OK walletFailureWebHookNew..");
            return new ResponseEntity(HttpStatus.OK);
        } catch (Throwable e) {
            logger.info("Inside Exception walletFailureWebHookNew..");
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.OK);  // For Configuring Webhook
        }
    }


}


