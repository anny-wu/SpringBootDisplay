<#import "/spring.ftl" as spring/>

<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css"
          integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="/static/css/style.css"/>
    <script src="https://code.jquery.com/jquery-3.7.0.js"
            integrity="sha256-JlqSTELeR4TLqP0OG9dxM7yDPqX1ox/HfgiSLBj8+kM=" crossorigin="anonymous"></script>
    <title>Delete User</title>
</head>
<body>
<div>
    <h2>Delete User</h2>
</div>
<div class="row">
    <div class="col-8 m-auto back">
        <form>
            <input id="deleted" type="hidden" name="id" value="1">
            <div class="form-group row">
                <label class="flabel col-2 col-form-label"><strong>TABLE NAME</strong></label>
                <div class="col-4">
                    <input type="text" readonly class="flabel col-4 form-control-plaintext" name="table_name"
                           value="STUDENT">
                </div>
            </div>
                <div class="form-group row">
                    <label class="flabel col-2 col-form-label"><strong>DELETE</strong></label>
                    <div class="col-4">
                        <div class="form-outline">
                            <#if students??>
                            <select id="deleteS" class="form-select">
                                <#list students as student>
                                <option value=${student[0]}>User ${student[0]} (${student[1]})</option>
                                </#list>
                            </select>
                            </#if>
                        </div>
                    </div>
                </div>
                <div id="buttons">
                    <div>
                        <button type="submit" class="btn btn-lg btn-primary controlB" formmethod="post"
                                formaction="/deleteStudent">Delete
                        </button>
                        <button type="submit" class="btn btn-lg btn-primary controlB" formmethod="get"
                                formaction="/display">Back
                        </button>
                    </div>
                </div>
        </form>
    </div>
</div>
</body>
<script>
    //Record the selected user id for deletion
    $(document).ready(function() {
        $('#deleteS').change(function () {
            $("#deleted").attr("value", $("#deleteS").val());
        });
    });

</script>
</html>
