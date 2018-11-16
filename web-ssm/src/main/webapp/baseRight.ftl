<#import "./commComponet.ftl" as comp/>
<#--=============================================================================================-->
<#--像页面注入 angular model；js中可以通过$scope.name
    name：模型的名称
    data：数据
-->
<#macro sys_register_js_model name data>
<div ng-init='${name}=${data}' class="hide"></div>
</#macro>
<#--=============================================================================================-->
<#--像页面注入 angular 指令；通过这个功能，你可以在指令中给当前scope添加 方法；或者创建一些特定业务的model等
        比如：下面的 fromFunction 这个注入的方法，需要你提前定义好，那几可以需要在指令中进行定义这些方法；
        当时指令的 restrict 必须是 E 元素
    name：指令的名称
-->
<#macro sys_register_js_directive name
formSearchModelName=""
viewModelName=""
updateModelName="">

<${name}
formsearchmodelname="formSearch${formSearchModelName}Model"
viewmodelname="view${viewModelName}Model"
updatemodelname="add${updateModelName}Model"
></${name}>
</#macro>
<#--=============================================================================================-->
<#--容器-->
<#macro sys_container_content>
<container-content>
    <ngfunctions></ngfunctions>
    <cared-heart-directive class="noprint"></cared-heart-directive>
    <#nested />
</container-content>
</#macro>
<#--=============================================================================================-->
<#--命令描述：tableList的 搜索块
    参数：formUrl：表单搜索提交的地址
    参数：fields：搜索字段
    参数：remark：搜索字段备注
    参数：formSearchModelName：搜索model名称，默认为空
    参数：hasOptype：是否有按钮操作栏
    参数：isAutoSearch：是否默认打开页面时候就触发搜索
    参数：directive：加载指令名称
-->
<#macro sys_search_form
formUrl=""
fields=[]
remarks=[]
formSearchModelName=""
hasOptype=true
isAutoSearch="true"
directive="common-search-form-helper"
viewmodelname=""
<#--是否监听 页面打开之后，再点击菜单和tab标签-->
listenTabOrgMenuClick="true"

<#--tableList的 工具栏
updateModelName:新增和修改时表单对应的modal的名称；
updateGetDataUrl：修改时，获取修改数据的地址:格式 : /demo/update/; 会自动在后拼接一个id；get请求
deleteUrl：删除提交的url
exportUrl:导出的url
operatTypes:有哪些操作类型以及他的一些属性
-->
<#--<#macro sys_table_tools_bar-->
operatTypes={}
updateModelName=""
viewModelName=""
formSearchModelName=""

<#--通过注入 指令名称 调用########################################################################-->
<#--禁用的指令名称-->
forbiddenDirective=""
forbiddenButtonText="禁用"
forbiddenUrl=""
<#--解禁的指令名称-->
unForbiddenDirective=""
unForbiddenButtonText="解禁"
unForbiddenUrl=""
<#--通过注入 方法名称 调用########################################################################-->
<#--“新增” 按钮的事件名称，显示文字-->
addClickName="sysAddClick"
addButtonText="新增"
addButtonIsDisplay=true
<#--点击新增后的回调函数；默认null-->
defaultAddClickAfterCallBackFunction="null"

<#--“修改” 按钮的事件名称，显示文字，以及修改的url-->
updateClickName="sysUpdateClick"
updateButtonText="修改"
updateGetDataUrl=""
updateButtonIsDisplay=true
<#--点击修改获取数据成功了之后的回掉函数；默认null-->
defaultUpdateGetDataAfterCallBackFunction="null"
<#--自定义更新时获取更新的数据的url；点击修改获取数据之前的回掉函数，返回结果规定为获取数据的地址；默认null-->
defaultGetUpdateUrlCallbackFunction="null"

<#-- 新增、修改，模态框加载成功后的回调函数，用在一些特别的dom加载，比如上传图片控件，富文本控件 (modalName,modalId[,data] -->
defaultModalFinishedCallback="null"

<#--“超级权限” 按钮的事件名称，显示文字，以及url-->
superButtonText=""
superUrl=""
superDirective=""
superClickName="sysSuperClick"
<#--点击修改获取数据成功了之后的回掉函数；默认null-->
defaultSuperAfterCallBackFunction="null"

<#--“删除” 按钮的事件名称，显示文字，以及删除的url-->
deleteClickName="sysDeleteClick"
deleteButtonText="删除"
deleteUrl=""
deleteButtonIsDisplay=true

<#--“查看详情” 按钮的事件名称，显示文字-->
viewClickUseUpdate=false  <#-- 使用修改来显示查看详情，省去适配查看详情 -->
viewClickName="sysViewClick"
viewButtonText="查看详情"
viewGetDataUrl=""
viewButtonIsDisplay=true   <#-- 查看详情按钮是否显示 -->

<#--“导出” 按钮的事件名称，显示文字，以及导出的url-->
exportClickName="sysExport"
exportButtonText="导出"
exportUrl=""

<#--“审核通过” 按钮的事件名称，显示文字，以及审核通过的url-->
auditYesClickName="sysAuditYesClick"
auditYesButtonText="审核通过"
auditYesUrl=""

<#--“审核不通过” 按钮的事件名称，显示文字，以及审核不通过的url-->
auditNoClickName="sysAuditNoClick"
auditNoButtonText="审核不通过"
auditNoUrl=""

<#--打印列表-->
printListClickName="printListClick"
printListButtonText="打印列表"
printListUrl=""
printListButtonIsDisplay=true
printListTitle="-1"

<#--公共导出-->
commonExportClickName="sysCommonExport"
commonExportButtonText="公共导出"
commonExportUrl=""
commonExportButtonIsDisplay=true

<#--查看修改记录-->
historyRecordsClickName="sysHistoryRecordsClick"
historyRecordsButtonText="查看历史记录"
historyRecordsUrl=""

<#--“下载” 按钮的事件名称，显示文字，以及下载的url-->
downloadClickName="sysDownloadClick"
downloadButtonText="下载"
downloadUrl=""

<#--============================-->
<#--充值的指令名称-->
rechargeDirective=""
rechargeButtonText="充值"
rechargeUrl=""
rechargeDisableType="single"
rechargeButtonIsDisplay=true

<#--取款的指令名称-->
withdrawDirective=""
withdrawButtonText="取款"
withdrawUrl=""
withdrawDisableType="single"
withdrawButtonIsDisplay=true

<#--挂失的指令名称-->
lossDirective=""
lossButtonText="挂失"
lossUrl=""
lossDisableType="multi"

<#--解挂的指令名称-->
unLossDirective=""
unLossButtonText="解挂"
unLossUrl=""
unLossDisableType="multi"

<#--校验的指令名称-->
verifyDirective=""
verifyButtonText="卡校验位检测"
verifyUrl=""

<#--校验的指令名称-->
checkDirective=""
checkButtonText="卡余额校队"
checkUrl=""

<#--注销的指令名称-->
deRegisterDirective=""
deRegisterButtonText="注销"
deRegisterUrl=""
deRegisterOpType="deregister"
deResisterDisableType="single"

<#--写卡的指令名称-->
writeCardDirective=""
writeCardButtonText="写卡"
writeCardUrl=""
writeCardDisableType="single"

<#--卡还原的指令名称-->
resetCardDirective=""
resetCardButtonText="卡还原"
resetCardUrl=""

<#--发卡的指令名称-->
issueDirective=""
issueButtonText="发卡"
issueUrl=""

<#--读卡的指令名称-->
readCardDirective=""
readCardButtonText="读卡"
readCardUrl=""
readCardDisableType=""

