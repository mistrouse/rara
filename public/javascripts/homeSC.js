angular.module('AWIAPP', ['ngCookies'])
.controller('homeSC', function($scope, $http, $window, $cookies, $cookieStore) {

    // Check if the person is connected or not and redirect or not in consequence
    var id_person = $cookies.get('id');
    var token_person = $cookies.get('token');
    if(!angular.isUndefined(id_person) && !angular.isUndefined(token_person)){
        var rqt = {
            method : 'GET',
            url : '/isConnected/' + id_person + '/' + token_person,
            data : $.param({id: id_person, token: token_person}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data) {
           person = data;
           // If the user is connected but not an SU, redirect to '/' else normal
           if(person["role"] != 1) {
               $window.location.href = '/';
           }
           // Else the user is connected and is an SU, normal
           else {
               $scope.typeAccount = "Simple Seller";
               $scope.pseudo = person["pseudo"];
           }
        })
        // If the session is expired <=> id or token cookies on the client is different from the database, redirect to '/'
        .error(function(data){
            $window.location.href = '/';
        });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // When the user would to log out and it is redirect to '/'
    $scope.logout = function() {
        var rqt = {
                method : 'POST',
                url : '/logout',
                data : $.param({id: id_person, token: token_person}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $window.location.href = '/';
        });
    };

    // If the user would see his information account is redirect to '/myaccount'
    $scope.myaccount = function() {
        $window.location.href = '/SC/myaccount';
    }
});