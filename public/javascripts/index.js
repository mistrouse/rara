angular.module('AWIAPP', ['ngCookies'])
.controller('index', function($scope, $http, $window, $cookies, $cookieStore) {

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
            /*if(data["role"] == 0) {
                $window.location.href = '/SU/home';
            }
            // If the person is a SC, redirect to /homeSC
            else if(data["role"] == 1) {
                $window.location.href = '/SC/home';
            }
            // If the person is an Admin, redirect to /homeAdmin
            else {
                $window.location.href = '/Admin/home';
            }*/
            $window.location.href = '/home.html';
        });
    }

    $scope.login = function() {
        //$window.location.href = '/login';
        $window.location.href = '/login.html';
    }

    $scope.createPerson = function() {
        //$window.location.href = '/createPerson';
        $window.location.href = '/createPerson.html';
    }

});