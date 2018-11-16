var config = new function () {
    'use strict';
// 获取项目根目录
    var basePath = function () {
        var path = window.document.location.href;
        var pathName = window.document.location.pathname;
        var pos = path.indexOf(pathName);
        var localhostPath = path.substring(0, pos);
        var projectName = pathName
            .substring(0, pathName.substr(1).indexOf('/') + 1);
        return localhostPath + projectName;
    };

    var url_local = basePath();
    this.picUpload = {
        swf: url_local + '/webUpload',
    };
}