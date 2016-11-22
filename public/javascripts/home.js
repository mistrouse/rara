angular.module('AWIAPP', ['ngCookies'])
.controller('home', function($scope, $http, $window, $cookies, $cookieStore) {

    // Check if the person is connected or not
    // If not, redirect to '/'
    // If yes, displayed information based on the type of account
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
           if(person["role"] == 0) {
               $scope.typeAccount = "Simple User";
               $scope.isSimpleUser = "isSimpleUser";
           }
           else if(person["role"] == 1) {
               $scope.typeAccount = "Simple Seller";
               $scope.isSeller = "isSeller";
           }
           else {
               $scope.typeAccount = "Admin";
               $scope.isAdmin = "isAdmin";
           }
           $scope.pseudo = person["pseudo"];
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
       /*if(person["role"] == 0) {
           $window.location.href = '/SU/myaccount';
       }
       else if(person["role"] == 1) {
           $window.location.href = '/SC/myaccount';
       }
       else {
           $window.location.href = '/Admin/myaccount';
       }*/
       $window.location.href = '/myaccount.html';
    }

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


    // If an admin would manage account, is redirect to '/Admin/manage/account'
    $scope.manageAccount = function() {
        //$window.location.href='/Admin/manage/account';
        $window.location.href = '/manageAccountAdmin.html';
    }

    // If an admin would manage product, is redirect to '/Admin/manage/product'
    $scope.manageProduct = function() {
        //$window.location.href='/Admin/manage/product';
        $window.location.href = '/manageProductAdmin.html';
    }

    // If the SU or SC would to search a product
    $scope.searchProduct =  function() {
       /*if(person["role"] == 0) {
           $window.location.href = '/SU/product/search';
       }
       else if(person["role"] == 1) {
           $window.location.href = '/SC/product/search';
       }*/
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
          // If a SU wants to show his comment
          $scope.myComment = function(){
          $window.location.href = '/myCommentUser.html';
          }

          // If a SU wants to create a comment
          $scope.createComment = function(){
          window.location.href='/createCommentUser.html';
          }

});