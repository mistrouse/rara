angular.module('AWIAPP', ['ngCookies', 'smart-table'])
.controller('manageProductAdmin', function($scope, $http, $window, $cookies, $cookieStore) {

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

    // Show the different choice for an Admin when he arrived
    $scope.isChoiceShow = true;
    // Hide the table of all product to update
    $scope.isTableShow = false;
    // Hide the form for create a product
    $scope.isFormCreateProductShow = false;
    // Hide the form to update a product when he click on the table
    $scope.isFormUpdateShow = false;
    // Hide the error message at the beginning
    $scope.hideError = true;
    // Hide the success message ath the beginning
    $scope.hideSuccess = true;
    // ID of the seller when the admin create a product
    var id_seller;

    $scope.product;

    // Hide the error and success message on the page
    $scope.hideErrorOrSuccessMessage = function() {
        if($scope.hideError == false) {
            $scope.hideError = true;
        }
        if($scope.hideSuccess == false) {
            $scope.hideSuccess = true;
        }
    }

    //Get all the product to fill the table for update the product
    $scope.getAllProduct = function() {
        var rqt = {
                method: 'GET',
                url : '/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allProduct = data;
        });
    };

    // Hide the different choice and show the table with the product in the database
    $scope.showUpdateTable = function() {
        $scope.isChoiceShow = false;
        $scope.isTableShow = true;
        $scope.hideErrorOrSuccessMessage();
        $scope.getAllProduct();
    };

    // Show the update form and initialize it for the product when the admin click on the update button
    $scope.showUpdateFormProduct = function(valueProduct) {
        $scope.isFormUpdateShow = true;
        $scope.product = valueProduct;
        $scope.nameToUpdate = valueProduct["name"];
        $scope.descriptionToUpdate = valueProduct["description"];
        $scope.priceToUpdate = parseInt(valueProduct["price"]);
        $scope.quantityToUpdate = valueProduct["quantity"];
    };

    // Delete automatically the product when the admin click on the delete button and refresh the table
    $scope.deleteProduct = function(person) {
        var rqt = {
                method : 'DELETE',
                url : '/product/' + person.id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllProduct();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    };

    // Hide the choice and show the form to create a product
    $scope.showFormCreateProduct = function() {
        $scope.isChoiceShow = false;
        $scope.isFormCreateProductShow = true;
        $scope.getAllSeller();
        $scope.hideErrorOrSuccessMessage();
    };

    // Create a product with the value of the form
    $scope.createProduct = function(name, description, price, quantity) {
        console.log(id_seller);
        var rqt = {
                method : 'POST',
                url : '/product',
                data : $.param({name: name, description : description, price: price, quantity: quantity, id: id_seller}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.isChoiceShow = true;
            $scope.isFormCreateProductShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product is created";
            $scope.hideError = true;
        }).error(function(data){
            $scope.hideSuccess = true;
            $scope.hideError = false;
            $scope.titleError = data;
        });
    };

    // Cancel the creation of product and show the different choice for an admin
    $scope.cancelCreateAccount = function() {
        $scope.isChoiceShow = true;
        $scope.isFormCreateProductShow = false;
   }

    // Update the product with the value of the form, reload the table with the new information and hide the form for update
   $scope.updateProduct = function(nameToUpdate, descriptionToUpdate, priceToUpdate, quantityToUpdate){
        var rqt = {
            method : 'PUT',
            url : '/product/' + $scope.product["id"],
            data : $.param({newName: nameToUpdate, newDescription: descriptionToUpdate, newPrice: priceToUpdate, newQuantity: quantityToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllProduct();
            $scope.isFormUpdateShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The product has been updated";
        });
   }

   // Get all seller who will fill the combo
   $scope.getAllSeller = function() {
       var rqt = {
           method: 'GET',
           url : '/persons/seller',
           headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
       };
       $http(rqt).success(function(data){
           $scope.allSeller = data;
       });
   }

   // When the user select a company, we retrieve the company and get all products from his company
   $scope.hasChanged = function(seller) {
       if(seller.name != null) {
           id_seller = seller.name.id;
       }
   }

    // Hide the form for update a person if the admin doesn't want...
   $scope.cancelFormUpdate = function() {$scope.isFormUpdateShow = false;}

});