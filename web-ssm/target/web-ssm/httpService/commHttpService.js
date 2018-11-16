myApp.factory('commHttpService',['$http','$timeout',function ($http,$timeout) {
    var commHttpService = {};

    /**
     * get请求
     * @param params 参数
     * @param url 地址
     * @param successCallback 成功后的回调函数；
     * @param errorCallback 失败后的回调函数；
     */
    commHttpService.get = function ($scope, params, url, successCallback, errorCallback) {
        var _param = "";
        for (var prop in params) {
            _param += prop + "=" + params[prop] + "&";
        }
        _param = _param.substring(0, _param.length - 1);
        $http({
                  method: "get",
                  url: url + "?" + _param,
                  params: params,
                  headers: {
                      'Content-Type': 'application/json'
                  }
              })
            .success(function (data) {
                //getToken($scope,data);
                if (data) {
                    if (successCallback) {
                        successCallback(data); // 成功后，调用成功回调函数
                    } else {
                        alert("操作成功");
                    }
                }else {
                    if (errorCallback) {
                        errorCallback(data); // 失败后，调用失败回调函数
                    } else {
                        if (data.msg) {
                            alert(data.msg);
                        } else {
                            alert("操作失败");
                        }
                    }
                }
            })
            .error(function (data) {
                if (errorCallback) {
                    errorCallback(data); // 异常后，调用失败回调函数
                } else {
                    alert("操作失败");
                }
            });
    };

    /**
     * post请求
     * @param params 参数
     * @param url 地址
     * @param successCallback 成功后的回调函数；
     * @param errorCallback 失败后的回调函数；
     */
    commHttpService.post = function ($scope, params, url, successCallback, errorCallback) {
        $http({
                  method:'post',
                  url: url,
                  params: params,
                  headers: {
                      'Content-Type': 'application/json'
                  }
              })
            .success(function (data) {
                //getToken($scope,data);
                if (data) {
                    if (successCallback) {
                        successCallback(data); // 成功后，调用成功回调函数
                    } else {
                        Window.alert("操作成功");
                    }
                }else {
                    if (errorCallback) {
                        errorCallback(data); // 失败后，调用失败回调函数
                    } else {
                        if (data.msg) {
                            Window.alert(data.msg);
                        } else {
                            Window.alert("操作失败");
                        }
                    }
                }
            })
            .error(function (data) {
                if (errorCallback) {
                    errorCallback(data); // 异常后，调用失败回调函数
                } else {
                    Window.alert("操作失败");
                }
            });
    };

    return commHttpService;
}]);