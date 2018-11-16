/**具体组件help；*/
/**公共searchFormHelper*/
PlatformIndexApp.directive("commonSearchFormHelper", ["commonCrudUtilService", function (commonCrudUtilService) {
    return {
        scope: false,
        restrict: "AE",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink(scope, element, attributes, ctrs) {
                },
                post: function postLink($scope, $element, $attrs, ctrs) {
                    var _formSearchModelName = $attrs.formsearchmodelname;
                    if ($attrs.isautosearch == 'true') {
                        var defaultSortTh = $("[" + _formSearchModelName + "DefaultSortField" + "]",
                            $scope.rootContainerElement);

                        $scope[_formSearchModelName].sort.field = defaultSortTh.attr("field");
                        $scope[_formSearchModelName].sort.type = defaultSortTh.attr("defaultSortType");

                        $scope[_formSearchModelName].search(1);// 初次自动运行一次
                    }
                    if ($attrs.listentaborgmenuclick == "true") {
                        $scope.$on("tabOrgMenuClick_" + $scope.rootMenuId, function () {
                            $scope[_formSearchModelName].search($scope[_formSearchModelName].page.currentPage);
                        });
                    }
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
            var _formSearchModelName = $attrs.formsearchmodelname;
            /** 搜索条件*/
            $scope[_formSearchModelName] = {};
            $scope[_formSearchModelName].formSearchData = {};

            /**分页对象 */
            $scope[_formSearchModelName].page = {
                currentPage: 1, // 当前第几页
                pageItemSize: '10', //每页显示条数
                totalItemCount: 0, // 总数据条数
                totalPageCount: 0, // 总页数
            };
            /** 排序：排序字段_asc、排序字段_desc : name_asc,name_desc*/
            $scope[_formSearchModelName].sort = {
                field: "", // 排序字段名称
                type: ""// asc,desc
            };
            $scope[_formSearchModelName + "IsLoading"] = 3;
            /**搜索按钮点击事件**/
            $scope[_formSearchModelName].search = function (currentPage) {
                $scope[_formSearchModelName + "IsLoading"] = 1;

                var _searchData = angular.copy($scope[_formSearchModelName].formSearchData);

                _searchData.pageItemSize = $scope[_formSearchModelName].page.pageItemSize;
                _searchData.sortField = $scope[_formSearchModelName].sort.field;
                _searchData.sortType = $scope[_formSearchModelName].sort.type;

                if (!currentPage || currentPage < 1) {
                    currentPage = 1;
                }

                _searchData.currentPage = currentPage;

                commonCrudUtilService.get($scope, _searchData, $attrs.url,
                                          function (data) {
                                              $scope[_formSearchModelName + "IsLoading"] = 2;

                                              $scope[_formSearchModelName].page = data.page;
                                              $scope[_formSearchModelName].page.pageItemSize =
                                                  $scope[_formSearchModelName].page.pageItemSize + "";
                                              $scope[_formSearchModelName].formSearchResult = data.content;

                                              if ($scope[_formSearchModelName].searchCallBack) {
                                                  $scope[_formSearchModelName].searchCallBack(data.content)
                                              }

                                          }, function (data) {
                        $scope[_formSearchModelName + "IsLoading"] = 2;
                        if (data.msg) {
                            PWindow.alert(data.msg);
                        } else {
                            PWindow.alert("搜索失败");
                        }
                    }
                );
            };

            /**监听修改当前页 事件*/
            $scope.$on(_formSearchModelName + '-changeCurrentPage', function (e, currentPage) {
                $scope[_formSearchModelName].search(currentPage);
            });

            /**监听修改排序 事件*/
            $scope.$on(_formSearchModelName + '-changeSortPage', function (e, sort) {
                $scope[_formSearchModelName].sort.field = sort[0];
                $scope[_formSearchModelName].sort.type = sort[1];
                $scope[_formSearchModelName].search($scope[_formSearchModelName].page.currentPage);
            });

            /**监听数据发生变化，需要刷新数据 事件*/
            $scope.$on(_formSearchModelName + '-hasDataChange', function (e, data) {
                $scope[_formSearchModelName].search($scope[_formSearchModelName].page.currentPage);
            });

            /**监听每页显示数量发生变化事件，需要刷新数据 事件*/
            $scope.$on(_formSearchModelName + '-changePageItemSize', function (e, data) {
                $scope[_formSearchModelName].search($scope[_formSearchModelName].page.currentPage);
            });

        }
    }
}])/*.directive("pageBarHelp", function () {//分页工具条 help
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
            /!*$scope.$watch($attrs.formsearchmodelname + ".page", function () {
                try {
                    $($element).jqPaginator('destroy');
                } catch (e) {

                }*!/
            //if ($scope[$attrs.formsearchmodelname] && $scope[$attrs.formsearchmodelname].page.totalItemCount > 0) {

            /!*var _totalPageCount = parseInt($scope[$attrs.formsearchmodelname].page.totalPageCount);
            var _currentPage = parseInt($scope[$attrs.formsearchmodelname].page.currentPage);

            if (_currentPage > _totalPageCount) {
                $scope.$emit($attrs.formsearchmodelname + '-changeCurrentPage', (_totalPageCount));
                return;
            }*!/

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
})*/;