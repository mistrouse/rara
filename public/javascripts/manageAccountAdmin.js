angular.module('AWIAPP', ['ngCookies', 'smart-table'])
.controller('manageAccountAdmin', function($scope, $http, $window, $cookies, $cookieStore) {

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
            if(data["role"] != 2){
                $window.location.href = '/';
            }
        });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // This pattern permit to verify if the passwords are the same
    $scope.getPattern = function(){ return ($scope.password && $scope.password.replace(/([.*+?^${}()|\[\]\/\\])/g, '\\$1')); };

    // Show the different choice for an Admin when he arrived
    $scope.isChoiceShow = true;
    // Hide the table of all persons to update
    $scope.isTableShow = false;
    // Hide the form for create a person (SU/SC)
    $scope.isFormCreatePersonShow = false;
    // Hide the form to update a person when he click on the table
    $scope.isFormUpdateShow = false;
    // Hide the error message at the begining
    $scope.hideError = true;
    // Hide the success message ath the begining
    $scope.hideSuccess = true;

    $scope.person;

    // Hide the error and success message on the page
    $scope.hideErrorOrSuccessMessage = function() {
        if($scope.hideError == false) {
            $scope.hideError = true;
        }
        if($scope.hideSuccess == false) {
            $scope.hideSuccess = true;
        }
    }

    //Get all the persons to fill the table for update the user
    $scope.getAllPerson = function() {
        var rqt = {
                method: 'GET',
                url : '/persons',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            for(var i = 0; i < data.length; i++) {
                if(data[i]["role"] == 0) {
                    data[i]["role"] = "Simple user";
                }
                else if(data[i]["role"] == 1) {
                    data[i]["role"] = "Simple seller";
                }
                else {
                    data[i]["role"] = "Admin";
                }
            }
            $scope.allPerson = data;
        });
    };

    // Hide the different choice and show the table with the person in the database
    $scope.showUpdateTable = function() {
        $scope.isChoiceShow = false;
        $scope.isTableShow = true;
        $scope.hideErrorOrSuccessMessage();
        $scope.getAllPerson();
    };

    // Show the update form and initialize it for the person when the admin click on the update button
    $scope.showUpdateFormPerson = function(valuePerson) {
        $scope.isFormUpdateShow = true;
        $scope.person = valuePerson;
        // If the user to update is a SC, the form for the update show is n° SIRET, else nothing
        if(valuePerson["siret"] != null) {
            $scope.isSiretNotNull = " ";
            $scope.siretToUpdate = valuePerson["siret"];
        }
        else {
            $scope.isSiretNotNull = "";
        }
        $scope.nameToUpdate = valuePerson["name"];
        $scope.pseudoToUpdate = valuePerson["pseudo"];
        $scope.passwordToUpdate = valuePerson["password"];
    };

    // Delete automatically the user when the admin click on the delete button and refresh the table
    $scope.deletePerson = function(person) {
        var rqt = {
                method : 'DELETE',
                url : '/person/' + person.id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllPerson();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    };

    // Hide the choice and show the form to create a SU (whitout n° SIRET and Admin option)
    $scope.showFormCreateSU = function() {
        $scope.isChoiceShow = false;
        $scope.isFormCreatePersonShow = true;
        $scope.typeAccount = "Simple User";
        $scope.isAdminAccount = "";
        $scope.isNotNullSiret = "";
        $scope.hideErrorOrSuccessMessage();
    };

    // Hide the choice and show the form to create a SC (whitout Admin option)
    $scope.showFormCreateSC = function() {
        $scope.isChoiceShow = false;
        $scope.isFormCreatePersonShow = true;
        $scope.typeAccount = "Simple Seller";
        $scope.isNotNullSiret = " ";
        $scope.isAdminAccount = "";
        $scope.hideErrorOrSuccessMessage();
    };

    // Hide the choice and show the form to create a Admin (whitout n° SIRET)
    $scope.showFormCreateAdmin = function() {
        $scope.isChoiceShow = false;
        $scope.isFormCreatePersonShow = true;
        $scope.typeAccount = "Admin";
        $scope.isAdminAccount = " ";
        $scope.isNotNullSiret = "";
        $scope.hideErrorOrSuccessMessage();
    };

    // Create a person with the value of the form
    $scope.createPerson = function(name, pseudo, email, siret, password, admin) {
        var rqt = {
            method : 'POST',
            url : '/person',
            data : $.param({name: name, pseudo : pseudo, email: email, siret: siret, password: password, admin: admin}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.isChoiceShow = true;
            $scope.isFormCreatePersonShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The account is created";
            $scope.hideError = true;
        }).error(function(data){
            $scope.hideSuccess = true;
            $scope.hideError = false;
            $scope.titleError = data;
        });
    };

    // Cancel the creation of account and show the differents choice for an admin
    $scope.cancelCreateAccount = function() {
        $scope.isChoiceShow = true;
        $scope.isFormCreatePersonShow = false;
   }

    // Update the person with the value of the form, reload the table with the new information and hide the form for update
   $scope.updatePerson = function(name, pseudo, siret, password){
        var rqt = {
            method : 'PUT',
            url : '/person/' + $scope.person["id"],
            data : $.param({newName: name, newPseudo: pseudo, newSiret: siret, newPassword: password}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllPerson();
            $scope.isFormUpdateShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The person has been updated";
        });

   }

    // Hide the form for update a person if the admin doesn't want...
   $scope.cancelFormUpdate = function() {$scope.isFormUpdateShow = false;}

});