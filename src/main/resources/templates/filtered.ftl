<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css" integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <title>Database Display</title>

    <style>
        body{
            text-align: center;
        }

        h2 {
            text-align: center;
            margin-top: 50px;
        }

        a {
            color: black;
            text-decoration: none;
            margin-right: 20px;
        }

        #controls{
            text-align:right;
        }

        .display{
            margin-top:20px;
            margin-bottom: 20px;
        }

        table {
            width: 800px;
        }

    </style>
</head>

<body>
<div>
    <h2>Users</h2>
</div>
<div class="row">
    <div class="col-8 m-auto">
        <div id="controls">
            <form method="post" class="display" action="/logout">
                <button class="btn btn-secondary" type="submit" value="Logout">Log Out</button>
            </form>
            <div>
                <form id="display" class="display d-flex justify-content-between">
                    <#--<input type="hidden" id="pageS" name="pageS" value="${pageS}">-->
                    <div>
<#--<<input type="hidden" name="table_name" value="${table_name}">-->
                        <#if count?? && count gt 0>
                            <button id="edit" type="submit" formaction="editUser.jsp"
                                    class="btn btn-primary btn-sm">Edit</button>
                        </#if>
                    </div>
                    <div>
                        <#if values??>
                            Display
                            <#import "select.ftl" as selection/>
                            <@selection.select id="ps" values= values selected="${pageS!3}"/>
                            rows per page
                            <button class="btn btn-outline-primary btn-sm"
                                    formaction="/display" type="submit">Go</button>
                        </#if>
                    </div>
                    </form>
                    </div>

                    <table class="table table-striped table-hover mx-auto">
                    <thead class="table-info">
                    <tr>
                        <#if fields??>
                    <#list fields as fname>
                    <th scope="col">${fname}</th>
                    </#list>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#if students??>
                    <#list students as student>
                    <tr>
                    <#list student as value>
                        <td>${value}</td>
                    </#list>
                    </tr>
                    </#list>
                    </#if>
                    </tbody>
                    </table>
<#--
<nav class="m-auto">
<ul class="pagination justify-content-center">
<#if currentPage == 1>
<li class="page-item disabled">
<a class="page-link" href="#" tabindex="-1" aria-disabled="true"><span aria-hidden="true">&laquo;</span></a>
</li>
<#else>
<li class="page-item">
<a class="page-link" href="display?currentPage=${currentPage-1}&pageS
=${pageS}" aria-disabled="true"><span aria-hidden="true">&laquo;</span></a>
</li>
</#if>

<#list 1..totalCount as i>
<#if currentPage == i>
    <li class="page-item active" aria-current="page">
<#else>
    <li class="page-item">
</#if>
<a class="page-link"
   href="display?currentPage=${i}&pageS=${pageS}">${i}</a></li>
</#list>

<#if currentPage == totalCount>
<li class="page-item">
<a class="page-link disabled" href="#">&raquo;</a>
</li>
<#else>
<li class="page-item">
<a class="page-link" href="display?currentPage=${currentPage+1}&pageS=${pageS}">
    &raquo;</a>
</li>
</#if>
</ul>
</nav>
-->
</div>
</div>
</div>
</body>
</html>

