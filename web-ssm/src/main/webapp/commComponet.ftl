<#if basePath??>
    <#global SYS_BASE_PATH=basePath />
    <#global SYS_BASE_PATH_ADMIN=baseAdminPath />
    <#global SYS_BASE_PATH_FRONT=basePath+'front/' />
<#else >
    <#global SYS_BASE_PATH=springMacroRequestContext.getContextUrl('/') />
    <#if CTRL_PREFIX??>
        <#global SYS_BASE_PATH_ADMIN=springMacroRequestContext.getContextUrl(CTRL_PREFIX) />
    <#else>
        <#global SYS_BASE_PATH_ADMIN=springMacroRequestContext.getContextUrl("/") />
    </#if>
    <#global SYS_BASE_PATH_FRONT=springMacroRequestContext.getContextUrl('/front/') />
</#if>

<#global G_ADD_MODEL_NAME = r"${addModelName}" />

<#--组件 ftl-->
<#--
新增修改modal 组件

    modelName：model名称;不可为空
    modalTitile：modal默认标题
    modalWidth：表示设置弹出的模态窗体的最大宽度；但这个值只能影响 窗口的大小；但不能决定窗口的大小；窗口的实际大小是根据浏览器大小来决定，并结合modalWidth的值
    formSearchModelName：新增成功后，查询对于的model的名称
-->
<#macro sys_comp_add_modal
modelName

modalTitile="新增"
modalUpdateTitile="修改"

formSearchModelName=""
modalWidth=0
<#--窗体指令-->
modalDirective=''
>
    <#local addModelName = "add${modelName}Model">
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}">
    <#local addModalId = "{{rootMenuId}}_add${modelName}_modal">

    <#local modalWidthString=""/>
    <#if modalWidth gt 0>
        <#local modalWidthString="max-width:"+modalWidth+"px"/>
    <#else>
        <#local modalWidthString="max-width:80%"/>
    </#if>

<div class="modal fade modal-config-help tooltip-help"
     id="${addModalId}"
     modelname="${addModelName}"
     formid="${addFormId}"
     backdrop="static"
     keyboard="false"
     role="dialog"
     aria-labelledby="add${modelName}ModalLabel"

     modalLabelModelName="add${modelName}ModalLabel"
     modalLableAddTitle="${modalTitile}"
     modalLableUpdateTitle="${modalUpdateTitile}"

<#--创建 model-->
     ng-init="${addModelName}={}"
${modalDirective}
>
<#--=============================================================================================-->
    <div class="modal-dialog" role="document" style="${modalWidthString}">
        <div class="modal-content">
            <div class="modal-header bg-primary custom-modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="add${modelName}ModalLabel"
                    ng-init="add${modelName}ModalLabel='${modalTitile}'"
                    ng-bind="add${modelName}ModalLabel"></h4>
            </div>

            <#nested/>

        </div>
    </div>

</div>
</#macro>
<#--add modal的body


-->
<#macro sys_comp_add_modal_body >
<div class="modal-body">
    <div class="container-fluid">
        <#nested />
    </div>
</div>
</#macro>

<#--form组件
        modelName:model名称；必填
        submiturl：form提交的url
-->
<#macro sys_comp_form
modelName
submiturl=""
idName="id"
fields=[]
>
    <#local addModelName = "add${modelName}Model">
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}">

<form class="form-horizontal css-form sysForm" novalidate
      id="${addFormId}"
      name="${addFormId}"
      form-help=""
      tooltip-help
      modelname="${addModelName}"
      submiturl="${SYS_BASE_PATH_ADMIN}${submiturl}"
      method="post"
>
<#--对应的model对象的 id的值的表达式-->
    <#local _modelIdName="${addModelName}.${idName}" />
    <input type="hidden" name="${idName}" ng-model="${_modelIdName}"/>
    <#nested />
</form>
</#macro>

<#--form元素-->
<#macro sys_comp_form_element
modelName
submiturl=""
idName="id"
fields=[]
>
    <#local addModelName = "add${modelName}Model">
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}">
    <#local addModalId = "{{rootMenuId}}_add${modelName}_modal">

    <#local _modelIdName="${addModelName}.${idName}" />

