angular.module('AWIAPP', ['ngCookies'])
.controller('createProductSeller', function($scope, $http, $window, $cookies, $cookieStore) {

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
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    $scope.hideSuccess = true;

    $scope.createProduct = function(name, description, price, quantity) {
        var rqt = {
                method : 'POST',
                url : '/product',
                data : $.param({name: name, description : description, price: price, quantity: quantity, id: id_person}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
        console.log(data);
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });

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