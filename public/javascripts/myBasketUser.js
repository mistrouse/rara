angular.module('AWIAPP', ['ngCookies'])
.controller('myBasketUser', function($scope, $http, $window, $cookies, $cookieStore) {

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

    // Hide the success at the beginning
    $scope.hideSuccess = true;
    // Hide the error at the beginning
    $scope.hideError = true;

    // Get all product of the seller account
    $scope.getAllProductInBasket = function() {
        var rqt = {
                method: 'GET',
                url: '/person/'+id_person+'/baskets',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allProductInBasket = data;
            $scope.sumPrice = 0;
            // Calcul total amount
            for(var i = 0; i < data.length; i++) {
                $scope.sumPrice += data[i].price * data[i].quantity;
            }
            console.log(data);
        });
    }

    // Update the quantity for a product in the basket
    $scope.showUpdateFormProductInBasket = function(newQuantity, productBasket) {
        var rqt = {
            method : 'PUT',
            url : '/person/' + id_person + '/basket/' + productBasket.idLine,
            data : $.param({newQuantity: newQuantity, idProduct: productBasket.idProduct}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideError = true;
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
            $scope.getAllProductInBasket();
        }).error(function(data){
          $scope.hideSuccess = true;
          $scope.hideError = false;
          $scope.titleError = data;
      });
    }

    // Delete the product in the basket of the SU
    $scope.deleteProductInBasket = function(productBasket) {
        var rqt = {
            method : 'DELETE',
            url : '/person/' + id_person + '/line/' + productBasket.idLine + '/basket/'+productBasket.idBasket,
            data : $.param({idProduct: productBasket.idProduct}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.hideError = true;
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
            $scope.getAllProductInBasket();
        }).error(function(data){
          $scope.hideSuccess = true;
          $scope.hideError = false;
          $scope.titleError = data;
      });
    }
});