<html>
    <head>
        <title>${title}</title>
    </head>
    <body>
        <label>学号</label>${student.id}<br/>
        <label>姓名</label>${student.name}<br/>
        <label>住址</label>${student.address}<br/>
    </body><br/>
    学生列表：
    <table border="1">
        <#list students as s>
            <tr>
                <td>${s_index}</td>
                <td>${s.id}</td>
                <td>${s.name}</td>
                <td>${s.address}</td>
            </tr>
        </#list>
    </table>

</html>