<div class="row">

    <#local _isRight=false /> <#--是否右半边-->

    <#list fields as _field>
        <#local _required=_field.required!false /> <#--是否必填-->
        <#local _title=_field.title!"" /> <#--元素标题-->
        <#local _type=_field.type!"text" /><#--类型：文本，数字还select等-->
        <#local _isRow=_field.isRow!false /><#--占一行还是列-->
        <#local _allowUpdate=_field.allowUpdate!true /><#--是否允许修改-->
        <#local _showLabel=_field.showLabel!true /><#--是否显示label标签-->
        <#local _placeholder=_field.placeholder!"请输入"+_field.text/>
        <#local _tips=_field.tips!"" /> <#--元素描述-->
        <#local _tipsClass=_field.tipsClass!"text-danger" /> <#--元素描述样式-->

    <#--一个field对应的单元model-->
        <#local _columnModel=_field.columnModel!_field.name />

        <#local _labelChange=_field.labelChange!false /> <#--label是否可以支持动态改变：默认false；默认以name为model自定义通过columnModel改变-->

    <#--<p class="text-muted">...</p>
    <p class="text-primary">...</p>
    <p class="text-success">...</p>
    <p class="text-info">...</p>
    <p class="text-warning">...</p>
    <p class="text-danger">...</p>-->
    <#--
        注入js指令：指令的名称；
        通过注入 js angularjs的指令；
        可以在在指令中对当前的元素添加事件或者处理数据等等；
        编写此指令时，restrict:"A",必须A
    -->
        <#local _jsDirective=_field.jsDirective!"" />
    <#--不可修改的代码-->
        <#local _allowUpdateDisabledText>
        ${_allowUpdate?then("","ng-disabled=\"${_modelIdName}!=null && ${_modelIdName}!=''\"")}
        </#local>

        <#local _ngClass>
            {true:'requiredNgClass'}[${addFormId}.${_field.name}.$error.required]
        </#local>
    <#--=============================================================================================-->

    <div ng-init="column_${_columnModel}_visible=true;column_${_columnModel}_display=true"
         ng-class="{'labelVisibleHidden': !column_${_columnModel}_visible, 'labelDisplayHidden': !column_${_columnModel}_display}"
         class="col-sm-${_isRow?then("12","6")}">
    <div class="form-group">
        <#if _showLabel>
            <#if _isRow>
                <#local _isRight=false /> <#--是否右半边-->
                <label class="col-sm-1 control-label text-custom ${_required?then("require","noRequire")
                }"
                    <#if _labelChange>
                       ng-init="label_${_columnModel}='${_field.text}'"
                       ng-bind-html="label_${_columnModel} | trustHtml"
                    </#if>
                >${_field.text}</label>
            <div class="col-sm-11">
            <#else>
                <#if !_isRight>
                    <#local _isRight=true /> <#--是否右半边-->
                    <label class="col-sm-2 control-label text-custom ${_required?then("require","noRequire")
                    }"
                        <#if _labelChange>
                           ng-init="label_${_columnModel}='${_field.text}'"
                           ng-bind-html="label_${_columnModel} | trustHtml"
                        </#if>
                    >${_field.text}</label>
                <div class="col-sm-10">
                <#else>
                    <#local _isRight=false /> <#--是否右半边-->
                    <label class="col-sm-2 control-label text-custom ${_required?then("require","noRequire")
                    }"
                        <#if _labelChange>
                           ng-init="label_${_columnModel}='${_field.text}'"
                           ng-bind-html="label_${_columnModel} | trustHtml"
                        </#if>
                    >${_field.text}</label>
                <div class="col-sm-10">
                </#if>
            </#if>
        <#--<label class="col-sm-${_isRow?then("2","4")} control-label bold text-custom ${_required?then("require","noRequire")}">${_field.text}</label>-->
        <#--<div class="col-sm-${_isRow?then("9","7")}">-->
        <#elseif !_showLabel>
        <div class="col-sm-12">
        </#if>
    <#--=============================================================================================-->
        <#if _type=="text"><#--普通文本 类型-->
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _maxlength =_field.maxlength!"" />
            <#-- 调用input_text组件 -->
            <@input_text
            placeholder="${_placeholder}"
            addModelName=addModelName
            modelName="${modelName}"
            name="${_field.name}"
            modelIdName="${_modelIdName}"
            defaultValue="${_defaultValue}"
            maxlength=_field.maxlength
            title="${_title}"
            jsDirective="${_jsDirective}"
            allowUpdate=_field.allowUpdate
            required="${_required?string}"
            ></@input_text>
            <#--<input type="text" class="form-control"
                   id="${modelName}_${_field.name}"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                <#if _maxlength != "">
                   maxlength="${_maxlength}"
                </#if>
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >-->
        <#--=============================================================================================-->
        <#elseif _type="password"><#--内容必须是加密的-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="password" class="form-control" form-password-help
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
                   autocomplete="off"
            >
        <#--=============================================================================================-->
        <#elseif _type="passwordNumberText"><#--内容必须是由 数字组成：可以是： 00001，20002-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="password" class="form-control" form-password-help
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
                   pattern="^[0-9]*$"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
                   autocomplete="off"
            >
        <#--=============================================================================================-->
        <#elseif _type="numberText"><#--内容必须是由 数字组成：可以是： 00001，20002-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   id="${modelName}_${_field.name}"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
                   pattern="^[0-9]*$"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="customPattern"> <#--自定义规则 -->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
                <#if _field.pattern??>
                   pattern="${_field.pattern}"
                </#if>
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >

        <#--=============================================================================================-->
        <#elseif _type="lable"><#--内容必须是由 数字组成：可以是： 00001，20002-->
            <#local _defaultValue =_field.defaultValue!"" />
            <label class="col-sm-1 control-label text-warning bold "></label>
        <#--=============================================================================================-->
        <#elseif _type="characterNumberText"><#--内容只能由数字字母下划线组成-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
                   pattern="[A-Za-z0-9_]+"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="hyperlink"><#--超链接-->
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _address =_field.adress!"" />
            <a class="form-control"
               name="${_field.name}"
               ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
               ng-model="${addModelName}.${_field.name}"
               data-toggle="tooltip"
               data-placement="top"
               title="${_title}"
               onmouseover="this.title=' '"
               ng-required="${_required?string}"

            ${_allowUpdateDisabledText}
            ${_jsDirective}
            ></a>
        <#--=============================================================================================-->
        <#elseif _type="email"><#--邮件-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="email" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#elseif _type="phone"><#--手机号-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   pattern="^1[3-9]\d{9}$"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="textarea"><#--文本域 类型-->
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _rows=_field.rows!"4" /><#--行数-->
            <textarea class="form-control"
                      ng-class="${_ngClass}"
                      name="${_field.name}"
                      placeholder="${_placeholder}"
                      data-toggle="tooltip"
                      data-placement="left"
                      rows="${_rows}"
                      ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                      ng-model="${addModelName}.${_field.name}"
                      title="${_title}"
                      onmouseover="this.title=' '"
                      ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            ></textarea>
        <#--=============================================================================================-->
        <#elseif _type="year"><#--年份 类型：yyyy-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" year-help="" modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="month"><#--年份月份 类型-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" year-month-help="" modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="date"><#--日期 类型：yyyy-MM-dd-->
            <#local _defaultValue =_field.defaultValue!"" />
        <#-- 是否具备限制条件 -->
            <#local _hasLimit =_field.hasLimit!"false" />
        <#-- 限制最小时间范围(如：-1表示昨天，1表示明天) -->
            <#local _limitMinRange =_field.limitMinRange!"0" />
        <#-- 限制起始时间来源 -->
            <#local _limitStart =_field.limitStart!"" />
        <#-- 限制结束时间来源 -->
            <#local _limitEnd =_field.limitEnd!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   has-limit="${_hasLimit}"
                   limit-min-range="${_limitMinRange}"
                   limit-start="${_limitStart}"
                   limit-end="${_limitEnd}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" date-help="" date-format
                   modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
                   readonly style="cursor:default!important;"
            >
        <#--=============================================================================================-->
        <#elseif _type="datetime"><#--日期+时间 类型：yyyy-MM-dd HH:mm-->
            <#local _defaultValue =_field.defaultValue!"" />
        <#-- 是否具备限制条件 -->
            <#local _hasLimit =_field.hasLimit!"false" />
        <#-- 限制最小时间范围(如：-1表示昨天，1表示明天) -->
            <#local _limitMinRange =_field.limitMinRange!"0" />
        <#-- 限制起始时间来源 -->
            <#local _limitStart =_field.limitStart!"" />
        <#-- 限制结束时间来源 -->
            <#local _limitEnd =_field.limitEnd!"" />
        <#-- 调用input_dateTime组件 -->
            <@input_dateTime
            placeholder="${_placeholder}"
            addModelName=addModelName
            modelName=modelName
            name="${_field.name}"
            defaultValue="${_defaultValue}"
            modelIdName="${_modelIdName}"
            hasLimit="${_hasLimit}"
            limitMinRange="${_limitMinRange}"
            limitStart="${_limitStart}"
            limitEnd="${_limitEnd}"
            jsDirective="${_jsDirective}"
            required="${_required?string}"
            allowUpdate=true
            >
            </@input_dateTime>
            <#--<input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   has-limit="${_hasLimit}"
                   limit-min-range="${_limitMinRange}"
                   limit-start="${_limitStart}"
                   limit-end="${_limitEnd}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" date-time-help="" date-time-format
                   modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
                   readonly style="cursor:default!important;"
            >-->
        <#--=============================================================================================-->
        <#elseif _type="selecthourtime"><#--时间 类型HH:mm-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" search-hour-help="" date-time-format
                   modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="hourtime"><#--时间 类型HH:mm-->
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   ng-class="${_ngClass}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}" hour-help="" date-time-format
                   modelname="${addModelName}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="select"><#--已过时不用：下拉框-->
            <#local _defaultValue =_field.defaultValue!"" />
        <#--下拉显示已 key作为 select option的value，以keyText做为option的text-->
            <#local _key =_field.key!"itemCode" />
            <#local _keyText = _field.keyText!"itemName" />
            <#local _showDefaultSelectOption =_field.showDefaultSelectOption!true />
        <#--_allowUpdate等于false的时候，是否以 input文本框显示值；默认是以select显示-->
            <#local _showInput =_field.showInput!false />
        <#--是否多选-->
                <#local _isMuilt =_field.isMuilt!false /><!-- 是否多选 -->
            <#local _muiltText ="" />
            <#if _isMuilt>
                <#local _muiltText ='multiple="multiple" ng-multiple="true"' />
            </#if>
            <select ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                    ng-model="${addModelName}.${_field.name}"
                    modelname="${addModelName}"
                    select-num-format=""
                    style="width:100%"
                    name="${_field.name}"
                    class="form-control"
                    ng-class="${_ngClass}"
                    data-toggle="tooltip"
                    data-placement="top"
                    title="${_title}"
                    onmouseover="this.title=' '"
                    formid="${addFormId}"
                    ng-required="${_required?string}"
            ${_muiltText}
            ${_jsDirective}

            <#--_showInput等于true的时候表示在允许修改等于false的时候，以及文本框的方式显示-->
                <#if _showInput == true>
                ${_allowUpdate?then("","ng-show=\"${_modelIdName}==null || ${_modelIdName}==''\"")}
                </#if>
            <#--_showInput等于false的时候，标识还是以select本地显示-->
                <#if _showInput == false>
                ${_allowUpdateDisabledText}
                </#if>
            >
                <#if !_isMuilt>
                    <#if _showDefaultSelectOption>
                        <option value=""
                                ng-selected="!${addModelName} || !${addModelName}.${_field.name} || ${addModelName}.${_field.name} == null || ${addModelName}.${_field.name} == 'undefined'">
                            请选择
                        </option>
                    </#if>
                </#if>
                <#list _field.data as _selectData>
                    <option value="${_selectData[_key]}">${_selectData[_keyText]}</option>
                </#list>
            </select>
        <#--_showInput等true，_allowUpdate等与false的时候，表示在修改时，此字段不允许修改，并 以input的方式显示；还可以指定显示的字段showInputName-->
            <#if _showInput==true && _allowUpdate == false>
                <input type="text" class="form-control"
                       ng-show="${_modelIdName}!=null && ${_modelIdName}!=''"
                       ng-disabled="true"
                       ng-value="${addModelName}.${_field.showInputName}">
            </#if>

        <#--=============================================================================================-->
        <#elseif _type="searchSelect"><#--搜索式的下拉框-->

        <#--下拉显示已 key作为 select option的value，以keyText做为option的text-->
            <#local _key =_field.key!"itemCode" />
            <#local _keyText = _field.keyText!"itemName" />
            <#local _showDefaultSelectOption =_field.showDefaultSelectOption!true />
        <#--_allowUpdate等于false的时候，是否以 input文本框显示值；默认是以select显示-->
            <#local _showInput =_field.showInput!false />
            <#local _useNgOptions = _field.useNgOptions!false />
        <#--是否多选-->
            <#local _isMuilt =_field.isMuilt!false />
            <#local _muiltText ="" />
            <#if _isMuilt>
                <#local _defaultValue =_field.defaultValue!"[]" />
                <#local _muiltText ='multiple="multiple"' />
            <#else>
                <#local _defaultValue =_field.defaultValue!"" />
                <#local _defaultValue ="'"+_defaultValue+"'" />
            </#if>

                <#local _showAll =_field.showAll!false /><!-- 是否显示全部 -->
                <#local _showAllValue =_field.showAllValue!"0" /><!-- 显示全部时的value的值 -->

            <select ng-init="${addModelName}.${_field.name}=${_defaultValue}"
                    ng-model="${addModelName}.${_field.name}"
                    modelname="${addModelName}"
                    id="${modelName}_${_field.name}"
                    form-select2-help
                    select-num-format=""
                    style="width:100%"
                    name="${_field.name}"
                    id="${modelName}_${_field.name}"
                    class="form-control"
                    ng-class="${_ngClass}"
                    data-toggle="tooltip"
                    data-placement="top"
                    title="${_title}"
                    key="${_key}"
                    keytext="${_keyText}"
                    onmouseover="this.title=' '"
                    formid="${addFormId}"
                    ng-required="${_required?string}"
                <#if _useNgOptions>
                    ngoptionsmodelname="${_field.ngOptionsModelName}"
                </#if>
            ${_jsDirective}
            ${_muiltText}

            <#--_showInput等于true的时候表示在允许修改等于false的时候，以及文本框的方式显示-->
                <#if _showInput == true>
                ${_allowUpdate?then("","ng-show=\"${_modelIdName}==null || ${_modelIdName}==''\"")}
                </#if>
            <#--_showInput等于false的时候，表示还是以select本地显示-->
                <#if _showInput == false>
                ${_allowUpdateDisabledText}
                </#if>
            >
                <#if !_isMuilt>
                    <#if _showDefaultSelectOption>
                        <option value="" ng-selected="${addModelName}.${_field.name} == null">请选择
                        </option>
                    </#if>
                </#if>
                <#if _showAll>
                    <option value="${_showAllValue}"
                            ng-selected="${addModelName}.${_field.name} == ${_showAllValue}">全部
                    </option>
                </#if>
                <#if _useNgOptions>
                    <#if !_isMuilt>
                        <option ng-repeat="option in ${_field.ngOptionsModelName}"
                                ng-if="option.${_keyText}"
                                value="{{option.${_key}}}">{{option.${_keyText}}}
                        </option>
                    </#if>
                    <#if _isMuilt>
                        <option ng-repeat="option in ${_field.ngOptionsModelName}"
                                ng-if="option.${_keyText}"
                                value="_{{option.${_key}}}">{{option.${_keyText}}}
                        </option>
                    </#if>

                <#else>
                    <#list _field.data as _selectData>
                        <#if _selectData[_keyText]??>
                            <option value="${_selectData[_key]}">${_selectData[_keyText]}</option>
                        </#if>
                    </#list>
                </#if>
            </select>
        <#--_showInput等true，_allowUpdate等与false的时候，表示在修改时，此字段不允许修改，并 以input的方式显示；还可以指定显示的字段showInputName-->
            <#if _showInput==true && _allowUpdate == false>
                <input type="text" class="form-control"
                       ng-show="${_modelIdName}!=null && ${_modelIdName}!=''"
                       ng-disabled="true"
                       ng-value="${addModelName}.${_field.showInputName}">
            </#if>

        <#--=============================================================================================-->
        <#elseif _type="int"><#--整数，不允许小数-->
            <#local _defaultValue =_field.defaultValue!"null" />
            <#local _isNegative=_field.isNegative!false ><#--是否负数-->
            <@input_int
                placeholder="${_placeholder}"
                addModelName=addModelName
                modelName="${modelName}"
                name="${_field.name}"
                modelIdName="${_modelIdName}"
                defaultValue="${_defaultValue}"
                title="${_title}"
                jsDirective="${_jsDirective}"
                allowUpdate=_field.allowUpdate
                required="${_required?string}"
            >
            </@input_int>
            <#--<input type="text"
                   class="form-control"
                   ng-class="${_ngClass}"
                   pattern="${_isNegative?then("^-?[0-9]*$","^[0-9]*$")}"

                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}=${_defaultValue}"
                   ng-model="${addModelName}.${_field.name}"
                   modelname="${addModelName}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >-->
        <#elseif _type="nonzeroint"><#--非零整数，不允许小数-->
            <#local _defaultValue =_field.defaultValue!"null" />
            <#local _isNegative=_field.isNegative!false ><#--是否负数-->
            <input type="text"
                   class="form-control"
                   ng-class="${_ngClass}"
                   pattern="${_isNegative?then("^-?[1-9]([0-9])*$","^[1-9]([0-9])*$")}"

                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}=${_defaultValue}"
                   ng-model="${addModelName}.${_field.name}"
                   modelname="${addModelName}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="float"><#--数字允许小数-->
            <#local _defaultValue =_field.defaultValue!"null" />
            <#local _isNegative=_field.isNegative!false ><#--是否负数-->
            <#local _precision=_field.precision!2><#--小数点精度 -->

            <input type="text"
                   id="${modelName}_${_field.name}"
                   ng-class="${_ngClass}"
                   class="form-control"
                   pattern="${_isNegative?then("^-?\\d*(\\.\\d{0,${_precision}})?$","^\\d*(\\.\\d{0,${_precision}})?$")}"
                   name="${_field.name}"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}=${_defaultValue}"
                   ng-model="${addModelName}.${_field.name}"
                   modelname="${addModelName}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   ng-required="${_required?string}"
            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--=============================================================================================-->
        <#elseif _type="radio">
        <#--
            radio单选按钮
            defaultValue：必需的；
            data:必须的
        -->
            <#local _key =_field.key!"itemCode" />
            <#local _keyText = _field.keyText!"itemName" />

            <div class="radio">
                <#list _field.data as _radioItem>
                    <label>
                        <input ng-init="${addModelName}.${_field.name}=${_field.defaultValue}"
                               ng-model="${addModelName}.${_field.name}"
                               modelname="${addModelName}"
                               type="radio"
                               data-toggle="tooltip"
                               data-placement="top"
                               title="${_title}"
                               name="${_field.name}"
                               value="${_radioItem[_key]}"
                        ${_allowUpdateDisabledText}
                        ${_jsDirective}/> ${_radioItem[_keyText]}
                    </label>
                </#list>
            </div>
        <#--=============================================================================================-->
        <#elseif _type="checkbox">
        <#--checkbox:复选框; 没有必填；
        -->
            <div class="checkbox custom-checkbox">

                <#local _key =_field.key!"itemCode" />
                <#local _keyText = _field.keyText!"itemName" />

                <#list _field.data as _checkboxItem>
                    <#local _checkedbox=_checkboxItem.checked!"no" >
                    <label>
                        <input type="checkbox"
                               class="ace ace-checkbox-2"
                               ng-init="${addModelName}.${_field.name}['${_checkboxItem[_key]}']='${_checkedbox}'"
                               ng-model="${addModelName}.${_field.name}['${_checkboxItem[_key]}']"
                               modelname="${addModelName}"
                               ng-true-value="'yes'"
                               ng-false-value="'no'"
                               name="${_field.name}"
                        ${_allowUpdateDisabledText}
                        ${_jsDirective}
                        >
                    <#--${_checkboxItem[_keyText]}&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                        <span class="lbl"> ${_checkboxItem[_keyText]}</span>
                    </label>
                </#list>
            </div>
        <#--=============================================================================================-->
        <#elseif _type="customCheckbox">
        <#--customCheckbox:自定义复选框; 没有必填；
        -->
            <div class="checkbox custom-checkbox "
                 required="${_required?string}"
                 fieldNgName="${addModelName}.${_field.name}">

                <#local _key =_field.key!"itemCode" />
                <#local _keyText = _field.keyText!"itemName" />
            <#--多了个这个-->
                <#local _trueValueField =_field.trueValueField!_key />

                <#list _field.data as _checkboxItem>
                    <#local _checkedbox=_checkboxItem.checked!"no" >
                    <label>
                        <input type="checkbox"
                               class="ace ace-checkbox-2"
                               ng-init="${addModelName}.${_field.name}['${_checkboxItem[_key]}']='${_checkedbox}'"
                               ng-model="${addModelName}.${_field.name}['${_checkboxItem[_key]}']"
                               modelname="${addModelName}"
                               ng-true-value="'${_checkboxItem[_trueValueField]}'"
                               ng-false-value="'no'"
                               name="${_field.name}"
                        ${_allowUpdateDisabledText}
                        ${_jsDirective}
                        >
                        <span class="lbl"> ${_checkboxItem[_keyText]}</span>
                    </label>
                </#list>
            </div>
        <#--=============================================================================================-->
        <#--input配合checkbox-->
        <#elseif _type="inputWithCheckbox">
            <input type="text" ng-if="${addModelName}.${_field.name} != ${_field.trueValue}" ng-model="${addModelName}.${_field.name}"
                   class="col-sm-11" name="${_field.name}" autocomplete="off">
            <input type="text" ng-if="${addModelName}.${_field.name} == ${_field.trueValue}" class="col-sm-11" name="${_field.name}"
                   autocomplete="off" placeholder="${_field.labelName}"
                   disabled="disabled">
            <label class="pull-right" style="margin-top: 7px;">
                <input type="checkbox"
                       class="ace ace-checkbox-2"
                       ng-checked="${addModelName}.${_field.name} == ${_field.trueValue}"
                       ng-model="${addModelName}.${_field.name}"
                       modelname="${addModelName}"
                       ng-true-value="${_field.trueValue}"
                       ng-false-value="${_field.falseValue}"
                       name="${_field.name}"
                ${_allowUpdateDisabledText}
                ${_jsDirective}
                >
                <span class="lbl">${_field.labelName} </span>
            </label>
        <#--=============================================================================================-->
        <#--static的只读状态；readonly=true显示-->
        <#elseif _type="static">
            <#local _defaultValue =_field.defaultValue!"" />
            <input type="text" class="form-control"
                   name="${_field.name}"
                   ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                   ng-model="${addModelName}.${_field.name}"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   ng-readonly="true"
            >
        <#elseif _type="customDiv">
            <#local _defaultValue =_field.defaultValue!"" />
            <div ng-if="${addModelName}"
                 class="${_field.customCss}"
                 name="${_field.name}"
                 ng-bind-html="${addModelName}.${_field.name}"
            >
            </div>
        <#elseif _type="upImg">
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _imgRequired = (_field.upImgConfig.required)!true/>

            <div class="input-group">
                <!--用来存放item-->
            <#--<div id="fileList" class="uploader-list"></div>-->
                <input type="text" class="form-control "
                       name="${_field.name}"
                       ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                       ng-model="${addModelName}.${_field.name}"
                       data-toggle="tooltip"
                       data-placement="top"
                       readonly
                    <#if _imgRequired >
                       required
                    </#if>
                       common-value-change
                       title="${_title}"
                       id="${modelName}_${_field.name}"

                       data-fieldid="${modelName}_${_field.name}"
                       data-field="${_field.name}"
                       data-modelName="${addModelName}"
                >
                <div class="input-group-addon ${addModelName}FilePicker" id="${_field.upImgId!'filePicker'}"
                     data-fieldid="${modelName}_${_field.name}"
                     data-field="${_field.name}"
                     data-modelName="${addModelName}"
                     data-target="#${modelName}_${_field.name}"
                     data-conf="${_field.upImgConfig.conf!'18x11'}"
                     data-minPixel="${_field.upImgConfig.minPixel!'1080x660'}"
                     data-maxPixel="${_field.upImgConfig.maxPixel!'2160x1320'}"
                     data-preview="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_url_img'}"
                     data-uploadDir="${_field.upImgConfig.uploadDir!'coupon'}"
                     data-fileExt="${_field.upImgConfig.fileExt!'jpg,jpeg,png'}">
                    上传图片
                <#--<img id="${_field.upImgConfig.preview!'coupon_img'}" src="" style="width:80px;height:80px;">-->
                </div>
            </div>
            <div id="${_field.upImgId!'filePicker'}_tips" style="color:red">
                商品图片尺寸${_field.upImgConfig.minPixel!'1080x660'},请上传后缀为${_field.upImgConfig.fileExt!'jpg,jpeg,png'}的图片
            </div>
        <#elseif _type="upImgPreview">
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _imgRequired = (_field.upImgConfig.required)!true/>

            <div class="input-group">
                <!--用来存放item-->
            <#--<div id="fileList" class="uploader-list"></div>-->
                <input type="text" class="form-control" style="display:none"
                       name="${_field.name}"
                       ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                       ng-model="${addModelName}.${_field.name}"
                       data-toggle="tooltip"
                       data-placement="top"
                       readonly
                    <#if _imgRequired >
                       required
                    </#if>
                       data-previewid="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_preview_img'}"
                       title="${_title}"
                       id="${modelName}_${_field.name}"

                       data-fieldid="${modelName}_${_field.name}"
                       data-field="${_field.name}"
                       data-modelName="${addModelName}"
                >
                <div style="position:relative;border: 1px solid #cccccc;padding:2px;">
                    <img id="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_preview_img'}"
                         ng-src="{{${addModelName}.${_field.name}}}"
                         style="width:${_field.width!'40'}px;height:${_field.height!'34'}px;">
                    <div style="position:absolute;top:0px;left:0px;width:100%;height:100%;"
                         id="${_field.upImgId!'filePicker'}"
                         class="${addModelName}FilePicker"
                         data-fieldid="${modelName}_${_field.name}"
                         data-field="${_field.name}"
                         data-modelName="${addModelName}"
                         data-target="#${modelName}_${_field.name}"
                         data-conf="${_field.upImgConfig.conf!'18x11'}"
                         data-minPixel="${_field.upImgConfig.minPixel!'1080x660'}"
                         data-maxPixel="${_field.upImgConfig.maxPixel!'2160x1320'}"
                         data-preview="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_preview_img'}"
                         data-uploadDir="${_field.upImgConfig.uploadDir!'coupon'}"
                         data-fileExt="${_field.upImgConfig.fileExt!'jpg,jpeg,png'}">
                        <img ng-src="{{${addModelName}.${_field.name}}}"
                             style="width:${_field.width!'80'}px;height:${_field.height!'80'}px;visibility:hidden">
                    </div>
                </div>
            </div>
            <div id="${_field.upImgId!'filePicker'}_tips" style="color:red">
                商品图片尺寸${_field.upImgConfig.minPixel!'1080x660'},请上传后缀为${_field.upImgConfig.fileExt!'jpg,jpeg,png'}的图片
            </div>
        <#-- 多图上传 -->
        <#elseif _type="multiUpImgPreview">
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _maxUploadCount = (_field.maxUploadCount)!''/>
            <@upImgPreview_component
            addModelName=addModelName
            modelName=modelName
            name=_field.name
            defaultValue=_defaultValue
            maxUploadCount=_maxUploadCount
            required=_required
            title=_title
            upImgId=_field.upImgId
            upImgConfigPreview="${_field.upImgConfig.preview!''}"
            upImgConfigConf="${_field.upImgConfig.conf}"
            upImgConfigMinPixel="${_field.upImgConfig.minPixel}"
            upImgConfigMaxPixel="${_field.upImgConfig.maxPixel}"
            upImgConfigUploadDir="${_field.upImgConfig.uploadDir}"
            upImgConfigFileExt="${_field.upImgConfig.fileExt!'jpg,jpeg,png'}"
            >
            </@upImgPreview_component>
            <#--<div class="input-group">
                <!--用来存放item&ndash;&gt;
            &lt;#&ndash;<div id="fileList" class="uploader-list"></div>&ndash;&gt;
                <input type="text" class="form-control multi-upload-img" style="display: none"
                       name="${_field.name}"
                       max-count="${_maxUploadCount}"
                       ng-init="${addModelName}.${_field.name}='${_defaultValue}';${addModelName}${_field.name}TempImgList = []"
                       ng-model="${addModelName}.${_field.name}"
                       data-toggle="tooltip"
                       data-placement="top"
                       readonly

                       ng-required="${_required?string}"

                       data-previewid="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_preview_img'}"
                       title="${_title}"
                       id="${modelName}_${_field.name}"

                       data-fieldid="${modelName}_${_field.name}"
                       data-field="${_field.name}"
                       data-modelName="${addModelName}"
                >
                <div style="padding:2px;" class="clearfix">
                    <div class="pull-left">
                    <span ng-repeat="itemImg in ${addModelName}${_field.name}TempImgList track by $index"
                          style="position: relative;margin-right: 5px;">
                        <img ng-src="{{itemImg}}" ng-click="previewImg($index,${addModelName}${_field.name}TempImgList)"
                             style="width:${_field.width!'40'}px;height:${_field.height!'34'}px;cursor: pointer;">
                        <b class="delete-img-close"
                           ng-click="deleteImg($index, '${_field.name}' ,${addModelName}${_field.name}TempImgList)">&times;</b>
                    </span>
                    </div>
                    <div style="width: 30px;height: 35px;float: left;margin-left: 0px;"
                         id="${_field.upImgId!'filePicker'}"
                         class="${addModelName}FilePicker"
                         data-fieldid="${modelName}_${_field.name}"
                         data-field="${_field.name}"
                         data-modelName="${addModelName}"
                         data-target="#${modelName}_${_field.name}"
                         data-conf="${_field.upImgConfig.conf!'18x11'}"
                         data-minPixel="${_field.upImgConfig.minPixel!'1080x660'}"
                         data-maxPixel="${_field.upImgConfig.maxPixel!'2160x1320'}"
                         data-preview="${_field.upImgConfig.preview!'{{rootMenuId}}_${_field.name}_preview_img'}"
                         data-uploadDir="${_field.upImgConfig.uploadDir!'coupon'}"
                         data-fileExt="${_field.upImgConfig.fileExt!'jpg,jpeg,png'}">
                        <b style="font-size: 30px;display: inline-block;width: 30px;height: 30px;border: 1px solid #ccc;text-align: center;line-height: 25px;">&plus;</b>
                    </div>
                </div>
            </div>
            <div id="${_field.upImgId!'filePicker'}_tips" style="color:red">
                商品图片尺寸${_field.upImgConfig.minPixel!'1080x660'},请上传后缀为${_field.upImgConfig.fileExt!'jpg,jpeg,png'}的图片
            </div>-->
        <#elseif _type="richText">
            <#local _defaultValue =_field.defaultValue!"" />
            <div class="col-sm-10">
                        <textarea modalid="${addModalId}"
                                  data-name="${_field.name}"
                                  class="class-${addModelName}"
                                  ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                                  ng-model="${addModelName}.${_field.name}"
                                  id="richText"
                                  name="${_field.name}"
                                  style="width:100%;max-width:100%;height:400px;"></textarea>
                <input type="hidden" id="richTextHide" name="${_field.name}">
            </div>
        <#elseif _type="richText2">
            <#local _defaultValue =_field.defaultValue!"" />
            <div class="col-sm-10">
                        <textarea modalid="${addModalId}"
                                  data-name="${_field.name}"
                                  class="class-${addModelName}"
                                  ng-init="${addModelName}.${_field.name}='${_defaultValue}'"
                                  ng-model="${addModelName}.${_field.name}"
                                  id="richText2"
                                  name="${_field.name}"
                                  style="width:100%;max-width:100%;height:400px;"></textarea>
                <input type="hidden" id="richText2Hide" name="${_field.name}">
            </div>
        <#elseif _type="EmptyDiv"><#--自定义内容：content 为自定义的html-->
            <#local _content =_field.content!"" />
        <#--${_content}-->
            <#assign inlineTemplate = _content?interpret>
            <@inlineTemplate/>

        <#elseif _type=="file"><#--普通文件上传：不会自动上传，只是单纯的选择 一个文件；自己添加 指令处理-->
            <#local _defaultValue =_field.defaultValue!"" />
            <#local _maxlength =_field.maxlength!"" />
            <input type="file" class="form-control"
                   id="${modelName}_${_field.name}"
                   ng-class="${_ngClass}"
            <#--ng-class="{true:'validErrorClass'}[${addFormId}.${_field.name}.$error.required]"-->
                   name="${_field.name}_file"
                   placeholder="${_placeholder}"
                   ng-init="${addModelName}.${_field.name}=''"
                   data-toggle="tooltip"
                   data-placement="top"
                   title="${_title}"
                   onmouseover="this.title=' '"
                   modelname="${_field.name}"
                   form-file-help

            ${_allowUpdateDisabledText}
            ${_jsDirective}
            >
        <#--记录是否选择了文件；用于表单验证及验证样式判断-->
            <input type="hidden" id="${modelName}_${_field.name}_tag" name="${_field.name}"
                   ng-model="${addModelName}.${_field.name}" ng-required="${_required?string}">
            <p style="font-size:12px;" class="${_tipsClass!'text-warning'}">${_tips!''}</p>
        <#elseif _type=="newTreeView"> <#-- 新型树形显示插件 -->
            <@sys_tree_view
            addModelName=addModelName
            name=_field.name
            key=_field.key
            isMulti=_field.isMulti
            requestUrl=_field.requestUrl
            jsDirective=_jsDirective
            />
        <#--<#local _key =_field.key!"itemCode" />-->
        <#--<#local _keyText = _field.keyText!"itemName" />-->
        <#--<#local _useNgOptions = _field.useNgOptions!false />-->
        <#--&lt;#&ndash;是否多选&ndash;&gt;-->
        <#--<#local _isMulti =_field.isMulti!false />-->
        <#--<#if _isMulti>-->
        <#--<#local _defaultValue =_field.defaultValue!"[]" />-->
        <#--<#else>-->
        <#--<#local _defaultValue =_field.defaultValue!"''" />-->
        <#--</#if>-->
        <#--<div style="position: relative;" class="selectTreeViewComponent">-->
        <#--<div class="form-control" tree-view-operation-->
        <#--${_jsDirective}-->
        <#--request-url="${_field.requestUrl}" ng-init="${addModelName}.${_field.name}=${_defaultValue}"-->
        <#--request-id="${_field.key}"-->
        <#--is-multi="${_isMulti?string}"-->
        <#--modelname="${addModelName}.${_field.name}">-->
        <#--<span class="current">请选择</span>-->
        <#--<span class="pull-right"><b class="caret"></b></span>-->
        <#--</div>-->
        <#--<div class="selectTreeView" style="display: none;">-->

        <#--</div>-->
        <#--</div>-->
        </#if>
    </div>
    </div>
    </div>
    </#list>
    <#nested />
