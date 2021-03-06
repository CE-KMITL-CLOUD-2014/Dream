angular.module('debtApp', ['ngMaterial'])

  /**
   *  Simple controller to build a `user` data model
   *  that will be used to databinding with `<tf-float>` directives
   */
  .controller('debtController', function($scope) {
    $scope.user = {
       title:     "Technical Program Manager",
       email:     "ipsum@lorem.com",
       firstName: "Naomi",
       lastName:  "" ,
       company:   "Google" ,
       address:   "1600 Amphitheatre Pkwy" ,
       city:      "Mountain View" ,
       state:     "CA" ,
       country:   "USA" ,
       postalCode : "94043"
    };
  });