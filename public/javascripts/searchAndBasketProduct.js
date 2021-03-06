angular.module('AWIAPP', ['ngCookies'])
.controller('searchProduct', function($scope, $http, $window, $cookies, $cookieStore) {

    // Test if the person can stay on the page
    var id_person = $cookies.get('id');
    var token_person = $cookies.get('token');
    $scope.showBasket = false;
    if(!angular.isUndefined(id_person) && !angular.isUndefined(token_person)){
        var rqt = {
            method : 'GET',
            url : '/isConnected/' + id_person + '/' + token_person,
            data : $.param({id: id_person, token: token_person}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data) {
           person = data;
           if(data.role == 0) {
                $scope.showBasket = true;
                $scope.typeAccount = "Simple User";
                $scope.isSimpleUser = "isSimpleUser";
                $scope.pseudo = data["pseudo"];
           }
           else if(data.role == 1){
                $scope.typeAccount = "Simple Seller";
                $scope.isSeller = "isSeller";
                $scope.pseudo = data["pseudo"];
           }
        })
        $http(rqt).error(function(data){
            $window.location.href = '/';
        })
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // Hide the error message if the seller has no product in his shop
    $scope.hideErrorNoProduct = true;
    // Hide the error message if the user want too much product
    $scope.hideErrorNotEnoughProduct = true;
    // Hide the success message if the user add product in his basket
    $scope.hideSuccessAddBasketProduct = true;
    // Hide the information of a product
    $scope.showProductInformation = false;
    var valueProductSelected;

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

    // Get all product if the user would search with no company
    $scope.getAllProduct = function() {
        var rqt = {
                method: 'GET',
                url : '/products',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            // Parse and delete the products if they are no quantity available
            for(var i = 0; i < data.length; i++) {
                if(data[i].quantity == 0) {
                    data.splice(data[i].id, 1);
                }
            }
            $scope.searchBar(data);
        });
    }

    // When the user select a company, we retrieve the company and get all products from his company
    $scope.hasChanged = function(seller) {
        // Empty the search bar when change company
        $scope.searchProduct = null;
        // Hide the information of a product
        $scope.showProductInformation = false;
        if(seller.name == null) {
            $scope.getAllProduct();
        }
        else {
            // Parse and verify if there is no quantity for a product (remove if the quantity is equal to zero)
            for(var i = 0; i < seller.name.productSell.length; i++) {
                if(seller.name.productSell[i].quantity == 0) {
                    seller.name.productSell.splice(seller.name.productSell[i], 1);
                }
            }
            $scope.searchBar(seller.name.productSell);
        }
    }

    // Create the product search in the search bar
    $scope.searchBar= function(data) {
        // If the seller has no product in his shop, we display a error message
        if(data.length == 0) {
            $scope.hideErrorNoProduct = false;
            $scope.titleErrorNoProduct = "No product for this company..."
        }
        // Else hide the error message
        else {
            $scope.hideErrorNoProduct = true;
        }
        // Create the product
        var options = {
            // Take all product for data
            data: data,
            // Choose the data by the "name" attribut
            getValue: "name",
            // Define the view of each row find
            template: {
                type: "description",
                fields: {
                    description: "description"
                }
            },
            // When a user click on a row, there is information of the product show below
            list: {
                match: {
                    enabled: true
                },
                onClickEvent: function() {
                    //retrieve JSON to which the user clicked
                    valueProductSelected = $("#search-product").getSelectedItemData();
                    // We show the information of the product
                    $scope.$apply(function(){
                        $scope.nameProduct = valueProductSelected.name;
                        $scope.descriptionProduct = valueProductSelected.description;
                        $scope.priceProduct = valueProductSelected.price;
                        $scope.quantityProduct = valueProductSelected.quantity;
                        $scope.showProductInformation = true;
                    });
                },
                showAnimation: {
                    type: "fade",
                    time: 400,
                    callback: function() {}
                },
                hideAnimation: {
                    type: "slide",
                    time: 400,
                    callback: function() {}
                },
            }
        };
        // link the search options to the search bar in the html
        $("#search-product").easyAutocomplete(options);
    };

    // When the user would add to his basket the product
    $scope.addToBasket = function(desiredQuantity) {
        console.log(valueProductSelected);
        var id_product = valueProductSelected.id;
        var quantity_product_stock = valueProductSelected.quantity;
        // If the user want too much product, show error message
        if(desiredQuantity > quantity_product_stock) {
            $scope.hideErrorNotEnoughProduct = false;
            $scope.tittleErrorNotEnoughProduct = "You cannot buy this quantity, the seller hasn’t have the capability..."
        }
        // Else, we modified in the database and show the success to the user
        else {
            $scope.hideErrorNotEnoughProduct = true;
            var rqt = {
                method : 'PUT',
                url : '/product/' + id_product +'/addToBasket',
                data : $.param({quantityPurchased: desiredQuantity, id_buyer: id_person, idProductPurchased: valueProductSelected.id}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            // Reload the data and show success message
            $http(rqt).success(function(data){
                $scope.hideErrorNotEnoughProduct = true;
                $scope.nameProduct = data.name;
                $scope.descriptionProduct = data.description;
                $scope.priceProduct = data.price;
                $scope.quantityProduct = data.quantity;
                $scope.hideSuccessAddBasketProduct = false;
                $scope.tittleHideSuccessAddBasketProduct = "The product "+data.name+" has been add to your basket. Thank you."
            }).error(function(data){
                $scope.hideSuccessAddBasketProduct = true;
                $scope.hideErrorNotEnoughProduct = false;
                $scope.tittleErrorNotEnoughProduct = data;
            });
        }
    };

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
    $scope.searchProductt =  function() {
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

    $scope.back=function(){
        $window.location.href='/home.html';
        }

});