<#--退卡的指令名称-->
returnCardDirective=""
returnCardButtonText="退卡"
returnCardUrl=""

<#--卡还原的指令名称-->
resetDirective=""
resetButtonText="卡还原"
resetUrl=""

<#--卡密码修改的指令名称-->
updatePwdDirective=""
updatePwdButtonText="卡密码重置"
updatePwdUrl=""
updatePwdDisableType="single"

<#--卡密码重置的指令名称-->
resetPwdDirective=""
resetPwdButtonText="卡密码重置"
resetPwdUrl=""

<#--上架的指令名称-->
upShelfDirective=""
upShelfButtonText="上架"
upShelfUrl=""
<#--下架的指令名称-->
offShelfDirective=""
offShelfButtonText="下架"
offShelfUrl=""

<#--同步数据的指令名称-->
syncDataClickName="syncDataClick"
syncDataButtonText="同步数据"
syncDataUrl=""
syncDataModelName=""
<#-- 查询同步进度地址 -->
querySyncProgressUrl=""
<#-- 是否需要定时器 -->
needInterval="true"

<#--============================-->
isNeedResetFormBtn="false"
<#--通过注入自定义 按钮 调用########################################################################-->
<#--动态添加按钮
    [
        {name:saveButton,text:"新增",jsDirective:指令,permiss:归属权限}
    ]
-->
barRegisterButtons=[]
>

    <#local formSearchDataModelName="formSearch${formSearchModelName}Model"/>
    <#local addModelName = "add${updateModelName}Model" />
    <#local allBoxDataModelName="${addModelName}-selectAllBoxData"/>

