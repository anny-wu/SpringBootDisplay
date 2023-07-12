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
<div class="cover-container d-flex h-100 flex-column align-items-center justify-content-center">
    <h1>Register</h1>
        <form method="post">
            <div>
                <label class="flabel"><strong>Email</strong></label>
                <@spring.bind "user.email"/>
                <#if spring.status.value?has_content>
                <input type="text" class="form-control"
                       aria-describedby="emailHelp"
                       placeholder="email" id="email"
                       name="${spring.status.expression}"
                       value="${spring.status.value}"/>
                <#else>
                    <input type="text" class="form-control"
                           aria-describedby="emailHelp"
                           placeholder="email" id="email"
                           name="${spring.status.expression}"/>
                </#if>
                <span class="error">
                    <#if spring.status.errorMessages?has_content>
                        ${spring.status.errorMessages[0]}
                    </#if>
                </span>
            </div>
            <div>
                <label class="flabel"><strong>Username</strong></label>
                <@spring.bind "user.username"/>
                <#if spring.status.value?has_content>
                    <input type="text" class="form-control"
                           aria-describedby="usernameHelp'"
                           placeholder="username" id="username"
                           name="${spring.status.expression}"
                           value="${spring.status.value}">
                    <#else>
                        <input type="text" class="form-control"
                               aria-describedby="usernameHelp'"
                               placeholder="username" id="username"
                               name="${spring.status.expression}">
                </#if>
                <span class="error">
                    <#if spring.status.errorMessages?has_content>
                        ${spring.status.errorMessages[0]}
                    </#if>
                </span>
            </div>
            <div>
                <label class="flabel"><strong>Password</strong></label>
                <@spring.bind "user.password"/>
                <input type="password" class="form-control"
                       aria-describedby="passwordHelp"
                       placeholder="password" id="password"
                       name="${spring.status.expression}">
                <i id="showpassword" class="bi bi-eye-slash"></i>
                <span class="error">
                    <#if spring.status.errorMessages?has_content>
                        ${spring.status.errorMessages[0]}
                    </#if>
                </span>
            </div>

            <div>
                <label class="flabel"><strong>Confirm Password</strong></label>
                <input type="password" class="form-control" name="confirm"
                       aria-describedby="passwordHelp"
                       placeholder="password" id="confirm">
                <span class="error" id="confirmerror"></span>
            </div>

            <div>
                <label class="flabel"><strong>Admin Code</strong>(optional)</label>
                <@spring.bind "user.privilege"/>
                <input type="text" class="form-control" name="privilege"
                       aria-describedby="privilegeHelp"
                       placeholder="admin code" id="privilege">
            </div>
        <div class="row d-flex justify-content-center">
            <button class="controls flabel btn btn-info" type="submit" onclick="return validateSame()">Sign up</button>
        </div>
        <div class="row d-flex justify-content-center">
            <button class="controls flabel btn btn-info" type="submit" formaction="/home">Back</button>
        </div>
        </form>
</div>
</body>
<script>
    //Toggle password visibility
    $("#showpassword").on("click", function(){
        if($("#showpassword").hasClass("bi-eye")){
            $("#showpassword").removeClass("bi-eye");
            $("#showpassword").addClass("bi-eye-slash");
            $("input[name='password']").attr("type","password");
        }else{
            $("#showpassword").removeClass("bi-eye-slash");
            $("#showpassword").addClass("bi-eye");
            $("input[name='password']").attr("type","text");
        }
    });

    function validateSame(){
        //Check if the passwords match each other
        if ($("#password").val() == $("#confirm").val()) {
            console.log("same");
            $("#confirm").focus();
            window.location.replace("/register");
            return true;
        } else {
            console.log("Not same");
            $("#confirmerror").text("Passwords do not match");
            $("#confirm").focus();
            return false;
        }
    }
</script>
</html>

