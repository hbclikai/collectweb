<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>status</title>
	<script type="text/javascript" src="js/echarts.min.js"></script>
</head>
<body>
	<div id="main" style="width: 100%;height:400px;"></div>
    <script type="text/javascript">
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

       
        
        function randomData() {
            now = new Date(+now + oneDay);
            value = value + Math.random() * 21 - 10;
            return {
                name: now.toString(),
                value: [
                    [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
                    Math.round(value)
                ]
            }
        }

        var data = [];
        var now = +new Date(1997, 9, 3);
        var oneDay = 24 * 3600 * 1000;
        var value = Math.random() * 1000;
        for (var i = 0; i < 100 ; i++) {
            data.push(randomData());
        }
        for (var i = 0; i < 10 ; i++) {
            data.push({
                name: '1',
                value: [
                    '2002/12/22',
                    1234
                ]
            });
        }
        var option = {
            title: {
                text: '动态数据 + 时间坐标轴'
            },
            tooltip: {
                trigger: 'axis',
//                 formatter: function (params) {
//                     params = params[0];
//                     var date = new Date(params.name);
//                     return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
//                 },
                axisPointer: {
                    animation: false
                }
            },
            xAxis: {
                type: 'time'
            },
            yAxis: {
                type: 'value',
                boundaryGap: [0, '100%']
            },
            dataZoom: [
                       {
                           show: true,
                           realtime: true,
                           start: 90,
                           end: 100
                       },
                       {
                           type: 'inside',
                           realtime: true,
                           start: 90,
                           end: 100
                       }
                   ],
            series: [{
                name: '模拟数据',
                type: 'line',
                showSymbol: false,
                hoverAnimation: false,
                data: data
            }]
        };

        setInterval(function () {
            for (var i = 0; i < 1; i++) {
                data.shift();
                data.push(randomData());
            }
            myChart.setOption({
                series: [{
                    data: data
                }]
            });
        }, 1000);
        
        

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    </script>
</body>
</html>