<div class="breadcrumbs breadcrumbs-fixed base-tools operation-bar">

    <div class="base-tools-search noprint"
        <#if (fields?size<=0)>
         style="display: none"
        </#if>
    >
        <div class="panel panel-primary">
        <#--<div class="panel-heading">工具栏</div>-->
            <div class="panel-body">
                <form class="form-inline tableSearchForm"
                      listentaborgmenuclick="${listenTabOrgMenuClick}"
                ${directive}
                      url="${SYS_BASE_PATH_ADMIN}${formUrl}"
                      formsearchmodelname="${formSearchDataModelName}"
                      isautosearch="${isAutoSearch}"
                      viewmodelname="view${viewmodelname}Model"
                >

                    <#list fields as _field>
                        <#local _type=_field.type!"text" />
                        <#local _jsDirective=_field.jsDirective!"" />
                        <#local _placeholder = _field.placeholder!("输入"+_field.text) />
                        <#local _relationName = _field.relationName!"" />

                        <div class="form-group">

                            <#if _type != "static"><#--静态的；有个默认值；页面是看不到的-->
                                <#if  _relationName =="">
                                    <label for="exampleInputName2" class="font-bold">${_field.text}：</label>
                                <#else>
                                <#--添加像时间范围这种搜索条件-->
                                    <label for="exampleInputName2" class="font-bold">${_relationName}</label>
                                </#if>
                            </#if>

                            <#if _type=="text" >
                                <input type="${_type}"
                                       class="form-control"
                                       placeholder="${_placeholder}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >

                            <#elseif _type == "int">
                                <input type="text" checking-int
                                       class="form-control"
                                       placeholder="${_placeholder}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                       modelname="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >

                            <#elseif _type == "checkbox">
                            <#--  <#local _defaultValue =_field.defaultValue!""  />-->
                                <input type="checkbox"
                                       class="form-control" style="width:18px"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                       ng-true-value="'yes'"
                                       ng-false-value="'no'"
                                       checked="checked"
                                ${_jsDirective}
                                >

                            <#elseif _type == "float">
                                <input type="text"
                                       checking-float
                                       class="form-control"
                                       placeholder="${_placeholder}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                       modelname="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >
                            <#elseif _type == "year">
                                <input type="text"
                                       search-year-help=""
                                       class="date-picker form-control"
                                       placeholder="选择${_field.text}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >
                            <#elseif _type == "month">
                                <input type="text"
                                       year-month-help=""
                                       class="date-picker form-control"
                                       placeholder="选择${_field.text}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >
                            <#elseif _type == "date">
                                <#local _defaultValue =_field.defaultValue!"" />
                                <#local _readonly =_field.readonly!false />
                                <#if _readonly == true>
                                    <input type="text"
                                           search-date-help=""
                                           class="date-picker form-control"
                                           placeholder="选择${_field.text}"
                                           ng-init="${formSearchDataModelName}.formSearchData.${_field.name}='${_defaultValue}'"
                                           ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                           readonly style="cursor:default!important;"
                                    ${_jsDirective}
                                    >
                                <#elseif _readonly == false>
                                    <input type="text"
                                           search-date-help=""
                                           class="date-picker form-control"
                                           placeholder="选择${_field.text}"
                                           ng-init="${formSearchDataModelName}.formSearchData.${_field.name}='${_defaultValue}'"
                                           ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                           style="cursor:default!important;"
                                    ${_jsDirective}
                                    >
                                </#if>


                            <#elseif _type == "datetime">
                                <input type="text"
                                       search-date-time-help=""
                                       class="form-control"
                                       placeholder="选择${_field.text}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                       readonly style="cursor:default!important;"
                                ${_jsDirective}
                                >
                            <#elseif _type == "hourtime">
                                <input type="text"
                                       hour-help=""
                                       class="form-control"
                                       placeholder="选择${_field.text}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >
                            <#elseif _type == "selecthourtime">
                                <input type="text"
                                       search-hour-help=""
                                       class="form-control"
                                       placeholder="选择${_field.text}"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >

                            <#elseif _type == "static"><#--静态的；有个默认值；页面是看不到的-->
                                <span ng-init="${formSearchDataModelName}.formSearchData.${_field.name}='${_field.defaultValue}'"></span>

                            <#elseif _type == "staticText">
                                <input type="text"
                                       class="form-control"
                                       ng-init="${formSearchDataModelName}.formSearchData.${_field.name}='${_field.defaultValue}'"
                                       ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                       disabled
                                >

                            <#elseif _type=="select">
                                <#local _key =_field.key!"itemCode" />
                                <#local _keyText = _field.keyText!"itemName" />
                                <#local _useNgOptions = _field.useNgOptions!false />

                                <select class="form-control" search-select2-help select-num-format=""
                                        ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                    <#if _useNgOptions>
                                        ngoptionsmodelname="${_field.ngOptionsModelName}"
                                    </#if>
                                >
                                    <option value="">请选择</option>
                                    <#if _useNgOptions>
                                        <option ng-repeat="option in ${_field.ngOptionsModelName}"
                                                value="{{option.${_key}}}">{{option.${_keyText}}}
                                        </option>
                                    <#else>
                                        <#list _field.data as _selectData>
                                            <option
                                                    ng-selected="${formSearchDataModelName}.formSearchData.${_field.name}
                                                     == '${_selectData[_key]}'"
                                                    value="${_selectData[_key]}">${_selectData[_keyText]}</option>
                                        </#list>
                                    </#if>
                                </select>
                            <#elseif _type=="select_normal">
                                <#local _key =_field.key!"itemCode" />
                                <#local _keyText = _field.keyText!"itemName" />

                                <select class="form-control"
                                        ng-model="${formSearchDataModelName}.formSearchData.${_field.name}"
                                ${_jsDirective}
                                >
                                    <option value="">请选择</option>
                                    <#list _field.data as _selectData>
                                        <option
                                                ng-selected="${formSearchDataModelName}.formSearchData.${_field.name}
                                                 == ${_selectData[_key]}"
                                                value="${_selectData[_key]}">${_selectData[_keyText]}</option>
                                    </#list>
                                </select>

                            <#elseif _type=="searchTreeView">
                                <#local _key =_field.key!"itemCode" />
                                <#local _keyText = _field.keyText!"itemName" />
                                <#local _useNgOptions = _field.useNgOptions!false />
                            <#--是否多选-->
                                <#local _isMulti =_field.isMulti!false />
                                <#if _isMulti>
                                    <#local _defaultValue =_field.defaultValue!"[]" />
                                <#else>
                                    <#local _defaultValue =_field.defaultValue!"''" />
                                </#if>
                                <div style="position: relative;" class="searchTreeViewComponent">
                                    <div class="form-control" style="min-width:180px;overflow: hidden;"
                                         tree-view-operation
                                    ${_jsDirective}
                                         request-url="${_field.requestUrl}"
                                         ng-init="${formSearchDataModelName}.formSearchData.${_field.name}=${_defaultValue}"
                                         request-id="${_field.key}"
                                         is-multi="${_isMulti?string}"
                                         modelname="${formSearchDataModelName}.formSearchData.${_field.name}">
                                        <span class="current">请选择</span>
                                        <span class="pull-right"><b class="caret"></b></span>
                                    </div>
                                    <div class="selectTreeView" style="display: none;">

                                    </div>
                                </div>
                            </#if>

                        </div>
                    </#list>

                    <button type="button" class="btn btn-primary btn-sm btn-round2 button-base"
                            ng-click="${formSearchDataModelName}.search(1)">
                        <i class="ace-icon fa fa-search bigger-120 "></i>查询
                    </button>
                    <#if isNeedResetFormBtn == "true">
                        <button type="button" class="btn btn-primary btn-sm btn-round2"
                                ng-click="${formSearchDataModelName}.reset()">
                            <i class="ace-icon fa fa-reply bigger-120 "></i>重置
                        </button>
                    </#if>
                </form>
            <#-- 查询条件说明-->
                <div
                    <#if (remarks?size<=0)>
                            style="display: none"
                    </#if>
                >
                    <#list remarks as _remark>
                        <div style="height: 15px;margin-top:10px;line-height:0px;!important">
                            <label style="color: red;">${_remark.text}</label>
                        </div>
                    </#list>
                </div>
            </div>
        </div>
    </div>
    <div class="base-tools-operation noprint"
        <#if (operatTypes?size > 0 || barRegisterButtons?size >0 || hasOptype)>

        <#else>
         style="display: none"
        </#if>
    >


        <div class="btn-group pull-left tools-bar tools-bar-help" formsearchmodelname="${formSearchDataModelName}"
             role="group"
             aria-label="...">
            <#if sys_the_url_has_own_menu_permissions>
            <#--系统级判断-->
            <#--查看详情-->
                <#if sys_user_permissions_for_menu.retrieve?? && sys_user_permissions_for_menu.retrieve &&
                viewGetDataUrl != "" && viewButtonIsDisplay>
                <#--<#if operatTypes.retrieve?? && operatTypes.retrieve>-->
                    <#if viewClickUseUpdate>
                        <button type="button"
                                id="${addModelName}_updatebtn"
                                ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                                class="btn btn-primary btn-sm button-common button-hover updateButton"
                                selectallboxdataname="${allBoxDataModelName}"
                                formsearchmodelname="${formSearchDataModelName}"
                                ng-click="${updateClickName}(
                                    rootMenuId + '_add${updateModelName}_modal',
                                    '${addModelName}',
                                    '${allBoxDataModelName}',
                                    ${defaultUpdateGetDataAfterCallBackFunction},
                                    ${defaultModalFinishedCallback},
                                    'view',
                                    ${defaultGetUpdateUrlCallbackFunction})"
                                getDataUrl="${SYS_BASE_PATH_ADMIN}${updateGetDataUrl}"
                        >${viewButtonText}</button>
                    <#else >
                        <button type="button"
                                ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                                class="btn btn-primary btn-sm button-common button-hover viewButton"
                                selectallboxdataname="${allBoxDataModelName}"
                                formsearchmodelname="${formSearchDataModelName}"
                                ng-click="${viewClickName}(rootMenuId + '_view${viewModelName}_modal','view${viewModelName}Model','${allBoxDataModelName}')"
                                getDataUrl="${SYS_BASE_PATH_ADMIN}${viewGetDataUrl}"
                        >${viewButtonText}</button>
                    </#if>

                <#--</#if>-->
                </#if>
            <#--新增-->
                <#if sys_user_permissions_for_menu.create?? && sys_user_permissions_for_menu.create && addButtonIsDisplay>
                <#--用户定义级别判断-->
                <#--<#if operatTypes.create?? && operatTypes.create>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover addButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${addClickName}(rootMenuId + '_add${updateModelName}_modal','${addModelName}',${defaultAddClickAfterCallBackFunction},${defaultModalFinishedCallback})"
                    >${addButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--修改-->
                <#if sys_user_permissions_for_menu.update?? && sys_user_permissions_for_menu.update && updateButtonIsDisplay>
                <#--<#if operatTypes.update?? && operatTypes.update>-->
                    <button type="button"
                            id="${addModelName}_updatebtn"
                            ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover updateButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${updateClickName}(
                                rootMenuId + '_add${updateModelName}_modal',
                                '${addModelName}',
                                '${allBoxDataModelName}',
                                 ${defaultUpdateGetDataAfterCallBackFunction},
                                 ${defaultModalFinishedCallback},
                                 null,
                                 ${defaultGetUpdateUrlCallbackFunction})"
                            getDataUrl="${SYS_BASE_PATH_ADMIN}${updateGetDataUrl}"
                    >${updateButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--删除-->
                <#if sys_user_permissions_for_menu.delete?? && sys_user_permissions_for_menu.delete && deleteButtonIsDisplay>
                <#--<#if operatTypes.delete?? && operatTypes.delete>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover deleteButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${deleteClickName}()"
                            submiturl="${SYS_BASE_PATH_ADMIN}${deleteUrl}"
                    >${deleteButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--同步数据-->
                <#if sys_user_permissions_for_menu.synchronize?? && sys_user_permissions_for_menu.synchronize>
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover syncButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${syncDataUrl}"
                            ng-click="${syncDataClickName}('sync${syncDataModelName}Modal',
                            '${syncDataUrl}','${querySyncProgressUrl}','${needInterval}')"
                    >${syncDataButtonText}
                    </button>
                </#if>
            <#--导出-->
                <#if sys_user_permissions_for_menu.export?? && sys_user_permissions_for_menu.export>
                <#--<#if operatTypes.export?? && operatTypes.export>-->
                    <button type="button"
                            ng-disabled="sysFormSearchResultNotEmpty('${formSearchDataModelName}')"
                            class="btn btn-primary btn-sm button-hover exportButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${exportClickName}()"
                            exportUrl="${SYS_BASE_PATH_ADMIN}${exportUrl}"
                    >${exportButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--审核-->
                <#if sys_user_permissions_for_menu.audit?? && sys_user_permissions_for_menu.audit>
                <#--<#if operatTypes.audit?? && operatTypes.audit>-->
                <#--审核通过-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover auditYesButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${auditYesClickName}($event)"
                            url="${SYS_BASE_PATH_ADMIN}${auditYesUrl}"
                    >${auditYesButtonText}</button>
                <#--审核不通过-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover auditNoButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${auditNoClickName}($event)"
                            url="${SYS_BASE_PATH_ADMIN}${auditNoUrl}"
                    >${auditNoButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--查看历史记录-->
                <#if sys_user_permissions_for_menu.historyMark?? && sys_user_permissions_for_menu.historyMark>
                <#--<#if operatTypes.historyMark?? && operatTypes.historyMark>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-hover historyMarkButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${historyRecordsClickName}('${allBoxDataModelName}')"
                            url="${SYS_BASE_PATH_ADMIN}${historyRecordsUrl}"
                    >${historyRecordsButtonText}</button>
                <#--</#if>-->
                </#if>
            <#--禁用-->
                <#if sys_user_permissions_for_menu.forbidden?? && sys_user_permissions_for_menu.forbidden>
                <#--<#if operatTypes.forbidden?? && operatTypes.forbidden>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover forbiddenButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${forbiddenUrl}"
                    ${forbiddenDirective}
                    >${forbiddenButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--解禁-->
                <#if sys_user_permissions_for_menu.unForbidden?? && sys_user_permissions_for_menu.unForbidden>
                <#--<#if operatTypes.unForbidden?? && operatTypes.unForbidden>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover unForbiddenButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${unForbiddenUrl}"
                    ${unForbiddenDirective}
                    >${unForbiddenButtonText}
                    </button>
                <#--</#if>-->
                </#if>

            <#--上架-->
                <#if sys_user_permissions_for_menu.upShelf?? && sys_user_permissions_for_menu.upShelf>
                <#--<#if operatTypes.forbidden?? && operatTypes.forbidden>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover forbiddenButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${upShelfUrl}"
                    ${upShelfDirective}
                    >${upShelfButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--下架-->
                <#if sys_user_permissions_for_menu.offShelf?? && sys_user_permissions_for_menu.offShelf>
                <#--<#if operatTypes.unForbidden?? && operatTypes.unForbidden>-->
                    <button type="button"
                            ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-common button-hover unForbiddenButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${offShelfUrl}"
                    ${offShelfDirective}
                    >${offShelfButtonText}
                    </button>
                <#--</#if>-->
                </#if>

            <#--超级权限-->
                <#if sys_user_permissions_for_menu.super?? && sys_user_permissions_for_menu.super && superUrl != "">
                <#--<#if operatTypes.super?? && operatTypes.super>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover superButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${superClickName}(rootMenuId + '_add${updateModelName}_modal','${addModelName}','${allBoxDataModelName}',${defaultSuperAfterCallBackFunction})"
                            url="${SYS_BASE_PATH_ADMIN}${superUrl}"
                    ${superDirective}
                    >${superButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--打印列表-->
                <#if sys_user_permissions_for_menu.printList?? && sys_user_permissions_for_menu.printList && printListButtonIsDisplay>
                <#--<#if operatTypes.printList?? && operatTypes.printList>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover printButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${printListClickName}()"
                            url="${SYS_BASE_PATH_ADMIN}${printListUrl}"
                    >${printListButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--公共导出-->
                <#if sys_user_permissions_for_menu.commonExport?? && sys_user_permissions_for_menu.commonExport && commonExportButtonIsDisplay>
                    <button type="button"
                            ng-disabled="sysFormSearchResultNotEmpty('${formSearchDataModelName}')"
                            class="btn btn-primary btn-sm button-hover commonExportButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${commonExportClickName}('${allBoxDataModelName}')"
                            url="${SYS_BASE_PATH_ADMIN}${formUrl}"
                    >${commonExportButtonText}</button>
                </#if>

            <#--公共下载-->
                <#if sys_user_permissions_for_menu.commonDownload?? && sys_user_permissions_for_menu.commonDownload && downloadUrl != "">
                    <button type="button"
                            ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                            class="btn btn-primary btn-sm button-hover downloadButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            ng-click="${downloadClickName}('${allBoxDataModelName}')"
                            url="${SYS_BASE_PATH_ADMIN}${downloadUrl}"
                    >${downloadButtonText}</button>
                </#if>
            <#--===============================================================================================-->

            <#--充值-->
                <#if sys_user_permissions_for_menu.recharge?? && sys_user_permissions_for_menu.recharge && rechargeButtonIsDisplay>
                <#--<#if operatTypes.recharge?? && operatTypes.recharge>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover rechargeButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${rechargeUrl}"
                        <@disableText
                        disabledType=rechargeDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${rechargeDirective}
                    >${rechargeButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--取款-->
                <#if sys_user_permissions_for_menu.withdraw?? && sys_user_permissions_for_menu.withdraw && withdrawButtonIsDisplay>
                <#--<#if operatTypes.withdraw?? && operatTypes.withdraw>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover withdrawButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${withdrawUrl}"
                        <@disableText
                        disabledType=withdrawDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${withdrawDirective}
                    >${withdrawButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--挂失-->
                <#if sys_user_permissions_for_menu.loss?? && sys_user_permissions_for_menu.loss>
                <#--<#if operatTypes.loss?? && operatTypes.loss>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover lossButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${lossUrl}"
                        <@disableText
                        disabledType=lossDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${lossDirective}
                    >${lossButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--解挂-->
                <#if sys_user_permissions_for_menu.unLoss?? && sys_user_permissions_for_menu.unLoss>
                <#--<#if operatTypes.unLoss?? && operatTypes.unLoss>-->
                    <button type="button"
                            class="btn btn-primary btn-sm unLossButton button-common button-hover"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${unLossUrl}"
                        <@disableText
                        disabledType=unLossDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${unLossDirective}
                    >${unLossButtonText}
                    </button>
                <#--</#if>-->
                </#if>
            <#--卡校验位检测-->
                <#if sys_user_permissions_for_menu.verify?? && sys_user_permissions_for_menu.verify>
                <#--<#if operatTypes.verify?? && operatTypes.verify>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover verifyButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${verifyUrl}"
                    ${verifyDirective}
                    >${verifyButtonText}
                    </button>
                <#--</#if>-->
                </#if>

            <#--卡余额校队-->
                <#if sys_user_permissions_for_menu.check?? && sys_user_permissions_for_menu.check>
                <#--<#if operatTypes.check?? && operatTypes.check>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover checkButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${checkUrl}"
                    ${checkDirective}
                    >${checkButtonText}
                    </button>
                <#--</#if>-->
                </#if>

            <#--注销-->
                <#if sys_user_permissions_for_menu.deregister?? && sys_user_permissions_for_menu.deregister>
                <#--<#if operatTypes.deregister?? && operatTypes.deregister>-->
                    <button type="button"
                            class="btn btn-primary btn-sm deregisterButton button-common button-hover"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${deRegisterUrl}"
                            optype="${deRegisterOpType}"
                        <@disableText
                        disabledType=deResisterDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${deRegisterDirective}
                    >${deRegisterButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--写卡-->
                <#if sys_user_permissions_for_menu.writeCard?? && sys_user_permissions_for_menu.writeCard>
                <#--<#if operatTypes.writeCard?? && operatTypes.writeCard>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover writeCardButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${writeCardUrl}"
                        <@disableText
                        disabledType=writeCardDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${writeCardDirective}

                    >${writeCardButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--发卡-->
                <#if sys_user_permissions_for_menu.issue?? && sys_user_permissions_for_menu.issue>
                <#--<#if operatTypes.issue?? && operatTypes.issue>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover issueButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${issueUrl}"

                    ${issueDirective}

                    >${issueButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--读卡-->
                <#if sys_user_permissions_for_menu.readCard?? && sys_user_permissions_for_menu.readCard>
                <#--<#if operatTypes.readCard?? && operatTypes.readCard>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover readCardButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${readCardUrl}"
                        <@disableText
                        disabledType=readCardDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${readCardDirective}

                    >${readCardButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--退卡-->
                <#if sys_user_permissions_for_menu.returnCard?? && sys_user_permissions_for_menu.returnCard>
                <#--<#if operatTypes.returnCard?? && operatTypes.returnCard>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover returnCardButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${returnCardUrl}"

                    ${returnCardDirective}

                    >${returnCardButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--写卡-->
                <#if sys_user_permissions_for_menu.reset?? && sys_user_permissions_for_menu.reset>
                <#--<#if operatTypes.reset?? && operatTypes.reset>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover resetButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${resetUrl}"

                    ${resetDirective}

                    >${resetButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--卡密码修改-->
                <#if sys_user_permissions_for_menu.updatePwd?? && sys_user_permissions_for_menu.updatePwd>
                <#--<#if operatTypes.updatePwd?? && operatTypes.updatePwd>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover updatePwdButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${updatePwdUrl}"
                        <@disableText
                        disabledType=updatePwdDisableType
                        allBoxDataModelName=allBoxDataModelName
                        ></@disableText>
                    ${updatePwdDirective}

                    >${updatePwdButtonText}</button>
                <#--</#if>-->
                </#if>

            <#--卡密码重置-->
                <#if sys_user_permissions_for_menu.resetPwd?? && sys_user_permissions_for_menu.resetPwd>
                <#--<#if operatTypes.resetPwd?? && operatTypes.resetPwd>-->
                    <button type="button"
                            class="btn btn-primary btn-sm button-hover resetPwdButton"
                            selectallboxdataname="${allBoxDataModelName}"
                            formsearchmodelname="${formSearchDataModelName}"
                            url="${SYS_BASE_PATH_ADMIN}${resetPwdUrl}"

                    ${resetPwdDirective}

                    >${resetPwdButtonText}</button>
                <#--</#if>-->
                </#if>



            <#--循环自定义的按钮-->
                <#if barRegisterButtons?size gt 0>
                    <#list barRegisterButtons as button>
                        <#if sys_user_permissions_for_menu[button.permiss]?? && sys_user_permissions_for_menu[button.permiss]>
                            <#local _url=button.url!"" />
                            <#local _jsDirective=button.jsDirective!"" />
                            <#local _optype=button.optype!"" />
                            <#local _jsClickScopeFunction=button.jsClickScopeFunction!"" />

                        <#--disabledType：single(只有选择单挑数据的时候可用)，
                            multi（只有选择一条或在多条的时候可用）；
                            notEmpty(表格数据不为空的时候可用)-->
                            <#local _disabledType=button.disabledType!"" />
                        <#-- 弹出模态框名称 -->
                            <#local _newModalName = button.newModelName!"" />

                            <button type="button"
                                    class="btn btn-primary btn-sm button-common button-hover ${button.name}"
                                    selectallboxdataname="${allBoxDataModelName}"
                                    formsearchmodelname="${formSearchDataModelName}"
                                    url="${SYS_BASE_PATH_ADMIN}${_url}"

                                <#if _disabledType == "single">
                                    ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
                                <#elseif _disabledType == "multi">
                                    ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
                                <#elseif _disabledType == "notEmpty">
                                    ng-disabled="sysFormSearchResultNotEmpty('${formSearchDataModelName}')"
                                <#else></#if>

                                <#if _jsClickScopeFunction != "">
                                    ng-click="${_jsClickScopeFunction}($event,'add${_newModalName}_modal')"
                                </#if>
                            ${_jsDirective}
                                    optype="${_optype}"
                            >${button.text}</button>
                        </#if>
                    </#list>
                </#if>
            </#if>
        </div>
    </div>
</div>

<div class="printListArea noprinttable noprint">
    <div class="alert alert-danger text-left yesprint" style="display: none;" role="alert">
        <#if printListTitle != "-1"><span>${printListTitle}</span></#if>
        <#if printListTitle == "-1"><span ng-bind="rootMenuText"></span></#if>
    </div>
</div>
</#macro>
<#-- 禁用文本 -->
<#macro disableText
disabledType=""
allBoxDataModelName=""
>
    <#if disabledType == "single">
    ng-disabled="sysIsDisabledForSingle('${allBoxDataModelName}')"
    <#elseif disabledType == "multi">
    ng-disabled="sysIsDisabledForMulti('${allBoxDataModelName}')"
    <#elseif disabledType == "notEmpty">
    ng-disabled="sysFormSearchResultNotEmpty('${formSearchDataModelName}')"
    <#else></#if>
</#macro>
<#--=============================================================================================-->
<#--tableList 的 数据显示table-->
<#--
    参数：tableFields：定义的显示列
         showBox：显示 全选复选框：默认false
         idName:数据的id的字段名称
         showOperat:显示操作 一栏：false
         operatTypes:定义有哪些操作按钮

         deleteUrl：删除提交的url;同上面的删除一样
-->
<#macro sys_form_table
tableFields=[]
showOperat=false
showCheckBox=false
idName="id"
updateModelName=""
formSearchModelName=""

<#-- 参数说明：selectionCallback 是用来做为弹出层列表中选择按钮的回调函数 -->
selectionCallback=""
<#-- 参数说明：selectionTableClass 针对Table的Class进行特殊设定 -->
selectionTableClass=""
<#-- 参数说明：selectionTableStyle 针对Table的Style进行特殊设定 -->
selectionTableStyle=""
<#-- 参数说明 用来控制回调选择按钮是否可以点击 默认可以点-->
ngDisabled = false
<#-- 参数说明 用来控制回调选择按钮是否可以点击的函数-->
ngDisabledFun = ""
>
    <#local addModelName = "add${updateModelName}Model">
    <#local allBoxDataModelName="${addModelName}-selectAllBoxData"/>
    <#local formSearchDataModelName="formSearch${formSearchModelName}Model"/>
<div class="scroll-header noprint" formsearchmodelname="${formSearchDataModelName}">
    <table class="table table-striped table-bordered table-hover dataTable no-footer printListArea noprint
       printWidthPer100 tableCustom ${selectionTableClass}"
           style="${selectionTableStyle}">
        <thead>
        </thead>
        <tbody></tbody>
    </table>
</div>

<table form-search-data-table=""
       selectallboxdataname="${allBoxDataModelName}"
       updatemodalid="{{rootMenuId}}_add${updateModelName}_modal"
       modelname="${addModelName}"
       formsearchmodelname="${formSearchDataModelName}"
       class="table table-striped table-bordered table-hover dataTable no-footer printListArea noprint
       printWidthPer100 tableCustom ${selectionTableClass}"
       style="${selectionTableStyle}"
       role="grid"
       aria-describedby="sample-table-2_info">
    <thead>
        <tr role="row">
        <#--=============================================================================================-->
            <#if showCheckBox>
                <th class="center noprint selectAllBoxTh">
                    <label class="position-relative">
                        <input type="checkbox" class="ace selectAllBox"
                               ng-disabled="${formSearchDataModelName}.page.totalItemCount == 0">
                        <span class="lbl"></span>
                    </label>
                </th>
            </#if>
            <th nowrap="nowrap" class="searchTableNoTh center">序号</th>
        <#--=============================================================================================-->
        <#--迭代 表头 列 -->
            <#list tableFields as _field>
                <#local _type=_field.type!'text' />
                <th nowrap="nowrap"
                    <#local _sort=_field.sort!true />
                    <#if _sort>
                        <#if _field.defaultSort??>
                    class="sorting_${_field.defaultSort}" tabindex="0"
                    ${formSearchDataModelName}DefaultSortField="${_field.name}"
                    defaultSortType="${_field.defaultSort}"
                    ng-init="${formSearchDataModelName}.sort.field='${_field.name}';${formSearchDataModelName}.sort.type='${_field.defaultSort}';"
                        <#else>
                    class="sorting" tabindex="0"
                        </#if>
                    <#else>
                    class=""
                    </#if>
                    field="${_field.name}" text="${_field.text}" fieldtype="${_type}">${_field.text}&nbsp;&nbsp;
                </th>
            </#list>
        <#--=============================================================================================-->
        </tr>
    </thead>
    <tbody>
        <tr role="row" ng-repeat="repeat_item in ${formSearchDataModelName}.formSearchResult"
            ng-click="chooseThis($event,'${allBoxDataModelName}')">
        <#--=============================================================================================-->
            <#if showCheckBox>
                <td class="center noprint">
                    <label ng-show="repeat_item.${idName} && repeat_item.${idName}!=''" class="position-relative">
                        <input type="checkbox" class="ace selectItemBox" value="{{repeat_item.${idName}}}"
                               indexNum="{{$index}}">
                        <span class="lbl"></span>
                    </label>
                </td>
            </#if>
            <td class="text-nowrap printWidthPer100 center">
                <span ng-bind="${formSearchDataModelName}.page.pageItemSize*(${formSearchDataModelName}.page.currentPage-1)+($index+1)"></span>
            </td>
        <#--=============================================================================================-->
            <#list tableFields as _field>
                <td class="printNowrap printWidthPer100">
                    <#local _type=_field.type!'text' />
                <#-- 是否需要解码 -->
                    <#local _isNeedDecode=_field.isNeedDecode!false />
                    <#if _type == 'text'>
                        <#if _isNeedDecode>
                            <span ng-bind="repeat_item.${_field.name} | decode"></span>
                        <#else >
                            <span ng-bind="repeat_item.${_field.name}"></span>
                        </#if>

                    <#elseif _type="month"><#--年份月份 类型-->
                        <span ng-if="repeat_item.${_field.name}"
                              ng-bind="repeat_item.${_field.name}*1000 | date:'yyyy-MM'"></span>
                    <#elseif _type == 'date'>
                        <span ng-if="repeat_item.${_field.name}"
                              ng-bind="repeat_item.${_field.name}*1000 | date:'yyyy-MM-dd'"></span>
                    <#elseif _type == 'datetime'>
                        <span ng-if="repeat_item.${_field.name}"
                              ng-bind="repeat_item.${_field.name}*1000 | date:'yyyy-MM-dd HH:mm:ss'"></span>
                    <#elseif _type="specificime"><#--具体时间 类型：HH:mm:ss-->
                        <span ng-if="repeat_item.${_field.name}"
                              ng-bind="repeat_item.${_field.name}*1000 | date:'HH:mm:ss'"></span>
                    <#elseif _type == 'fromModel'>
                        <span ng-bind="${_field.modelName}[repeat_item.${_field.name}]"></span>

                    <#elseif _type == 'fromModelArray'><#--从定义好的 model中去取：并${_field.name}是个list；用逗号分隔显示-->
                        <span ng-bind-html="fromModelArray(repeat_item.${_field.name},${_field.modelName}) | trustHtml"></span>

                    <#elseif _type == 'fromFunction'><#---->
                        <#-- 是否需要添加过滤条件 -->
                        <#local _isNeedFilter = _field.isNeedFilter!false/>
                        <span ng-bind-html="${_field.functionName}(repeat_item) <#if _isNeedFilter>| filterScript | trustHtml </#if>"></span>
                    <#elseif _type == 'number'><#--数字-->
                        <span ng-bind="repeat_item.${_field.name} || 0"></span>
                    <#elseif _type == 'image'><#--图片-->
                        <#local _height=_field.height!'80px' />
                        <#local _show=_field.show!false />
                        <#if _show==true>
                            <img ng-src="{{repeat_item.${_field.name}}}" style="max-height:${_height};"
                                 ng-click="${_field.event}(this)">
                        </#if>
                        <#if _show==false>
                            <img ng-src="{{repeat_item.${_field.name}}}" style="max-height:${_height};">
                        </#if>
                    <#elseif _type == 'selection'>
                        <#local buttonName=_field.buttonName!'选择'/>
                        <#local buttonClass=_field.buttonClass!'btn btn-primary btn-sm'/>
                        <button class="${buttonClass} button-base"
                                ng-click="${selectionCallback}(repeat_item)" <#if ngDisabled = true>
                                ng-disabled="${ngDisabledFun}(repeat_item)"</#if>>${buttonName}</button>
                    <#--<#elseif _type == 'custome'>&lt;#&ndash;自定义&ndash;&gt;-->
                    <#--<span ${_field.directive} itemid="{{repeat_item.${idName}}}" itemdetails="{{repeat_item}}"></span>-->
                    <#--自定义-->
                    <#elseif _type="EmptyDiv"><#--自定义内容：content 为自定义的html-->
                        <#local _content =_field.content!"" />
                    <#--${_content}-->
                        <#assign inlineTemplate = _content?interpret>
                        <@inlineTemplate/>
                    </#if>
                </td>
            </#list>
        <#--=============================================================================================-->
        </tr>
    </tbody>
</table>
<div
        ng-show="${formSearchDataModelName}.page.totalItemCount == 0" class="alert alert-danger text-center"
        role="alert">

    <span ng-if="${formSearchDataModelName}IsLoading==1">
        数据加载中。。。
    </span>
    <span ng-if="${formSearchDataModelName}IsLoading==2">
        无数据
    </span>
    <span ng-if="${formSearchDataModelName}IsLoading==3">
        请查询数据
    </span>
</div>

<div class="row noprint" ng-show="${formSearchDataModelName}.page.totalItemCount > 0">
    <div class="col-xs-4">
        <div class="" style="margin:15px 12px;">
            共 <span ng-bind="${formSearchDataModelName}.page.totalPageCount"></span> 页，每页 <span
                ng-bind="${formSearchDataModelName}.page.pageItemSize"></span> 条，共 <span
                ng-bind="${formSearchDataModelName}.page.totalItemCount"></span> 条数据
        </div>
    </div>
    <div class="col-xs-8">
        <div class="pull-right" style="margin: 15px 8px;height: 32px;">
            <select page-item-size-bar-help="" style="height: 32px;" title="每页显示条数"
                    formsearchmodelname="${formSearchDataModelName}"
                    ng-model="${formSearchDataModelName}.page.pageItemSize">
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
        <div class="pull-right" style="margin: 15px 8px;height: 32px;">
            <input style="height: 32px;width: 50px;text-align: center;border-radius: 0px 4px 4px 0px !important;"
                   placeholder="页码"
                   title="输入跳转的页码"
                   page-bar-input-num-help=""
                   formsearchmodelname="${formSearchDataModelName}"/>
        </div>
        <ul class="pagination pull-right" page-bar-help=""
            formsearchmodelname="${formSearchDataModelName}"></ul>
    </div>
</div>
</#macro>
<#--=============================================================================================-->
<#--modal弹出新增表单form：
    modelName：新增的模型名称：如果User;首字母大写；
    modalTitile:modal的标题；
    fields：表单字段
    submiturl：新增和修改时提交的url；
    idName：主键的名称默认id
-->
<#macro sys_modal_add_form
modalTitile="新增"
modalUpdateTitile="修改"

modelName=""
fields=[]
submiturl=""
idName="id"
formSearchModelName=""
<#--注入操作按钮；如果registerButtons的长度大于0，那么默认的按钮将不显示-->
registerButtons=[]
<#--表示设置弹出的模态窗体的最大宽度；
但这个值只能影响 窗口的大小；但不能决定窗口的大小；
窗口的实际大小是根据浏览器大小来决定，并结合modalWidth的值
-->
modalWidth=0
defaultSaveButtonText="保存"
<#--强制显示默认的保存按钮-->
forceShowDefaultSaveButton = false
<#--窗体指令-->
modalDirective=''
<#--默认的保存，保存成功后的回掉函数；默认null-->
defaultSaveSuccessCallBackFunction="null"
<#--保存之前调用，返回true，就继续执行，返回false就中断执行；可用于 保存之前自定义验证特殊字段:默认null-->
defaultSaveBeforeCallBackFunction="null"
<#--保存提交的数据类型：默认空或者json 是json提交；可以为 formData或者file-->
transferDataType="json"
>
    <@comp.sys_comp_add_modal
    modelName=modelName
    modalTitile=modalTitile
    formSearchModelName=formSearchModelName
    modalWidth=modalWidth
    modalDirective=modalDirective
    modalTitile=modalTitile
    modalUpdateTitile=modalUpdateTitile
    >
    <#--=============================================================================================-->
        <@comp.sys_comp_add_modal_body>
            <@comp.sys_comp_form_and_element modelName=modelName fields=fields submiturl=submiturl idName=idName >
            </@comp.sys_comp_form_and_element>
        </@comp.sys_comp_add_modal_body>
    <#--=============================================================================================-->
        <#local showDefaultSaveButton=false/>
        <#if registerButtons?size == 0>
            <#local showDefaultSaveButton=true/>
        </#if>
        <#if forceShowDefaultSaveButton == true>
            <#local showDefaultSaveButton=true/>
        </#if>

        <@comp.sys_comp_add_modal_footer
        modelName=modelName
        registerButtons=registerButtons
        formSearchModelName=formSearchModelName
        showDefaultSaveButton=showDefaultSaveButton defaultSaveButtonText=defaultSaveButtonText
        defaultSaveSuccessCallBackFunction=defaultSaveSuccessCallBackFunction
        defaultSaveBeforeCallBackFunction=defaultSaveBeforeCallBackFunction
        transferDataType=transferDataType>
        </@comp.sys_comp_add_modal_footer>
    <#--=============================================================================================-->
    </@comp.sys_comp_add_modal>
</#macro>
<#--=============================================================================================-->


<#--
    view modal 窗体
-->
<#macro sys_modal_view
modalTitile="详情"
modelName=""
fields=[]
<#--表示设置弹出的模态窗体的最大宽度；
但这个值只能影响 窗口的大小；但不能决定窗口的大小；
窗口的实际大小是根据浏览器大小来决定，并结合modalWidth的值
-->
modalWidth=0
>
    <#local modalWidthString=""/>
    <#if modalWidth gt 0>
        <#local modalWidthString="max-width:"+modalWidth+"px"/>
    </#if>

<div class="modal fade modal-config-help printWidthPer100"
     id="{{rootMenuId}}_view${modelName}_modal"
     modelname="view${modelName}Model"
     backdrop="static"
     keyboard="false"
     role="dialog"
     aria-labelledby="view${modelName}ModalLabel"

     ng-init="view${modelName}Model={}"
>
<#--=============================================================================================-->
    <div class="modal-dialog printWidthPer98" role="document" style="${modalWidthString}">
        <div class="modal-content printWidthPer100">
            <div class="modal-header bg-primary custom-modal-header">
                <button type="button" class="close noprint" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="view${modelName}ModalLabel"
                    ng-init="view${modelName}ModalLabel='${modalTitile}'" ng-bind="view${modelName}ModalLabel">
                </h4>
            </div>
        <#--=============================================================================================-->
            <div class="modal-body printWidthPer100">
                <#nested />
            </div>
        <#--=============================================================================================-->
            <div class="modal-footer noprint custom-modal-footer">
                <button type="button" class="btn btn-default btn-sm button-default btn-round2" data-dismiss="modal">关闭
                </button>
            </div>
        </div>
    </div>
</div>
</#macro>

<#--view modal 窗体的 tabs=============================================================================================-->
<#macro sys_modal_view_tabs>
<ul class="nav nav-tabs " role="tablist">
    <#nested />
</ul>
</#macro>
<#--view modal 窗体的 tabs的元素=============================================================================================-->
<#macro sys_modal_view_tabs_element name title isActive=false>
<li role="presentation" class="${isActive?then("active","")}">
    <a href="#${name}_{{$id}}" id="${name}-tab_{{$id}}" role="tab" data-toggle="tab" aria-controls="${name}_{{$id}}"
       aria-expanded="true">${title}</a>
</li>
</#macro>

<#--view modal 窗体的 所有tab的对应的内容块=============================================================================================-->
<#macro sys_modal_view_tab_contents>
<div class="tab-content">
    <#nested />
</div>
</#macro>
<#--view modal 窗体的 没有tab的对应的内容；需要放在sys_modal_view_tab_contents中=============================================================================================-->
<#macro sys_modal_view_tab_contents_panel name isActive=false>
<div id="${name}_{{$id}}" role="tabpanel" class="tab-pane fade ${isActive?then("active in","")}"
     aria-labelledby="${name}-tab_{{$id}}">
    <#nested />
</div>
</#macro>

<#--view modal 窗体的 sys_modal_view_tab_contents_panel的内容；但也可以单独和sys_modal_view使用=============================================================================================-->
<#macro sys_modal_view_content
modelName=""
fields=[]
>
<div class="container-fluid">
    <form class="form-horizontal viewForm" novalidate>
    <#--=============================================================================================-->
        <div class="row">
            <#list fields as _field>
                <#local _title=_field.title!"" /> <#--元素标题-->
                <#local _type=_field.type!"text" /><#--类型：文本，数字还select等-->
                <#local _isRow=_field.isRow!false /><#--占一行还是列-->
                <#local _hasColorStyle=_field.hasColorStyle!false /><#-- 是否具有颜色样式 -->
                <#local _filterNull=_field.filterNull!false /><#-- 是否具有过滤返回值为空的字段 -->
            <#-- 是否需要解码 -->
                <#local _isNeedDecode=_field.isNeedDecode!false />
            <#--=============================================================================================-->
                <div class="col-sm-${_isRow?then("12","6")}"
                    <#if _filterNull>
                     ng-if="view${modelName}Model.${_field.name}"
                    </#if>
                >
                    <div class="form-group">
                        <label class="col-sm-${_isRow?then("2","4")} control-label text-custom bold">${_field.text}</label>

                        <div class="col-sm-${_isRow?then("10","8")}"
                            <#if _hasColorStyle>
                             style="color: ${_field.color}"
                            </#if>
                        >
                        <#--=============================================================================================-->
                            <#if _type=="text"><#--普通文本 类型-->
                                <#if _isNeedDecode>
                                    <p class="form-control-static"
                                       ng-bind="view${modelName}Model.${_field.name} | decode"></p>
                                <#else>
                                    <p class="form-control-static" ng-bind="view${modelName}Model.${_field.name}"></p>
                                </#if>

                            <#--=============================================================================================-->
                            <#elseif _type="year"><#--年份 类型：yyyy-->
                                <p class="form-control-static"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'yyyy'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="month"><#--年份月份 类型-->
                                <p class="form-control-static"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'yyyy-MM'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="date"><#--日期 类型：yyyy-MM-dd-->
                                <p class="form-control-static"
                                   ng-if="view${modelName}Model.${_field.name}"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'yyyy-MM-dd'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="datetime"><#--日期+时间 类型：yyyy-MM-dd HH:mm-->
                                <p ng-if="view${modelName}Model.${_field.name}" class="form-control-static"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'yyyy-MM-dd HH:mm:ss'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="hourtime"><#--日期 类型：yyyy-MM-dd-->
                                <p class="form-control-static"
                                   ng-if="view${modelName}Model.${_field.name}"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'HH:mm'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="specificime"><#--具体时间类型：HH:mm:ss-->
                                <p class="form-control-static"
                                   ng-if="view${modelName}Model.${_field.name}"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'HH:mm:ss'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type="selecthourtime"><#--日期 类型：yyyy-MM-dd-->
                                <p class="form-control-static"
                                   ng-if="view${modelName}Model.${_field.name}"
                                   ng-bind="view${modelName}Model.${_field.name}*1000 | date:'HH:mm'"></p>
                            <#--=============================================================================================-->
                            <#elseif _type == 'fromModel'><#--从定义好的 model中去取-->
                                <p class="form-control-static"
                                   ng-bind="${_field.modelName}[view${modelName}Model.${_field.name}]"></p>
                            <#--=============================================================================================-->
                            <#elseif _type == 'fromModelArray'><#--从定义好的 model中去取：并${_field.name}是个list；用逗号分隔显示-->
                                <p class="form-control-static"
                                   ng-bind-html="fromModelArray(view${modelName}Model.${_field.name},${_field.modelName}) | trustHtml"></p>
                            <#--=============================================================================================-->
                            <#elseif _type == 'fromFunction'><#--从定义好的 方法中去取；会将整个对象传给这个方法-->
                                <p class="form-control-static"
                                   ng-bind-html="${_field.functionName}(view${modelName}Model)"></p>
                            <#elseif _type == 'number'><#--从定义好的 方法中去取；会将整个对象传给这个方法-->
                                <p class="form-control-static"
                                   ng-bind="view${modelName}Model.${_field.name} || 0"></p>
                            <#elseif _type == 'image'><#--从定义好的 方法中去取；会将整个对象传给这个方法-->
                                <div class="panel-body" style="border:0px solid #f0f0f0;padding: 0px;">
                                    <img ng-src="{{view${modelName}Model.${_field.name}}}" style="max-height:60px;">
                                </div>
                            <#elseif _type == 'richText'>
                                <div style="max-height:400px;overflow:auto;width:100%;padding-top: 10px;"
                                     ng-bind-html="view${modelName}Model.${_field.name} | trustHtml">
                                </div>
                            <#elseif _type="checkbox">
                            <#--checkbox:复选框; 没有必填；
                            -->
                                <div class="checkbox">

                                    <#local _key =_field.key!"itemCode" />
                                    <#local _keyText = _field.keyText!"itemName" />

                                    <#list _field.data as _checkboxItem>
                                        <#local _checkedbox=_checkboxItem.checked!"no" >
                                        <label>
                                            <input type="checkbox"
                                                   class="ace ace-checkbox-2"
                                                   ng-init="view${modelName}Model.${_field.name}['${_checkboxItem[_key]}']='${_checkedbox}'"
                                                   ng-model="view${modelName}Model.${_field.name}['${_checkboxItem[_key]}']"
                                                   modelname="view${modelName}Model"
                                                   ng-true-value="'yes'"
                                                   ng-false-value="'no'"
                                                   name="${_field.name}"
                                                   ng-readonly="true"
                                            >
                                        <#--${_checkboxItem[_keyText]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                                            <span class="lbl"> ${_checkboxItem[_keyText]}</span>
                                        </label>
                                    </#list>
                                </div>
                            <#elseif _type="EmptyDiv"><#--自定义内容：content 为自定义的html-->
                                <#local _content =_field.content!"" />
                            <#--${_content}-->
                                <#assign inlineTemplate = _content?interpret>
                                <@inlineTemplate/>
                            </#if>
                        </div>
                    </div>
                </div>
            </#list>
            <#nested />
        </div>
    </form>
</div>
</#macro>
<#--=============================================================================================-->

<#--modal弹出新增表单form：
    modelName：新增的模型名称：如果User;首字母大写；
    modalTitile:modal的标题；
    fields：表单字段
    submiturl：新增和修改时提交的url；
    idName：主键的名称默认id
-->
<#macro sys_modal_add_tab
modalTitile="新增"
modalUpdateTitile="修改"

modelName=""
fields=[]
submiturl=""
idName="id"
formSearchModelName=""
<#--注入操作按钮；如果registerButtons的长度大于0，那么默认的按钮将不显示-->
registerButtons=[]
<#--表示设置弹出的模态窗体的最大宽度；
但这个值只能影响 窗口的大小；但不能决定窗口的大小；
窗口的实际大小是根据浏览器大小来决定，并结合modalWidth的值
-->
modalWidth=0
defaultSaveButtonText="保存"
<#--强制显示默认的保存按钮-->
forceShowDefaultSaveButton = false
<#--窗体指令-->
modalDirective=''
<#--默认的保存，保存成功后的回掉函数；默认null-->
defaultSaveSuccessCallBackFunction="null"
<#--保存之前调用，返回true，就继续执行，返回false就中断执行；可用于 保存之前自定义验证特殊字段:默认null-->
defaultSaveBeforeCallBackFunction="null"
>
    <@comp.sys_comp_add_modal
    modelName=modelName
    modalTitile=modalTitile
    formSearchModelName=formSearchModelName
    modalWidth=modalWidth
    modalDirective=modalDirective
    modalTitile=modalTitile
    modalUpdateTitile=modalUpdateTitile
    >
    <#--=============================================================================================-->
        <@comp.sys_comp_add_modal_body>
            <@comp.sys_comp_form modelName=modelName fields=fields submiturl=submiturl idName=idName >
                <#nested />
            </@comp.sys_comp_form>
        </@comp.sys_comp_add_modal_body>
    <#--=============================================================================================-->
        <#local showDefaultSaveButton=false/>
        <#if registerButtons?size == 0>
            <#local showDefaultSaveButton=true/>
        </#if>
        <#if forceShowDefaultSaveButton == true>
            <#local showDefaultSaveButton=true/>
        </#if>

        <@comp.sys_comp_add_modal_footer
        modelName=modelName
        registerButtons=registerButtons
        formSearchModelName=formSearchModelName
        showDefaultSaveButton=showDefaultSaveButton defaultSaveButtonText=defaultSaveButtonText
        defaultSaveSuccessCallBackFunction=defaultSaveSuccessCallBackFunction
        defaultSaveBeforeCallBackFunction=defaultSaveBeforeCallBackFunction>
        </@comp.sys_comp_add_modal_footer>
    <#--=============================================================================================-->
    </@comp.sys_comp_add_modal>
</#macro>


<#macro sys_modal_img_preview
modelName=""
modalWidth=0
>
    <#local modalWidthString=""/>
    <#if modalWidth gt 0>
        <#local modalWidthString="max-width:"+modalWidth+"px"/>
    <#else>
        <#local modalWidthString="max-width:30%"/>
    </#if>
<div class="modal fade" id="add${modelName}Model-preview-img">
    <div class="modal-dialog" style="${modalWidthString}">
        <div class="modal-content">
            <div class="modal-header custom-modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">图片详情</h4>
            </div>
            <div class="modal-body">
                <div class="container-fluid">
                    <img src="" class="img-responsive" style="margin: auto;">
                </div>
            </div>
            <div class="modal-footer custom-modal-footer">
                <button type="button" class="btn btn-default btn-round2 button-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</#macro>