</div>
</#macro>

<#-- 文本输入框组件 -->
<#macro input_text
modelName
name
<#--替换文字-->
placeholder
addModelName
modelIdName=""
<#--是否需要初始化-->
isInit=true
defaultValue=""
maxlength=""
title=""
<#-- 是否必填 -->
required="false"
<#-- 添加指令 -->
jsDirective=""
<#-- 是否允许修改 -->
allowUpdate=true
>
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}">
    <#local _allowUpdateDisabledText>
    ${allowUpdate?then("","ng-disabled=\"${modelIdName}!=null && ${modelIdName}!=''\"")}
    </#local>
    <#local _ngClass>
        {true:'requiredNgClass'}[${addFormId}.${name}.$error.required]
    </#local>
    <input type="text" class="form-control"
           id="${modelName}_${name}"
           ng-class="${_ngClass}"
           name="${name}"
           placeholder="${placeholder}"
           <#if isInit>
                ng-init="${addModelName}.${name}='${defaultValue}'"
           </#if>
           ng-model="${addModelName}.${name}"
           <#if maxlength != "">
               maxlength="${maxlength}"
           </#if>
           data-toggle="tooltip"
           data-placement="top"
           title="${title}"
           onmouseover="this.title=' '"
           ng-required="${required}"
           ${jsDirective}
           ${_allowUpdateDisabledText}
    >
