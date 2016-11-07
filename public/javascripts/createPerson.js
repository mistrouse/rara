angular.module('AWIAPP', ['ngCookies'])
.controller('createPerson', function($scope, $http, $window, $cookies, $cookieStore) {

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
    // Show the account creation opportunities
    $scope.choicePersonShow = true;
    // Show the account creation form
    $scope.signUpFormShow = false;
    // Change the name of the title according to the account
    $scope.typeAccount;
    // Display or not the input for the number siret according to the account
    $scope.isNotNullSiret;

    // Invert the presentation of Choice or Form
    $scope.reverseChoiceOrForm = function() {
        if ($scope.choicePersonShow == true) {
            $scope.choicePersonShow = false;
        }
        else {
            $scope.choicePersonShow = true;
        }
        if ($scope.signUpFormShow == false) {
            $scope.signUpFormShow = true;
        }
        else {
            $scope.signUpFormShow = false;
        }
    }

    // If the user want to create an SU account, change the name of the title, show the form and hide the choice account
    $scope.createSimpleUser = function() {
        $scope.typeAccount = "Simple User";
        $scope.isNotNullSiret = "";
        $scope.reverseChoiceOrForm();
    };

    // If the user want to create an SC account, change the name of the title, show the form and hide the choice account
    // There is the a "siret number" in addition to a SU account
    $scope.createSimpleSeller = function() {
            $scope.typeAccount = "Simple Seller";
            $scope.isNotNullSiret = "isNotNull";
            $scope.reverseChoiceOrForm();
    };

    // If the user wants to cancel the account creation, inverse the div
    $scope.cancelCreateAccount = function() {
            $scope.typeAccount = "";
            $scope.reverseChoiceOrForm();
    };

    // If the user doesn't want create an account and back, it redirects to the "/"
    $scope.back = function() { $window.location.href = '/'; };

    // CREATE (POST) a person in the database with the information enter in the form
    $scope.createPerson = function(name, pseudo, email, siret, password) {
            var admin;
            var rqt = {
                    method : 'POST',
                    url : '/person',
                    data : $.param({name: name, pseudo : pseudo, email: email, siret: siret, password: password, admin: admin}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                var rqt = {
                        method: 'GET',
                        url : '/persons',
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    $window.location.href = '/login';
                });
            }).error(function(data) {
                $scope.hideError = false;
                $scope.titleError = data;
            });
        };

    // This pattern permit to verify if the passwords are the same
    $scope.getPattern = function(){ return ($scope.password && $scope.password.replace(/([.*+?^${}()|\[\]\/\\])/g, '\\$1')); };
});