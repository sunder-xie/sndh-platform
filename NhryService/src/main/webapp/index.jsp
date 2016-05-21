<html>
<head>
	<meta charset="utf-8">
	<link rel="stylesheet" href="http://apps.bdimg.com/libs/bootstrap/3.3.4/css/bootstrap.min.css">
	<script src="http://apps.bdimg.com/libs/angular.js/1.4.6/angular.min.js"></script>
</head>
<body ng-app="myApp" ng-controller="userCtrl" >

<div class="container">
	<h3>Users</h3>

	<table class="table table-striped">
		<thead>
		<tr>
			<th>id</th>
			<th>name</th>
			<th>comments</th>
		</tr>
		</thead>
		<tbody>
		<tr ng-repeat="user in users">
			<td>{{ user.id }}</td>
			<td>{{ user.userName }}</td>
			<td>{{ user.comments }}</td>
		</tr>
		</tbody>
	</table>
</div>
<script >
	angular.module('myApp', []).controller('userCtrl', function($scope,$http) {

		$http({
			url:'/NhryService/rest/user/all',
			method:'GET'
		}).success(function(data,header,config,status){
			$scope.users = data;

		}).error(function(data,header,config,status){
			alert(data);
		});
		/* $http.get("/rest/user/all")
		 .success(function(data) {
		 alert(data);
		 alert("success");
		 $scope.users = data;
		 }).error(function(data) {
		 alert($http.url);
		 alert("server error");
		 });*/

	});

</script>
</body>
</html>