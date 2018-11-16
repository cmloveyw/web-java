//定义公共方法
PlatformIndexApp.directive("ng-controller", ["commonCrudUtilService", "$rootScope", "$parse", "$timeout", function (commonCrudUtilService, $rootScope, $parse, $timeout) {
    return {
        scope: false,
        restrict: "E",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink(scope, element, attributes, ctrs) {
                },
                post: function postLink(scope, element, attributes, ctrs) {

                    /*动态 修改表单 的label的显示文字*/
                    scope.setLabelText = function (name, text) {
                        scope['label_' + name] = text;
                    };
                    /*动态 隐藏 表单的 一个单元；隐藏后继续占用空间*/
                    scope.setColumnVisiableHidden = function (name) {
                        scope['column_' + name + '_visible'] = false;
                    };
                    /*动态 显示 表单的 一个单元*/
                    scope.setColumnVisiableShow = function (name) {
                        scope['column_' + name + '_visible'] = true;
                    };
                    /*动态 隐藏 表单的 一个单元；隐藏后不占用空间*/
                    scope.setColumnDisplayHidden = function (name) {
                        scope['column_' + name + '_display'] = false;
                    };
                    /*动态 显示 表单的 一个单元*/
                    scope.setColumnDisplayShow = function (name) {
                        scope['column_' + name + '_display'] = true;
                    };
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
            //表单验证 方法
            $scope.invalidForm = function (formId) {
                if ($scope[formId]) {
                    var checkboxList = $(".custom-checkbox[required='true']", $("#" + formId));
                    for (var i = 0; i < checkboxList.length; i++) {
                        var _fieldNgName = $(checkboxList[i]).attr("fieldNgName");
                        var _value = $parse(_fieldNgName)($scope);
                        if (_value) {
                            var _num = 0;
                            for (var _key in _value) {
                                if (_value[_key] != "no") {
                                    _num++;
                                }
                            }
                            if (_num == 0) {
                                return true;
                            }
                        } else {
                            return true;
                        }
                    }

                    return $scope[formId].$invalid;
                }
                return false;
            };

            var saveData = function (_copy, formId, modelname, formsearchmodelname, modalId, successCallBack, saveBeforeCallBack,
                                     errorCallBack, transferDataType) {
                var isRun = true;

                if (saveBeforeCallBack) {
                    isRun = saveBeforeCallBack(_copy, formId);
                }
                if (isRun) {
                    $("#" + formId).find("select[multiple][ngoptionsmodelname]:visible").each(function () {
                        var _name = $(this).attr("name");

                        var _nameArray = _name.split(".");
                        var _stepObj;
                        for (var _i = 0; _i < _nameArray.length; _i++) {
                            if (!_stepObj) {
                                _stepObj = _copy[_nameArray[_i]];
                            } else {
                                _stepObj = _stepObj[_nameArray[_i]];
                            }
                        }
                        var _selectValueArray = _stepObj;

                        if (_selectValueArray && _selectValueArray.length > 0) {
                            for (var _i = 0; _i < _selectValueArray.length; _i++) {
                                if (_selectValueArray[_i].indexOf("_") == 0) {
                                    _selectValueArray[_i] =
                                        _selectValueArray[_i].substring(1, _selectValueArray[_i].length);
                                }
                            }
                        }
                    });

                    commonCrudUtilService.saveData($scope, _copy, $("#" + formId).attr("submiturl"),
                        function (data) {
                            if (!successCallBack) {
                                PWindow.sucess("保存成功", function () {
                                    $("#" + modalId, $scope.rootContainerElement)
                                        .modal("hide");
                                    $scope.$emit(
                                        formsearchmodelname + '-hasDataChange', ""); // 触发事件；刷新数据
                                }, $scope);
                            } else {
                                $("#" + modalId, $scope.rootContainerElement)
                                    .modal("hide");
                                $scope.$emit(formsearchmodelname + '-hasDataChange',
                                    ""); // 触发事件；刷新数据
                                if (successCallBack) {
                                    successCallBack(data, formsearchmodelname);
                                }
                            }
                        },
                        errorCallBack,
                        transferDataType
                    );
                }
            };
            ;
            /**新增修改-保存数据 */
            $scope.saveModalFormData =
                function (formId, modelname, formsearchmodelname, modalId, successCallBack, saveBeforeCallBack,
                          errorCallBack, transferDataType) {
                    if ($scope[formId].$valid) {
                        var _copy = angular.copy($scope[modelname]);
                        // 如果保存数据与初始详情获取数据一致时不请求保存数据的接口
                        if (angular.equals($scope[modelname + "init"], _copy)) {
                            PWindow.confirm("您未作任何修改,是否继续提交吗?", function () {
                                saveData(_copy, formId, modelname, formsearchmodelname, modalId, successCallBack, saveBeforeCallBack,
                                    errorCallBack, transferDataType);
                            });
                            return;
                        }
                        saveData(_copy, formId, modelname, formsearchmodelname, modalId, successCallBack, saveBeforeCallBack,
                            errorCallBack, transferDataType);
                    }
                };
            /**modal form 重置*/
            $scope.sysResetModalForm = function (modalId, modelName) {
                $scope[modelName] = angular.copy($scope[modelName + "_reset_data"]); // 还原初始数据
                $scope[$("#" + modalId).attr("formid")].$setPristine();
                $scope[$("#" + modalId).attr("formid")].$setUntouched();
            };
            /**设置modal 新增时的title*/
            $scope.setModalAddTitle = function (modalId, addTitle) {
                var modalLabelModelName = $("#" + modalId, $scope.rootContainerElement).attr("modalLabelModelName");
                var modalLableAddTitle = "";
                if (addTitle) {
                    modalLableAddTitle = addTitle;
                } else {
                    modalLableAddTitle = $("#" + modalId, $scope.rootContainerElement).attr("modalLableAddTitle");
                }
                $scope[modalLabelModelName] = modalLableAddTitle;
            };
            /**设置modal 修改时的title*/
            $scope.setModalUpdateTitle = function (modalId, updateTitle) {
                var modalLabelModelName = $("#" + modalId, $scope.rootContainerElement).attr("modalLabelModelName");
                var modalLableUpdateTitle = "";
                if (updateTitle) {
                    modalLableUpdateTitle = updateTitle;
                } else {
                    modalLableUpdateTitle = $("#" + modalId, $scope.rootContainerElement).attr("modalLableUpdateTitle");
                }
                $scope[modalLabelModelName] = modalLableUpdateTitle;
            };
            /**工具条*/
            /**
             * 新增按钮事件
             * @param modalId
             * @param modelName
             * @param defaultAddClickAfterCallBack
             * @param modalFinishedCallback
             * @param flag 显示模型，如果有值，并且是view，则只显示详情，不进行操作。
             */
            $scope.sysAddClick =
                function (modalId, modelName, defaultAddClickAfterCallBack, modalFinishedCallback, flag) {

                    if (flag == 'view') {
                        $scope[modelName].MODAL_FLAG = 'view';
                        $("#" + modalId + " button[role=sysbutton]").hide();
                        $("#" + modalId + " a[role=sysbutton]").hide();
                    } else {
                        $("#" + modalId + " button[role=sysbutton]").show();
                        $("#" + modalId + " a[role=sysbutton]").show();
                        $scope[modelName].MODAL_FLAG = 'insert';
                    }

                    Upload.initModelUpImg(modalId, modelName);
                    var umeditor, umeditor2;
                    $("#" + modalId, $scope.rootContainerElement).one("shown.bs.modal", function () {
                        var contentId = $('.contentRow:visible').attr("id");
                        if ($('#' + contentId + "rich").length != 0) {
                            umeditor = new UmeditorUtils().init(contentId + "rich");
                            umeditor.changeScope($scope, modelName)
                            var richTextField = $('#' + umeditor.getSelectorHide()).attr("name")
                            umeditor.val("");
                        }
                        if ($('#' + contentId + "rich2").length != 0) {
                            umeditor2 = new UmeditorUtils().init(contentId + "rich2");
                            umeditor2.changeScope($scope, modelName)
                            var richTextField = $('#' + umeditor2.getSelectorHide()).attr("name")
                            umeditor2.val("");
                        }
                        if (modalFinishedCallback) {
                            modalFinishedCallback(modelName, modalId);
                        }
                    })
                    $("#" + modalId, $scope.rootContainerElement).one("hide.bs.modal", function () {

                    })

                    $scope.setModalAddTitle(modalId);
                    if (defaultAddClickAfterCallBack) {
                        defaultAddClickAfterCallBack(modalId, modelName);
                    } else {
                        $("#" + modalId, $scope.rootContainerElement).modal("show");
                    }
                    $scope.sysResetModalForm(modalId, modelName);
                };
            /**预览按钮事件*/
            $scope.sysViewClick = function (modalId, modelName, selectedBoxModelName) {
                if ($scope[selectedBoxModelName] && $scope[selectedBoxModelName].length == 1) {
                    $scope[modelName] = angular.copy($scope[modelName + "_reset_data"]); // 还原初始数据
                    var _updateObj = $scope[selectedBoxModelName][0].value;
                    commonCrudUtilService.getDataById($scope, _updateObj, $(event.target).attr("getDataUrl"),
                        function (data) {
                            $scope[modelName] = data;
                            $("#" + modalId, $scope.rootContainerElement).modal("show");
                        });
                } else {
                    PWindow.alert("请勾选要查看详情的数据，只能勾选一条");
                }
            };
            /**
             * 修改按钮事件
             *
             * @param flag 显示模型，如果有值，并且是view，则只显示详情，不进行操作。
             * @param modalId 需要弹出的modal的id
             * @param modelName 弹出的modal中的form，对应的 模型 名称：modelName
             * @param selectedBoxModelName 修改时需要从 哪个 模型中去取 当前选中的 数据；
             * @param updateGetDataAfterCallBack 获取数据之后回调
             * @param modalFinishedCallback 模态框结束回调
             * @param getUpdateUrlCallback 自定义获取更新数据的url
             * */
            $scope.sysUpdateClick =
                function (modalId, modelName, selectedBoxModelName, updateGetDataAfterCallBack, modalFinishedCallback,
                          flag, getUpdateUrlCallback) {
                    //判断只能 选择一条数据
                    if ($scope[selectedBoxModelName] && $scope[selectedBoxModelName].length == 1) {

                        $scope[modelName] = angular.copy($scope[modelName + "_reset_data"]); // 还原初始数据

                        var _updateObj = $scope[selectedBoxModelName][0].value;

                        if (flag == 'view') {
                            $scope.setModalUpdateTitle(modalId, "查看详情");
                        } else {
                            $scope.setModalUpdateTitle(modalId);
                        }

                        var umeditor, umeditor2;
                        $rootScope.$on('dateFormated', function () {
                            $scope[modelName + "init"] = angular.copy($scope[modelName]);
                        });

                        var dataUrl = $(event.target).attr("getDataUrl");

                        if (getUpdateUrlCallback) {
                            var tmpUrl = getUpdateUrlCallback($(event.target), flag);
                            if (tmpUrl) {
                                // 控制数据查询地址
                                dataUrl = tmpUrl;
                            }
                        }

                        commonCrudUtilService.getDataById($scope, _updateObj, dataUrl,
                            function (data) {
                                Upload.initModelUpImg(modalId, modelName);
                                $("#" + modalId, $scope.rootContainerElement)
                                    .one("shown.bs.modal", function () {
                                        var contentId = $('.contentRow:visible')
                                            .attr("id");
                                        if ($('#' + contentId + "rich").length != 0) {
                                            umeditor =
                                                new UmeditorUtils().init(
                                                    contentId + "rich");
                                            umeditor.changeScope($scope, modelName)
                                            var richTextField = $(
                                                '#' + umeditor.getSelectorHide())
                                                .attr("name")
                                            umeditor.val(
                                                $scope[modelName][richTextField] || "");
                                        }
                                        if ($('#' + contentId + "rich2").length != 0) {
                                            umeditor2 =
                                                new UmeditorUtils().init(
                                                    contentId + "rich2");
                                            umeditor2.changeScope($scope, modelName)
                                            var richTextField = $(
                                                '#' + umeditor2.getSelectorHide())
                                                .attr("name")
                                            umeditor2.val(
                                                $scope[modelName][richTextField] || "");
                                        }

                                        if (modalFinishedCallback) {
                                            modalFinishedCallback(
                                                modelName, modalId, data, flag);
                                        }
                                        var modal = $(this)
                                    })
                                $("#" + modalId, $scope.rootContainerElement)
                                    .one("hide.bs.modal", function () {
                                        if ($('#' + modalId + '_zhezhao')) {
                                            $('#' + modalId + '_zhezhao').remove();
                                        }
                                    })
                                if (updateGetDataAfterCallBack) {
                                    updateGetDataAfterCallBack(data, modelName, modalId);
                                } else {
                                    $scope[modelName] = data;
                                    // 获取详情之后赋予初始值
                                    $("#" + modalId, $scope.rootContainerElement)
                                        .modal("show");
                                }

                                var zhezhao = "<div id='" + modalId + "_zhezhao'"
                                    + " style='position:absolute;top:0;left:0;width:100%;height:100%;z-index:999'></div>";
                                if (flag == 'view') {
                                    $scope[modelName].MODAL_FLAG = 'view';
                                    $("#" + modalId + " button[role=sysbutton]").hide();
                                    $("#" + modalId + " a[role=sysbutton]").hide();
                                    $("#" + modalId + " .modal-body").append(zhezhao)
                                } else {
                                    $("#" + modalId + " button[role=sysbutton]").show();
                                    $("#" + modalId + " a[role=sysbutton]").show();
                                    if ($('#' + modalId + '_zhezhao')) {
                                        $('#' + modalId + '_zhezhao').remove();
                                    }
                                    $scope[modelName].MODAL_FLAG = 'update';
                                    $scope[modelName + "init"] = angular.copy(data);
                                }
                            });

                    } else {
                        PWindow.alert("请勾选要修改的数据，只能勾选一条");
                    }
                };
            /**删除按钮事件*/
            $scope.sysDeleteClick = function () {
                var _submitUrl = $(event.target).attr("submiturl");
                var _formsearchmodelname = $(event.target).attr("formsearchmodelname");
                var _selectedBoxModelName = $(event.target).attr("selectallboxdataname");

                if ($scope[_selectedBoxModelName] && $scope[_selectedBoxModelName].length > 0) {
                    PWindow.confirm("确定要删除吗，共" + $scope[_selectedBoxModelName].length + "条数据", function () {
                        $scope.$apply(function () {
                            var _ids = "";
                            $($scope[_selectedBoxModelName]).each(function (i) {
                                if (i != 0) {
                                    _ids += ",";
                                }
                                _ids += $(this).val();
                            });
                            commonCrudUtilService.deleteDataByIds($scope, _ids, _submitUrl, function (data) {
                                PWindow.sucess("数据已删除", function () {
                                    $scope.$emit(_formsearchmodelname + '-hasDataChange', "");// 触发事件，刷新数据
                                }, $scope);
                            }, function (data) {
                                if (data.msg) {
                                    PWindow.alert(data.msg, function () {
                                        $scope.$emit(_formsearchmodelname + '-hasDataChange', "");
                                    }, $scope);
                                } else {
                                    PWindow.alert("数据删除异常", function () {
                                        $scope.$emit(_formsearchmodelname + '-hasDataChange', "");
                                    }, $scope);
                                }
                            });
                        });
                    });
                    //}
                } else {
                    PWindow.alert("请勾选要删除的数据");
                }
            };
            /**
             * 同步数据按钮事件
             * @param modelName 同步弹出框名称
             * @param syncDataUrl 同步数据地址
             * @param queryProgressUrl 查询同步进度地址
             */
            $scope.syncModalName = null;
            $scope.queryProgressUrl = null;
            var interval = null;
            $scope.syncDataClick = function (modalName, syncDataUrl, queryProgressUrl, needInterval) {
                PWindow.confirm("确定同步数据吗？", function () {
                    if (needInterval == "true") {
                        $("#" + modalName).modal("show");
                    }
                    $scope.syncModalName = modalName;
                    $scope.queryProgressUrl = queryProgressUrl;
                    commonCrudUtilService.get(
                        $scope,
                        {},
                        syncDataUrl,
                        function (data) {
                            if (needInterval == "true") {
                                console.log("开始定时器");
                                interval = setInterval(updateProgress, 1000);
                            } else {
                                PWindow.sucess("数据同步成功");
                                try {
                                    $scope.formSearchModel.search(1);
                                } catch (e) {

                                }
                                $("#" + modalName).modal("hide");
                            }
                        },
                        function (data) {
                            $("#" + modalName).modal("hide");
                            clearInterval(interval);
                            PWindow.alert("同步数据失败，请联系管理员");
                        }
                    );
                });
            };

            function updateProgress() {
                commonCrudUtilService.get(
                    $scope,
                    {},
                    $scope.queryProgressUrl,
                    function (data) {
                        console.log(data);
                        $("#progressBar").attr("style", "width:" + data.content + "%;");
                        $("#progress").text(data.content + "%");
                        if (data.content == 100) {
                            console.log("清除定时器");
                            clearInterval(interval);
                            $("#progressBar").attr("style", "width:" + 100 + "%;");
                            $("#progress").text("100%");
                            try {
                                $scope.formSearchModel.search(1);
                            } catch (e) {

                            }
                            PWindow.sucess("同步数据完成");
                            $("#" + $scope.syncModalName).modal("hide");
                            $("#progressBar").attr("style", "width:" + 1 + "%;");
                            $("#progress").text("");
                        }
                    },
                    function (data) {
                        console.log(data);
                        $("#" + $scope.syncModalName).modal("hide");
                        $("#progress").text("");
                        clearInterval(interval);
                        PWindow.alert("同步数据失败，请联系管理员");
                    }
                );
            }

            /**导出*/
            function setExportButtonDisabled(target) {
                target.attr("disabled", "disabled");
                target.text("正在导出，请耐心等待");
            }

            function setExportButtonEnabled(target, enabledText) {
                target.removeAttr("disabled");
                target.text(enabledText);
            }

            /**自定义导出*/
            $scope.sysExport = function () {
                var _formsearchmodelname = $(event.target).attr("formsearchmodelname");
                var _selectedBoxModelName = $(event.target).attr("selectallboxdataname");
                var _searchData = angular.copy($scope[_formsearchmodelname].formSearchData);
                _searchData.sortField = $scope[_formsearchmodelname].sort.field;
                _searchData.sortType = $scope[_formsearchmodelname].sort.type;
                /**----------------------------------------------------------------------*/
                if ($scope[_selectedBoxModelName] && $scope[_selectedBoxModelName].length > 0) {
                    var _ids = "";
                    $($scope[_selectedBoxModelName]).each(function (i) {
                        if (i != 0) {
                            _ids += ",";
                        }
                        _ids += $(this).val();
                    });
                    _searchData.exportIds = _ids;
                }
                /**----------------------------------------------------------------------*/
                var _exportUrl = $(event.target).attr("exportUrl");
                _searchData.isExport = 1;
                var target = $(event.target);
                var _enabledText = target.text();
                setExportButtonDisabled(target);
                commonCrudUtilService.get($scope, _searchData, _exportUrl,
                    function (data) {
                        setExportButtonEnabled(target, _enabledText);
                        window.open(data.content);
                    }, function (data) {
                        setExportButtonEnabled(target, _enabledText);
                        if (data.msg) {
                            PWindow.alert(data.msg);
                        } else {
                            PWindow.alert("导出失败");
                        }
                    }
                );
            };
            /**公共导出*/
            $scope.sysCommonExport = function () {
                var _formsearchmodelname = $(event.target).attr("formsearchmodelname");
                var _selectedBoxModelName = $(event.target).attr("selectallboxdataname");
                var _url = $(event.target).attr("url");

                var _searchData = angular.copy($scope[_formsearchmodelname].formSearchData);
                _searchData.sortField = $scope[_formsearchmodelname].sort.field;
                _searchData.sortType = $scope[_formsearchmodelname].sort.type;
                /**兼容之前的查询*/
                _searchData.pageItemSize = "10000000";
                _searchData.currentPage = "1";
                /**----------------------------------------------------------------------*/
                if ($scope[_selectedBoxModelName] && $scope[_selectedBoxModelName].length > 0) {
                    var _ids = "";
                    $($scope[_selectedBoxModelName]).each(function (i) {
                        if (i != 0) {
                            _ids += ",";
                        }
                        _ids += $(this).val();
                    });
                    _searchData.exportIds = _ids;
                }
                /**----------------------------------------------------------------------*/
                var _exportColumns = [];
                _searchData.exportTitle = $scope.rootMenuText;
                var _tableFields = $("table[form-search-data-table] th[field]", $scope.rootContainerElement);
                for (var i = 0; i < _tableFields.length; i++) {
                    var _column = $(_tableFields[i]).attr("field");
                    var _text = $(_tableFields[i]).attr("text");
                    var _fieldtype = $(_tableFields[i]).attr("fieldtype");
                    _exportColumns.push({"d": _text, "c": _column, "t": _fieldtype});
                }
                _searchData.exportColumns = _exportColumns;
                _searchData.isCommonExport = 1;

                var target = $(event.target);
                var _enabledText = target.text();

                setExportButtonDisabled(target);

                commonCrudUtilService.get($scope, _searchData, _url,
                    function (data) {
                        setExportButtonEnabled(target, _enabledText);
                        window.open(data.content);
                    }, function (data) {
                        setExportButtonEnabled(target, _enabledText);
                        if (data.msg) {
                            PWindow.alert(data.msg);
                        } else {
                            PWindow.alert("导出失败");
                        }
                    }
                );
            };
            /**审核通过*/
            $scope.sysAuditYesClick = function () {
                var _url = $(event.target).attr("url");
                var _formsearchmodelname = $(event.target).attr("formsearchmodelname");
                var _selectedBoxModelName = $(event.target).attr("selectallboxdataname");

                if ($scope[_selectedBoxModelName] && $scope[_selectedBoxModelName].length > 0) {
                    PWindow.confirm("确定要审核通过吗，共" + $scope[_selectedBoxModelName].length + "条数据", function () {
                        $scope.$apply(function () {
                            var _ids = "";
                            $($scope[_selectedBoxModelName]).each(function (i) {
                                if (i != 0) {
                                    _ids += ",";
                                }
                                _ids += $(this).val();
                            });
                            var _params = {ids: _ids, type: "SUCCESS"}
                            commonCrudUtilService.post($scope, {}, _url, _params, function (data) {
                                $scope.$emit(_formsearchmodelname + '-hasDataChange', "");// 触发事件，刷新数据
                            }, function (data) {
                                if (data.msg) {
                                    PWindow.alert(data.msg);
                                } else {
                                    PWindow.alert("操作失败");
                                }
                            });
                        });
                    });
                } else {
                    PWindow.alert("请勾选要审核通过的数据");
                }
            };
            /**审核不通过*/
            $scope.sysAuditNoClick = function () {
                var _url = $(event.target).attr("url");
                var _formsearchmodelname = $(event.target).attr("formsearchmodelname");
                var _selectedBoxModelName = $(event.target).attr("selectallboxdataname");

                if ($scope[_selectedBoxModelName] && $scope[_selectedBoxModelName].length > 0) {
                    PWindow.confirm("确定要不通过吗，共" + $scope[_selectedBoxModelName].length + "条数据", function () {
                        $scope.$apply(function () {
                            var _ids = "";
                            $($scope[_selectedBoxModelName]).each(function (i) {
                                if (i != 0) {
                                    _ids += ",";
                                }
                                _ids += $(this).val();
                            });
                            var _params = {ids: _ids, type: "FAILED"}
                            commonCrudUtilService.post($scope, {}, _url, _params, function (data) {
                                $scope.$emit(_formsearchmodelname + '-hasDataChange', "");// 触发事件，刷新数据
                            }, function (data) {
                                if (data.msg) {
                                    PWindow.alert(data.msg);
                                } else {
                                    PWindow.alert("操作失败");
                                }
                            });
                        });
                    });
                } else {
                    PWindow.alert("请勾选要不通过的数据");
                }
            };
            /**打印按钮按钮事件*/
            $scope.printListClick = function () {
                $(".printListArea", $scope.rootContainerElement).removeClass("noprint").addClass("yesprint");
                window.print();
                $(".printListArea", $scope.rootContainerElement).removeClass("yesprint").addClass("noprint");
            };
            /**下载按钮事件*/
            $scope.sysDownloadClick = function (selectedBoxModelName) {
                if ($scope[selectedBoxModelName]
                    && $scope[selectedBoxModelName].length == 1) {
                    var _url = $(event.target).attr("url");

                    var _updateObj = $scope[selectedBoxModelName][0].value;
                    commonCrudUtilService.get($scope, {ids: _updateObj}, _url,
                        function (data) {
                            window.open(data.content);
                        }, function (data) {
                            if (data.msg) {
                                PWindow.alert(data.msg);
                            } else {
                                PWindow.alert("下载失败");
                            }
                        });
                } else {
                    PWindow.alert("请勾选要下载的数据，只能勾选一条");
                }
            };

            /**列表复选卡只能选择1一个*/
            $scope.sysIsDisabledForSingle = function (selectallboxdataname) {
                if ($scope[selectallboxdataname] && $scope[selectallboxdataname].length == 1) {
                    return false;
                } else {
                    return true;
                }
            };
            /**列表复选卡最少要选择1一个*/
            $scope.sysIsDisabledForMulti = function (selectallboxdataname) {
                if ($scope[selectallboxdataname] && $scope[selectallboxdataname].length > 0) {
                    return false;
                } else {
                    return true;
                }
            };
            /**表单查询的列表必须要有数据*/
            $scope.sysFormSearchResultNotEmpty = function (formsearchmodelname) {
                if ($scope[formsearchmodelname] && $scope[formsearchmodelname].formSearchResult
                    && $scope[formsearchmodelname].formSearchResult.length > 0) {
                    return false;
                } else {
                    return true;
                }
            };

            $scope.fromModelArray = function (filed, model) {
                if (angular.isArray(filed)) {
                    var v = "";
                    for (var i = 0; i < filed.length; i++) {
                        if (i != 0) {
                            v += "、";
                        }
                        v += model[filed[i]];
                    }
                    return v;
                } else {
                    return model[filed];
                }

            };
        }
    };
}])
// 循环结束指令,当ng-repeat循环结束发送消息
    .directive('onFinishRender', ['$timeout', function ($timeout) {
        return {
            restricet: 'A',
            link: function (scope, element, attr) {
                if (scope.$last === true) {
                    $timeout(function () {
                        scope.$emit('ngRepeatFinished');
                    });
                }
            }
        };
    }])
    // 收藏触发指令
    .directive('caredHeartDirective', ['$rootScope', function ($rootScope) {
        return {
            template: '<div class="care-opertion"><p>收藏菜单<i class="glyphicon glyphicon-heart"></i></p></div>',
            replace: true,
            restrict: 'EAC',
            link: function postLink(scope, iElement, iAttrs) {
                $(iElement).on('click', function () {
                    var offset = $(".favarate").offset();
                    var flyer = $('<i class="glyphicon glyphicon-heart cared-heart"></i>');
                    flyer.fly({
                        start: {
                            left: event.pageX,
                            top: event.pageY
                        },
                        end: {
                            left: offset.left + 10,
                            top: offset.top + 10,
                            width: 0,
                            height: 0
                        },
                        onEnd: function () {
                            $rootScope.$emit('careMenu', scope.rootMenuId.slice(3));
                        }
                    });
                });
            }
        }
    }]);
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
}]).directive("pageBarHelp", function () {//分页工具条 help
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
                //if ($scope[$attrs.formsearchmodelname] && $scope[$attrs.formsearchmodelname].page.totalItemCount > 0) {

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
}).directive("pageBarInputNumHelp", function () {//输入需要跳转的页 help
    return {
        scope: false,
        restrict: "A",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attributes, $ctrs) {
                },
                post: function postLink($scope, $element, $attributes, $ctrs) {

                    $($element).on("keyup", function () {
                        var _val = $.trim($($element).val());
                        if (_val != "") {
                            _val = _val.replace(/\D/g, "");
                            if (_val != "") {
                                _val = parseInt(_val) + "";
                                if (_val == "0") {
                                    _val = "";
                                }
                            }
                            $($element).val(_val);
                        }
                        if (event.keyCode == 13 && _val && _val != "") {
                            $scope.$emit($attributes.formsearchmodelname + '-changeCurrentPage', parseInt(_val));

                            $($element).val("");
                        }
                    });
                }
            };
        },
        controller: function ($scope, $element, $attrs) {

        }
    };
}).directive("pageItemSizeBarHelp", function () {//每页显示条数 help
    return {
        scope: false,
        restrict: "A",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attributes, $ctrs) {
                },
                post: function postLink($scope, $element, $attributes, $ctrs) {
                    $($element).on("change", function () {
                        $scope.$emit($attributes.formsearchmodelname + '-changePageItemSize', "");
                    });
                }
            };
        },
        controller: function ($scope, $element, $attrs) {

        }
    };
}).directive("formSearchDataTable", ["commonCrudUtilService", function (commonCrudUtilService) {//搜索后显示数据的table
    return {
        scope: false,
        restrict: "A",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, element, attributes, ctrs) {
                },
                post: function postLink($scope, element, attributes, ctrs) {

                    /**表格头 排序 事件*/
                    $("thead>tr>th[class^='sorting']", element).on("click", function () {
                        var _sort = "asc";
                        var _to_sort = "sorting";
                        if ($(this).hasClass("sorting")) {
                            _sort = "asc";
                            _to_sort = "sorting_asc";
                        } else if ($(this).hasClass("sorting_asc")) {
                            _sort = "desc";
                            _to_sort = "sorting_desc";
                        } else if ($(this).hasClass("sorting_desc")) {
                            _sort = "asc";
                            _to_sort = "sorting_asc";
                        }
                        $("thead>tr>th[class^='sorting']", element)
                            .removeClass("sorting")
                            .removeClass("sorting_desc")
                            .removeClass("sorting_asc")
                            .addClass("sorting");

                        $(this).removeClass("sorting").addClass(_to_sort);

                        var _field = $(this).attr("field");
                        $scope.$emit(attributes.formsearchmodelname + '-changeSortPage', [_field, _sort]);
                    });

                    /**表格多选功能 start*/
                    $(".selectAllBox", element).on("click", function () {
                        var _allBox = this;
                        $scope.$apply(function () {
                            var _checked = $(_allBox).prop("checked");
                            if (_checked) {
                                $(".selectItemBox", element).prop("checked", true);
                                ctrs.addScopeProperty(attributes.selectallboxdataname,
                                    $(".selectItemBox:checked[value!='']", element));

                            } else {
                                $(".selectItemBox", element).prop("checked", false);
                                ctrs.addScopeProperty(attributes.selectallboxdataname, []);
                            }
                        });
                    });
                    /**监听表格数据，如果发生变化，就还原box的状态*/
                    $scope.$watch(attributes.formsearchmodelname + ".formSearchResult", function () {
                        $(".selectAllBox", element).prop("checked", false);// 设置为未选中

                        ctrs.addScopeProperty(attributes.selectallboxdataname, []); // 清空checkbox选择

                        /**每次表格数据变化后（重新查询后）；需要重新给没一条数据的checkbox；重新加上事件*/
                        $(".selectItemBox", element).on("click", function () {
                            // var _checked = $(this).prop("checked");
                            var _item = this;
                            $scope.$apply(function () {
                                var _selectAllBoxData = ctrs.getScopeProperty(attributes.selectallboxdataname);
                                var _index = $.inArray(_item, _selectAllBoxData);
                                if (_index != -1) {
                                    _selectAllBoxData.splice(_index, 1);
                                } else {
                                    if (_item.value && _item.value != "") {
                                        _selectAllBoxData.push(_item);
                                    }
                                }
                            });
                        });

                        $('[data-toggle="tooltip"]').tooltip();// 初始化tooltip

                    });
                    /**表格多选功能 end*/

                    /* 选择当前单元行,更改checkbox选中状态 */
                    $scope.chooseThis = function ($event, selectAllBoxDataName) {
                        var element = $event.target;
                        if (element.nodeName == "TD") {
                            var _selectAllBoxData = ctrs.getScopeProperty(selectAllBoxDataName);

                            var $inputCheckbox = $(element).parent().children('td.noprint').children()
                                .children('input[type=checkbox]');
                            // 获取当前元素在inputBox数组中的下标值
                            var index = $.inArray($inputCheckbox.get(0), _selectAllBoxData);
                            // 将checkbox的checked设置为其相反值
                            $inputCheckbox.prop({checked: !$inputCheckbox.prop('checked')});

                            $inputCheckbox.prop('checked') ? _selectAllBoxData.push($inputCheckbox.get(0))
                                : _selectAllBoxData.splice(index, 1);
                        }
                    };
                }
            };
        },
        controller: function ($scope, $element, $attrs) {

        }
    };
}]).directive("toolsBarHelp", ["commonCrudUtilService", function (commonCrudUtilService) { // 工具条
    return {
        scope: false,
        restrict: "C",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attrs, ctrs) {
                },
                post: function postLink($scope, $element, $attrs, ctrs) {

                }
            };
        },
        controller: function ($scope, $element, $attrs) {
        }
    };
}]).directive("modalConfigHelp", ["commonCrudUtilService", function (commonCrudUtilService) {//新增时弹出的 modal help
    return {
        scope: false,
        restrict: "C",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attrs, $ctrs) {
                },
                post: function postLink($scope, $element, $attrs, $ctrs) {
                    $($element).modal({backdrop: $attrs.backdrop, keyboard: $attrs.keyboard, show: false});
                    var initModalHeight = function () {
                        $element.css({height: $('.modal-backdrop', element).height()});
                    };
                    initModalHeight();
                    $(window).resize(function () {
                        initModalHeight();
                    });
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
        }

    };
}]).directive("tooltipHelp", ["commonCrudUtilService", function (commonCrudUtilService) {//tooltipHelp help
    return {
        scope: false,
        restrict: "AC",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attrs, $ctrs) {
                },
                post: function postLink($scope, $element, $attrs, $ctrs) {
                    $('[data-toggle="tooltip"]').tooltip();
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
        }

    };
}]).directive("formHelp", function () { // form表单 初始化 help
    return {
        scope: false,
        restrict: "AC",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attrs, $ctrs) {
                },
                post: function postLink($scope, $element, $attrs, $ctrs) {
                    // 备份初始化表单数据；用于以后 reset表单 时候，还原数据
                    $scope[$attrs.modelname + "_reset_data"] = angular.copy($scope[$attrs.modelname]);
                }
            };
        },
        controller: function ($scope, $element, $attrs) {

        }
    };
}).directive("formFileHelp", function () { // form表单普通file控件 初始化 help
    return {
        scope: false,
        restrict: "AC",
        require: "?^containerContent",
        compile: function (element, attributes) {
            return {
                pre: function preLink($scope, $element, $attrs, $ctrs) {
                },
                post: function postLink($scope, $element, $attrs, $ctrs) {

                    var modelname = $($($element)[0].form).attr("modelname");

                    $($element).change(function () {
                        $scope.$apply(function () {
                            if ($($element).val() != "") {
                                $scope[modelname][$attrs.modelname] = "1";
                                $($element).removeClass("validErrorClass");
                            } else {
                                $scope[modelname][$attrs.modelname] = "";
                                $($element).removeClass("validErrorClass").addClass("validErrorClass");
                            }
                        });
                    });
                    $scope.$watch(modelname + "." + $attrs.modelname, function (newValue) {
                        if (newValue == "") {
                            $($element).val("");
                        }
                    });
                }
            };
        },
        controller: function ($scope, $element, $attrs) {
        }
    };
}).directive('scrollHeader', ['$timeout', function ($timeout) {
    return {
        restrict: 'AC',
        controller: function ($scope, $element, $attrs) {

        },
        compile: function compile(tElement, tAttrs, transclude) {
            return function postLink(scope, iElement, iAttrs, controller) {
                var loadHeader = function (isChecked) {
                    $(iElement).find('thead').html($(iElement).next().find('thead').html());
                    $(iElement).find('th').each(function (index, element) {
                        $(element).css({width: $(iElement).next().find('th').eq(index).css('width')});
                    });
                    $(iElement).find('.selectAllBox').prop('checked', isChecked);
                };
                scope.$watch(iAttrs.formsearchmodelname + ".formSearchResult", function (newVal) {
                    $timeout(function () {
                        loadHeader(false);
                    }, 300);
                });
                $(window).scroll(function () {
                    if ($(window).scrollTop() > 0) {
                        $(iElement).show();
                    } else {
                        $(iElement).hide();
                    }
                    $(iElement).css({marginLeft: -$(window).scrollLeft() + "px"})
                });
                $(window).scrollTop(0);
                scope.$on('toggleChange', function (event) {
                    $timeout(function () {
                        loadHeader($(iElement).next().find('.selectAllBox').prop('checked'));
                    }, 200)
                });
                $(window).resize(function () {
                    loadHeader($(iElement).next().find('.selectAllBox').prop('checked'));
                });
                $(iElement).on('click', 'th', function () {
                    if ($(this).find('.selectAllBox').length == 0) {
                        $(iElement).next().find('th').eq($(this).index()).trigger('click');
                        $timeout(function () {
                            loadHeader($(iElement).next().find('.selectAllBox').prop('checked'));
                        }, 100)
                    }
                });
                $(iElement).on('click', '.selectAllBox', function () {
                    $(iElement).next().find('.selectAllBox').trigger('click');
                })
            }
        },
        link: function postLink(scope, iElement, iAttrs) {

        }
    };
}]).directive('multiUploadImg', ['$timeout', '$parse', function ($timeout, $parse) {
    return {
        restrict: 'AC',
        controller: function ($scope, $element, $attrs) {

        },
        compile: function compile(tElement, tAttrs, transclude) {
            return function postLink(scope, iElement, iAttrs, controller) {
                scope.$watch(iAttrs.modelname + '.' + iAttrs.name, function (newVal) {
                    if (newVal == '') {
                        // scope[iAttrs.tempImgList] = [];
                        $parse(iAttrs.tempImgList + '= []')(scope);
                    } else {
                        if (newVal) {
                            if (newVal.split(',').length > 1) {
                                // scope[iAttrs.tempImgList] = newVal.split(',');
                                $parse(iAttrs.tempImgList + "=" + JSON.stringify(newVal.split(',')))(scope);
                            } else {
                                var tempList = $parse(iAttrs.tempImgList)(scope);
                                if (iAttrs.maxCount && tempList.length
                                    >= iAttrs.maxCount) {
                                    return PWindow.alert('最多上传' + iAttrs.maxCount + '张图片');
                                }
                                if (tempList.indexOf(newVal) == -1) {
                                    tempList.push(newVal);
                                    if (eval(iAttrs.isNgForm)) {
                                        scope[iAttrs.modelname][iAttrs.modelChildName][iAttrs.extensionId] =
                                            tempList.join(',');
                                    } else {
                                        scope[iAttrs.modelname][iAttrs.name] =
                                            tempList.join(',');
                                    }
                                    $parse(iAttrs.tempImgList + '=' + JSON.stringify(tempList))(scope);
                                }
                            }
                        }
                    }
                });
                /**
                 * 删除图片
                 * @param $index
                 * @param name 当前name字段
                 * @param targetImgList  目标源图片数组
                 */
                scope.deleteImg = function ($index, name, targetImgList) {
                    targetImgList.splice($index, 1);
                    if (eval(iAttrs.isNgForm)) {
                        scope[iAttrs.modelname][iAttrs.modelChildName][iAttrs.extensionId] =
                            targetImgList.join(',');
                        return false;
                    }
                    scope[iAttrs.modelname][name] = targetImgList.join(',');
                };
                /**
                 * 预览图片
                 * @param $index
                 * @param targetImgList  目标源图片数组
                 */
                scope.previewImg = function ($index, targetImgList) {
                    $('#' + iAttrs.modelname + '-preview-img').modal('show');
                    $('#' + iAttrs.modelname + '-preview-img img').attr('src', targetImgList[$index]);
                };
            }
        },
        link: function postLink(scope, iElement, iAttrs) {

        }
    };
}]).directive('ngFormUpImgHelp', ['$timeout', function ($timeout) {
    return {
        restrict: 'AC',
        require: "?^containerContent",
        controller: function ($scope, $element, $attrs) {

        },
        compile: function compile(tElement, tAttrs, transclude) {
            return function postLink($scope, iElement, iAttrs, controller) {
                var modalId = $scope.rootMenuId + '_' + iAttrs.modelname.substr(0, iAttrs.modelname.length - 5) + '_modal';
                Upload.destoryUploadImg(iAttrs.modelname);

                $timeout(function () {
                    Upload.initUploadImg(iAttrs.modelname);
                });
            }
        },
        link: function postLink($scope, iElement, iAttrs) {

        }
    }
}]);