</#macro>

<#-- 整数输入框组件 -->
<#macro input_int
modelName
name
<#--替换文字-->
placeholder
addModelName
modelIdName=""
<#--是否需要初始化-->
isInit=true
<#-- 是否允许负数 -->
isNegative=false
defaultValue=""
maxlength=""
title=""
<#-- 是否必填 -->
required="false"
<#-- 添加指令 -->
jsDirective=""
<#-- 是否允许修改 -->
allowUpdate=true
>
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}"/>
    <#local _allowUpdateDisabledText>
    ${allowUpdate?then("","ng-disabled=\"${modelIdName}!=null && ${modelIdName}!=''\"")}
    </#local>
    <#local _ngClass>
        {true:'requiredNgClass'}[${addFormId}.${name}.$error.required]
    </#local>
    <input type="text"
           class="form-control"
           ng-class="${_ngClass}"
           pattern="${isNegative?then("^-?[0-9]*$","^[0-9]*$")}"
           name="${name}"
           placeholder="${placeholder}"
           ng-init="${addModelName}.${name}=${defaultValue}"
           ng-model="${addModelName}.${name}"
           modelname="${addModelName}"
           data-toggle="tooltip"
           data-placement="top"
           title="${title}"
           onmouseover="this.title=' '"
           ng-required="${required}"
    ${_allowUpdateDisabledText}
    ${jsDirective}
    >
