angular.module('AWIAPP', ['ngCookies', 'smart-table'])
.controller('myObjectivesSU', function($scope, $http, $window, $cookies, $cookieStore) {

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

    // Hide the success at the beginning
    $scope.hideSuccess = true;
    // Hide the form to update the objective at the beginning
    $scope.isFormUpdateShow = false;

     // Get all objective of the seller account
        $scope.getAllObjectivesForSU = function() {
            var rqt = {
                    method: 'GET',
                    url : '/person/' + id_person + '/objectives',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.allObjectives = data;
            });
        }

        // Show the form with the prefilled value of the objective
            $scope.showUpdateFormObjective = function(objective) {
                $scope.isFormUpdateShow = true;
                $scope.objective = objective;
                $scope.nameToUpdate = objective["name"];
                $scope.descriptionToUpdate = objective["description"];
            }

        // Delete the objective in the database, reload the data in the table and show a message success
            $scope.deleteObjective = function(objective) {
                var rqt = {
                        method : 'DELETE',
                        url : '/objective/' + objective.id,
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    $scope.getAllObjectivesForSU();
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = data;
                });
            }

        // Hide the form for update an objective if the SU doesn't want...
            $scope.cancelFormUpdate = function() {$scope.isFormUpdateShow = false;}

            $scope.updateObjective = function(nameToUpdate, descriptionToUpdate) {
                var rqt = {
                    method : 'PUT',
                    url : '/objective/' + $scope.objective["id"],
                    data : $.param({newName: nameToUpdate, newDescription: descriptionToUpdate}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    $scope.getAllObjectivesForSU();
                    $scope.isFormUpdateShow = false;
                    $scope.hideSuccess = false;
                    $scope.titleSuccess = "The objective has been updated";
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