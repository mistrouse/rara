angular.module('AWIAPP', ['ngCookies'])
.controller('myaccount', function($scope, $http, $window, $cookies, $cookieStore) {

    // Test if the person can stay on the page
    var id_person = $cookies.get('id');
    var token_person = $cookies.get('token');
    if(!angular.isUndefined(id_person) && !angular.isUndefined(token_person)){
        var rqt = {
                method : 'GET',
                url : '/isConnected/' + id_person + '/' + token_person,
                data : $.param({id: id_person, token: token_person}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
        $http(rqt).error(function(data){
            $window.location.href = '/';
        })
        .success(function(data) {
            person = data;
            $scope.name = person["name"];
            $scope.pseudo = person["pseudo"];
            $scope.email = person["email"];
            if(person["role"] == 0) {
                $scope.typeAccount = "Simple User";
            }
            if(person["role"] == 1) {
                $scope.siret = person["siret"];
                $scope.typeAccount = "Simple Seller";
            }
            if(person["role"] == 2){
                $scope.typeAccount = "Admin";
            }
            $scope.password = person["password"];
            $scope.numberAddress = parseInt(person["numberAddress"]);
            $scope.streetAddress = person["streetAddress"];
            $scope.cityAddress = person["cityAddress"];
            $scope.postCodeAddress = parseInt(person["postCodeAddress"]);
        });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // This pattern permit to verify if the passwords are the same
    $scope.getPattern = function(){
        return ($scope.password && $scope.password.replace(/([.*+?^${}()|\[\]\/\\])/g, '\\$1'));
    }

    // Invert the presentation of Choice or Form
    $scope.reverseChoiceOrForm = function() {
        if ($scope.isValueShow == true) {
            $scope.isValueShow = false;
        }
        else {
            $scope.isValueShow = true;
        }
        if ($scope.isFormShow == false) {
            $scope.isFormShow = true;
        }
        else {
            $scope.isFormShow = false;
        }
    }

    // Show the information of the person
    $scope.isValueShow = true;
    // Show the form to update the information
    $scope.isFormShow = false;

    $scope.showFormUpdate = function() {
        $scope.reverseChoiceOrForm();
    }

    // Update the value of the person
    $scope.update = function(name, pseudo, numberAddress, streetAddress, postCodeAddress, cityAddress, siret, passwordv) {
        var rqt = {
            method : 'PUT',
            url : '/person/' + id_person,
            data : $.param({newName: name, newPseudo: pseudo, newNumberAddress: numberAddress, newStreetAddress: streetAddress, newPostCodeAddress: postCodeAddress, newCityAddress: cityAddress, newSiret: siret, newPassword: passwordv}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
        var rqt = {
                    method: 'GET',
                    url : '/person/' +  id_person,
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.name = person["name"];
                $scope.pseudo = person["pseudo"];
                $scope.email = person["email"];
                $scope.password = person["password"];
                $scope.numberAddress = parseInt(person["numberAddress"]);
                $scope.streetAddress = person["streetAddress"];
                $scope.cityAddress = person["cityAddress"];
                $scope.postCodeAddress = parseInt(person["postCodeAddress"]);
                if(person["role"] == 0)
                    $scope.typeAccount = "Simple User"
                if(person["role"] == 1) {
                    $scope.typeAccount = "Simple Seller"
                    $scope.siret = person["siret"];
                }
                if(person["role"] == 2)
                    $scope.typeAccount = "Admin"
                $scope.reverseChoiceOrForm();
            });
        });
    };

    // If the user want to cancel the update, the application hide the form and show the information again
    $scope.cancel = function() {
        $scope.reverseChoiceOrForm();
    }

        //fonction used by navLeft
            // If the Seller would to show his product
            $scope.myProduct = function(){
                //$window.location.href = '/SC/myProduct';
                $window.location.href = '/myProductSeller.html';
            }

            // If the Seller would to create a product
            $scope.createProduct = function() {
                //$window.location.href = '/SC/createProduct';
                $window.location.href = '/createProductSeller.html';
            }

        // If the SU or SC would to search a product
        $scope.searchProduct =  function() {

           $window.location.href = '/searchAndBasketProduct.html';
        }

      // When the user would to log out and it is redirect to '/'
        $scope.logOut = function() {
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

        // If the user would see his information account is redirect to '/typeOfAccount/myaccount'
        $scope.myAccount = function() {

           $window.location.href = '/myaccount.html';
        }
    $scope.back=function(){
    $window.location.href='/home.html';
    }


});