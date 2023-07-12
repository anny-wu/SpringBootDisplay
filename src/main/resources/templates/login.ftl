<#import "/spring.ftl" as spring/>

<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.2/font/bootstrap-icons.css" integrity="sha384-b6lVK+yci+bfDmaY1u0zE8YYJt0TZxLEAFyYSLHId4xoVvsrQu3INevFKo+Xir8e" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
</head>
<style>
    .flabel{
        margin-top: 20px;
    }
    .form-control{
        width:500px;
    }
    .controls{
        width:200px;
    }
    #showpassword{
        float:right;
        margin-top: -25px;
        right:10px;
        position: relative;
        z-index: 2;
    }
</style>
<body>
<div class="cover-container d-flex h-100 p-3 flex-column align-items-center justify-content-center">
    <h1>Welcome to Database Users</h1>
    <#if username??>
        Log in as : ${username}
        <span class="errormsg">${noPrivilege}</span>
        <form id="choose">
            <div class="row">
                <button class="flabel btn btn-info" type="submit" id="admin" formaction="user/display">Log In As
                    Admin</button>
            </div>
            <div class="row">
                <button class="flabel  btn btn-info" type="submit" id="user" formaction="user/display">Log In As
                    User</button>
            </div>
        </form>
    <#else>
        <form>
            <#if error??>
            <span class="errormsg">${errorMessage}</span>
            </#if>
            <input type="hidden" name="action" value="login">
            <div>
                <label class="flabel"><strong>Username</strong></label>
                <input type="text" class="form-control" name="username"
                       aria-describedby="usernameHelp'"
                       placeholder="username">
                <#if uerror??>
                <span class="errormsg">${uerror}</span>
                </#if>
            </div>
            <div>
                <label class="flabel"><strong>Password</strong></label>
                <input  type="password" class="form-control" name="password"
                        aria-describedby="passwordHelp"
                        placeholder="password">
                <i id="showpassword" class="bi bi-eye-slash"></i>
                <#if perror??>
                <span class="errormsg">${perror}</span>
                </#if>
            </div>
            <div>
                <div class="row text-center">
                    <div>
                        <button class="controls flabel btn btn-info" type="submit"  formaction="Access">Log In</button>
                    </div>
                    <div>
                        <button class="controls flabel btn btn-info" type="submit" formaction="/register">Sign
                            up</button>
                    </div>
                </div>
            </div>
        </form>
    </#if>

</div>
</body>
<script>
    $(document).ready(function() {
        //Toggle password visibility
        $("#showpassword").on("click", function () {
            if ($("#showpassword").hasClass("bi-eye")) {
                $("#showpassword").removeClass("bi-eye");
                $("#showpassword").addClass("bi-eye-slash");
                $("input[name='password']").attr("type", "password");
            } else {
                $("#showpassword").removeClass("bi-eye-slash");
                $("#showpassword").addClass("bi-eye");
                $("input[name='password']").attr("type", "text");
            }
        });
        //Get the privilege selected for login
        let buttonClicked = "";
        $("#choose button[type = 'submit']").click(function(e){
            buttonClicked = $(this).attr("id");
        })
        $("#choose").submit(function () {
            $("#privilege").val(buttonClicked);
        });
    });

</script>
</html>


