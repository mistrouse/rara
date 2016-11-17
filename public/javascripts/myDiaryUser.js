angular.module('AWIAPP', ['ngCookies', 'smart-table'])
.controller('myDiaryUser', function($scope, $http, $window, $cookies, $cookieStore) {

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
        });
    }
    // If there is no cookie on the client side --> not connected so redirect to '/'
    else {
        $window.location.href = '/';
    }

    // Hide the success at the beginning
    $scope.hideSuccess = true;
    // Hide the form to update the diary at the beginning
    $scope.isFormUpdateShow = false;

    // Get all diary of the user account
    $scope.getAllDiaryForUser= function() {
        var rqt = {
                method: 'GET',
                url : '/person/' + id_person + '/diaries',
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.allDiary = data;
        });
    }
    // Show the form with the prefilled value of the diary
    $scope.showUpdateFormDiary = function(diary) {
        $scope.isFormUpdateShow = true;
        $scope.diary = diary;
        $scope.diary = diary;
        $scope.titleToUpdate = diary["title"];
        $scope.descriptionToUpdate = diary["description"];

    }

    // Delete the diary in the database, reload the data in the table and show a message success
    $scope.deleteDiary = function(diary) {
        var rqt = {
                method : 'DELETE',
                url : '/diary/' + diary.id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllDiaryForUser();
            $scope.hideSuccess = false;
            $scope.titleSuccess = data;
        });
    }

    // Hide the form for update a diary if the user doesn't want...
    $scope.cancelFormUpdate = function() {$scope.isFormUpdateShow = false;}

    $scope.updateDiary = function(titleToUpdate, descriptionToUpdate) {
        var rqt = {
            method : 'PUT',
            url : '/diary/' + $scope.diary["id"],
            data : $.param({newTitle: titleToUpdate, newDescription: descriptionToUpdate}),
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            $scope.getAllDiaryForUser();
            $scope.isFormUpdateShow = false;
            $scope.hideSuccess = false;
            $scope.titleSuccess = "The diary has been updated";
        });
    }
});