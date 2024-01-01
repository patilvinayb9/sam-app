
function initLangMessages($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.lg={};

$scope.es.lg.CareNumber = "+91-7506004585";
$scope.es.lg.ShortestForm="Shortest Form to start with";
$scope.es.lg.Personal="Personal Details";

$scope.es.lg.New="New";
$scope.es.lg.PhoneNumber="Phone Number";
$scope.es.lg.Gender="Gender";
$scope.es.lg.BirthDate="Birth Date";
$scope.es.lg.Income="Income";
$scope.es.lg.Lakhs="Lakhs";

$scope.es.lg.FirstName="First Name";
$scope.es.lg.LastName="Last Name";

$scope.es.lg.Ft="Ft";
$scope.es.lg.Inches="Inches";
$scope.es.lg.Kg="Kg";

$scope.es.lg.MaritalStatus="Marital Status";
$scope.es.lg.About="About";
$scope.es.lg.BriefIntroduction="Brief Introduction";
$scope.es.lg.Physical="Physical Attributes";
$scope.es.lg.Height="Height";
$scope.es.lg.Weight="Weight";
$scope.es.lg.BloodGroup="Blood Group";
$scope.es.lg.BodyType="Body Type";
$scope.es.lg.SkinColor="Skin Color";
$scope.es.lg.Challenged="Challenged";
$scope.es.lg.Diet="Diet";
$scope.es.lg.Lifestyle="Lifestyle";

$scope.es.lg.Smoking="Smoking";
$scope.es.lg.Drinking="Drinking";
$scope.es.lg.Ethnicity="Ethnicity";
$scope.es.lg.Religion="Religion";
$scope.es.lg.Caste="Caste";
$scope.es.lg.MotherTongue="Mother Tongue";
$scope.es.lg.Birth="Birth Details";
$scope.es.lg.Date="Date";
$scope.es.lg.Day="Day";
$scope.es.lg.Month="Month";
$scope.es.lg.Year="Year";
$scope.es.lg.Years="Years";
$scope.es.lg.Time="Time";
$scope.es.lg.Hour="Hour";
$scope.es.lg.Minutes="Minutes";
$scope.es.lg.Country="Country";
$scope.es.lg.State="State";
$scope.es.lg.City="City";

$scope.es.lg.Job="Job";
$scope.es.lg.Education="Education";
$scope.es.lg.Details="Details";
$scope.es.lg.Optional="Optional";
$scope.es.lg.Profession="Profession";
$scope.es.lg.Stream="Stream";
$scope.es.lg.Designation="Designation";

$scope.es.lg.Family="Family Details";
$scope.es.lg.FatherOccupation="Father's Occupation";
$scope.es.lg.MotherOccupation="Mother's Occupation";
$scope.es.lg.Total="Total";
$scope.es.lg.Brothers="Brothers";
$scope.es.lg.Sisters="Sisters";
$scope.es.lg.Married="Married";
$scope.es.lg.Wealth="Wealth";

$scope.es.lg.Kundali="Kundali";
$scope.es.lg.Ras="Ras";
$scope.es.lg.Nakshatra="Nakshatra";
$scope.es.lg.Nadi="Nadi";
$scope.es.lg.Charan="Charan";
$scope.es.lg.Gan="Gan";
$scope.es.lg.Gotra="Gotra";
$scope.es.lg.Devak="Devak";

$scope.es.lg.CheckAll="Check All";
$scope.es.lg.UncheckAll="Uncheck All";
$scope.es.lg.Done="Done";

$scope.es.lg.PleaseConfirm="Please confirm if details mentioned above are correct";
$scope.es.lg.MandatoryInputs="Mandatory inputs are marked with Red ribbon. Please fill up those";
$scope.es.lg.OnlyAccepted="Only accepted connections can see each other's contact details";
$scope.es.lg.ProfileToOthers="This is how your profile would look like to others";

$scope.es.lg.Select="Please Select";
$scope.es.lg.PleaseSelect="Please Select";

$scope.es.lg.MaximumDegree="Maximum Degree";

$scope.es.lg.Contact="Contact";
$scope.es.lg.ContactDetails="Contact Details";
$scope.es.lg.Secure="Secure";
$scope.es.lg.OtherCell="Alternate";
$scope.es.lg.LoginCredentials="Login Credentials";
$scope.es.lg.UserName="User Name";
$scope.es.lg.Password="Password";


$scope.es.lg.CountryCode="Country Code";

$scope.es.lg.Register="Register";
$scope.es.lg.SignIn="Sign in";
$scope.es.lg.Search="Search Profiles";

$scope.es.lg.MembershipValidTill="Membership End Date";
$scope.es.lg.Membership="Membership";

$scope.es.lg.MembershipText="You can extend your membership by one year by paying just 1000 Rs./. This is entirely refundable amount. If you do not get any acceptance, your entire amount will be refunded in 30 working days.";

$scope.es.lg.Amount="Amount";
$scope.es.lg.PaymentDetails="Payment Details";
$scope.es.lg.ReflectPayment="It may take few hours to reflect your latest payment";

$scope.es.lg.Proceed="Proceed";
$scope.es.lg.Feedback="Feedback";
$scope.es.lg.FeedbackText="What you feel is important to us. If you have any <B>suggestions, queries, feedback, complaints</B> please drop us an email at below mentioned address"

$scope.es.lg.RefundRequest="Refund Request";
$scope.es.lg.RefundRequestText1="If none of your connection requests are accepted and your account is at least 3 months old, you can initiate refund request.";
$scope.es.lg.RefundRequestText2="On initiation of Refund Request <B>all the amount you paid so far will be refunded to the same payment source / bank account within maximum of 30 calendar days</B>, and your account will be deactivated. To initiate refund, please email us your profile id and bank account details.";


$scope.es.lg.Refer="Refer and Earn 200/- Rs. for each referral";
$scope.es.lg.ReferText="If you know someone eligible for marriage, you can send them your 'Referral Code'. When they register and mention your referral code and make payment, you earn 200/- Rs. in your bank account. So start earning now. Feel up below form to get your referral code.";
$scope.es.lg.ReferButton="Get Referral Code";
$scope.es.lg.BankAccount="Bank Account";
$scope.es.lg.IFSCCode="IFSC Code";
$scope.es.lg.EnterDetails="Please Enter Details To Proceed";

$scope.es.lg.ReferStatus="Payment Status Check";
$scope.es.lg.ReferStatusText="You can check status of your payments here";
$scope.es.lg.VerificationCodeButton="Get Verification Code";
$scope.es.lg.VerificationCode="Verification Code";
$scope.es.lg.Verification="Verification";

$scope.es.lg.QuickLinks="Search by Type of Request";
$scope.es.lg.From="From";
$scope.es.lg.To="To";

$scope.es.lg.RangeFrom="Minimum";
$scope.es.lg.RangeTo="Maximum";

$scope.es.lg.Manglik="Manglik";

$scope.es.lg.OneTime="One Time";
$scope.es.lg.SetAsExpectation="Set as Expectations";
$scope.es.lg.ProfileId="Profile Id";
$scope.es.lg.ProfileIdAndProfilePic="Your Profile Id and Profile Pic would appear Here...!";
$scope.es.lg.UpdateProfile="Update Profile";

$scope.es.AlbumPics="Album Pics";
$scope.es.lg.Residence="Residence";
$scope.es.lg.NativePlace="Native";

$scope.es.lg.UpdateExpectations="This will update your expectations, Continue?";
$scope.es.lg.removeFromWall="This Profile will be permanently removed from your Wall, Continue?"
$scope.es.lg.shortlistProfile="This Profile will be added to your Shortlisted Profile, Continue?";
$scope.es.lg.sendConnectionRequest="This Profile will be sent connection request, Continue?";
$scope.es.lg.undoRemoveFromWall="This Profile will be added back to your Wall, Continue?";
$scope.es.lg.withdrawRequest="This will withdraw the request; Continue?";
$scope.es.lg.acceptedMsg= "Both you and this profile would be able to see each others Contact Details, Continue?";
$scope.es.lg.rejectedMsg="None of you would be able to connect with each other hence on, Are you sure to continue?";


$scope.es.lg.PwdResetForm="Password Reset Form";
$scope.es.lg.ConfirmPwd="Confirm Password";
$scope.es.lg.Submit="Submit";
$scope.es.lg.Cancel="Cancel";
$scope.es.lg.Clear="Clear";
$scope.es.lg.Forgot="Forgot";
$scope.es.lg.LoginForm="Login Form";
$scope.es.lg.InvalidCredentials="Please click 'Register' if you have not registered with us before, or else please click 'Forgot' to reset your password.";
$scope.es.lg.ErrorWhileProcessing="Error while processing";
$scope.es.lg.ShowHideDetails="Show / Hide Details";
$scope.es.lg.EDetails="Please mail us below details, so that we can resolve this issue at earliest. Thank You!";
$scope.es.lg.Close="Close";
$scope.es.lg.Information="Information";
$scope.es.lg.Confirmation="Confirmation";
$scope.es.lg.AdvancedSearch="Advanced Search / Manage Expectations";
$scope.es.lg.SearchById="Search By Id";
$scope.es.lg.MarkRead ="Mark Read";
$scope.es.lg.ViewAction="Action";
$scope.es.lg.View="View";
$scope.es.lg.ReadOn="Read On";
$scope.es.lg.ReceivedOn="Received On";
$scope.es.lg.ThisInactiveProfile="This profile is currently inactive.";
$scope.es.lg.Album="Album";
$scope.es.lg.ManageAlbum="Manage Album";
$scope.es.lg.BrowseImage="Select Image";
$scope.es.lg.UploadAs="Upload As";
$scope.es.lg.ProfilePic="Profile Pic";
$scope.es.lg.Image="Image";
$scope.es.lg.ViewUploads="View Uploads";
$scope.es.lg.ViewAlbum="View Album";

$scope.es.lg.Registration="Congratulations! Your account is created successfully!";
$scope.es.lg.ThanksRegards="Thanks and Regards";
$scope.es.lg.SendingVerificationCode="Sending SMS...";
$scope.es.lg.Documents="Documents";
$scope.es.lg.MoreOnProfessionDetails="Designation etc.";
$scope.es.lg.DesignationEg="Student / Owner / Senior Manager";

$scope.es.lg.Donate="Donate Us?";
$scope.es.lg.Connect="Connect";
$scope.es.lg.Accept="Accept";
$scope.es.lg.Reject="Reject";
$scope.es.lg.Withdraw="Withdraw";
$scope.es.lg.Shortlist="Shortlist";
$scope.es.lg.Recharge="Recharge";
$scope.es.lg.ViewContact="View Contact";
$scope.es.lg.Remove="Remove";
$scope.es.lg.UndoRemove="Undo Remove";
$scope.es.lg.WithdrawTitle="This will withdraw the request and refund the money back to your wallet.";
$scope.es.lg.ShowContactDetail="Show the Contact Details!";
$scope.es.lg.AcceptTitle="Click here to Accept the request, Once Accepted, You both can see each others Contact Details!";
$scope.es.lg.ConnectTitle="Send Connection Request, if accepted, you can see each other's contact details!";
$scope.es.lg.UndoRemoveTitle="Undo-Remove from My Wall, I accidently removed this profile!";
$scope.es.lg.RemoveTitle="Remove from My Wall, I do not want to see this profile again!";
$scope.es.lg.ShortlistTitle="Click here to Shortlist the request!";
$scope.es.lg.URL="URL";
$scope.es.lg.Update="Update";
$scope.es.lg.More="More";

$scope.es.lg.ProfilesList="Profiles List";

$scope.es.lg.WallFeed="Profiles List";
$scope.es.lg.Received="Requests Received";
$scope.es.lg.Expected="Top Recommendations";
$scope.es.lg.Sent="Requests Sent";
$scope.es.lg.Withdrawn="Requests Withdrawn";
$scope.es.lg.Shortlisted="Shortlisted Profiles";
$scope.es.lg.BuyContacts="Contacts Book";
$scope.es.lg.Removed="Removed Profiles";
$scope.es.lg.Accepted="Accepted Profiles";
$scope.es.lg.Rejected="Rejected Profiles";

$scope.es.lg.Me="Me";
$scope.es.lg.Them="Them";
$scope.es.lg.Either="Either";

$scope.es.lg.ByMe="By You";
$scope.es.lg.ByThem="By Them";
$scope.es.lg.ByEither="By Either";
$scope.es.lg.Requests="Requested Profiles";


$scope.es.lg.AcceptedMe="Profiles accepted by you";
$scope.es.lg.AcceptedThem="Profiles accepted by them";
$scope.es.lg.AcceptedEither="All accepted profiles";

$scope.es.lg.RejectedMe="Profiles rejected by you";
$scope.es.lg.RejectedThem="Profiles rejected by them";
$scope.es.lg.RejectedEither="All rejected profiles";

$scope.es.lg.ExpectedHeader="Top Profiles As Per Your Expectation";
$scope.es.lg.ShortlistedHeader="Shortlisted Profiles";
$scope.es.lg.BuyContactsHeader="Contact Book";
$scope.es.lg.ReceivedHeader="Requests Received";
$scope.es.lg.SentHeader="Requests Sent";
$scope.es.lg.WithdrawnHeader="Requests Withdrawn";
$scope.es.lg.RemovedHeader="Requests Removed";
$scope.es.lg.AcceptedHeaderByEither="Accepted Profiles";
$scope.es.lg.RejectedHeaderByEither="Rejected Profiles";
$scope.es.lg.AcceptedHeaderByMe="Accepted Profiles (By You)";
$scope.es.lg.RejectedHeaderByMe="Rejected Profiles (By You)";
$scope.es.lg.AcceptedHeaderByThem="Accepted Profiles (By Them)";
$scope.es.lg.RejectedHeaderByThem="Rejected Profiles (By Them)";
$scope.es.lg.NoMoreProfiles="Oops! No Profiles Found..";
$scope.es.lg.Donation="Donation";
$scope.es.lg.DonationText1="We do not sell your data to anyone, your donation is the only source of income we do have. So if you like the service, please think of donating to us.";
$scope.es.lg.DonationText2="We will be obliged. We assure you the best service in return. Thank You!";

$scope.es.lg.ActionedOn="Actioned On";
$scope.es.lg.NotActioned="Not Actioned Yet";
$scope.es.lg.Logout="Logout";
$scope.es.lg.Notifications="Notifications";
$scope.es.lg.IdentityDocument="Identity Document";

$scope.es.lg.CurrentResidence="Current Residence";
$scope.es.lg.FamilyResidence="Family Residence";
$scope.es.lg.LoginToSearch="Register for free to search";
$scope.es.lg.MultiselectFilter="Type to filter";


$scope.es.lg.RememberMe="Remember Me";
$scope.es.lg.RefundButton="Refund money and delete the profile";
$scope.es.lg.OneTimeSearchText="Click here for one time search";
$scope.es.lg.ExpectationSearchText="Click here if these are your expectations";
$scope.es.lg.OneTimeSearchWallHeader="Profiles as per one time search";
$scope.es.lg.aboutPlaceholder="Optional - About You";
$scope.es.lg.EverythingOptional="These are all optional expectations. Keep fields empty if you are okay with any value"
$scope.es.lg.UpdateLong="Update Details";
$scope.es.lg.AlbumLong="Upload Images";
$scope.es.lg.ManageAccount="Manage Account";
$scope.es.lg.DocumentsLong="Upload Document";
$scope.es.lg.EducationJob="Education / Job";
$scope.es.lg.BrowseImageAndUpload="You can upload image in 2 simple steps";
$scope.es.lg.ImageUploadStep1="Please select the image to upload.";
$scope.es.lg.ImageUploadStep2="Please verify the image and click on upload.";

$scope.es.lg.FillCodeHere="Fill code here";
$scope.es.lg.RegisterInstructions="Please fill up below information and request for code";
$scope.es.lg.ClickHereTypingSupport="To help with typing in reginal languages, click here";
$scope.es.lg.Expectations="Expectations";
$scope.es.lg.expectationsPlaceholder="Expectations in brief";
$scope.es.lg.digitPassword="Password should have at least 4 characters";
$scope.es.lg.pwdConfPwdNoMatch="Password and Password (confirm) do not match";
$scope.es.lg.Yes="Yes";
$scope.es.lg.No="No";
$scope.es.lg.NextProfiles="Click here to view next profiles";
$scope.es.lg.MarketingHeader="How did you get to know about us?";
$scope.es.lg.MarketingLabel="Reference";
$scope.es.lg.MarketingOtherLabel="Details";

// Verified on 21st July

$scope.es.lg.UploadPicToViewPic1="You need to upload";
$scope.es.lg.UploadPicToViewPic2="your profile image";
$scope.es.lg.UploadPicToViewPic3="to view other's image";
$scope.es.lg.ClickHere="Please click here to upload";

$scope.es.lg.UploadImage="Upload Image";
$scope.es.lg.InWords="In words";
$scope.es.lg.ClickHereToSetExpectations="Profiles not as per your expectations? Click here to change the expectations.";
$scope.es.lg.Minimum="Minimum";
$scope.es.lg.RegisterToSeeMoreProfiles="Register free today to view all profiles";
$scope.es.lg.ProfileWithPicOnly="Profiles with photo only";

$scope.es.lg.Previous="Previous";
$scope.es.lg.Next="Next";

$scope.es.lg.AndroidApp="Android App is here";
$scope.es.lg.AndoridAppDownload="Please rate us on Google Play and support us! Thank You!";

$scope.es.lg.RegisterNow="Register Now For Free!";

$scope.es.lg.SureToDeleteMyProfile="Are you sure to delete your profile ?";
$scope.es.lg.DeleteMyProfile="Delete My Profile";

$scope.es.lg.lastLogin="Last Login On";
$scope.es.lg.ActWithin45Days="Please accept or reject the request within 45 days. Thank You!";

$scope.es.lg.ExpectationsLine="Please let us know what kind of profiles you are interested in.";
$scope.es.lg.ExpectationsLineButton="Click here to set the expectations";

$scope.es.lg.PENDING="Pending";
$scope.es.lg.REGISTER="Register";
$scope.es.lg.MEMBERSHIP="Membership";
$scope.es.lg.FEATURES="Features";
$scope.es.lg.CONTACTS="Contact Us";

$scope.es.lg.SaveAsDraft="Save as draft";
$scope.es.lg.Welcome="Welcome";
$scope.es.lg.DownloadApp="Download App";
$scope.es.lg.RateUs="Please Rate Us";
$scope.es.lg.Support="Support";

$scope.es.lg.AnyIssues="Facing Issues? Click here."
$scope.es.lg.RegisterToday="Complete your registration today and get premium membership absolutely free for first month. Offer valid for limited profiles only. Hurry!";

$scope.es.lg.searchPartnerWithUs = "Find your soulmate with lagnsthal.com";
$scope.es.lg.tagLine1 = "India's #1 affordable and Secure Matrimony!";
$scope.es.lg.tagLine2 = "Get free month of premium";
$scope.es.lg.tagLine3 = "Click here to Register";


$scope.es.lg.wishes1="Happy New Year 2021!";

};