</#macro>

<#-- 日期组件 -->
<#macro input_dateTime
modelName
name
<#--替换文字-->
placeholder
addModelName
modelIdName=""
<#--是否需要初始化-->
isInit=true
defaultValue=""
title=""
<#-- 是否必填 -->
required="false"
<#-- 添加指令 -->
jsDirective=""
<#-- 是否允许修改 -->
allowUpdate=true
<#-- 是否具有限制 -->
hasLimit="false"
limitMinRange=""
limitStart=""
limitEnd=""
>
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}"/>
    <#local _allowUpdateDisabledText>
    ${allowUpdate?then("","ng-disabled=\"${modelIdName}!=null && ${modelIdName}!=''\"")}
    </#local>
    <#local _ngClass>
        {true:'requiredNgClass'}[${addFormId}.${name}.$error.required]
    </#local>
    <input type="text" class="form-control"
           ng-class="${_ngClass}"
           name="${name}"
           has-limit="${hasLimit}"
           limit-min-range="${limitMinRange}"
           limit-start="${limitStart}"
           limit-end="${limitEnd}"
           placeholder="${placeholder}"
           <#if isInit>
            ng-init="${addModelName}.${name}='${defaultValue}'"
           </#if>
           ng-model="${addModelName}.${name}"
           data-toggle="tooltip"
           data-placement="top"
           title="${title}"
           onmouseover="this.title=' '"
           ng-required="${required}" date-time-help="" date-time-format
           modelname="${addModelName}"
           ${_allowUpdateDisabledText}
           ${jsDirective}
           readonly style="cursor:default!important;"
    >
