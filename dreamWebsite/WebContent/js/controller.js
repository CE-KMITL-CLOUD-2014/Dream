
var mainApp = angular.module('mainApp',['ngCookies', 'ngRoute', 'mgcrea.ngStrap', 'ngMaterial']);

mainApp.config(['$httpProvider', function ($httpProvider) {
    delete $httpProvider.defaults.headers.common['X-Requested-With'];
    $httpProvider.defaults.useXDomain = true;
}]);

mainApp.config(function($datepickerProvider) {
	  angular.extend($datepickerProvider.defaults, {
		    dateFormat: 'dd/MM/yyyy',
		    startWeek: 1
		  });
})

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
		    $mdSidenav('leftDebt').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('leftDebt').close();
		};
	})
	
	mainApp.controller('planCtrl', function ($scope, $http, $mdSidenav, $window, $mdBottomSheet) {
		$scope.event = {
			type: "event",
			end:   null,
			description: null
		}
		
		$scope.save = {
			type: "saving",
			end: null,
			amount: null,
			sAmount: null
		}
		
		$scope.budget = {
			type: "budget",
			end: null,
			start: null,
			amount: null,
			finance: null
		}
		
		$scope.AddBudget = function(finance, amount, start, end) {
			if(finance == null) {
				alert("Please select finance type");
				return;
			}
			if(amount == null || amount == 0) {
				alert("Amount can't be 0");
				return;
			}
			if(start == null || end == null) {
				alert("Please select start & end time");
				return;
			}
			
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/planing/budget/insert?type_id="+finance+"&goal="+amount+"&startTime="+start+"&endTime="+end,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/save.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
			
		}
		
		$scope.showGridBudget = function($event) {
		    $mdBottomSheet.show({
		      templateUrl: 'templates/planing/planing_grid.html',
		      controller: 'planingGridCtrl',
		      targetEvent: $event
		    }).then(function(clickedItem) {
		      $scope.budget.finance = clickedItem.name;
		    });
		};
		
		$scope.AddSaving = function(amount, sAmount, description, end) {
			if(amount == null) {
				amount = 0;
			}
			if(sAmount == null) {
				sAmount = 0;
			}
			if(description == null) {
				description = 0;
			}
			if(end == null) {
				alert("Please Insert Date");
				return;
			}
			
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/planing/saving/insert?goal="+sAmount+"&description="+description+"&end_time="+end+"&start_amount="+amount,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/save.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
			
		}
		
		$scope.AddEvent = function(endTime, description) {
			if(endTime == null) {
				alert("Please Insert Date");
				return;
			}
			if(description == null) {
				description = "";
			}
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/planing/event/insert?end_time="+endTime+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/save.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.eventList;
		$scope.hasClick = false;
		
		$scope.listEvent = function() {
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/event/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.eventList = data;
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.deleteEvent = function(eventId) {
			$http({
				   withCredentials: true,
			       method: 'delete',
			       url: "http://dreamservice.azurewebsites.net/planing/event/delete/eventid="+eventId,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/event.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.editEvent = function(eventId) {
			
		}
		
		$scope.toggleLeft = function() {
		    $mdSidenav('leftPlan').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('leftPlan').close();
		};
	})
	
	mainApp.controller('planingGridCtrl', function($scope, $mdBottomSheet) {
		$scope.items = [
		                { name: 'Food', icon: "images/income_icon/stock_48.png" },
		                { name: 'Travel', icon: "images/income_icon/gift_48.png" },
		                { name: 'Education', icon: 'images/income_icon/plane_48.png' },
		                { name: 'Trip', icon: 'images/income_icon/infinity_48.png' },
		                { name: 'Health', icon: 'images/income_icon/facebook_48.png' },
		                { name: 'Shopping', icon: 'images/income_icon/twitter_48.png' },
		                { name: 'Lend', icon: 'images/income_icon/twitter_48.png' },
		                { name: 'Love', icon: 'images/income_icon/twitter_48.png' }
		              ];

		$scope.listItemClick = function($index) {
		    var clickedItem = $scope.items[$index];
		    $mdBottomSheet.hide(clickedItem);
		};

	})

	mainApp.controller('financeCtrl', function($scope, $http, $mdSidenav, $mdBottomSheet, $window) {
		$scope.income = {
			type: "income",
			amount: null,
			description: null,
			finance: null,
			eventId: null,
			saveId: null,
			budgetId: null
		}
		
		$scope.outcome = {
				type: "outcome",
				amount: null,
				description: null,
				finance: null,
				eventId: null,
				saveId: null,
				budgetId: null
		}
		
		$scope.list = {};
		$scope.hasClick = false;
		
		$scope.listFinance = function() {
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/finance/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.list = data;
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.editFinance = function(finance, dateTime, amount, description) {
			if(description == null) {
				description = "";
			}
			if(amount == null) {
				amount = 0;
			}
			$http({
				   withCredentials: true,
			       method: 'put',
			       url: "http://dreamservice.azurewebsites.net/finance/update?finance="+finance+"&dateTime="+dateTime+"&amount="+amount+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/finance/income.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.deleteFinance = function(dateTime) {
			$http({
				   withCredentials: true,
			       method: 'delete',
			       url: "http://dreamservice.azurewebsites.net/finance/delete?date_time="+dateTime,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/finance/income.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		}
		
		$scope.addFinanceIncome = function(finance, amount, description) {
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/finance/insert?finance="+finance+"&amount="+amount+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/finance/activity.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		};

		$scope.addFinanceOutcome = function(finance, amount, description) {
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/finance/insert?finance="+finance+"&amount="+amount+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/finance/activity.html"
			}).
			error(function(data, status) {
				if(status == 401) {
					alert("Please Login");
					$window.location.reload();
				} else {
					alert("Timeout Server not Responsding. Please try again");
				}
			});
		};

		$scope.showGridIncome = function($event) {
		    $mdBottomSheet.show({
		      templateUrl: 'templates/finance/list_grid.html',
		      controller: 'incomeCtrl',
		      targetEvent: $event
		    }).then(function(clickedItem) {
		      $scope.income.finance = clickedItem.name;
		    });
		};
		
		$scope.showGridOutcome = function($event) {
		    $mdBottomSheet.show({
		      templateUrl: 'templates/finance/list_grid.html',
		      controller: 'outcomeCtrl',
		      targetEvent: $event
		    }).then(function(clickedItem) {
		      $scope.outcome.finance = clickedItem.name;
		    });
		};
		
		$scope.toggleLeft = function() {
		    $mdSidenav('leftFinance').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('leftFinance').close();
		};
	})

	mainApp.controller('incomeCtrl', function($scope, $mdBottomSheet) {
		$scope.items = [
		                { name: 'Stock', icon: "images/income_icon/stock_48.png" },
		                { name: 'Gift', icon: "images/income_icon/gift_48.png" },
		                { name: 'Salary', icon: 'images/income_icon/plane_48.png' },
		                { name: 'Bonus', icon: 'images/income_icon/infinity_48.png' },
		                { name: 'Interest', icon: 'images/income_icon/facebook_48.png' },
		                { name: 'Borrow', icon: 'images/income_icon/twitter_48.png' }
		              ];

		$scope.listItemClick = function($index) {
		    var clickedItem = $scope.items[$index];
		    $mdBottomSheet.hide(clickedItem);
		};

	})
	
	mainApp.controller('outcomeCtrl', function($scope, $mdBottomSheet) {
		$scope.items = [
		                { name: 'Food', icon: "images/income_icon/stock_48.png" },
		                { name: 'Travel', icon: "images/income_icon/gift_48.png" },
		                { name: 'Education', icon: 'images/income_icon/plane_48.png' },
		                { name: 'Trip', icon: 'images/income_icon/infinity_48.png' },
		                { name: 'Health', icon: 'images/income_icon/facebook_48.png' },
		                { name: 'Shopping', icon: 'images/income_icon/twitter_48.png' },
		                { name: 'Lend', icon: 'images/income_icon/twitter_48.png' },
		                { name: 'Love', icon: 'images/income_icon/twitter_48.png' }
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

	mainApp.controller('memberCtrl', function($cookieStore, $scope, $http, $window, $mdSidenav) {
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
				$window.location.reload();
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
		
		$scope.toggleLeft = function() {
		    $mdSidenav('leftMem').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('leftMem').close();
		};
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
		       method: 'get',
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
	      { title: 'Member', active: false, url: "templates/member.html", style:"tab1" },
	      { title: 'Debt Calulator', active: false, url: "templates/debt.html", style:"tab2" },
	      { title: 'Finance', active: false, disabled: false, url: "templates/finance.html",style:"tab3" },
	      { title: 'Planing', active: true, url: "templates/planing.html", style:"tab4"},
	    ];

	    $scope.tabs = tabs;
	    $scope.predicate = "title";
	    $scope.reversed = true;
	    $scope.selectedIndex = 0;
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

