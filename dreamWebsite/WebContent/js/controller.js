
var mainApp = angular.module('mainApp',['ngCookies', 'ngRoute', 'mgcrea.ngStrap', 'ngMaterial']);

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
	
	mainApp.controller('registerCtrl', function($cookieStore, $scope, $http, $filter, $window) {
		$scope.member = {
			username: null,
			password: null,
			confirmPass: null,
			email: null,
			phone: null,
			fname: null,
			lname: null,
			nickname: null,
			birth: null
		};

		$scope.regisFunc = function(username, password, confirmPass, fname, lname, nickname, email, phone, birth) {
			if(password != confirmPass) {
				alert("Password != Confirm Password")
				return;
			}
			
			var birthDate = $filter('date')(new Date(birth), "yyyy-MM-dd");
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/member/insert?username="+username+"&password="+password+
			       "&fname="+fname+"&lname="+lname+"&nickname="+nickname+"&email="+email+"&phone="+phone+"&birth="+birthDate,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				alert("Registration Complete");
				$window.location.reload();
			}).
			error(function(data, status) {
				alert("Timeout Server not Responsding. Please try again");
			});
		};
	})

	mainApp.controller('loginCtrl', function($cookieStore, $scope, $http) {
		$scope.member;
		$scope.hasLogin = false;
		$scope.template;
		$scope.checkLogin = function() {
			$scope.member = $cookieStore.get('member');
			$scope.hasLogin = $cookieStore.get('hasLogin');
			if($scope.hasLogin == false) {
				$scope.template = "templates/member/login.html";
			}
			else if($scope.hasLogin == true) {
				$scope.template = "templates/member/userDetail.html";
			} 
			else {
				$scope.hasLogin = false;
				$scope.hasLogin = $cookieStore.put('hasLogin', $scope.hasLogin);
			}
		}
		
		$scope.logoutFunc = function() {
			logout($scope,$http, $cookieStore);
		}

		$scope.loginFunc = function(username, password) {
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/j_spring_security_check?j_username="+username+"&j_password="+password,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				if(status == 200) {
					getUsername($scope, $http, $cookieStore);
				}
			}).
			error(function(data, status) {
				alert("Timeout Server not Responsding. Please try again");
			});
		};
		
//		$scope.toggleLeft = function() {
//		    $mdSidenav('left').toggle();
//		};
//		
//		$scope.close = function() {
//		    $mdSidenav('left').close();
//		};
	})
	
	function logout($scope, $http, $cookieStore) {
		$http({
			   withCredentials: true,
		       method: 'post',
		       url: "http://dreamservice.azurewebsites.net/j_spring_security_logout",
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			alert("Logout Complete")
			$cookieStore.put('member', null);
			$cookieStore.put('hasLogin', false);
			$scope.checkLogin();
			$scope.template = "templates/member/login.html";
		}).
		error(function(data, status) {
			alert("Logout Incomplete");
		});
	}
	
	function getUsername($scope, $http, $cookieStore) {
		$http({
			   withCredentials: true,
		       method: 'post',
		       url: "http://dreamservice.azurewebsites.net/member/findfromuser",
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			$scope.hasLogin = true;
			$scope.member = data;
			$cookieStore.put("hasLogin", $scope.hasLogin);
			$cookieStore.put("member", $scope.member);
			$scope.template = "templates/member/userDetail.html";
		}).
		error(function(data, status) {
			alert("invalid login");
			$scope.hasLogin = false;
			$cookieStore.put("hasLogin", hasLogin);
		});
	}

	function mainTabSelect ($scope, $interpolate) {
		var tabs = [
	      { title: 'Home', active: true, url: "templates/angular_index.html", style:"tab1"},
	      { title: 'Member', active: false, url: "templates/member.html", style:"tab2" },
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

