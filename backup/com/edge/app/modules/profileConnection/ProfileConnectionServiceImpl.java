package com.edge.app.modules.profileConnection;

import com.edge.app.modules.common.AppConstants;
import com.edge.app.modules.common.ConnectionStatusEnum;
import com.edge.app.modules.communications.CommunicationSender;
import com.edge.app.modules.expectations.Expectations;
import com.edge.app.modules.language.LangMessages;
import com.edge.app.modules.notification.NotificationService;
import com.edge.app.modules.profile.dto.ProfileDetailsDto;
import com.edge.app.modules.profile.nonSecure.AllowRequestFromEnum;
import com.edge.app.modules.profile.nonSecure.ProfileDetails;
import com.edge.app.modules.profile.nonSecure.ShareContactsWithEnum;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetails;
import com.edge.app.modules.profile.profileSecure.SecureProfileDetailsService;
import com.edge.app.modules.profileInternalInfo.ProfileInternalInfoService;
import com.edge.core.exception.AppRuntimeException;
import com.edge.core.helper.DbHelper;
import com.edge.core.utils.CoreDateUtils;
import com.edge.repositories.ExpectationsRepository;
import com.edge.repositories.ProfileConnectionRepository;
import com.edge.repositories.ProfileDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ProfileConnectionServiceImpl implements ProfileConnectionService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    @Qualifier("mongoDbHelper")
    private DbHelper dbHelper;

    @Autowired
    private LangMessages langMessages;

    @Autowired
    private ProfileConnectionRepository profileConnectionRepo;

    @Autowired
    private SecureProfileDetailsService secureProfileDetailsService;

    @Autowired
    private ProfileDetailsRepository profileDetailsRepo;

    @Autowired
    private ProfileInternalInfoService profileInternalInfoService;

