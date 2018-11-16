var myApp = angular.module('myApp', []);
myApp.controller('ShowUser', ['$scope', 'commHttpService', '$http', function ($scope, commHttpService, $http) {
    //一种方式
    /* $http({
               method: 'GET',
               url: 'http://localhost:8080/user/list'
           }).then(function successCallback(response) {
         $scope.UserList = response.data;
         console.log($scope.UserList);
     }, function errorCallback(response) {
         $scope.UserList = "";
         // 请求失败执行代码
     });

 });*/

    $scope.UserList = null;
    $scope.page = {
        pageCount: 1,
        currentPage: 1,
        pageSize: 10,
        count: 10,
        initialPage: 0,
        totalCount: 0
    };

    function findList() {
        var param = $("#search_info").serializeJson();
        var url = "http://localhost:8080/user/list";
        commHttpService.get($scope, param, url, function (response) {
            $(".loading").attr("style", "display:none");
            $(".mt10").removeAttr("style");
            $scope.UserList = response;
            console.log($scope.page);
            $scope.page.count = 10;
        });
    };
    $scope.$parent.getPage = function (currentPage) {
        $scope.page.currentPage = currentPage;
        findList();
    };

    $scope.searchInfoClick = function () {
        findList();

    };

    $scope.modifyUser = function (item) {
        $scope.updateUser = {};
        $scope.updateUser = angular.copy(item);
        console.log($scope.updateUser);
        $("#updateUser").modal("show");
    };
    // 保存
    $scope.saveClick = function () {
        if (confirm("确认保存？")) {
            console.log($scope.updateUser);
            var url = "http://localhost:8080/user/save";
            //var aa = $scope.updateUser;
            var param = $("#updateUserForm").serializeJson();
            var _param = "";
            for (var prop in param) {
                _param += prop + "=" + param[prop] + "&";
            }
            _param = _param.substring(0, _param.length - 1);
            $http({
                      method: 'post',
                      url: url,
                      data: _param,
                      headers: {
                          'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8'
                      }

                  }).success(function (req) {
                $("#updateUser").modal('hide');
                findList();
                console.log(req);

            });

            /*var aa = JSON.stringify(param);
             $http({
                       method:'post',
                       url: url,
                       data:aa,
                       headers: {
                           'Content-Type': 'application/json;charset=utf-8'
                       }

                   }).success(function(req){

                 console.log(req);

             });*/

            /*$.ajax({
                       url: url,
                       type: "post",
                       data: aa,
                       success: function (data) {
                           if (data) {
                               console.log(data);
                           } else {
                               console.log("sdfsdff");
                           }
                       }
                   });*/

            /*console.log(aa);
            commHttpService.post($scope, aa,url,null,function (data) {
                console.log(data);
                $("#updateUser").modal("hide");
            });*/

        }
    };

    $scope.addUserInfo = function () {
        $scope.updateUser = {};
        $("#updateUser").modal("show");
    };

    $scope.deleteUser = function (item) {
        var id = item.id;
        var url = "http://localhost:8080/user/delete" + "/" + id;
        commHttpService.get($scope, {}, url, function (response) {
            console.log(response);
            if (response.errorCode == 0) {
                window.alert(response.msg);
                findList();
            } else {
                window.alert("删除失败");
            }
        });
    }

    function init() {
        $(function () {
            $("[data-toggle='tooltip']").tooltip();
        });
        var url = "http://localhost:8080/user/importExcel";
        var uploader = WebUploader.create({
                                              auto: true,
                                              swf: config.picUpload + '/Uploader.swf',
                                              server: url,
                                              pick: '#importUserInfo',
                                          });
        uploader.on('uploadSuccess', function (file, response) {
            uploader.removeFile(file, true);
            uploader.reset();
            if (response) {
                console.log(response);
                if (response.errorCode == 0) {
                    window.alert(response.msg);
                    findList();
                    /* _alert("WindowAlert",response.msg,function () {
                         findList();
                     });*/
                }
            }
        });
        uploader.on('uploadError', function (file) {
            window.alert("导入失败");
            uploader.removeFile(file, true);
            uploader.reset();
            return false;
        });
        uploader.on('error', function () {
            window.alert("最多只能上传1个并且后缀是xlsx,xls的excel文件");
            uploader.reset();
            return false;
        })
        findList();
        //$("#updateUser").validation();
        /*$("#updateActivityForm").validation();*/
    };

    // 初始化
    init();

}])
    .directive("pageBarHelp", function () {//分页工具条 help
        return {
            scope: false,
            restrict: "A",
            require: "?^containerContent",
            compile: function (element, attributes) {
                return {
                    pre: function preLink(scope, element, attributes, ctrs) {
                    },
                    post: function postLink(scope, element, attributes, ctrs) {
                    }
                };
            },
            controller: function ($scope, $element, $attrs) {
                /*$scope.$watch($attrs.formsearchmodelname + ".page", function () {
                    try {
                        $($element).jqPaginator('destroy');
                    } catch (e) {

                    }*/
                //if ($scope[$attrs.formsearchmodelname] && $scope[$attrs.formsearchmodelname].page.totalItemCount > 0)
                // {

                /*var _totalPageCount = parseInt($scope[$attrs.formsearchmodelname].page.totalPageCount);
                var _currentPage = parseInt($scope[$attrs.formsearchmodelname].page.currentPage);

                if (_currentPage > _totalPageCount) {
                    $scope.$emit($attrs.formsearchmodelname + '-changeCurrentPage', (_totalPageCount));
                    return;
                }*/

                $($element).jqPaginator({
                                            pageSize: 10,
                                            currentPage: $scope.page.currentPage,
                                            totalCounts: $scope.page.count,
                                            visiblePages: 10,
                                            first: '<li class="first"><a href="javascript:void(0);">首页<\/a><\/li>',
                                            prev: '<li class="prev"><a href="javascript:void(0);"><i class="arrow arrow2"><\/i>上一页<\/a><\/li>',
                                            next: '<li class="next"><a href="javascript:void(0);">下一页<i class="arrow arrow3"><\/i><\/a><\/li>',
                                            last: '<li class="last"><a href="javascript:void(0);">末页<\/a><\/li>',
                                            page: '<li class="page"><a href="javascript:void(0);">{{page}}<\/a><\/li>',
                                        });
                //}
                //});

            }
        };
    });

/*
myApp.controller('ShowUser',['$scope', '$sce',
             function ($scope, $sce, $http) {
                $scope.UserList = null;
                $scope.findList = function(){
                    $http(
                        {
                            method:'GET',
                            url:'http://localhost:8080/user/list'
                        }
                    ).then(function successCallBack(data) {
                        $scope.UserList =data.content;
                    }),function errorCallBack() {
                        $scope.UserList ="";
                    }

                };

                function init() {
                    $scope.findList();
                };
                init();
             }]);*/
