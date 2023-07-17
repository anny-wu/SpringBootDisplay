<#import "/spring.ftl" as spring/>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css"/>
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <title>editUser</title>
</head>
<style>
    h2{
        text-align: center;
        margin-top: 50px;
    }
    .back{
        background-color: lightsalmon;
        border-radius: 20px;
    }
    .flabel{
        margin-top: 20px;
    }
    .form-group{
        margin-top: 20px;
        margin-left: 20px;
    }
    #labele{
        margin-top: 10px;
    }
    .controlB{
        width: 200px;
        margin: 10px;
    }
    #buttons{
        margin-top:20px;
        margin-bottom:20px;
        float:right;
    }

</style>

<body>
<div>
    <h2>Edit User</h2>
</div>
<div class="row">
    <div class="col-8 m-auto back">
        <form>
            <input type="hidden" id="selected" name="id" value="1">
            <div class="form-group row">
                <label class="flabel col-2 col-form-label"><strong>TABLE NAME</strong></label>
                <div class="col-4">
                    <input type="text" readonly class="flabel col-4 form-control-plaintext" name="table_name"
                           value="STUDENT">
                </div>
            </div>
                <div class="form-group row">
                    <label id="labele" class="col-2 col-form-label"><strong>Edit</strong></label>
                    <div class="col-6">
                        <div class="form-outline">
                            <#if students??>
                                <select id="selectS" class="form-select">
                                    <@spring.bind "student.id"/>
                                    <#if errorMsg??>
                                        <#list students as s>
                                            <#if s[0] == selected>
                                                <option value=${s[0]} selected>User ${s[0]} (${s[1]})</option>
                                            <#else>
                                                <option value=${s[0]}>User ${s[0]} (${s[1]})</option>
                                            </#if>
                                        </#list>
                                    <#else>
                                        <#list students as s>
                                            <option value=${s[0]}>User ${s[0]} (${s[1]})</option>
                                        </#list>
                                    </#if>
                                </select>
                            </#if>
                        </div>
                    </div>
                </div>
                <div class="form-group row">
                    <#list 0..uppercase?size-1 as i>
                        <#assign str = "student." + lowercase[i]>
                        <@spring.bind "${str}"/>
                        <div class="col-6">
                            <label class="flabel col-2 col-form-label"><strong>${uppercase[i]}</strong></label>
                            <div class="form-outline w-50">
                                <input type="${lowercase[i]}" class="form-control edit" name="${lowercase[i]}"
                                       placeholder="${lowercase[i]}">
                            </div>
                        </div>
                    </#list>
                </div>
                <#if errorMsg??>
                    <span id="userExists" class="text-center">${errorMsg}</span>
                </#if>
                <div id="buttons">
                    <div>
                        <button id="edit" type="submit" class="btn btn-lg btn-primary controlB disabled"
                                formmethod="post" formaction="/editStudent">Edit</button>
                        <button type="submit" class="btn btn-lg btn-primary controlB"
                                formmethod="get" formaction="/display">Back</button>
                    </div>
                </div>
        </form>
    </div>
</div>
</body>
<script>
    $(document).ready(function() {
        //Record the selected user id for edit
        $('#selectS').change(function () {
            $("#selected").attr("value", $("#selectS").val());
        });
        //Change edit enable status when input is empty
        $('.edit').on('input',function() {
            var empty = false;
            $('.edit').each(function() {
                if ($(this).val() == '') {
                    empty = true;
                }
            });

            if (empty) {
                $('#edit').addClass('disabled');
            }
            else{
                $('#edit').removeClass('disabled');
            }
        });
    });
</script>
</html>
