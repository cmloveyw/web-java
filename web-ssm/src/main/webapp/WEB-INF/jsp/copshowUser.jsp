<%--
  Created by IntelliJ IDEA.
  User: Zhangxq
  Date: 2016/7/16
  Time: 0:07
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html ng-app="myApp" lang="en">
<head>
    <meta charset="utf-8"/>
    <title>用户信息列表</title>
    <link rel="stylesheet" href="<%=basePath%>bootstrap/css/bootstrap.css"/>
    <link rel="stylesheet" href="<%=basePath%>assets/css/ace.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>assets/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>assets/css/font-awesome-ie7.min.css"/>
    <link rel="stylesheet" href="<%=basePath%>webUpload/webuploader1.css"/>
    <%--<link rel="stylesheet" href="<%=basePath%>webUpload/picUploader.css"/>--%>
    <link rel="stylesheet" href="<%=basePath%>jquery-chosen/chosen.css"/>
</head>
<style type="text/css">
    /* 弹窗整体样式修改 */
    .modal-footer {
        text-align: center;
    }

    .has-error .form-control, .has-error .select2-selection {
        border-color: red;
        box-shadow: 0px 0px 5px red !important;
    }
</style>
<body ng-controller="ShowUser">
<div class="main-container" id="main-container">
    <div class="page-content">
        <div class="page-header">
            <form class="form-inline" id="search_info">
                <div class="form-group">
                    <label>姓名:</label>
                    <input id="userName" name="userName" type="text" placeholder="请输入姓名:" class="form-control">
                </div>
                <button type="button" class="btn btn-info btn-sm btn-search-info"
                        id="btn_search" ng-click="searchInfoClick()">
                    <%--<i class="icon-search icon-on-right bigger-110"></i>查询--%>
                    <i class="ace-icon fa fa-search "></i>查询
                </button>
                <button type="button" class="btn btn-sm" ng-click="addUserInfo()">
                    <i class="glyphicon glyphicon-plus"></i>新增
                </button>
                <%--<button id = "importUserInfo"  class="btn btn-sm " ng-click="exportUserInfo()">
                    <i class="glyphicon glyphicon-import"></i>导入</button>--%>

                <button id="importUserInfo" type="button" class="btn btn-success btn-sm tooltips"
                        style="height: 34px;" data-toggle="tooltip" data-placement="bottom" data-html="true"
                        data-title="导入的excel文件格式如下：<br/>
						                     <table style='border:1px solid #FFFFFF'>
						                      <tr>
							                   <td style='width:70px;border-right:1px solid #FFFFFF'>姓名</td>
							                   <td style='width:70px;border-right:1px solid #FFFFFF'>手机号</td>
							                   <td style='width:70px;border-right:1px solid #FFFFFF'>邮箱</td>
						                      </tr>
						                    </table>">
                    <i class="glyphicon glyphicon-import"></i> 导入
                </button>
            </form>
        </div>

        <div class="loading">正在加载,请稍候......</div>
        <div class="row mt10" style='display:none;'>
            <div class="col-xs-12">
                <div class="row">
                    <div class="col-xs-12">
                        <div class="dataTables_wrapper ">
                            <table id="tabProduct"
                                   class="table table-striped table-bordered table-hover"
                                   style="margin-bottom: 0 ">
                                <tr>
                                    <th>姓名</th>
                                    <th>手机号</th>
                                    <th>邮箱</th>
                                    <th>操作</th>
                                </tr>
                                <tr ng-class="{true:'active'}" ng-repeat="item in UserList">
                                    <td>{{item.userName}}</td>
                                    <td>{{item.userPhone}}</td>
                                    <td>{{item.userEmail}}</td>
                                    <td>
                                        <button class="btn btn-sm btn-info " ng-click="modifyUser(item)">
                                            <i class="icon-edit"></i>
                                        </button>
                                        <button class="btn btn-sm btn-danger" ng-click="deleteUser(item)">
                                            <i class="icon-trash"></i>
                                        </button>
                                        <%--<button class="btn btn-xs btn-info">
                                            <i class="icon-edit"></i>
                                        </button>--%>
                                    </td>
                                </tr>
                                <tr ng-if="UserList == null ||UserList.length==0">
                                    <td colspan="8" class="alert alert-block alert-danger"
                                        style="text-align:center">暂无数据展示！
                                    </td>
                                </tr>
                            </table>

                            <%--<div class="row" style="margin:5px 0 0 0;"
                                 ng-if="UserList.length>0">
                                <div class="pagination" pagesize="{{page.pageSize}}"
                                     buttons="10" total="{{page.count}}"
                                     currentpage="{{page.currentPage}}"></div>
                                &lt;%&ndash; <select class="selectPageSize"></select>&ndash;%&gt;
                            </div>
                            <div id="pager"></div>
                            <input type="hidden" id="page" name="page"
                                   ng-bind="page.initialPage"/>--%>


                            <div class="row noprint" ng-show="UserList.length>0">
                                <div class="col-xs-4">
                                    <div style="margin: 15px 12px">
                                        共<span ng-bind="page.pageCount"></span>页，每页
                                        <span ng-bind="page.pageSize"></span>条，共
                                        <span ng-bind="UserList.length"></span>条数据
                                        </span>
                                    </div>
                                </div>
                                <div class="col-xs-8">
                                    <div class="pull-right" style="margin: 18px 8px;height: 32px;">
                                        <select style="height: 32px;" title="每页显示条数" ng-model="page.pageSize">
                                            <option value="10">10</option>
                                            <option value="20">20</option>
                                            <option value="30">30</option>
                                            <option value="40">40</option>
                                            <option value="60">60</option>
                                            <option value="80">80</option>
                                            <option value="100">100</option>
                                            <option value="100000">全部</option>
                                        </select>
                                    </div>
                                    <div class="pull-right" style="margin: 18px 8px;height: 32px;">
                                        <input style="height: 32px;width: 50px;text-align: center;border-radius: 0px 4px 4px 0px !important;"
                                               placeholder="页码"
                                               title="输入跳转的页码"/>
                                    </div>
                                    <ul class="pagination pull-right" page-bar-help=""></ul>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <%--<c:if test="${!empty userList}">
        <c:forEach var="user" items="${userList}">
            姓名：${user.userName} &nbsp;&nbsp;手机号：${user.userPhone} &nbsp;&nbsp;邮箱：${user.userEmail} &nbsp;&nbsp;<br>
        </c:forEach>
    </c:if>--%>
