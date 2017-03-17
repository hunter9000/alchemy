
alchApp.factory('APIService', function($window, $location, $http, $log) {
    return {
        getHeaders: function() {
            return { headers: {'x-access-token': $window.localStorage['jwtToken']} };
        },

        getStandardFailureCallback: function(response) {
            return function(response) {
                $log.error(response);
                $location.path('/error');
            };
        },

        getSuccessCallbackWrapper: function(callback, method, uri, data) {
            return function(response) {
                $log.debug('Response from '+method+': ' + uri);
                if (data) {
                    $log.debug('With data: ');
                    $log.debug(data);
                }
                $log.debug(response);
                callback(response);
            };
        },

        get: function(uri, successCallback) {
            $log.debug('GET: ' + uri);
            $http.get(uri, this.getHeaders())
            .then(this.getSuccessCallbackWrapper(successCallback, 'GET', uri), this.getStandardFailureCallback());
        },

        post: function(uri, data, successCallback) {
            $log.debug('POST: ' + uri + ' DATA:');
            $log.debug(data);
            return $http.post(uri, data, this.getHeaders())
            .then(this.getSuccessCallbackWrapper(successCallback, 'POST', uri, data), this.getStandardFailureCallback());
        },

        put: function(uri, data, successCallback) {
            $log.debug('PUT: ' + uri + ' DATA:');
            $log.debug(data);
            return $http.put(uri, data, this.getHeaders())
            .then(this.getSuccessCallbackWrapper(successCallback, 'PUT', uri, data), this.getStandardFailureCallback());
        },

        patch: function(uri, data, successCallback) {
            $log.debug('PATCH: ' + uri + ' DATA:');
            $log.debug(data);
            return $http.patch(uri, data, this.getHeaders())
            .then(this.getSuccessCallbackWrapper(successCallback, 'PATCH', uri, data), this.getStandardFailureCallback());
        },

        delete: function(uri, successCallback) {
            $log.debug('DELETE: ' + uri);
            return $http.delete(uri, this.getHeaders())
            .then(this.getSuccessCallbackWrapper(successCallback, 'DELETE', uri), this.getStandardFailureCallback());
        },

        // API CALLS
        authenticate: function(data, successCallback) {
            this.post('/api/authenticate/', data, successCallback);
        },
        getUser: function(userId, successCallback) {
            this.get('/api/users/'+userId+'/', successCallback);
        },
        getUsers: function(successCallback) {
            this.get('/api/users/', successCallback);
        },
        updateUser: function(userId, data, successCallback) {
            this.put('/api/users/'+userId+'/', data, successCallback);
        },
        createUser: function(data, successCallback) {
            this.post('/api/user/', data, successCallback);
        },
        getProfile: function(successCallback) {
            this.get('/api/profile/', successCallback);
        },
        editProfile: function(data, successCallback) {
            this.put('/api/profile/', data, successCallback);
        },
        getAllRoles: function(successCallback) {
            this.get('/api/roles/', successCallback);
        },

        createNewGrid: function(successCallback) {
            this.post('/api/grid/', {}, successCallback);
        },
        getAllGridsForUser: function(successCallback) {
            this.get('/api/grid/', successCallback);
        },
		getGrid: function(gridId, successCallback) {
		    this.get('/api/grid/'+gridId+'/', successCallback);
		},

        deleteGrid(gridId, successCallback) {
            this.delete('/api/grid/'+gridId+'/', successCallback);
        },

        purchaseUnit(gridId, unitId, data, successCallback) {
            this.post('/api/grid/'+gridId+'/units/', data, successCallback);
        },

        placeUnit(gridId, unitId, data, successCallback) {
            this.put('/api/grid/'+gridId+'/units/', data, successCallback);
        },

//		getPurchasableUnits: function(gridId, successCallback) {
//		    this.get('/api/grid/'+gridId+'/store/', successCallback)
//		},
    };
});