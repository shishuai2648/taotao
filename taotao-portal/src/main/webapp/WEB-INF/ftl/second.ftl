<html>
    <head>
        <title>${title!"我是标题"}</title>
    </head>
    <body>
        <#include "first.ftl">
        <label>学号</label>${student.id}<br/>
        <label>姓名</label>${student.name}<br/>
        <label>住址</label>${student.address}<br/>
    </body><br/>
    学生列表：
    <table border="1">
        <#list students as s>
            <#if s_index % 2 == 0>
                <tr style="color:red">
            <#else>
                <tr>
            </#if>
                    <td>${s_index}</td>
                    <td>${s.id}</td>
                    <td>${s.name}</td>
                    <td>${s.address}</td>
                </tr>
        </#list>
    </table>

    <#--<br/>-->
    <#--当前日期：${curdate?date}-->
    <#--<br/>-->
    <#--当前日期：${curdate?time}-->
    <#--<br/>-->
    <#--当前日期：${curdate?datetime}-->
    <#--<br/>-->
    <#--当前日期：${curdate?string("yyyy-MM-dd HH:mm:ss")}-->

    <br />
    <#if curdate ??>
        当前日期：${curdate?string("yyyy-MM-dd HH:mm:ss")}
        <#else>
            curdate属性为null
    </#if>

</html>