</#macro>

<#--  图片上传组件 -->
<#macro upImgPreview_component
addModelName
modelName
name
<#--是否需要初始化-->
isInit=true
<#-- 是否是ngForm -->
isNgForm=false
defaultValue=""
maxUploadCount=""
required = false
title=""
upImgId="filePicker"
upImgConfigPreview=""
upImgConfigConf="18x11"
upImgConfigMinPixel="1080x660"
upImgConfigMaxPixel="2160x1320"
upImgConfigUploadDir="coupon"
upImgConfigFileExt="jpg,jpeg,png"
targetSource=""
modelChildName=""
extensionId=""
<#-- 添加指令 -->
jsDirective=""
>
    <#local _upImgConfigPreview="${upImgConfigPreview!'{{rootMenuId}}_${name}_preview_img'}"/>
    <#local tempImgList = "${addModelName}TempImgList${name}"/>
    <#if isNgForm>
        <#local _targetSource = "${targetSource}" />
    <#else >
        <#local _targetSource = "${modelName}_${name}" />
    </#if>
    <div class="input-group">
        <!--用来存放item-->
    <#--<div id="fileList" class="uploader-list"></div>-->
        <input type="text" class="form-control multi-upload-img" style="display: none"
               name="${name}"
               max-count="${maxUploadCount}"
               temp-img-list="${tempImgList}"
               ng-init="<#if isInit>${addModelName}.${name}='${defaultValue}';</#if>${tempImgList} = []"
               ng-model="${addModelName}.${name}"
               is-ng-form="${isNgForm?string}"
               <#if isNgForm>
                   model-child-name="${modelChildName}"
                   extension-id="${extensionId}"
               </#if>
               data-toggle="tooltip"
               data-placement="top"
               readonly

               ng-required="${required?string}"

               data-previewid="${_upImgConfigPreview}"
               title="${title}"
               id="${_targetSource}"

               data-fieldid="${_targetSource}"
               data-field="${name}"
               data-modelName="${addModelName}"
        >
        <div style="padding:2px;" class="clearfix">
            <div class="pull-left">
                    <span ng-repeat="itemImg in ${tempImgList} track by $index"
                          style="position: relative;margin-right: 5px;">
                        <img ng-src="{{itemImg}}" ng-click="previewImg($index,${tempImgList})"
                             style="width:${width!'40'}px;height:${height!'34'}px;cursor: pointer;">
                        <b class="delete-img-close"
                           ng-click="deleteImg($index, '${name}' ,${tempImgList})">&times;</b>
                    </span>
            </div>
            <div style="width: 30px;height: 35px;float: left;margin-left: 0px;"
                 id="${upImgId}"
                 ${jsDirective}
                 class="${addModelName}FilePicker"
                 data-fieldid="${_targetSource}"
                 data-field="${name}"
                 data-modelName="${addModelName}"
                 data-target="#${_targetSource}"
                 data-conf="${upImgConfigConf}"
                 data-minPixel="${upImgConfigMinPixel}"
                 data-maxPixel="${upImgConfigMaxPixel}"
                 data-preview="${_upImgConfigPreview}"
                 data-uploadDir="${upImgConfigUploadDir}"
                 data-fileExt="${upImgConfigFileExt}">
                <b style="font-size: 30px;display: inline-block;width: 30px;height: 30px;border: 1px solid #ccc;text-align: center;line-height: 25px;">&plus;</b>
            </div>
        </div>
    </div>
    <div id="${upImgId!'filePicker'}_tips" style="color:red">
        商品图片尺寸${upImgConfigMinPixel},请上传后缀为${upImgConfigFileExt}的图片
    </div>
