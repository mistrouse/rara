angular.module('AWIAPP', ['ngCookies'])
.controller('createDiaryUser', function($scope, $http, $window, $cookies, $cookieStore) {

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
        }).success(function(data) {
          if(data["role"] != 0){
              $window.location.href = '/';
          }
          else {
              $scope.typeAccount = "Simple User";
              $scope.isSimpleUser = "isSimpleUser";
              $scope.pseudo = data["pseudo"];
          }
         });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // Get all objectives who will fill the combo
        $scope.getAllObjectives = function() {
            var rqt = {
                method: 'GET',
                url : '/person/'+id_person+'/objectives',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.allObjectives = data;
            });
        }

    $scope.hideSuccess = true;

    $scope.createDiaryy = function(title, description) {
        var rqt = {
                method : 'POST',
                url : '/diary',
                data : $.param({title : title, description : description, id : id_person}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }

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

 // If a SU wants to show his comment
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