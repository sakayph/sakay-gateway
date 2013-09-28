function AppCtrl($scope, $http) {
	var params = {};

	$scope.messages = [];

	$scope.load = function() {
		$http({
			method: 'GET',
			url: 'api/list',
			headers: {
				'X-API-KEY': $scope.secret,
			},
			params: params
		})
		.success(function(data) {
			$scope.messages.push.apply($scope.messages, data);
			params.since = $scope.messages[$scope.messages.length-1].message.timestamp;
		});
	}
}
