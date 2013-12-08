inn = angular.module 'inn', []

InnController = ($scope, $http) ->
  $http.get('/items').success (data) ->
    $scope.items = data