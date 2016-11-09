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
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });

    }
});