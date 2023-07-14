<#import "/spring.ftl" as spring/>
<html>
<head>
    <title>Registration</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css" integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css"/>
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
</head>

<body>
<!--TODO: add a link to the login page when user accidently enters this page-->
<#if message??>
    <div class="cover-container d-flex h-100 p-3 flex-column align-items-center justify-content-center">
        <h1>Error</h1>
    <#if message == 1>
        <span class="error text-center">Email could not be sent</span>
        <#elseif message == 2>
        <span class="error text-center">Missing verification code</span>
        <#else>
        <span class="error text-center">Verification code has expired</span>
    </#if>
    <#if message == 3 >
        <form method="post">
            <input type="hidden" name="token" value="${token}">
            <div class="row">
                <button class="flabel btn btn-info" type="submit" formaction="/resend">Resend Email</button>
            </div>
        </form>
    <#else>
        <form>
            <div class="row">
                <button class="flabel btn btn-info" type="submit" formaction="/register">Sign Up</button>
            </div>
        </form>
    </#if>
    </div>
</#if>
</body>
</html>
