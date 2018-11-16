//重新设置布局
function resetLayout(_munuId) {
    var width = $(window).width();
    var baseToolsEle = $("#contentRow_" + _munuId + " .base-tools");
    if (width >= 992) {
        var _baseToolsHeight = baseToolsEle.height();
        baseToolsEle.next().attr('style', 'margin-top:' + (_baseToolsHeight + 6) + 'px');//'margin-top:' +
                                                                                         // (_baseToolsHeight + 106)
    } else {
        baseToolsEle.next().attr('style', 'margin-top:10px');
    }
}
/**********************************/
$('#WindowAlert').modal({
                            keyboard: false,
                            show: false,
                            backdrop: "static"
                        });

var PWindow = {};
PWindow.alert = function (text, callback, $scope) {
    _alert("WindowAlert", text, callback, $scope);
};

PWindow.sucess = function (text, callback, $scope) {
    _alert("WindowAlertSucess", text, callback, $scope);
};

var CRMWindow = {};
CRMWindow.alert = PWindow.alert;

var _alert = function (windowId, text, callback, $scope) {
    $('#' + windowId).off("hidden.bs.modal");
    $('#' + windowId).on('hidden.bs.modal', function (event) {
        if (callback) {
            if ($scope) {
                $scope.$apply(function () {
                    callback();
                });
            } else {
                callback();
            }
        }
        /**
         * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 START
         * @modify liuwei 2017-03-03 14:25:11
         */
        if ($('.modal .in').length != 0) {
            $('body').addClass('modal-open');
        } else {
            $('body').removeClass('modal-open');
        }
        /**
         * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 END
         * @modify liuwei 2017-03-03 14:25:11
         */
    });
    $("#" + windowId + " .modal-body > div").html(text);
    $("#" + windowId).modal("show");
};

/**================================================*/
$('#PWindowConfirm').modal({
                               keyboard: false,
                               show: false,
                               backdrop: "static"
                           });

PWindow.confirm = function (text, trueCallback, falseCallback, $scope) {
    _confirm(text, trueCallback, falseCallback, $scope);
};
CRMWindow.confirm = PWindow.confirm;
var _confirm = function (text, trueCallback, falseCallback, $scope) {

    $('#PWindowConfirm .yesButton').off("click");
    $('#PWindowConfirm .cancelButton').off("click");

    $('#PWindowConfirm .yesButton').on("click", function () {
        $("#PWindowConfirm").modal("hide");
        if (trueCallback) {
            if ($scope) {
                $scope.$apply(function () {
                    trueCallback();
                });
            } else {
                trueCallback();
            }
        }
    });

    $('#PWindowConfirm .cancelButton').on("click", function () {
        $("#PWindowConfirm").modal("hide");
        if (falseCallback) {
            if ($scope) {
                $scope.$apply(function () {
                    falseCallback();
                });
            } else {
                falseCallback();
            }
        }
    });
    /**
     * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 START
     * @modify liuwei 2017-03-03 14:25:11
     */
    $("#PWindowConfirm").on('hidden.bs.modal', function (event) {
        /*if($('.modal .in')){
         $('body').addClass('modal-open');
         }*/
    })
    /**
     * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 END
     * @modify liuwei 2017-03-03 14:25:11
     */
    $("#PWindowConfirm .modal-body p").text(text);
    $("#PWindowConfirm").modal("show");
};

/*********************************/

var PlatformIndexApp = angular.module('myApp', []);
var CRMIndexApp = PlatformIndexApp;

