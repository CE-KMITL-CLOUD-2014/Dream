
var mainApp = angular.module('mainApp',['ngCookies', 'ngRoute', 'mgcrea.ngStrap', 'ngMaterial', 'angularCharts']);

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

	mainApp.controller('calcCtrl', function ($scope, $http, $mdSidenav, $filter, $cookieStore) {
		$scope.hasLogin = $cookieStore.get('hasLogin');
		
		$scope.debtLoan = {
    		type:		"Loan",
    		totalMoney: null,
    		debtRate:	null,
    		time:		null,
    		payMonth:	null
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
		
		$scope.debtRatio = {
				type: "Debt Ratio",
				debt: null,
				totalAsset: null,
				ratio: null
			}
			
		$scope.getDebtRatio = function(debt, totalAsset) {
			$http.get("http://dreamservice.azurewebsites.net/financehealth/debtratio?debt="+debt+"&totalAsset="+totalAsset).
			success(function(data,status){
				$scope.debtRatio.ratio = $filter('number')(data.debtRatioValue * 100, 2) + "%";
			}).
			error(function(data,status){
				alert("error");
			})
		}
		
		$scope.saveRatio = {
			type: "Save Ratio",
			save: null,
			income: null,
			ratio: null
		}
		
		$scope.getSaveRatio = function(save, income) {
			$http.get("http://dreamservice.azurewebsites.net/financehealth/savingratio?savingPerYear="+save+"&incomePerYear="+income).
			success(function(data,status){
				$scope.saveRatio.ratio = $filter('number')(data.savingRatioValue * 100, 2) + "%";
			}).
			error(function(data,status){
				alert("error");
			})
		}
		
		$scope.liquidity = {
			type: "Liquidity",
			asset: null,
			expense: null,
			liquidtyValue: null
		}
		
		$scope.getLiquidity = function(asset, expense) {
			if(asset == null && expense == null) {
				$http({
					   withCredentials: true,
				       method: 'get',
				       url: "http://dreamservice.azurewebsites.net/financehealth/getliquidity",
				       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				 }).
				success(function(data,status){
					$scope.liquidity.liquidtyValue = $filter('number')(data.liquidtyValue, 0);
				}).
				error(function(data,status){
					alert("error");
				})
			} else if(asset != null && expense != null) {
				$http({
					   withCredentials: true,
				       method: 'get',
				       url: "http://dreamservice.azurewebsites.net/financehealth/getliquidity?assets="+asset+"&expenses="+expense,
				       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
				 }).
				success(function(data,status){
					$scope.liquidity.liquidtyValue = $filter('number')(data.liquidtyValue, 0);
				}).
				error(function(data,status){
					alert("error");
				})
			}
		}
		
		$scope.toggleLeft = function() {
		    $mdSidenav('leftDebt').toggle();
		};
		
		$scope.close = function() {
		    $mdSidenav('leftDebt').close();
		};
	})
	
	mainApp.controller('planCtrl', function ($scope, $http, $mdSidenav, $window, $mdBottomSheet, $filter, $cookieStore) {
		$scope.hasLogin = $cookieStore.get('hasLogin');
		if($scope.hasLogin != true) {
			alert("Please Login");
			$window.location.reload();
			return;
		}
		
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
		
		$scope.hasClick = false;
		$scope.budgetCheck = false;
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
			$scope.budgetCheck = false;
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/budget/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				var today = $filter('date')(new Date(), "yyyy-MM-dd");
				data.forEach(function(entry){
					if($filter('date')(new Date(entry.endTime), "yyyy-MM-dd") >= today) {
						if(entry.type_description == finance) {
							$scope.budgetCheck = true;
						}
					}
				});
				addBudgetToDB($scope, $http, $filter, $window, finance, amount, start, end);
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
		
		$scope.bugetList;
		$scope.listBudget = function() {
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/budget/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.bugetList = data;
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
		
		$scope.editBudget = function(budgetID, start, end, amount, goal, type_description) {
			if(goal - amount <= 0) {
				alert("Your Budget Limit Exceed. Don't use it no more !!");
			}
			var startDate = $filter('date')(new Date(start), "yyyy-MM-dd");
			var endDate = $filter('date')(new Date(end), "yyyy-MM-dd");
			$http({
				   withCredentials: true,
			       method: 'put',
			       url: "http://dreamservice.azurewebsites.net/planing/budget/edit/"+budgetID+"?goal="+goal+"&startTime="+startDate+"&endTime="+endDate+"&type_description="+type_description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/budget.html"
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
		
		$scope.deleteBudget = function(budgetID) {
			$http({
				   withCredentials: true,
			       method: 'delete',
			       url: "http://dreamservice.azurewebsites.net/planing/budget/delete/"+budgetID,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/budget.html"
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
		$scope.savingCheck = false;
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
			if(amount - sAmount < 0) {
				alert("Amount is less than start amount !!");
				return;
			} else if( amount - sAmount == 0) {
				alert("Amount is equal with start amount !!");
				return;
			}
			$scope.savingCheck = false;
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/saving/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				var today = $filter('date')(new Date(), "yyyy-MM-dd");
				data.forEach(function(entry){
					if($filter('date')(new Date(entry.end), "yyyy-MM-dd") >= today) {
						$scope.savingCheck = true;
					}
				});
				addSavingToDB($scope, $http, $filter, $window, amount, sAmount, description, end);
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
		
		$scope.savingList;
		
		$scope.listSaving = function() {
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/saving/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.savingList = data;
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
		
		$scope.editSaving = function(saveID, end, amount, goal, description) {
			if(goal - amount < 0) {
				alert("Goal is less than amount");
				return;
			}else if(goal - amount == 0) {
				alert("Your Goal Complete. Congratulation !!");
			}
			var endDate = $filter('date')(new Date(end), "yyyy-MM-dd");
			$http({
				   withCredentials: true,
			       method: 'put',
			       url: "http://dreamservice.azurewebsites.net/planing/saving/edit/"+saveID+"?goal="+goal+"&start_amount="+amount+"&end_time="+endDate+"&description="+description,
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
		
		$scope.deleteSaving = function(saveID) {
			$http({
				   withCredentials: true,
			       method: 'delete',
			       url: "http://dreamservice.azurewebsites.net/planing/saving/delete/"+saveID,
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
		
		$scope.eventCheck = false;
		
		$scope.AddEvent = function(endTime, description) {
			if(endTime == null) {
				alert("Please Insert Date");
				return;
			}
			if(description == null) {
				description = "";
			}
			$scope.eventCheck = false;
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/planing/event/list",
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				var today = $filter('date')(new Date(), "yyyy-MM-dd");
				data.forEach(function(entry){
					if($filter('date')(new Date(entry.end_time), "yyyy-MM-dd") >= today) {
						$scope.eventCheck = true;
					}
				});
				addEventToDB($scope, $http, $filter, $window, endTime, description);
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
			       url: "http://dreamservice.azurewebsites.net/planing/event/delete/"+eventId,
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
		
		$scope.editEvent = function(eventId, end, description) {
			var endDate = $filter('date')(new Date(end), "yyyy-MM-dd");
			$http({
				   withCredentials: true,
			       method: 'put',
			       url: "http://dreamservice.azurewebsites.net/planing/event/edit/"+eventId+"?end_time="+endDate+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.template = "templates/planing/event.html";
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
	
	function DialogController($scope, $mdDialog, $filter, $http) {
	$scope.eventList = [];
	$scope.today = $filter('date')(new Date(), "yyyy-MM-dd");
	
		$http({
			   withCredentials: true,
		       method: 'get',
		       url: "http://dreamservice.azurewebsites.net/planing/event/list",
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			data.forEach(function(entry) {
				if($filter('date')(entry.end_time, "yyyy-MM-dd") > $scope.today) {
					$scope.eventList.push(entry);
				}
			})
		}).
		error(function(data, status) {
			if(status == 401) {
				alert("Please Login");
				$window.location.reload();
			} else {
				alert("Timeout Server not Responsding. Please try again");
			}
		});
	
	  $scope.hide = function() {
	    $mdDialog.hide();
	  };
	
	  $scope.cancel = function() {
	    $mdDialog.cancel();
	  };
	
	  $scope.answer = function(answer) {
	    $mdDialog.hide(answer);
	  };
	}

	mainApp.controller('financeCtrl', function($scope, $http, $mdDialog, $mdSidenav, $mdBottomSheet, $window, $cookieStore, $filter) {
		$scope.hasLogin = $cookieStore.get('hasLogin');
		if($scope.hasLogin != true) {
			alert("Please Login");
			$window.location.reload();
			return;
		}
		
		$scope.income = {
			type: "income",
			amount: null,
			description: null,
			finance: null,
			eventId: null,
			saveId: null,
			budgetId: null
		}
		$scope.eventID = 1;
		
		$scope.showEventDialog = function(ev) {
		    $mdDialog.show({
		      controller: DialogController,
		      templateUrl: 'templates/finance/eventDialog.html',
		      targetEvent: ev,
		    })
		    .then(function(answer) {
		    	$scope.eventID = answer;
		    }, function() {
		    	$scope.eventID = 1;
		    });
		};
		
		$scope.outcome = {
				type: "outcome",
				amount: null,
				description: null,
				finance: null,
				eventId: null,
				saveId: null,
				budgetId: null
		}
		
		$scope.list;
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
		
		$scope.editFinance = function(finance, dateTime, amount, description, eventId, budgetId, savingId) {
			if(description == null) {
				description = "";
			}
			if(amount == null) {
				amount = 0;
			}
			$http({
				   withCredentials: true,
			       method: 'put',
			       url: "http://dreamservice.azurewebsites.net/finance/update?finance="+finance+"&dateTime="
			       +dateTime+"&amount="+amount+"&description="+description+"&eventId="+eventId+"&budgetId="+budgetId+"&savingId="+savingId,
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
		

		$scope.addFinanceIncome = function(finance, amount, description, eventID) {
			if(description == null) {
				description = "";
			}
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/finance/insert?eventId="+eventID+"&finance="+finance+"&amount="+amount+"&description="+description,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.income.amount = null;
				$scope.income.description = null;
				$scope.income.finance = null;
				$scope.income.eventId = null;
				$scope.income.saveId = null;
				$scope.income.budgetId = null;
				$scope.income.template = "templates/finance/activity.html";
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

		$scope.addFinanceOutcome = function(finance, amount, description, eventID) {
			if(description == null) {
				description = "";
			}
			$http({
				   withCredentials: true,
			       method: 'post',
			       url: "http://dreamservice.azurewebsites.net/finance/insert?finance="+finance+"&amount="+amount+"&description="+description+"&eventId="+eventID,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				$scope.outcome.amount = null;
				$scope.outcome.description = null;
				$scope.outcome.finance = null;
				$scope.outcome.eventId = null;
				$scope.outcome.saveId = null;
				$scope.outcome.budgetId = null;
				$scope.template = "templates/finance/activity.html";
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
				if(status == 401) {
					alert("Invalid username or password");
				}else {
					alert("Timeout Server not Responsding. Please try again");
				}
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
	      { title: 'Calulator', active: false, url: "templates/calculator.html", style:"tab2" },
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
	
	function addEventToDB($scope, $http, $filter, $window, endTime, description) {
		if($scope.eventCheck == true) {
			alert("You can't have more than 1 event");
			return;
		}
		var endDate = $filter('date')(new Date(endTime), "yyyy-MM-dd");
		$http({
			   withCredentials: true,
		       method: 'post',
		       url: "http://dreamservice.azurewebsites.net/planing/event/insert?end_time="+endDate+"&description="+description,
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			$scope.template = "templates/planing/event_activity.html";
			$scope.event.end = null;
			$scope.event.description = null;
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
	
	function addSavingToDB($scope, $http, $filter, $window, amount, sAmount, description, end) {
		if($scope.savingCheck == true) {
			alert("You can't have more than 1 saving");
			return;
		}
		
		var endDate = $filter('date')(new Date(end), "yyyy-MM-dd");
		$http({
			   withCredentials: true,
		       method: 'post',
		       url: "http://dreamservice.azurewebsites.net/planing/saving/insert?goal="+amount+"&description="+description+"&end_time="+endDate+"&start_amount="+sAmount,
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			$scope.template = "templates/planing/save_activity.html";
			$scope.saving.end = null;
			$scope.saving.amount = null;
			$scope.saving.sAmount = null;
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
	
	function addBudgetToDB($scope, $http, $filter, $window, finance, amount, start, end) {
		if($scope.budgetCheck == true) {
			alert("You can't have more than 1 in same buget");
			return;
		}
		var startDate = $filter('date')(new Date(start), "yyyy-MM-dd");
		var endDate = $filter('date')(new Date(end), "yyyy-MM-dd");
		$http({
			   withCredentials: true,
		       method: 'post',
		       url: "http://dreamservice.azurewebsites.net/planing/budget/insert?type_description="+finance+"&goal="+amount+"&startTime="+startDate+"&endTime="+endDate,
		       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
		 }).
		success(function(data, status, headers, config) {
			$scope.template = "templates/planing/budget_activity.html";
			$scope.budget.end = null;
			$scope.budget.start = null;
			$scope.budget.amount = null;
			$scope.budget.finance = null;
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
	
	
	mainApp.controller('chartCtrl', function ($scope, $http, $filter) {
		$scope.start;
		$scope.end;
		
		$scope.config = {
			    title: 'Finance',
			    tooltips: true,
			    labels: false,
			    mouseover: function() {},
			    mouseout: function() {},
			    click: function() {},
			    legend: {
			      display: true,
			      //could be 'left, right'
			      position: 'right'
			    }
			  };
		
		
		$scope.income = 0;
		$scope.outcome = 0;
		
		$scope.getData = function(start, end, myChart) {
			$scope.income = 0;
			$scope.outcome = 0;
			$scope.graphPie.data[0].y = [];
			$scope.graphPie.data[1].y = [];
			var tmpEnd = new Date(end);
			tmpEnd.setDate(tmpEnd.getDate() + 1);
			var startDate = $filter('date')(new Date(start), "yyyy-MM-dd");
			var endDate = $filter('date')(new Date(tmpEnd), "yyyy-MM-dd");
			$http({
				   withCredentials: true,
			       method: 'get',
			       url: "http://dreamservice.azurewebsites.net/finance/listdatetodate?start="+startDate+"&end="+endDate,
			       headers: {'Content-Type': 'application/x-www-form-urlencoded'}
			 }).
			success(function(data, status, headers, config) {
				if(data.length == 0) {
					alert("You don't have any finance in selected time");
				} else {
					data.forEach(function(entry) {
						if(entry.type == 1) {
							$scope.income += entry.amount;
						}else {
							$scope.outcome += entry.amount;
						}
					})
					$scope.changeChart(myChart);
				}
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
			
			$scope.graphPie = {
				series: [ 'Income', 'Outcome' ],
				data: [
				       { x: "Income", y : []},
				       { x: "Outcome", y : []}
			    ]
			}
			
			$scope.graphAll = {
					series: [ 'Income', 'Outcome' ],
					data: [
					       { x: "Finance", y : []}
				    ]
			}

			$scope.data = {};
			
			$scope.changeChart = function(chart) {
				if(chart == "pie") {
					$scope.graphPie.data[0].y = [];
					$scope.graphPie.data[1].y = [];
					$scope.graphPie.data[0].y.push($scope.income);
					$scope.graphPie.data[1].y.push($scope.outcome);
					$scope.data = $scope.graphPie;
				} else {
					$scope.graphAll.data[0].y = [];
					$scope.graphAll.data[0].y.push($scope.income);
					$scope.graphAll.data[0].y.push($scope.outcome);
					$scope.data = $scope.graphAll;
				}
			}
	})

