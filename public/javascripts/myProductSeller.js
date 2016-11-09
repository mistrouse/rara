angular.module('AWIAPP', ['ngCookies', 'smart-table'])
.controller('myProductSeller', function($scope, $http, $window, $cookies, $cookieStore) {

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
            if(data["role"] != 1){
                $window.location.href = '/';
            }
        });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // Hide the success at the beginning
    $scope.hideSuccess = true;
    // Hide the form to update the product at the beginning
    $scope.isFormUpdateShow = false;

    // Get all product of the seller account
    $scope.getAllProductForSeller = function() {
        var rqt = {
                method: 'GET',
                url : '/person/' + id_person + '/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allProduct = data;
        });
    }
    // Show the form with the prefilled value of the product
    $scope.showUpdateFormProduct = function(product) {
        $scope.isFormUpdateShow = true;
        $scope.product = product;
        $scope.nameToUpdate = product["name"];
        $scope.descriptionToUpdate = product["description"];
        $scope.priceToUpdate = product["price"];
        $scope.quantityToUpdate = product["quantity"];
        $scope.imageToUpdate = product["image"];
    }

    // Delete the product in the database, reload the data in the table and show a message success
    $scope.deleteProduct = function(product) {
        var rqt = {
                method : 'DELETE',
                url : '/product/' + product.id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllProductForSeller();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }

    // Hide the form for update a product if the seller doesn't want...
    $scope.cancelFormUpdate = function() {$scope.isFormUpdateShow = false;}

    $scope.updateProduct = function(nameToUpdate, descriptionToUpdate, priceToUpdate, quantityToUpdate) {
        var rqt = {
            method : 'PUT',
            url : '/product/' + $scope.product["id"],
            data : $.param({newName: nameToUpdate, newDescription: descriptionToUpdate, newPrice: priceToUpdate, newQuantity: quantityToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllProductForSeller();
            $scope.isFormUpdateShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been updated";
        });
    }
});