/**页面核心指令*/
PlatformIndexApp.directive("container", function () {
    return {
        scope: {},
        transclude: true,
        restrict: "A",
        template: "<ng-transclude ></ng-transclude>",
        link: function (scope, element, attrs) {
            scope.name = "container";

        },
        controller: function ($scope, $element, $attrs) {
            $scope.rootMenuId = $attrs.menuid;
            this.getRootMenuId = function () {
                return $attrs.menuid;
            }
            this.getRootMenuText = function () {
                return $attrs.menutext;
            }
        }
    };
}).directive("containerContent", function () {//root指令
    return {
        scope: true,
        restrict: "E",
        require: "?^container",
        compile: function (element, attributes) {
            return {
                pre: function preLink(scope, element, attributes, ctrs) {
                    scope.rootMenuId = ctrs.getRootMenuId();//当前菜单id
                    scope.rootMenuText = ctrs.getRootMenuText();
                },
                post: function postLink(scope, element, attributes, ctrs) {
                    scope.$on("contentRowRemove", function (e, a) {
                        if (a == scope.rootMenuId) {
                            scope.$parent.$parent.$destroy();
                            scope.$parent.$destroy();
                        }

                    });
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
            this.addScopeProperty = function (pname, value) {
                $scope[pname] = value;
            };
            this.getScopeProperty = function (pname) {
                return $scope[pname];
            };
            this.setScopePropertyKeyValue = function (pname, key, value) {
                $scope[pname][key] = value;
            };
            this.getScopePropertyKeyValue = function (pname, key) {
                return $scope[pname][key];
            };
            //初始化
            function init() {
                $scope.name = "containerContent";
                $scope.rootContainerElement = $element;
            }

            init();
        }
    };
});

/**以下为 页面公共 模块 功能*/
PlatformIndexApp.controller("navbarController",
                            ["$scope", "$rootScope", "$timeout", function ($scope, $rootScope, $timeout) {
                                // $scope.title = "客户关系管理系统";
                                $scope.scopeName = "navbarController";
                                // $("#headTitle").html($scope.title);
                                /*$scope.$watch("title", function () {
                                 $("#headTitle").html($scope.title);
                                 });*/
                                $scope.limitNum = 10;
                                var changeLimitNum = function () {
                                    var clientWidth = document.documentElement.clientWidth || document.body.clientWidth;
                                    $('#main-navbar').css({
                                                              width: clientWidth - parseInt(
                                                                  $('.navbar > .container-fluid .navbar-brand')
                                                                      .css('width')) - parseInt(
                                                                  $('.navbar-right').css('width')) - 50
                                                          });
                                };
                                $(window).resize(function () {
                                    changeLimitNum();
                                });
                                // 历史记录数据
                                var historyData = JSON.parse(localStorage.getItem('historyRecord')) || [];
                                // 消息数据
                                var newsData = JSON.parse(localStorage.getItem('newsRecord')) || [];
                                // 收藏数据
                                var favarateData = JSON.parse(localStorage.getItem('favarateRecord')) || [];
                                // 搜索词
                                $scope.searchWord = '';
                                // 有效数据集合
                                var validDataList = [];
                                //处理返回数据，单独提取相关有效数据
                                var childCount = 0;  // 初始递归次数
                                var rootId = '';
                                var getValidData = function (currentData, count) {
                                    var tempDataList = currentData;
                                    angular.forEach(tempDataList, function (val, idx) {

                                        if (count == 0) {
                                            rootId = val.id;
                                            childCount = 0;
                                        }
                                        if (val.hasOwnProperty('childs')) {
                                            childCount++;
                                            return getValidData(val.childs, childCount);
                                        } else {
                                            validDataList.push({
                                                                   rootId: rootId,
                                                                   id: val.id,
                                                                   moduleName: val.moduleName
                                                               });
                                        }
                                    });
                                };
                                /**
                                 * 检查记录列表是否存在当前模块
                                 * @param id    模块id
                                 * @param recordType    记录类型
                                 */
                                var checkRecordList = function (id, recordType) {
                                    var temRecord = JSON.parse(localStorage.getItem(recordType + 'Record')) || [];
                                    var hasCurrentItem = false;
                                    angular.forEach(temRecord, function (val, idx) {
                                        if (val.id == id) {
                                            hasCurrentItem = true;
                                        }
                                    });
                                    return hasCurrentItem;
                                };
                                // 接收循环遍历结束信息，进行初始赋值
                                $scope.$on('ngRepeatFinished', function (ngRepeatFinishedEvent) {
                                    $scope.chosenData = $scope.navList[0];
                                    changeLimitNum();
                                });
                                //默认显示的下标值
                                $scope.defaultIndex = 0;
                                /**
                                 * 选择当前一级菜单
                                 * @param dataId
                                 * @param $index    鼠标悬浮时当前菜单项的下标
                                 */
                                $scope.choosePrimaryMenu = function (dataId, $index, $event) {
                                    var timer = null;
                                    $($event.target).on('mouseout', function () {
                                        $scope.$apply(function () {
                                            $timeout.cancel(timer);
                                        });
                                    });

                                    timer = $timeout(function () {
                                        $scope.defaultIndex = $index;
                                        $scope.chosenData = $scope.navList[$index];
                                    }, 50);
                                };

                                /**
                                 * 选中当前导航项
                                 * @param currentId 当前导航项的id
                                 * @param dataId    当前导航项的父级id
                                 * @param moduleName    当前导航项的模块名称
                                 */
                                $scope.chooseCurrentNavItem = function (currentId, dataId, moduleName) {
                                    if (!checkRecordList(currentId, 'history')) {
                                        // 记录历史数据
                                        historyData.push({
                                                             rootId: dataId,
                                                             id: currentId,
                                                             moduleName: moduleName
                                                         });
                                        localStorage.setItem('historyRecord', JSON.stringify(historyData));
                                    }

                                    // 获取当前点击目标的id值
                                    var currentId = 'rm_' + currentId;

                                    // 触发左侧侧边栏中与点击目标id相同的a链接点击事件
                                    $('.redirectMenu').each(function () {
                                        if ($(this).attr('id') == currentId) {
                                            $('#leftNavUl').css({top:0});
                                            $(this).trigger('click');
                                        }
                                    });
                                    // 主体导航动态添加class'active'--判断找寻的元素是否存在，如果存在则直接对其添加class，否则默认'全部'添加class
                                    if ($('#main-navbar li[data-id=' + dataId + ']').text() != '') {
                                        $('#main-navbar li[data-id=' + dataId + ']').addClass('active').siblings()
                                            .removeClass('active');
                                    } else {
                                        $('#main-navbar li').eq(0).addClass('active').siblings().removeClass('active')
                                    }
                                    // 隐藏二级菜单
                                    $('.new-base-navbar-menu').hide();
                                    $('#navbar-layer').hide();
                                };

                                /**
                                 * 收起二级菜单
                                 */
                                $scope.slideUp = function () {
                                    $('.new-base-navbar-menu').hide();
                                    $('#navbar-layer').hide();
                                };

                                $('#main-navbar').on('mouseover', 'li', function () {
                                    // 判断鼠标悬浮时间是否超过200毫秒，若超过则显示，否则隐藏对应的二级菜单
                                    var timer = null;
                                    timer = setTimeout(function () {
                                        $(this).addClass('active').siblings('.active').removeClass('active');
                                        $('.new-base-navbar-menu').show();
                                        $('.new-base-navbar-menu ul.primary-navbar').eq(0).slimScroll({
                                                                                                          height: '500px',
                                                                                                          size: '5px',
                                                                                                          position: 'left',
                                                                                                          color: '#aaa',
                                                                                                          distance: '200px',
                                                                                                          railVisible: true,
                                                                                                          alwaysVisible: true,
                                                                                                          railColor: 'rgba(55,55,55,.4)',
                                                                                                          railOpacity: 0.3,
                                                                                                          wheelStep: 10,
                                                                                                          allowPageScroll: false,
                                                                                                          disableFadeOut: false
                                                                                                      });
                                        $('.new-base-navbar-menu .secondary-navbar>div').slimScroll({
                                                                                                        height: '500px',
                                                                                                        size: '5px',
                                                                                                        position: 'right',
                                                                                                        color: '#aaa',
                                                                                                        distance: '10px',
                                                                                                        railVisible: true,
                                                                                                        railColor: 'rgba(55,55,55,.4)',
                                                                                                        railOpacity: 0.3,
                                                                                                        wheelStep: 10,
                                                                                                        allowPageScroll: false,
                                                                                                        disableFadeOut: false
                                                                                                    });
                                        $('.new-base-navbar-menu').children().eq($(this).index()).show().siblings('div')
                                            .hide();
                                        $('#navbar-layer').show();
                                    }.bind(this), 200);

                                    $(this).on('mouseout', function () {
                                        clearTimeout(timer);
                                        timer = null;
                                    });
                                });

                                $('#navbar-layer').on('mouseover', function () {
                                    $('.new-base-navbar-menu').fadeOut();
                                    $(this).hide();
                                });

                                // 显示搜索框
                                var init = false;   // 是否初始化过
                                var sarchTimer = null;
                                $scope.showSearchInput = function ($event) {
                                    clearTimeout(sarchTimer);
                                    if (!init) {
                                        getValidData($scope.navList, 0);
                                        init = true;
                                    }
                                    $scope.$watch('searchWord', function (newVal, oldVal) {
                                        $scope.resultList = [];
                                        if (newVal == "") {
                                            $scope.resultList = [];
                                        } else {
                                            angular.forEach(validDataList, function (val, idx) {
                                                if (val.moduleName.indexOf(newVal) != -1) {
                                                    $scope.resultList.push(val);
                                                }
                                            });
                                        }
                                    });
                                    $('.new-base-navbar .navbar-right .searchInput ul').slimScroll({
                                                                                                       height: '200px',
                                                                                                       size: '5px',
                                                                                                       position: 'right',
                                                                                                       color: '#aaa',
                                                                                                       // distance:
                                                                                                       // '200px',
                                                                                                       railVisible: true,
                                                                                                       railColor: 'rgba(55,55,55,.4)',
                                                                                                       railOpacity: 0.3,
                                                                                                       wheelStep: 10,
                                                                                                       allowPageScroll: false,
                                                                                                       disableFadeOut: false
                                                                                                   });
                                    $('.new-base-navbar-menu').hide();
                                    var parent = angular.element($event.target).parent();
                                    var sibling = parent.siblings('.active');
                                    parent.toggleClass('active');
                                    sibling.removeClass('active').children('div').hide();
                                    if ($('.searchInput').css('display') == 'block') {
                                        $('.searchInput').hide();
                                    } else {
                                        $('.searchInput').show();
                                        $('.new-base-navbar .navbar-right .searchInput input').get(0).focus();
                                    }
                                };
                                $('.new-base-navbar .navbar-right .searchInput input').on('blur', function () {
                                    sarchTimer = setTimeout(function () {
                                        $('.searchInput').hide(400).parent().removeClass('active');
                                    }, 100);
                                });
                                // 按下回车触发对应搜索
                                $scope.pressEnter = function ($event) {
                                    if ($event.keyCode == 13 && $scope.resultList.length == 1) {
                                        $scope.search($scope.resultList[0]);
                                    }
                                };
                                $scope.clickEnter = function () {
                                    if ($scope.resultList.length == 1) {
                                        $scope.search($scope.resultList[0]);
                                    }
                                };
                                /**
                                 * 点击搜索具体的项目
                                 * @param item 当前模块
                                 */
                                $scope.search = function (item) {
                                    $scope.chooseCurrentNavItem(item.id, item.rootId, item.moduleName);
                                    $scope.resultList = [];
                                    $scope.searchWord = '';
                                    $('.searchInput').hide().parent().removeClass('active')
                                };

                                /**
                                 * 显示历史/收藏记录
                                 * @param recordType    记录类型
                                 * @param $event    $event对象
                                 */
                                    // 是否在指定区域内部
                                var isInside = true;
                                $scope.showRecordList = function (recordType, $event) {
                                    isInside = true;
                                    $('.new-base-navbar-menu').hide();
                                    var parent = angular.element($event.target).parent();
                                    var siblings = parent.siblings('.active');
                                    $scope.recordList = [];
                                    if (JSON.parse(localStorage.getItem(recordType + 'Record')) == null || JSON.parse(localStorage.getItem(recordType + 'Record')).length == 0) {
                                        $('.new-base-navbar .navbar-right > li').removeClass('active');
                                        return PWindow.alert('暂无相应记录');
                                    }
                                    parent.toggleClass('active').children('div').toggle();
                                    siblings.removeClass('active').children('div').hide();
                                    var tempRecordList = JSON.parse(localStorage.getItem(recordType + 'Record'))
                                    // 获取本地存储对应的记录
                                    if (recordType == "history") {
                                        tempRecordList.reverse();
                                    }
                                    $scope.recordList = tempRecordList;
                                    $('.new-base-navbar .navbar-right .' + recordType + ' ul').slimScroll({
                                                                                                              height: '200px',
                                                                                                              size: '5px',
                                                                                                              position: 'right',
                                                                                                              color: '#aaa',
                                                                                                              // distance:
                                                                                                              // '200px',
                                                                                                              railVisible: true,
                                                                                                              railColor: 'rgba(55,55,55,.4)',
                                                                                                              railOpacity: 0.3,
                                                                                                              wheelStep: 10,
                                                                                                              allowPageScroll: false,
                                                                                                              disableFadeOut: false
                                                                                                          });
                                };

                                /**
                                 * 获取相关
                                 * @param item  当前模块
                                 * @param className    class名称
                                 */
                                $scope.getRelated = function (item, className) {
                                    $scope.chooseCurrentNavItem(item.id, item.rootId, item.moduleName);
                                    $('.' + className).removeClass('active').children('div').hide();
                                };
                                $rootScope.$on('careMenu', function (event, menuId) {
                                    if (!init) {
                                        getValidData($scope.navList, 0);
                                        init = true;
                                    }
                                    if (!checkRecordList(menuId, 'favarate')) {
                                        angular.forEach(validDataList, function (val, idx) {
                                            if (val.id == menuId) {
                                                favarateData = JSON.parse(localStorage.getItem('favarateRecord')) || [];
                                                favarateData.push(val);
                                                localStorage.setItem('favarateRecord', JSON.stringify(favarateData));
                                                return;
                                            }
                                        });
                                    } else {
                                        PWindow.alert('您已收藏该菜单');
                                    }
                                });

                                /**
                                 * 移除当前收藏项
                                 * @param $index    下标值
                                 * @param $event    $event对象
                                 */
                                $scope.removeCurrentFavarate = function ($index, $event) {
                                    $event.stopPropagation();
                                    angular.element($event.target).parent().hide(500);

                                    $timeout(function () {
                                        $scope.recordList.splice($index, 1);
                                        localStorage.setItem('favarateRecord', JSON.stringify($scope.recordList));
                                    }, 400);
                                };

                                $('.history,.favarate').hover(function () {
                                    isInside = true;
                                }, function () {
                                    isInside = false;
                                });

                                $(document).click(function () {
                                    if (!isInside) {
                                        $('.history').removeClass('active').children('div').hide();
                                        $('.favarate').removeClass('active').children('div').hide();
                                    }
                                })

                            }])
    .controller("sidebarController", ["$scope", "shortCutTabEventService", function ($scope, shortCutTabEventService) {

        $scope.scopeName = "sidebarController";
        $('#sidebar-collapse').click(function () {
            $scope.$broadcast('toggleChange');
        });
        shortCutTabEventService($scope);

    }]).controller("footerController", ['$scope', "$compile", function ($scope) {
    $scope.scopeName = "footerController";
    $scope.companyName = "南京墨博云舟信息科技有限公司";

}]).factory("shortCutTabEventService", ["$rootScope", "$http", "$compile", function ($rootScope, $http, $compile) {
    return function ($scope) {
        /**当前页面全局对象；所有的全局变量和全局函数都声明在此对象下；PlatformIndex.变量；PlatformIndex.方法；*/
        var PlatformIndex = {};
        /** 是否关闭了shortCutTab的timeout任务；默认true开启；false标示开启；true标识关闭 */
        PlatformIndex.shortCutTabTimeOutIsClose = true;
        /** timeout任务的句柄 */
        PlatformIndex.shortCutTabTimeOutHandle = null;

        /** 初始化shortCutTab的事件 */
        PlatformIndex.initShortCutTabEvent = function () {
            $("#shortCutTab li a span").off();// 清空（关闭图标）之前的事件
            $("#shortCutTab li").off();// 清空（标签）之前的事件

            /** 点击标签的关闭图标的事件 */
            $("#shortCutTab li a span").on("click", function () {
                var _thisLi = $(this).parent().parent();

                var containerId = "contentRow_" + _thisLi.attr("data-menuid");
                $("#" + containerId).find("#content[data-a='special']").each(function () {
                    UM.clearCache(this.id);
                })

                if (_thisLi.hasClass("active")) {
                    var _nextLi = _thisLi.next();
                    if (_nextLi.length > 0) {
                        PlatformIndex.addShortCutTabActiveStatus(_nextLi.get(0));
                        PlatformIndex.showCurrentContentRowAndHiddenOtherContentRow(
                            $(_nextLi.get(0)).attr("data-menuId"));
                    } else {
                        var _preLi = _thisLi.prev();
                        if (_preLi.length > 0) {
                            PlatformIndex.addShortCutTabActiveStatus(_preLi.get(0));
                            PlatformIndex.showCurrentContentRowAndHiddenOtherContentRow(
                                $(_preLi.get(0)).attr("data-menuId"));
                        }
                    }
                } else {
                }
                _thisLi.remove();
                $("#contentRow_" + _thisLi.attr("data-menuid")).remove();

                try {
                    $scope.$broadcast("contentRowRemove", _thisLi.attr("data-menuid"));
                } catch (e) {
                    //PWindow.alert("exception");
                }
                var umeditorCacheId = UmeditorUtils.cache_editor["contentRow_" + _thisLi.attr("data-menuid")];
                if (umeditorCacheId && umeditorCacheId.length) {
                    for (var i = 0; i < umeditorCacheId.length; i++) {
                        UM.clearCache(umeditorCacheId[i]);
                    }
                }
                return false;
            });

            /**点击标签事件 ；切换标签*/
            $("#shortCutTab li").on("click", function () {
                PlatformIndex.clearShortCutTabActiveStatus(); // 清空所有的标签的active的状态
                PlatformIndex.addShortCutTabActiveStatus(this);// 给当前点击的标签添加active状态
                $scope.$broadcast("tabOrgMenuClick_" + $(this).attr("data-menuId"), "");
                $('#contentRow_' + $(this).attr("data-menuId") + ' .scroll-header').css({marginTop:'-16px'});
                PlatformIndex.showCurrentContentRowAndHiddenOtherContentRow($(this).attr("data-menuId"));
                resetLayout($(this).attr("data-menuId"));
            });
        };

        /**显示当前的contentRow和隐藏其他contentRow*/
        PlatformIndex.showCurrentContentRowAndHiddenOtherContentRow = function (menuId) {
            $(".contentRow").hide();
            $("#contentRow_" + menuId).show();
        };

        /** 清除active状态 */
        PlatformIndex.clearShortCutTabActiveStatus = function () {
            $("#shortCutTab li").removeClass("active");
        };
        /** 添加active状态 */
        PlatformIndex.addShortCutTabActiveStatus = function (obj) {
            $(obj).addClass("active");
        };

        /** 清空timeout handle */
        PlatformIndex.clearTimeoutHandle = function (handle) {
            if (handle) {
                clearTimeout(handle);
            }
        };

        /** 向右移动处理 */
        PlatformIndex.shortCutTabBackwardButtonProcess = function () {
            var _pxNum = 50; // 一次移动的px大小

            var _tab = $("#shortCutTab");
            var _tabMarginLeft = parseInt(_tab.css("margin-left"));

            if (_tabMarginLeft + 50 == _pxNum) {
                // 如果左边到头了，就关闭任务
                PlatformIndex.shortCutTabTimeOutIsClose = true;
            } else if (_tabMarginLeft + 50 < _pxNum) {
                // 否则继续移动
                _tab.css("margin-left", _tabMarginLeft + _pxNum);
            }
            // 如果任务是 false，未关闭；执行定时任务
            if (!PlatformIndex.shortCutTabTimeOutIsClose) {
                PlatformIndex.clearTimeoutHandle(PlatformIndex.shortCutTabTimeOutHandle);// 清空任务
                PlatformIndex.shortCutTabTimeOutHandle = setTimeout(function () {
                    PlatformIndex.shortCutTabBackwardButtonProcess();
                }, 150);
            }
        };

        /** shortCutTab 向左鼠标down移动事件 */
        $("#shortCutTabBackwardButton").on("mousedown", function () {
            PlatformIndex.shortCutTabTimeOutIsClose = false;
            PlatformIndex.shortCutTabBackwardButtonProcess(); //调用向右移动
        });

        /** shortCutTab 向左鼠标up移动事件 */
        $("#shortCutTabBackwardButton").on("mouseup blur", function () {
            PlatformIndex.shortCutTabTimeOutIsClose = true; // 标识关闭
            PlatformIndex.clearTimeoutHandle(PlatformIndex.shortCutTabTimeOutHandle);// 清空任务
        });

        /********************************************************************************/

        /** shortCutTab向右鼠标 移动事件 */
        PlatformIndex.shortCutTabForwardButtonProcess = function () {
            var _pxNum = 50;
            var _shortCutTabWidth = parseInt($("#shortCutTab").css("width"));
            var _breadcrumbsWidth = parseInt($("#breadcrumbs").css("width"));

            if (_shortCutTabWidth >= (_breadcrumbsWidth - 100)) {
                var _tab = $("#shortCutTab");
                var _tabMarginLeft = parseInt(_tab.css("margin-left"));
                if ((_shortCutTabWidth + _tabMarginLeft + 50) > _breadcrumbsWidth) {
                    _tab.css("margin-left", _tabMarginLeft - _pxNum);
                }
            }
            // 如果任务是 false，未关闭；执行定时任务
            if (!PlatformIndex.shortCutTabTimeOutIsClose) {
                PlatformIndex.shortCutTabTimeOutHandle = setTimeout(function () {
                    PlatformIndex.shortCutTabForwardButtonProcess();
                }, 200);
            }
        }

        /** shortCutTab 向右鼠标down移动事件 */
        $("#shortCutTabForwardButton").on("mousedown", function () {
            PlatformIndex.shortCutTabTimeOutIsClose = false;
            PlatformIndex.shortCutTabForwardButtonProcess(); //调用向右移动
        });

        /** shortCutTab 向右鼠标up移动事件 */
        $("#shortCutTabForwardButton").on("mouseup blur", function () {
            PlatformIndex.shortCutTabTimeOutIsClose = true; // 标识关闭
            if (PlatformIndex.shortCutTabTimeOutHandle) {
                clearTimeout(PlatformIndex.shortCutTabTimeOutHandle); // 清空任务
            }
        });

        /** 全部关闭 */
        $("#shortCutTabCloseAllButton").on("click", function () {
            PWindow.confirm("确定要关闭全部页面", function () {
                $("#shortCutTab li a span").trigger("click");
                $("#shortCutTab").css('margin-left', 0);
            });
        });

        /** 向左向右 按钮 添加 鼠标事件，改变样式 */
        $("#shortCutTabBackwardButton,#shortCutTabForwardButton,#shortCutTabCloseAllButton")
            .on("mousemove", function () {
                $(this).addClass("chosen");
            });

        /** 向左向右 按钮 添加 鼠标事件，改变样式 */
        $("#shortCutTabBackwardButton,#shortCutTabForwardButton,#shortCutTabCloseAllButton")
            .on("mouseout", function () {
                $(this).removeClass("chosen");
            });

        /********************************************************************************/

        /** 点击菜单的事件 */
        var count = 1;
        $(".redirectMenu").on("click", function () {

            if (count > 0) {
                $('#waitDiv').show();// 显示等待
            }
            PlatformIndex.clearShortCutTabActiveStatus(); // 清除shortCutTab的active状态

            var _munuId = $(this).attr("id"); // 获取当前点击的菜单的id
            var _menuText = $.trim($(this).text());// 获取当前点击的菜单的文本

            var _existedShortCutTabList = $("li[data-menuId='" + _munuId + "']"); // 获取是否已经打开过

            /** 判断是否已经打开了；大于0代表打开了；否则未打开*/
            if (_existedShortCutTabList.length > 0) {
                var _existedShortCutTab = _existedShortCutTabList.get(0);
                //如果当前已经是打开并选择中的；
                if ($(_existedShortCutTab).hasClass("active")) {
                    /* todo something */
                } else {
                    //如果之前以及打开过，但当前tab不是；重新触发事件
                    $(_existedShortCutTab).trigger("click");
                }

                $("#waitDiv").hide();// 显示等待

            } else {
                /** 未打开过；需要打开 */
                    //判断tab容器的长度，是否容的下 shortCutTab 继续放tab；如果容不小，就动态增加长度
                var _shortCutTabWidth = parseInt($("#shortCutTab").css("width"));
                var _shortCutTabWrapWidth = parseInt($("#shortCutTabWrap").css("width"));
                if ((_shortCutTabWidth + 500) > _shortCutTabWrapWidth) {
                    $("#shortCutTabWrap").css("width", _shortCutTabWrapWidth + 500 + "px");
                }
                //添加tab标签；最多放6个字符；
                var _fullMenuText = _menuText;
                if (_menuText.length > 10) {
                    _menuText = _menuText.substring(0, 10);
                }
                //获得父级的文本；得到完整的菜单路径
                var _parents = $(this).parent().parentsUntil("#leftNavUl");
                var _allText = "";
                if (_parents.length > 0) {
                    _parents.each(function (i) {
                        if (this.tagName.toUpperCase() == "LI") {
                            if (i > 0) {
                                _allText = ">" + _allText;
                            }
                            _allText = $.trim($(">a", this).text()) + _allText;
                        }
                    });
                }
                //添加标签到标签栏
                var _appendText = '<li role="presentation" class="shortCutTabLiDefault active shortCutTabLiA" data-menuId="'
                                  + _munuId + '" data-menuText="' + _fullMenuText + '"><a href="#" title="' + _allText
                                  + _fullMenuText + '">' + _menuText
                                  + '&nbsp;<span aria-hidden="true" title="关闭">&times;</span></a></li>';
                $("#shortCutTab").append(_appendText);

                var _mid = $(this).attr("mid");
                var _muri = $(this).attr("muri");

                if (!_mid) {
                    _mid = ""
                }
                if (!_muri) {
                    _muri = ""
                }

                var _url = $(this).attr("href") + "?mid=" + _mid + "&muri=" + _muri;

                /**根据菜单的地址，获取内容*/
                $http.get(_url).success(function (data, status, headers, config) {
                    if (data.code && data.msg) {
                        data =
                            '<container-content><div class="alert alert-danger" role="alert">' + data.msg
                            + '</div></container-content>';
                    }
                    var contentId = "contentRow_" + _munuId;
                    var _content = "<div container id='" + contentId + "' menuid='" + _munuId + "' menutext='"
                                   + _fullMenuText + "' class='contentRow row box'>" + data + "</div>";
//                      var _content = angular.element("<div container id='contentRow_" + _munuId + "'
// class='contentRow row box'></div>");
                    $("#pageContent").append(_content);
                    //将取得的ftl页面内容的富文本id重新命名
                    $('#' + contentId).find("#richText").attr("id", contentId + "rich");
                    $('#' + contentId).find("#richTextHide").attr("id", contentId + "richhide");
                    $('#' + contentId).find("#richText2").attr("id", contentId + "rich2");
                    $('#' + contentId).find("#richText2Hide").attr("id", contentId + "rich2hide");
                    UmeditorUtils.cache_editor[contentId] = [contentId + "rich", contentId + "rich2"];

//                        console.log(_content);
                    $compile($("#contentRow_" + _munuId))($scope);
                    //显示当前contentRow，隐藏其他contentRow
                    PlatformIndex.showCurrentContentRowAndHiddenOtherContentRow(_munuId);
                    if (count == 0) {
                        $("#welcomeWaitDiv").remove();// 关闭等待
                    } else {
                        $("#waitDiv").hide();// 关闭等待
                    }
                    count++;
                    setTimeout(function () {
                        resetLayout(_munuId);
                        $(window).resize(function () {
                            resetLayout(_munuId);
                        });
                    }, 100);

                    /**
                     * 添加公共的判断弹出层是否全部关闭，
                     * 针对每打开一个tab
                     */
                    $('.modal').on('hidden.bs.modal', function (event) {
                        /**
                         * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 START
                         * @modify liuwei 2017-03-03 14:25:11
                         */
                        if ($('.modal.in:visible').length != 0) {
                            $('body').addClass('modal-open');
                        } else {
                            $('body').removeClass('modal-open');
                        }
                        /**
                         * 增加多个模态框弹出后，关闭上层模态框后，对整体滚动的影响 END
                         * @modify liuwei 2017-03-03 14:25:11
                         */
                    });
                }).error(function (data, status, headers, config) {
                    PWindow.alert("菜单获取失败", function () {
                        if (count == 0) {
                            $("#welcomeWaitDiv").remove();// 关闭等待
                        } else {
                            $("#waitDiv").hide();// 关闭等待
                        }
                        count++;
                    });
                });

            }
            PlatformIndex.initShortCutTabEvent();// 重新初始化shortCutTab事件

            /** 当前tab偏移量位移；右侧*/
            var _tab = $("#shortCutTab");
            var _tabMarginLeft = parseInt(_tab.css("margin-left"));

            var _currentTab = $("li[data-menuId='" + _munuId + "']"); // 获取当前选择的tab；
            var _currentTabOffsetLeft = $(_currentTab).offset().left;
            var _shortCutTabForwardButtonOffsetLeft = $("#shortCutTabForwardButton").offset().left;
            if ((_currentTabOffsetLeft + 100) > _shortCutTabForwardButtonOffsetLeft) {
                var _offsetLeftDistance = _currentTabOffsetLeft - _shortCutTabForwardButtonOffsetLeft;
                var _multiple = parseInt(_offsetLeftDistance / 50);
                _tab.css("margin-left", _tabMarginLeft - (_multiple * 50 + 200));
            }
            /** 当前tab偏移量位移；左侧*/
            var _shortCutTabBackwardButtonOffsetLeft = 0;
            if ((_currentTabOffsetLeft) < _shortCutTabBackwardButtonOffsetLeft) {

                var _offsetLeftDistance = _shortCutTabBackwardButtonOffsetLeft - _currentTabOffsetLeft;
                var _multiple = parseInt(_offsetLeftDistance / 50);

                _tab.css("margin-left", _tabMarginLeft + (_multiple * 50 + 50));
            }

            /*****************************************/
            //左侧菜单 展开操作
            $("#leftNavUl .active").removeClass("active");
            $("#leftNavUl .open").removeClass("open");

            $(this).parent().addClass("active");
            ($(this).parents('[data-root]')).show().siblings().hide();
            var _parents = $(this).parent().parentsUntil("#leftNavUl");
            if (_parents.length > 0) {
                _parents.each(function (i) {
                    if (this.tagName.toUpperCase() == "LI") {
                        $(this).addClass("active").addClass("open");
                    }
                });
            }
            /*****************************************/

            return false;
        });

        PlatformIndex.initShortCutTabEvent(); // 初始化标签的事件

        $(".redirectMenu:first").trigger("click"); // 页面加载默认初始化 “我的桌面”

        $rootScope.PlatformIndex = PlatformIndex;
    };
}]);