// create the module and name it scotchApp
var alchApp = angular.module('alchApp', ['ngRoute', 'ui.bootstrap', 'checklist-model', 'ngMaterial', 'ngDragDrop']);

// configure our routes
alchApp.config(function($routeProvider) {
    $routeProvider
        // route for the home page
        // main-controller.js
        .when('/', {
            templateUrl : 'pages/home.html',
            controller  : 'mainController'
        })
        // route for the login page
        // login-controller.js
        .when('/login', {
            templateUrl : 'pages/login.html',
            controller  : 'loginController'
        })

        // USERS #######
        // admin/new-user-controller.js
        .when('/newuser', {
            templateUrl : 'pages/admin/newuser.html',
            controller  : 'newUserController'
        })
        // admin/users-controller.js
        .when('/users', {
            templateUrl : 'pages/admin/users.html',
            controller  : 'usersController'
        })
        // admin/edit-user-controller.js
        .when('/edituser/:userId', {
            templateUrl: 'pages/admin/edituser.html',
            controller: 'editUserController'
        })
        // profile-controller.js
        .when('/profile', {
            templateUrl : 'pages/profile.html',
            controller  : 'profileController'
        })

        // generic error page
        .when('/error', {
            templateUrl: 'pages/error.html',
            controller: 'errorController'
        })

        .when('/newgrid', {
            templateUrl: 'pages/newgrid.html',
            controller:  'newGridController'
        })
        .when('/grid/:gridId', {
            templateUrl: 'pages/grid.html',
            controller:  'gridController'
        })

        .otherwise({redirectTo:'/'});
        ;
});