</#macro>
<#--ng对应的form元素-->
<#macro ng_comp_form_element
modelName
modelChildName
ngFormElements=[]
>
    <#local addModelName = "add${modelName}Model"/>
    <div class="row">
        <div class="col-sm-6" ng-repeat="itemFormEle in ${ngFormElements}" on-finish-render>
            <div class="form-group">
                <label class="col-sm-2 control-label text-custom">{{itemFormEle.name}}</label>
                <#--text类型-->
                <div class="col-sm-10" ng-if="itemFormEle.type == 'text'">
                    <#-- 调用input_text组件 -->
                    <@input_text
                    modelName=modelName
                    name="${modelChildName}[itemFormEle.id]"
                    placeholder="{{'请输入'+itemFormEle.name}}"
                    addModelName=addModelName
                    isInit=false
                    ></@input_text>
                    <#--<input type="text"
                           class="form-control"
                           ng-if="itemFormEle.type == 'text'"
                           modelname="${addModelName}"
                           name="${modelChildName}.{{itemFormEle.id}}"
                           ng-model="${addModelName}.${modelChildName}[itemFormEle.id]"
                           placeholder="{{'请输入'+itemFormEle.name}}"
                    >-->
                </div>
                <div class="col-sm-10" ng-if="itemFormEle.type == 'int'">
                    <#--数字-->
                    <@input_int
                        modelName=modelName
                        placeholder="{{'请输入'+itemFormEle.name}}"
                        addModelName=addModelName
                        name="${modelChildName}[itemFormEle.id]"
                        isInit=false
                    >
                    </@input_int>
                    <#--<input type="text"
                           class="form-control"
                           ng-if="itemFormEle.type == 'int'"
                           pattern="^-?[0-9]*$"
                           modelname="${addModelName}"
                           name="${modelChildName}.{{itemFormEle.id}}"
                           ng-model="${addModelName}.${modelChildName}[itemFormEle.id]"
                           placeholder="{{'请输入'+itemFormEle.name}}"
                    >-->
                </div>
                <#--日期类型-->
                <div class="col-sm-10" ng-if="itemFormEle.type == 'dateTime'">
                    <#-- 调用input_dateTime组件 -->
                    <@input_dateTime
                        modelName=modelName
                        placeholder="{{'请选择'+itemFormEle.name}}"
                        addModelName=addModelName
                        name="${modelChildName}[itemFormEle.id]"
                        isInit=false
                    >
                    </@input_dateTime>
                    <#--<input type="text"
                           class="form-control"
                           ng-if="itemFormEle.type == 'dateTime'"
                           modelname="${addModelName}"
                           name="${modelChildName}.{{itemFormEle.id}}"
                           ng-model="${addModelName}.${modelChildName}[itemFormEle.id]"
                           placeholder="{{'请输入'+itemFormEle.name}}"
                           date-time-help="" date-time-format
                    >-->
                </div>
                <#-- 单图上传 -->
                <div class="col-sm-10" ng-if="itemFormEle.type == 'singleUploadImg'">
                    <@upImgPreview_component
                        addModelName=addModelName
                        modelName=modelName
                        name="${modelChildName}[itemFormEle.id]"
                        maxUploadCount="1"
                        isInit=false
                        isNgForm=true
                        modelChildName="${modelChildName}"
                        extensionId="{{itemFormEle.id}}"
                        targetSource="${modelName}_${modelChildName}_{{itemFormEle.code}}"
                        upImgId="${addModelName}FilePicker{{$index}}"
                        jsDirective="ng-form-up-img-help"
                    ></@upImgPreview_component>
                </div>
                <#-- 多图上传 -->
                <div class="col-sm-10" ng-if="itemFormEle.type == 'multiUploadImg'">
                    <@upImgPreview_component
                    addModelName=addModelName
                    modelName=modelName
                    name="${modelChildName}[itemFormEle.id]"
                    isInit=false
                    isNgForm=true
                    modelChildName="${modelChildName}"
                    extensionId="{{itemFormEle.id}}"
                    targetSource="${modelName}_${modelChildName}_{{itemFormEle.code}}"
                    upImgId="${addModelName}FilePicker{{$index}}"
                    jsDirective="ng-form-up-img-help"
                ></@upImgPreview_component>
                </div>
            </div>
        </div>
    </div>
