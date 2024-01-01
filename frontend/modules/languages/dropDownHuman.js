
var initDropDownsHuman = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.genderList =  {
"Female":"Bride / Female"
,"Male":"Groom / Male"
};

$scope.es.shareContactsWithList =  {
 "ANYONE":"ANYONE - Any registered can view my contact details"
,"EXPECTED":"EXPECTED - Only members which satisfy my expectations can view my contact details"
,"ACCEPTED":"ACCEPTED - Only members whose request I accept can view my contact details"
};

$scope.es.allowRequestFromList =  {
 "ANYONE":"ANYONE - Any registered member can send me a request"
,"EXPECTED":"EXPECTED - Only members which satisfy my expectations can send me a request"
};

$scope.es.bodyTypeList = {
 "Slim":"Slim"
,"Athletic":"Athletic"
,"Average":"Average"
,"Healthy":"Healthy"
,"Heavy":"Heavy"
};

$scope.es.skinColorList = {
 "Very_Fair":"Very Fair"
,"Fair":"Fair"
,"Brown":"Brown"
,"Dark_Brown":"Dark Brown"
,"Black":"Black"
};

$scope.es.maritalStatusList = {
"Never_Married":"Never Married"
,"Awaiting_Divorce":"Awaiting Divorce"
,"Divorced":"Divorced"
,"Widow":"Widow"
,"Other":"Other"
};

$scope.es.physicalStatusList = {
"Not_Challenged":"Not Challenged"
,"Physically_Challenged":"Physically Challenged"
,"Mentally_Challenged":"Mentally Challenged"
,"Physically_Mentally Challenged":"Physically Mentally Challenged"
};

$scope.es.manglikStatusList = {
"No":"No"
,"Souwmya":"Souwmya"
,"Yes":"Yes"
};

$scope.es.kundaliNadiList = {
"Adya":"Adya"
,"Madhya":"Madhya"
,"Antya":"Antya"
};

$scope.es.kundaliGanList =  {
"Dev":"Dev"
,"Manushya":"Manushya"
,"Rakshas":"Rakshas"
};


$scope.es.kundaliCharanList = {
"01":"01","02":"02","03":"03","04":"04"
};

$scope.es.siblingsList = {
"00":"00",
"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06"
};


$scope.es.bloodGroupList = {
"1": "Don't Know"
,"A+":"A+"
,"A-":"A-"
,"AB+":"AB+"
,"AB-":"AB-"
,"B+":"B+"
,"B-":"B-"
,"OP":"O+"
,"O-":"O-"
};

$scope.es.heightCompleteList = {

"301":"03'01''" ,
"302":"03'02''" ,
"303":"03'03''" ,
"304":"03'04''" ,
"305":"03'05''" ,
"306":"03'06''" ,
"307":"03'07''" ,
"308":"03'08''" ,
"309":"03'09''" ,
"310":"03'10''" ,
"311":"03'11''" ,

"400":"04'00''" ,
"401":"04'01''" ,
"402":"04'02''" ,
"403":"04'03''" ,
"404":"04'04''" ,
"405":"04'05''" ,
"406":"04'06''" ,
"407":"04'07''" ,
"408":"04'08''" ,
"409":"04'09''" ,
"410":"04'10''" ,
"411":"04'11''" ,

"500":"05'00''" ,
"501":"05'01''" ,
"502":"05'02''" ,
"503":"05'03''" ,
"504":"05'04''" ,
"505":"05'05''" ,
"506":"05'06''" ,
"507":"05'07''" ,
"508":"05'08''" ,
"509":"05'09''" ,
"510":"05'10''" ,
"511":"05'11''" ,

"600":"06'00''" ,
"601":"06'01''" ,
"602":"06'02''" ,
"603":"06'03''" ,
"604":"06'04''" ,
"605":"06'05''" ,
"606":"06'06''" ,
"607":"06'07''" ,
"608":"06'08''" ,
"609":"06'09''" ,
"610":"06'10''" ,
"611":"06'11''" ,

"700":"07'00''" ,
"701":"07'01''" ,
"702":"07'02''" ,
"703":"07'03''" ,
"704":"07'04''" ,
"705":"07'05''" ,
"706":"07'06''" ,
"707":"07'07''" ,
"708":"07'08''" ,
"709":"07'09''" ,
"710":"07'10''" ,
"711":"07'11''" ,


};

