
var initDropDownsTime = function($scope, $http, $modal, $log, $sce, $cookies, $window){

$scope.es.dayList = {
 "01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10"
,"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20"
,"21":"21","22":"22","23":"23","24":"24","25":"25","26":"26","27":"27","28":"28","29":"29","30":"30"
,"31":"31"
};

$scope.es.monthList = {
 "01":"January","02":"February","03":"March","04":"April","05":"May","06":"June","07":"July","08":"August","09":"September","10":"October"
,"11":"November","12":"December"
};

$scope.es.yearList = {
 "2020" : "2020", "2019" : "2019", "2018" : "2018", "2017" : "2017", "2016" : "2016", "2015" : "2015", "2014" : "2014", "2013" : "2013", "2012" : "2012", "2011" : "2011"
,"2010" : "2010", "2009" : "2009", "2008" : "2008", "2007" : "2007", "2006" : "2006", "2005" : "2005", "2004" : "2004", "2003" : "2003", "2002" : "2002", "2001" : "2001"
,"2000" : "2000", "1999" : "1999", "1998" : "1998", "1997" : "1997", "1996" : "1996", "1995" : "1995", "1994" : "1994", "1993" : "1993", "1992" : "1992", "1991" : "1991"
,"1990" : "1990", "1989" : "1989", "1988" : "1988", "1987" : "1987", "1986" : "1986", "1985" : "1985", "1984" : "1984", "1983" : "1983", "1982" : "1982", "1981" : "1981"
,"1980" : "1980", "1979" : "1979", "1978" : "1978", "1977" : "1977", "1976" : "1976", "1975" : "1975", "1974" : "1974", "1973" : "1973", "1972" : "1972", "1971" : "1971"
,"1970" : "1970", "1969" : "1969", "1968" : "1968", "1967" : "1967", "1966" : "1966", "1965" : "1965", "1964" : "1964", "1963" : "1963", "1962" : "1962", "1961" : "1961"
,"1960" : "1960", "1959" : "1959", "1958" : "1958", "1957" : "1957", "1956" : "1956", "1955" : "1955", "1954" : "1954", "1953" : "1953", "1952" : "1952", "1951" : "1951"
,"1950" : "1950", "1949" : "1949", "1948" : "1948", "1947" : "1947", "1946" : "1946", "1945" : "1945", "1944" : "1944", "1943" : "1943", "1942" : "1942", "1941" : "1941"

};

$scope.es.hrList = {
"00":"00",
"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10",
"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20",
"21":"21","22":"22","23":"23"
};

$scope.es.minList = {
 "00":"00"
,"01":"01","02":"02","03":"03","04":"04","05":"05","06":"06","07":"07","08":"08","09":"09","10":"10"
,"11":"11","12":"12","13":"13","14":"14","15":"15","16":"16","17":"17","18":"18","19":"19","20":"20"
,"21":"21","22":"22","23":"23","24":"24","25":"25","26":"26","27":"27","28":"28","29":"29","30":"30"
,"31":"31","32":"32","33":"33","34":"34","35":"35","36":"36","37":"37","38":"38","39":"39","40":"40"
,"41":"41","42":"42","43":"43","44":"44","45":"45","46":"46","47":"47","48":"48","49":"49","50":"50"
,"51":"51","52":"52","53":"53","54":"54","55":"55","56":"56","57":"57","58":"58","59":"59"

};

};
