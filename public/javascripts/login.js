angular.module('AWIAPP', ['ngCookies'])
.controller('login', function($scope, $http, $window, $cookies, $cookieStore) {

    // Check if the person is already connected or not and redirect or not
    var id_person = $cookies.get('id');
    var token_person = $cookies.get('token');
    if(!angular.isUndefined(id_person) && !angular.isUndefined(token_person)){
        var rqt = {
            method : 'GET',
            url : '/isConnected/' + id_person + '/' + token_person,
            data : $.param({id: id_person, token: token_person}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            // If the person is a SU, redirect to /homeSU
            if(data["role"] == 0) {
                $window.location.href = '/SU/home';
            }
            // If the person is a SC, redirect to /homeSC
            else if(data["role"] == 1) {
                $window.location.href = '/SC/home';
            }
            // If the person is an Admin, redirect to /homeAdmin
            else {
                $window.location.href = '/Admin/home';
            }
        });
    }

    // Show or not the error message depending on the return from the application
    $scope.hideError = true;

    // When the user want to connect, if it success redirect to the right home, else display error message
    $scope.login = function(email, password) {
        var rqt = {
                method : 'POST',
                url : '/login',
                data : $.param({email: email, password: password}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideError = true;
            if(data["role"] == 0) {
                $window.location.href = '/SU/home';
            }
            // If the person is a SC, redirect to /homeSC
            else if(data["role"] == 1) {
                $window.location.href = '/SC/home';
            }
            // If the person is an Admin, redirect to /homeAdmin
            else {
                $window.location.href = '/Admin/home';
            }
        }).error(function(data) {
            $scope.hideError = false;
            $scope.titleError = data;
        });
    };

    // If the user doesn't want create an account and back, it redirects to the "/"
    $scope.back = function() { $window.location.href = '/'; };

});