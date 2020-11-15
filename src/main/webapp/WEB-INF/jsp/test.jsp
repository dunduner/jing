<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="zh">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>Full Layout - jQuery EasyUI Demo</title>


    <script type="text/javascript">
        //需要判断浏览器支不支持，可以去w3c进行查看
        var source = new EventSource('/tingdan/get_data/1');
        source.onmessage = function (event) {
            console.info("开始听单！")
            console.info(event.data);
            document.getElementById('result').innerText = event.data
        };

    </script>
</head>
<body>


听单：<span id="result"></span>
</body>
</html>
