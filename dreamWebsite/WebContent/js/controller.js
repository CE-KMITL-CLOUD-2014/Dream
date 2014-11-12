
// ng-polymer-elements
var mainApp = angular.module('mainApp',['ngCookies', 'ngMaterial']);

mainApp.config(['$httpProvider', function ($httpProvider) {
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.useXDomain = true;
}]);

	mainApp.controller('AppCtrl', function ($scope, $interpolate) {
		var tabs = mainTabSelect($scope, $interpolate);
	})

	mainApp.controller('debtCtrl', function ($scope, $http, $mdSidenav) {
		$scope.debtLoan = {
    		type:		"Loan",
    		totalMoney: null,
    		debtRate:	null,
    		time:		null,
    		payMonth:	null
		}
		
		$scope.debtSave = {
	    		type:		"Save Account",
	    		totalMoney: null,
	    		debtRate:	null,
	    		time:		null,
	    		moneyDebt:	null
			}
		
		$scope.getDebtLoan = function(totalMoney, debtRate, time) {
			$http.get("http://dreamservice.azurewebsites.net/debt/cal?moneyLoan="+totalMoney+"&debtRate="+debtRate+"&time="+time).
			success(function(data,status){
				$scope.debtLoan.payMonth = data.moneyMonth;
			}).
			error(function(data,status){
				alert("error");
			})
		}
		
		$scope.getDebtSave = function(totalMoney, debtRate, time) {
			$http.get("http://dreamservice.azurewebsites.net/debt/cal?moneyLoan="+totalMoney+"&debtRate="+debtRate+"&time="+time).
			success(function(data,status){
				$scope.debtSave.moneyDebt = data.moneyDebt;
			}).
			error(function(data,status){
				alert("error");
			})
		}
		
		$scope.toggleLeft = function() {
		    $mdSidenav('left').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('left').close();
		};
	})

	mainApp.controller('financeCtrl', function($scope, $http, $mdSidenav, $mdBottomSheet) {
		$scope.income = {
			type: "income",
			id_string: null,
			amount: null,
			descriotion: null,
			username: null
		}

		$scope.showGridBottomSheet = function($event) {
		    $mdBottomSheet.show({
		      templateUrl: 'templates/finance/income_grid.html',
		      controller: 'incomeCtrl',
		      targetEvent: $event
		    }).then(function(clickedItem) {
		      alert("clickedItem.name" + clickedItem.name);
		    });
		};
	})

	mainApp.controller('incomeCtrl', function($scope, $mdBottomSheet) {
		$scope.items = [
		                { name: 'Stock', icon: "images/income_icon/stock_48.png" },
		                { name: 'Gift', icon: "images/income_icon/gift_48.png" },
		                { name: 'Plane', icon: 'images/income_icon/plane_48.png' },
		                { name: 'Infinity', icon: 'images/income_icon/infinity_48.png' },
		                { name: 'Facebook', icon: 'images/income_icon/facebook_48.png' },
		                { name: 'Twitter', icon: 'images/income_icon/twitter_48.png' },
		                { name: 'Skype', icon: 'images/income_icon/skype_48.png' },
		                { name: 'Tumblur', icon: 'images/income_icon/tumblur_48.png' },
		                { name: 'Heart', icon: 'images/income_icon/heart_48.png' },
		                { name: 'Phone', icon: 'images/income_icon/phone_48.png' },
		                { name: 'Notebook', icon: 'images/income_icon/notebook_48.png' },
		                { name: 'Hospital', icon: 'images/income_icon/hospital_48.png' }
		              ];

		$scope.listItemClick = function($index) {
		    var clickedItem = $scope.items[$index];
		    $mdBottomSheet.hide(clickedItem);
		};

	})

	mainApp.controller('loginCtrl', function($scope, $http, $cookies) {
		$scope.login = {
			login: "Login",
			username: null,
			password: null,
			sessionId: null
		}


		$scope.loginFunc = function(username, password, $cookies) {
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://localhost:8080/dreamService/j_spring_security_check?j_username="+username+"&j_password="+password,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, $cookies) {
				alert("aaa");
				alert("bbb");
			}).
			error(function(data, status, $cookies) {
				alert("bbbsss");
				var abc = $cookies.JSESSIONID;
				alert(abc);
			});
		};
	})

	function mainTabSelect ($scope, $interpolate) {
		var tabs = [
	      { title: 'Home', active: true, url: "templates/angular_index.html", style:"tab1"},
	      { title: 'Member', active: false, url: "templates/login.html", style:"tab2" },
	      { title: 'Debt Calulator', active: false, url: "templates/debt.html", style:"tab3" },
	      { title: 'Finance', active: false, disabled: false, url: "templates/finance.html",style:"tab4" }
	    ];

	    $scope.tabs = tabs;
	    $scope.predicate = "title";
	    $scope.reversed = true;
	    $scope.selectedIndex = 1;
	    $scope.allowDisable = true;

	    $scope.onTabSelected = onTabSelected;
	    $scope.announceSelected = announceSelected;
	    $scope.announceDeselected = announceDeselected;
	    return tabs;
	    // **********************************************************
	    // Private Methods
	    // **********************************************************

	    function onTabSelected(tab) {
	      $scope.selectedIndex = this.$index;

	      $scope.announceSelected(tab);
	    }

	    function announceDeselected(tab) {
	      $scope.farewell = $interpolate("Goodbye {{title}}!")(tab);
	    }

	    function announceSelected(tab) {
	      $scope.greeting = $interpolate("Hello {{title}}!")(tab);
	    }
	}