</div>

<div class="modal fade" id="updateUser" tabindex="-1" aria-hidden="true" aria-labelledby="myModelLabel" role="row">
    <div class="modal-dialog" style="width: 70%">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModelLabel">新增/修改</h4></div>
            <div class="modal-body">
                <form class="form-horizontal" id="updateUserForm" name="userForm" method="post">
                    <div class="row">
                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    <span class="red">*</span>姓名
                                </label>
                                <div ng-class="{'has-error':(userForm['updateUserName'].$invalid)}">
                                    <div class="col-sm-10">
                                        <input class="form-control" type="text" name="updateUserName"
                                               id="updateUserName" ng-required="true" data-toggle="tooltip"
                                               data-placement="top"
                                               placeholder="姓名" ng-model="updateUser.userName"
                                               title="请输入姓名">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    <span class="red">*</span>手机号
                                </label>
                                <div ng-class="{'has-error':(userForm['userPhone'].$invalid)}">
                                    <div class="col-sm-10">
                                        <input class="form-control" type="text" name="userPhone"
                                               id="userPhone" ng-required="true"
                                               placeholder="手机号" ng-model="updateUser.userPhone" data-toggle="tooltip"
                                               data-placement="top"
                                               title="请输入手机号">
                                    </div>
                                </div>
                            </div>
                        </div>

                        <div class="col-sm-6">
                            <div class="form-group">
                                <label class="col-sm-2 control-label">
                                    <span class="red">*</span>邮箱
                                </label>
                                <div ng-class="{'has-error':(userForm['userEmail'].$invalid)}">
                                    <div class="col-sm-10">
                                        <input class="form-control" type="text" name="userEmail"
                                               id="userEmail" ng-required="true"
                                               placeholder="邮箱" ng-model="updateUser.userEmail" data-toggle="tooltip"
                                               data-placement="top"
                                               title="请输入邮箱">
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="hidden">
                            <input type="text" id="id" name="id" ng-model="updateUser.id"/>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer ">
                <button id="btnClose" type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="btnSave" type="button" class="btn btn-success" ng-disabled="userForm.$invalid"
                        ng-click="saveClick()">保存<i class="icon-arrow-right icon-on-right"></i>
                </button>
            </div>
        </div>
    </div>
</div>
</div>

<script type="text/javascript" src="<%=basePath%>jquery/jquery-3.2.1.js"></script>
<script type="text/javascript" src="<%=basePath%>bootstrap/js/bootstrap.js"></script>
<script type="text/javascript" src="<%=basePath%>angularJs/angular-1.5.8/angular.js"></script>
<script type="text/javascript" src="<%=basePath%>jquery/jquery.form.js"></script>
<script type="text/javascript" src="<%=basePath%>scripts/showUser.js"></script>
<script type="text/javascript" src="<%=basePath%>common/common.js"></script>
<script type="text/javascript" src="<%=basePath%>httpService/commHttpService.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>core/core.js"></script>--%>
<%--<script type="text/javascript" src="<%=basePath%>common/common.js"></script>--%>
<script type="text/javascript" src="<%=basePath%>jqPaginator/jqPaginator.min.js"></script>
<script type="text/javascript" src="<%=basePath%>table/pageTable.js"></script>
<script type="text/javascript" src="<%=basePath%>webUpload/webuploader.js"></script>
<script type="text/javascript" src="<%=basePath%>base/base.js"></script>
<%--<script type="text/javascript" src="<%=basePath%>core/commonCrudUtilService.js"></script>
<script type="text/javascript" src="<%=basePath%>core/coreHelp.js"></script>--%>
</body>
</html>