</#macro>
<#--form和form元素================-->
<#macro sys_comp_form_and_element
modelName
submiturl=""
idName="id"
fields=[]
>
    <@sys_comp_form modelName=modelName fields=fields submiturl=submiturl idName=idName >
        <@sys_comp_form_element modelName=modelName idName=idName fields=fields />
    </@sys_comp_form>
</#macro>

<#--add modal的footer
    registerButtons:注入的按钮
    formSearchModelName：search的model名称
    showDefaultSaveButton:是否显示默认的保存按钮，默认false不显示
    defaultSaveBeforeCallBackFunction:保存之前调用，返回true，就继续执行，返回false就中断执行；可用于 自定义验证特殊字段
    transferDataType : 保存提交的数据类型：默认空或者json 是json提交；可以为 formData或者file

-->
<#macro sys_comp_add_modal_footer
modelName
registerButtons=[]
formSearchModelName=""
showDefaultSaveButton=false
defaultSaveButtonText="保存"
defaultSaveBeforeCallBackFunction="null"
defaultSaveSuccessCallBackFunction="null"
defaultSaveErrorCallBackFunction="null" <#-- 保存后失败的回调函数，不执行系统默认的函数-->
showCloseButton=true
transferDataType="json"
>
    <#local addModelName = "add${modelName}Model">
    <#local addFormId = "{{rootMenuId}}_add${modelName}_form_{{$id}}">
    <#local addModalId = "{{rootMenuId}}_add${modelName}_modal">

    <div class="modal-footer custom-modal-footer">

        <#if showDefaultSaveButton==true>
        <#--<div style="float: right;margin-left: 5px;position: relative;">-->
            <button type="" class="btn btn-primary btn-sm btn-round2 button-save" role="sysbutton"
                    ng-disabled="invalidForm(rootMenuId+'_add${modelName}_form_'+$id)"
                    ng-click="saveModalFormData(rootMenuId+'_add${modelName}_form_'+$id,'${addModelName}',
                    'formSearch${formSearchModelName}Model',rootMenuId+'_add${modelName}_modal',
${defaultSaveSuccessCallBackFunction},${defaultSaveBeforeCallBackFunction},${defaultSaveErrorCallBackFunction},${transferDataType})">
            ${defaultSaveButtonText}
            </button>
        <#--<div ng-show="invalidForm(rootMenuId+'_add${modelName}_form_'+$id)" style="position: absolute;width:100%;top: 0px;height:100%;background-color: red">&nbsp;</div>-->
        <#--</div>-->
        </#if>

        <#if registerButtons?size == 0>

        <#else>
        <#--自定义按钮-->
            <#list registerButtons as rbutton>

            <#--判断是否自动验证；如果为true，那么会自动验证表单来 启用和禁用 按钮的 功能
                等于false的时候，表单验证的结果不会影响按钮的 disabled，是disabled=false
            -->
                <#local _autoInvalidDisabled=rbutton.autoInvalidDisabled!true >
                <#local _url =rbutton.url!"" />
                <#local _className =rbutton.className!"button-save" /><#--html class 多个用空格分割-->
            <#--是否有点击事件-->
                <#local _clickFun = rbutton.clickFun!""/>
                <#local _defaultClick =rbutton.defaultClick!true/>
            <#--根据条件显示按钮，默认显示-->
                <#local _isShow =rbutton.isShow!""/>
                <button type="button" role="sysbutton"
                        class="btn btn-primary btn-sm btn-round2 ${_className}"
                        name="${rbutton.name}"
                <#--按钮需要的url-->
                        url="${SYS_BASE_PATH_ADMIN}${_url}"
                <#--表单模型-->
                        modelname="${addModelName}"
                <#--form id-->
                        formid="${addFormId}"
                <#--搜索的模型-->
                        formsearchmodelname="formSearch${formSearchModelName}Model"
                <#--弹出框的id-->
                        modalid="${addModalId}"
                <#--注入的指令-->
                ${rbutton.jsDirective}
                <#--是否有默认点击事件-->
                    <#if _clickFun != "" && _defaultClick == true>
                        ng-click="${_clickFun}(rootMenuId+'_add${modelName}_form_'+$id,'${addModelName}','formSearch${formSearchModelName}Model',rootMenuId+'_add${modelName}_modal',${defaultSaveSuccessCallBackFunction},${defaultSaveBeforeCallBackFunction},${defaultSaveErrorCallBackFunction})"
                    </#if>
                <#--非默认点击参数函数-->
                    <#if _clickFun != "" && _defaultClick == false>
                        ng-click="${_clickFun}"
                    </#if>
                ${_isShow}

                <#--是否自动验证，影响按钮的disabled-->
                    <#if _autoInvalidDisabled==true>
                        ng-disabled="invalidForm(rootMenuId+'_add${modelName}_form_'+$id)"
                    </#if>
                >${rbutton.text}</button>
            </#list>
        </#if>

        <#if showCloseButton>
            <button type="button" class="btn btn-default btn-sm btn-round2 button-default" data-dismiss="modal">关闭
            </button>
        </#if>
    </div>
</#macro>
<#--
树形结构封装
-->
<#macro sys_tree_view
addModelName
name
key="id"
isMulti=false
requestUrl=""
jsDirective=""
>
<#--是否多选-->
    <#if isMulti>
        <#local _defaultValue ="[]" />
    <#else>
        <#local _defaultValue ="''" />
    </#if>
    <div style="position: relative;" class="selectTreeViewComponent">
        <div class="form-control" tree-view-operation
        ${jsDirective}
             request-url="${requestUrl}"
             ng-init="${addModelName}.${name}=${_defaultValue}"
             request-id="${key}"
             is-multi="${isMulti?string}"
             modelname="${addModelName}.${name}">
            <span class="current">请选择</span>
            <span class="pull-right"><b class="caret"></b></span>
        </div>
        <div class="selectTreeView" style="display: none;">

        </div>
    </div>
</#macro>