$scope.es.professionalTypeList = {
"1" : "No Business and No Job"
,"2" : "Farmer"
,"Student":"Student"
,"Accountant":"Accountant"
,"AirHostess":"AirHostess"
,"Businessman":"Businessman"
,"Defence":"Defence"
,"Doctor":"Doctor"
,"Engineer":"Engineer"
,"Finance":"Finance"
,"Government":"Government"
,"Hospitality":"Hospitality"
,"IAS":"IAS"
,"Lawyer":"Lawyer"
,"Navy":"Navy"
,"NetworkSecurity":"NetworkSecurity"
,"Nurse":"Nurse"
,"Pilot":"Pilot"
,"Police":"Police"
,"Psychologist":"Psychologist"
,"SocialServices":"SocialServices"
,"Teacher":"Teacher"
,"Other":"Other"
};

$scope.es.motherTongueList = {
 "Marathi":"Marathi"
,"Assamese":"Assamese"
,"Bengali":"Bengali"
,"Bihari":"Bihari"
,"Gujarati":"Gujarati"
,"Haryanvi":"Haryanvi"
,"Himachali":"Himachali"
,"Hindi":"Hindi"
,"Kannada":"Kannada"
,"Kashmiri":"Kashmiri"
,"Konkani":"Konkani"
,"Malayalam":"Malayalam"
,"Nepali":"Nepali"
,"Oriya":"Oriya"
,"Punjabi":"Punjabi"
,"Rajasthani":"Rajasthani"
,"Sikkim":"Sikkim"
,"Tamil":"Tamil"
,"Telugu":"Telugu"
,"Tulu":"Tulu"
,"Other":"Other"
};

$scope.es.degreeTypeList = {
"Diploma":"Diploma"
,"Graduate":"Graduate - eg. BA/BCom/BSc"
,"Engineer":"Engineer - eg. BE/BTech"
,"Engineer-PG":"Engineer-PG - eg. MTech/MCA/MS"
,"Doctor":"Doctor - eg. MBBS/BDS/BAMS/BHMS"
,"Doctor-PG":"Doctor-PG - eg. MDS/MS/MD"
,"Finance":"Finance - eg. BBA/CA"
,"Finance-PG":"Finance-PG - eg. MBA in Finance"
,"PostGraduate":"PostGraduate - eg. MBA in any field"
,"Phd":"PHD"
,"Other":"Other - eg. SSC,HSC or any other Degree"
};

$scope.es.degreeTypeMap = {
"Diploma":"Diploma"
,"Graduate":"Graduate"
,"Engineer":"Engineer"
,"Engineer-PG":"Engineer-PG"
,"Doctor":"Doctor"
,"Doctor-PG":"Doctor-PG"
,"Finance":"Finance"
,"Finance-PG":"Finance-PG"
,"PostGraduate":"PostGraduate"
,"Phd":"PHD"
,"Other":"Other"
};

$scope.es.dietList = {
"Vegetarian":"Vegetarian"
,"Non-Vegetarian":"Non-Vegetarian"
,"Jain":"Jain"
,"Eggetarian":"Eggetarian"
,"Vegan":"Vegan"
,"Other":"Other"
};


$scope.es.smokingList = {
"Yes":"Yes"
,"No":"No"
,"Occasionally":"Occasionally"
};

$scope.es.drinkingList = {
"Yes":"Yes"
,"No":"No"
,"Occasionally":"Occasionally"
};

$scope.es.marketingRefList = {
"Google":"Google"
,"Facebook":"Facebook"
,"Newspaper":"Newspaper"
,"Relative":"Relative"
,"Other":"Other"
};


};
