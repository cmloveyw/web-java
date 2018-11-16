<%@ page language="java" pageEncoding="UTF-8" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
    <meta charset="utf-8">
    <link rel="icon" href="https://static.jianshukeji.com/highcharts/images/favicon.ico">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
    </style>
    <script src="https://img.hcharts.cn/jquery/jquery-1.8.3.min.js"></script>
    <script src="https://img.hcharts.cn/highcharts/highcharts.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/exporting.js"></script>
    <script src="https://img.hcharts.cn/highcharts-plugins/highcharts-zh_CN.js"></script>
    <script src="https://img.hcharts.cn/highcharts/modules/variable-pie.js"></script>
    <script src="https://img.hcharts.cn/highcharts/themes/dark-unica.js"></script>
</head>
<body>
<div id="container" style="width:400px;height:400px"></div>
<script>
    $(function () {
        Highcharts.chart('container', {
            chart: {
                type: 'variablepie'
            },
            title: {
                text: '不同国家人口密度及面积对比'
            },
            subtitle: {
                text: '扇区长度（圆周方法）表示面积，宽度（纵向）表示人口密度'
            },
            tooltip: {
                headerFormat: '',
                pointFormat: '<span style="color:{point.color}">\u25CF</span> <b> {point.name}</b><br/>' +
                             '面积 (平方千米): <b>{point.y}</b><br/>' +
                             '人口密度 (每平方千米人数): <b>{point.z}</b><br/>'
            },
            series: [{
                minPointSize: 10,
                innerSize: '20%',
                zMin: 0,
                name: 'countries',
                data: [{
                    name: '西班牙',
                    y: 505370,
                    z: 92.9
                }, {
                    name: '法国',
                    y: 551500,
                    z: 118.7
                }, {
                    name: '波兰',
                    y: 312685,
                    z: 124.6
                }, {
                    name: '捷克共和国',
                    y: 78867,
                    z: 137.5
                }, {
                    name: '意大利',
                    y: 301340,
                    z: 201.8
                }, {
                    name: '瑞士',
                    y: 41277,
                    z: 214.5
                }, {
                    name: '德国',
                    y: 357022,
                    z: 235.6
                }]
            }]
        });

        /*$('#container').highcharts({
                                       chart: {
                                           type: 'bar'
                                       },
                                       title: {
                                           text: '堆叠条形图'
                                       },
                                       xAxis: {
                                           categories: ['苹果', '橘子', '梨', '葡萄', '香蕉']
                                       },
                                       yAxis: {
                                           min: 0,
                                           title: {
                                               text: '水果消费总量'
                                           }
                                       },
                                       legend: {
                                           reversed: true
                                       },
                                       plotOptions: {
                                           series: {
                                               stacking: 'normal'
                                           }
                                       },
                                       series: [{
                                           name: '小张',
                                           data: [5, 3, 4, 7, 2]
                                       }, {
                                           name: '小彭',
                                           data: [2, 2, 3, 2, 1]
                                       }, {
                                           name: '小潘',
                                           data: [3, 4, 4, 2, 5]
                                       }]
                                   });*/
    });
</script>
</body>
</html>
​
