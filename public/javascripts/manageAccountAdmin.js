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
            else {
                $scope.typeAccount = "Admin";
                $scope.isAdmin = "isAdmin";
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
        $scope.numberAddressToUpdate = parseInt(valuePerson["numberAddress"]);
        $scope.streetAddressToUpdate = valuePerson["streetAddress"];
        $scope.postCodeAddressToUpdate = parseInt(valuePerson["postCodeAddress"]);
        $scope.cityAddressToUpdate = valuePerson["cityAddress"];
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
    $scope.createPerson = function(name, pseudo, email, numberAddress, streetAddress, postCodeAddress, cityAddress, siret, password, admin) {
        var rqt = {
            method : 'POST',
            url : '/person',
            data : $.param({name: name, pseudo : pseudo, email: email, numberAddress: numberAddress, streetAddress: streetAddress, cityAddress: cityAddress, postCodeAddress: postCodeAddress, siret: siret, password: password, admin: admin}),
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
   $scope.updatePerson = function(name, pseudo, siret, numberAddress, streetAddress, postCodeAddress, cityAddress, password){
        var rqt = {
            method : 'PUT',
            url : '/person/' + $scope.person["id"],
            data : $.param({newName: name, newPseudo: pseudo, newNumberAddress: numberAddress, newStreetAddress: streetAddress, newPostCodeAddress: postCodeAddress, newCityAddress: cityAddress, newSiret: siret, newPassword: password}),
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

    // If the user would see his information account is redirect to '/typeOfAccount/myaccount'
    $scope.myAccount = function() {
       $window.location.href = '/myaccount.html';
    }

    // If the Seller would to show his product
    $scope.myProduct = function(){
        $window.location.href = '/myProductSeller.html';
    }

    // If the Seller would to create a product
    $scope.createProduct = function() {
        $window.location.href = '/createProductSeller.html';
    }


    // If an admin would manage account, is redirect to '/Admin/manage/account'
    $scope.manageAccount = function() {
        $window.location.href = '/manageAccountAdmin.html';
    }

    // If an admin would manage product, is redirect to '/Admin/manage/product'
    $scope.manageProduct = function() {
        $window.location.href = '/manageProductAdmin.html';
    }

    // If the SU or SC would to search a product
    $scope.searchProduct =  function() {
       $window.location.href = '/searchAndBasketProduct.html';
    }

     // If the USer would to create a diary
     $scope.createDiary = function() {
         $window.location.href = '/createDiaryUser.html';
     }
     // If the User would to show his diary
     $scope.myDiary = function(){
         $window.location.href = '/myDiaryUser.html';
     }

     $scope.myBasket = function() {
        $window.location.href = '/myBasketUser.html'
     }

     // If a SU wants to show his objectives
           $scope.myObjective = function(){
           $window.location.href = '/myObjectivesSU.html';
           }

           // If a SU wants to create an objective
           $scope.createObjective = function(){
           window.location.href='/createObjectiveSU.html';
           }
              // If a SU wants to show his comments
                $scope.myComment = function(){
                $window.location.href = '/myCommentUser.html';
                }

                // If a SU wants to create a comment
                $scope.createComment = function(){
                window.location.href='/createCommentUser.html';
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
});