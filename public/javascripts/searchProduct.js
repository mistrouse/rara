angular.module('AWIAPP', ['ngCookies'])
.controller('searchProduct', function($scope, $http, $window, $cookies, $cookieStore) {

    // Test if the person can stay on the page
    var typeAccount;
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
                typeAccount = "SU";
           }
           else if(person["role"] == 1) {
               typeAccount = "SC";
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
            $scope.searchBar(data);
        });
    }

    // When the user select a company, we retrieve the company and get all products from his company
    $scope.hasChanged = function(seller) {
        $scope.searchProduct = null;
        if(seller.name == null) {
            $scope.getAllProduct();
        }
        else {
            $scope.searchBar(seller.name.productSell);
        }
    }

    $scope.searchBar= function(data) {
        // Create the product search in the search bar
        // If the seller has no product in his shop, we display a error message
        if(data.length == 0) {
            $scope.hideErrorNoProduct = false;
            $scope.titleErrorNoProduct = "No product for this company..."
        }
        else {
            $scope.hideErrorNoProduct = true;
        }
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
            /*
            template: {
                type: "custom",
                method: function(value, item) {
                    return res = value + ' -- <i>' +item.description +'</i>';
                }
            },
            */
            // When a user click on a row it is redirect to the page of the product
            list: {
                match: {
                    enabled: true
                },
                onClickEvent: function() {
                    //retrieve JSON to which the user clicked
                    var value = $("#search-product").getSelectedItemData();
                    // Redirect the user to product page
                    var path = '/' + typeAccount + '/product/view/' + value.id;
                    $window.location.href = path;
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
                }
            }
        };
        // link the search options to the search bar in the html
        $("#search-product").easyAutocomplete(options);
    }
});