//    @Autowired
//    private ExpectationsService expectationsService;

    @Autowired
    private ExpectationsRepository expectationsRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CommunicationSender communicationSender;

    @Autowired
    private ProfileConnectionUtil profileConnectionUtil;

    @Override
    @Transactional
    public ProfileDetails sendConnectionRequestById(String loggedInId, String internalIdTo) {
        try {

            ProfileDetails loggedInProfile = profileDetailsRepo.findById(loggedInId).get();

            if (!loggedInProfile.isMembershipValid()) {
                throw new AppRuntimeException(null, langMessages.msg_renewMembership());
            }

            ProfileDetails profileTo = profileDetailsRepo.findById(internalIdTo).get();

            // Check if request is permitted
            if(//AllowRequestFromEnum.NONE == profileTo.getAllowRequestFrom() ||
                    ( AllowRequestFromEnum.EXPECTED == profileTo.getAllowRequestFrom() &&
                            !checkIfRequestIsPermitted(loggedInProfile, profileTo)
                    ) 
            ){
                throw new AppRuntimeException(null, langMessages.msg_notToRestrictedProfile());
            }

            if (CoreDateUtils.isPast(profileTo.getMembershipValidTill())) {
                throw new AppRuntimeException(null, langMessages.msg_notToInactiveProfiles());
            }

            if (CoreDateUtils.isPast(loggedInProfile.getMembershipValidTill())) {
                throw new AppRuntimeException(null, langMessages.msg_noActionByInactiveProfile());
            }

            if (!profileConnectionUtil.checkIfConnectionIsAllowed(loggedInProfile.getInternalId(), internalIdTo)) {
                throw new AppRuntimeException(null, langMessages.msg_invalidGenderCombination());
            }

            Date today = new Date();

            ProfileConnection profileConnection = new ProfileConnection();
            profileConnection.setRequestedOn(today);
            profileConnection.setInternalFrom(loggedInProfile.getInternalId());
            profileConnection.setInternalTo(profileTo.getInternalId());
            profileConnection.setProfileFrom(loggedInProfile.getProfileId());
            profileConnection.setProfileTo(profileTo.getProfileId());
            profileConnection.setFirstNameFrom(loggedInProfile.getFirstName());
            profileConnection.setFirstNameTo(profileTo.getFirstName());

            String connectionId = profileConnectionUtil.deriveConnectionId(loggedInProfile.getInternalId(), profileTo.getInternalId());
            profileConnection.setConnectionId(connectionId);

            profileConnection.setConnectionStatus(ConnectionStatusEnum.Requested);

            Optional<ProfileConnection> byId = profileConnectionRepo.findById(profileConnection.getConnectionId());
            if (byId.isPresent()) {
                ConnectionStatusEnum connectionStatus = byId.get().getConnectionStatus();
                if (connectionStatus != ConnectionStatusEnum.Withdrawn) {
                    throw new AppRuntimeException(null, langMessages.msg_alreadyConnection() + connectionStatus.name());
                }
            }

            profileInternalInfoService.addToReadProfiles(loggedInProfile.getInternalId(), profileTo.getInternalId());
            profileInternalInfoService.addToReadProfiles(profileTo.getInternalId(), loggedInProfile.getInternalId());

            profileInternalInfoService.removeFromShortlistedIfAnyById(loggedInProfile.getInternalId(), profileTo.getInternalId());
            profileInternalInfoService.removeFromShortlistedIfAnyById(profileTo.getInternalId(), loggedInProfile.getInternalId());

            notificationService.addNotificationForRequest(profileConnection);

            profileConnectionRepo.save(profileConnection);

            communicationSender.sendAlertForConnection(profileConnection, loggedInProfile.getProfileId());

            return profileTo;

        } catch (DataIntegrityViolationException deve) {
            throw new AppRuntimeException(deve, langMessages.msg_alreadyConnection());
        }

    }

    @Override
    public List<ProfileDetailsDto> searchProfilesById(String loggedInId, String searchType) {

        List<ProfileDetailsDto> profileDetailsDtos = new ArrayList<ProfileDetailsDto>();

        SearchTypeEnum searchTypeEnum = SearchTypeEnum.valueOf(searchType);

        String queryClause = "";

        switch (searchTypeEnum) {
            case SearchTypeEnum.IRequested:
                /* VINAY query = " order by pc.requestedOn desc ";*/

                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Requested + "'" +
                        "   , internalFrom : '" + loggedInId + "'";
                break;

            case SearchTypeEnum.TheyRequested:
                queryClause = "       connectionStatus : '" + ConnectionStatusEnum.Requested + "'" +
                        "   ,   internalTo : '" + loggedInId + "'";
                break;

            case SearchTypeEnum.Rejected:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Rejected + "'" +
                        "   ,  $or: [ " +
                        "				 {	internalTo : '" + loggedInId + "' } " +
                        "   			,{ 	internalFrom : '" + loggedInId + "' } " +
                        "            ]";
                break;

            case SearchTypeEnum.IRejected:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Rejected + "'" +
                        "   , internalTo : '" + loggedInId + "'";
                break;

            case SearchTypeEnum.TheyRejected:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Rejected + "'" +
                        "   , internalFrom : '" + loggedInId + "'";
                break;

            case SearchTypeEnum.Accepted:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Accepted + "'" +
                        "   ,  $or: [ " +
                        "				 {	internalTo : '" + loggedInId + "' } " +
                        "   			,{ 	internalFrom : '" + loggedInId + "' } " +
                        "            ]";
                break;

            case SearchTypeEnum.Withdrawn:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Withdrawn + "'" +
                        "   ,  $or: [ " +
                        "				 {	internalTo : '" + loggedInId + "' } " +
                        "   			,{ 	internalFrom : '" + loggedInId + "' } " +
                        "            ]";
                break;

            case SearchTypeEnum.IAccepted:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Accepted + "'" +
                        "   , internalTo : '" + loggedInId + "'";
                break;

            case SearchTypeEnum.TheyAccepted:
                queryClause = "     connectionStatus : '" + ConnectionStatusEnum.Accepted + "'" +
                        "   , internalFrom : '" + loggedInId + "'";
                break;

        }

        Query queryConn
                = new BasicQuery("{" + queryClause + "}");
        queryConn.with(Sort.by(Sort.Direction.DESC, "actionedOn", "requestedOn"));
        queryConn.limit(AppConstants.MAX_REMOVED_SIZE);

        List<ProfileConnection> connList = mongoTemplate.find(queryConn, ProfileConnection.class);

        Set<String> profilesFrom = connList.stream().map(ProfileConnection::getInternalFrom).collect(Collectors.toSet());
        Set<String> profilesTo = connList.stream().map(ProfileConnection::getInternalTo).collect(Collectors.toSet());

        profilesFrom.addAll(profilesTo);

        Query queryProfile = new BasicQuery(
                "{ internalId: { $in: [ " + dbHelper.addINClause(profilesFrom, null) + " ] } }");
        List<ProfileDetails> profileList = mongoTemplate.find(queryProfile, ProfileDetails.class);

        Map<String, ProfileDetails> profilesMap = profileList.stream().collect(Collectors.toMap(ProfileDetails::getInternalId, Function.identity()));

        connList.forEach(currConn -> {
            ProfileDetailsDto profileDetailsDto = new ProfileDetailsDto();
            profileDetailsDto.setProfileConnection(currConn);
            if (loggedInId.equals(currConn.getInternalFrom())) {
                profileDetailsDto.setProfileDetails(profilesMap.get(currConn.getInternalTo()));
            } else {
                profileDetailsDto.setProfileDetails(profilesMap.get(currConn.getInternalFrom()));
            }
            profileDetailsDtos.add(profileDetailsDto);
        });

        return profileDetailsDtos;
    }

    @Override
    @Transactional
    public void withdrawRequestById(String loggedInId, String internalId) {

        String queryClause = " internalTo : '" + internalId + "'" +
                " , internalFrom : '" + loggedInId + "'";

        Query query = new BasicQuery("{ " + queryClause + "}");

        List<ProfileConnection> searchedConnections = mongoTemplate.find(query, ProfileConnection.class);

        if (searchedConnections != null && searchedConnections.size() == 1) {

            Date today = new Date();
            ProfileConnection connection = searchedConnections.get(0);

            if (connection.getConnectionStatus() != ConnectionStatusEnum.Requested) {
                throw new AppRuntimeException(null, langMessages.msg_withdrawNotAllowed());
            }

            connection.setActionedOn(today);
            connection.setConnectionStatus(ConnectionStatusEnum.Withdrawn);

            notificationService.addNotificationForWithdrawal(connection);

            /*BigDecimal amount = meetMateWalletService.updateWalletForConnection(
                    connection,
                    iInternalId);

            connection.setAmount(amount);*/
            profileConnectionRepo.save(connection);

            communicationSender.sendAlertForConnection(connection, loggedInId);

        } else {
            throw new AppRuntimeException(null, langMessages.msg_noSuchConnection());
        }
    }

    @Override
    @Transactional
    public ProfileConnection actionRequestById(String iInternalId, ConnectionAction connectionAction) {

        ProfileDetails iProfileDetails = profileDetailsRepo.findById(iInternalId).get();

        if (!iProfileDetails.isMembershipValid()) {
            throw new AppRuntimeException(null, langMessages.msg_renewMembership());
        }

        String otherInternalId = connectionAction.getInternalId();
        ProfileDetails otherProfileDetails = profileDetailsRepo.findById(otherInternalId).get();

        String queryClause = " internalTo : '" + iInternalId + "'" +
                " , internalFrom : '" + connectionAction.getInternalId() + "'";

        Query query = new BasicQuery("{ " + queryClause + "}");

        List<ProfileConnection> searchedConnections = mongoTemplate.find(query, ProfileConnection.class);

        if (searchedConnections != null && searchedConnections.size() == 1) {

            Date today = new Date();
            ProfileConnection connection = searchedConnections.get(0);

            if (connection.getConnectionStatus() == ConnectionStatusEnum.Accepted) {
                throw new AppRuntimeException(null, langMessages.msg_alreadyAccepted());
            }

            if (CoreDateUtils.isPast(otherProfileDetails.getMembershipValidTill())) {
                if (connectionAction.getConnectionStatus() == ConnectionStatusEnum.Accepted) {
                    throw new AppRuntimeException(null, langMessages.msg_inactiveCanNotBeAccepted());
                }
            }

            connection.setActionedOn(today);
            connection.setConnectionStatus(connectionAction.getConnectionStatus());

            notificationService.markNotificationAsReadOnAction(iInternalId, otherInternalId, connectionAction.getConnectionStatus());
            notificationService.addNotificationForAction(connection);

            profileConnectionRepo.save(connection);

            communicationSender.sendAlertForConnection(connection, iInternalId);

            return connection;

        } else {
            throw new AppRuntimeException(null, langMessages.msg_noSuchConnection());
        }
    }


    @Override
    @Transactional
    public SecureProfileDetails buySecureDetailsById(String loggedInId, ProfileDetails otherProfileDetails) {
        SecureProfileDetails secureDetails = null;

        Optional<ProfileDetails> loggedInProfileById = profileDetailsRepo.findById(loggedInId);
        ProfileDetails loggedProfileDetails = loggedInProfileById.get();

        String otherInternalId = otherProfileDetails.getInternalId();

        // Membership check
        if (!loggedProfileDetails.isMembershipValid()) {
            throw new AppRuntimeException(null, langMessages.msg_renewMembership());
        }

        // Check if contact is already bought
        String buyContactsProfiles = profileInternalInfoService.getBuyContactsProfiles(loggedInId);
        if(buyContactsProfiles.contains(otherInternalId + ",")){
            return secureProfileDetailsService.findById(otherInternalId);
        }

        // ALLOWED CONTACTS CHECK
        if(loggedProfileDetails.getUsedContacts() == loggedProfileDetails.getTotalContacts()){
            throw new AppRuntimeException(null, "You have exhausted allowed contacts to buy as per the membership plan. " +
                    "You can still send the request and then view the contact once the request is accepted.");
        }

        // Check if request is permitted - EXPECTED
        if(ShareContactsWithEnum.EXPECTED == otherProfileDetails.getShareContactsWith() &&
               !checkIfRequestIsPermitted(loggedProfileDetails, otherProfileDetails)){
            throw new AppRuntimeException(null, langMessages.msg_notToRestrictedProfile());
        }

        // Share Contacts with ACCEPTED
        if(ShareContactsWithEnum.ACCEPTED == otherProfileDetails.getShareContactsWith()){
            throw new AppRuntimeException(null, "Only accepted contacts are allowed to view contact details of profile " + otherProfileDetails.getProfileId() + " " +
                    "Please send a request and then you can view the contact if request is accepted. Apologies!");
        }

        profileInternalInfoService.addToBuyContactsProfiles(loggedInId, otherInternalId);

        SecureProfileDetails secureProfileDetails = secureProfileDetailsService.findById(otherInternalId);

        loggedProfileDetails.setUsedContacts(loggedProfileDetails.getUsedContacts()+1);
        profileDetailsRepo.save(loggedProfileDetails);

        // Communicate
        communicationSender.sendAlertForContactSharing(secureProfileDetails.getCompleteNumber(), loggedProfileDetails.getProfileId());

        notificationService.addNotificationForContactSharing(otherProfileDetails, loggedProfileDetails);

        return secureProfileDetails;
    }

    @Override
    public SecureProfileDetails getSecureDetailsById(String IInternalId, String otherInternalId) {
        SecureProfileDetails secureDetails = null;

        if (!checkIfConnectionExists(IInternalId, otherInternalId, ConnectionStatusEnum.Accepted)) {
            ProfileConnection iRequestedConnection = getIRequestedConnection(IInternalId, otherInternalId);
            if (iRequestedConnection == null) {
                throw new AppRuntimeException(null, langMessages.msg_onlyAccepetedCanViewContacts());
            }
            Date createdOn = iRequestedConnection.getCreatedOn();
            long count = CoreDateUtils.calculateDays(createdOn);
            if (count < AppConstants.VIEW_CONTACT_REQUESTED) {
                long days = AppConstants.VIEW_CONTACT_REQUESTED - count;
                throw new AppRuntimeException(null, langMessages.msg_viewContactAfterDays(String.valueOf(days)));
            }
        }

        Optional<ProfileDetails> iProfileDetailsOpt = profileDetailsRepo.findById(IInternalId);
        ProfileDetails iProfileDetails = iProfileDetailsOpt.get();

        if (!iProfileDetails.isMembershipValid()) {
            throw new AppRuntimeException(null, langMessages.msg_renewMembership());
        }

        notificationService.markNotificationAsReadOnAction(IInternalId, otherInternalId, ConnectionStatusEnum.Accepted);

        secureDetails = secureProfileDetailsService.findById(otherInternalId);

        return secureDetails;
    }

    @Override
    public ProfileConnection getIfConnectionExistsById(String otherInternalId, String loggedInId) {
        ProfileConnection profileConnection = null;
        String connectionId = profileConnectionUtil.deriveConnectionId(otherInternalId, loggedInId);

        if (connectionId != null) {
            Optional<ProfileConnection> profileConnectionOptional = profileConnectionRepo.findById(connectionId);
            if (profileConnectionOptional.isPresent()) {
                profileConnection = profileConnectionOptional.get();
            }
        }
        return profileConnection;
    }

    @Override
    public boolean checkIfConnectionExists(String internal1, String internal2, ConnectionStatusEnum status) {
        String connectionId = profileConnectionUtil.deriveConnectionId(internal1, internal2);
        Optional<ProfileConnection> connOptional = profileConnectionRepo.findById(connectionId);
        return connOptional.isPresent() && connOptional.get().getConnectionStatus() == status;
    }

    private ProfileConnection getIRequestedConnection(String internalFrom, String internalTo) {
        ConnectionStatusEnum status = ConnectionStatusEnum.Requested;
        String connectionId = profileConnectionUtil.deriveConnectionId(internalFrom, internalTo);
        Optional<ProfileConnection> connOptional = profileConnectionRepo.findById(connectionId);
        if (connOptional.isPresent() && connOptional.get().getInternalFrom().equals(internalFrom)) {
            return connOptional.get();
        }
        return null;
    }

    public boolean checkIfRequestIsPermitted(ProfileDetails profileFrom, ProfileDetails profileTo) {
        boolean result = true;
        Optional<Expectations> byId = expectationsRepository.findById(profileTo.getInternalId());

        if (byId.isPresent()) {
            Expectations expectations = byId.get();
            String restrictions = expectations.getRestrictions();

            String queryClause = " internalId:{ $eq: '" + profileFrom.getInternalId() + "' }";
            queryClause += "," + restrictions;

            queryClause += ", membershipValidTill : { $gte : new Date() }  "; // Membership has to be valid

            Query query = new BasicQuery(" { "
                    + queryClause
                    + " } ");

            query.limit(AppConstants.MAX_WALL_SIZE);
            List<ProfileDetails> list = mongoTemplate.find(query, ProfileDetails.class); //setMaxResults(AppConstants.MAX_REMOVED_SIZE);

            if (list == null || list.size() == 0) {
                return false;
            }

        }

        return result;

    }

}
