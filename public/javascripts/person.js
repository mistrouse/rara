angular.module('AWIAPP', [])
.controller('testCreateAccount', function($scope, $http) {

	$scope.moreInfo = true;

	// GET all person on the database at the url /persons and put it in the allPerson variable with $scope.
	var rqt = {
            method: 'GET',
            url : 'persons',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
    };
    $http(rqt).success(function(data){
        $scope.allPerson = data;
    });

	// GET a specific person with the id
	$scope.getPerson = function(person) {
		var rqt = {
				method : 'GET',
				url : 'person/' + person.id,
				headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
		};
		$http(rqt).success(function(data){
			$scope.moreInfo = false;
			$scope.moreInfoData = data;
		});
	};

	// DELETE a specific person and reload the list
	$scope.deletePerson = function(id) {
		var rqt = {
				method : 'DELETE',
				url : 'person/' + id,
				headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
		};
		$http(rqt).success(function(data){
			var rqt = {
					method: 'GET',
					url : 'persons',
					headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
			};
			$http(rqt).success(function(data){
				$scope.allPerson = data;
			});
			$scope.moreInfo = true;
		});
	};

	// This pattern permit to verify if the passwords are the same
        $scope.getPattern = function(){
            return ($scope.password && $scope.password.replace(/([.*+?^${}()|\[\]\/\\])/g, '\\$1'));
        }

        $scope.createPerson = function(name, pseudo, email, password) {
            /*console.log("name =" + name);
            console.log("pseudo =" + pseudo);
            console.log("email =" + email);
            console.log("password =" + password);*/
            console.log("passe ici");
            var rqt = {
                    method : 'POST',
                    url : 'person',
                    data : $.param({name: name, pseudo : pseudo, email: email, password: password}),
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                var rqt = {
                        method: 'GET',
                        url : 'persons',
                        headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
                };
                $http(rqt).success(function(data){
                    console.log(data);
                    $scope.allPerson = data;
                });
            });
        };

    // UPDATE a person with the information enter in the form
	$scope.updatePerson = function(person, newName, newPseudo, newEmail, newPassword) {
	    var rqt = {
                method : 'PUT',
                url : 'person/' + person.id,
                data : $.param({newName: newName, newPseudo: newPseudo, newEmail: newEmail, newPassword: newPassword}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
        var rqt = {
                    method: 'GET',
                    url : 'persons',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.allPerson = data;
            });
        });
	};




	// TASK

    // GET all task on the database at the url /tasks and put it in the allTask variable with $scope.
    var rqt = {
            method: 'GET',
            url : 'tasks',
            headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
    };
    $http(rqt).success(function(data){
        $scope.allTask = data;
    });

    // GET a specif task
    $scope.getTask = function(task) {
            var rqt = {
                    method : 'GET',
                    url : 'task/' + task.id,
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.moreInfo = false;
                $scope.moreInfoData = data;
            });
        };

    // DELETE a specific person and reload the list
    $scope.deleteTask = function(id) {
        var rqt = {
                method : 'DELETE',
                url : 'task/' + id,
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            var rqt = {
                    method: 'GET',
                    url : 'tasks',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.allTask = data;
            });
            $scope.moreInfo = true;
        });
    };

	// POST create a task
	$scope.addTask = function(nameTask) {
        var rqt = {
                method : 'POST',
                url : 'task',
                data : $.param({name: nameTask}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
            var rqt = {
                    method: 'GET',
                    url : 'tasks',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                console.log(data);
                $scope.allTask = data;
            });
        });
	};

    // UPDATE a person with the information enter in the form
    $scope.updateTask = function(taskChosen, newName) {
        var rqt = {
                method : 'PUT',
                url : 'task/' + taskChosen.id,
                data : $.param({newName: newName}),
                headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
        };
        $http(rqt).success(function(data){
        var rqt = {
                    method: 'GET',
                    url : 'tasks',
                    headers : { 'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8' }
            };
            $http(rqt).success(function(data){
                $scope.allTask = data;
            });
        });
    };




});