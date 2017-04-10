<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%--있어야하는 변수목록--%>

<%--connectedIPList--%>
<%--working directory--%>
<%--working directory contents--%>
<%--working ip--%>

<%--작성해야하는 jQuery--%>

<%--Dir--%>
<%--filedownload--%>
<%--fileupload--%>
<%--rename--%>
<%--copy, paste (local)--%>
<%--delete--%>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- title, character encoding, Metadata, style, script, external file(for page rendering image etc..)-->
    <title>ESTsecurity pilot project</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css"/>

    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css"/>
    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/al.jpg">
</head>

<body>
<header id="parentbox">
    <img id="logo" src="${pageContext.request.contextPath}/assets/img/ESTsecurity_CI_green.png">
    <h1>Remote File Explorer</h1>
</header>

<div>
    <nav id="connected-ip-list">
        <h4>Connected Computers</h4>
        <div id="jstree">
            <!-- in this example the tree is populated from inline HTML -->
            <ul>
                <li>192.168.0.1
                    <ul>
                        <li id="child_node_1">Child node 1</li>
                        <li>Child node 2</li>
                    </ul>
                </li>
                <li>192.168.0.2
                    <ul>
                        <li>technical document</li>
                        <li>workspace</li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <section id="main-contents">
        <div class="table-responsive">
            <h4>192.168.0.2</h4>
            <p>C:\Users\joonho\Desktop</p>
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Name</th>
                    <th>Last modified date</th>
                    <th>Type</th>
                    <th>Size</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td><a href="#">..</a></td>
                    <td> </td>
                    <td>dir</td>
                    <td> </td>
                </tr>
                <tr>
                    <td><a href="#">technical document</a></td>
                    <td>2017-04-05 12:05:23</td>
                    <td>dir</td>
                    <td> </td>
                </tr>
                <tr>
                    <td><a href="#">flower</a></td>
                    <td>2017-04-05 12:05:23</td>
                    <td>jpg</td>
                    <td> 34561B </td>
                </tr>
                <tr>
                    <td><a href="#">workspace</a></td>
                    <td>2017-04-05 12:05:23</td>
                    <td>dir</td>
                    <td> </td>
                </tr>
                <tr>
                    <td><a href="#">EffectiveJava</a></td>
                    <td>2017-04-05 12:05:23</td>
                    <td>pdf</td>
                    <td> 184361B </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div>
            <form id="file-upload" method="post" enctype="multipart/form-data">
                <div>
                    <input id="targfile" name="targfile" type="file">
                </div>
                <br>
                <div>
                    <button id="btn-submit" type="button" class="btn btn-link">upload</button>
                </div>
            </form>
        </div>
    </section>
</div>

<footer>
    <small>Copyright (C) 2017 ESTsecurity Corp. All Rights Reserved.</small>
</footer>
</body>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
<script>
    $(function () {
        // 6 create an instance when the DOM is ready
        $('#jstree').jstree();
        // 7 bind to events triggered on the tree
        $('#jstree').on("changed.jstree", function (e, data) {
            console.log(data.selected);
        });
        // 8 interact with the tree - either way is OK
        $('button').on('click', function () {
            $('#jstree').jstree(true).select_node('child_node_1');
            $('#jstree').jstree('select_node', 'child_node_1');
            $.jstree.reference('#jstree').select_node('child_node_1');
        });
    });
</script>
<script type="text/javascript">
    $(document).ready(function () {

        $("#btn-submit").click(function () {
            var formData = new FormData();
            formData.append("targfile", $("#targfile")[0].files[0]);
            $.ajax({
                url: "${pageContext.request.contextPath}/~~~",
                type: "POST",
                data: formData,
                dataType: 'json',
                processData: false,
                contentType: false,

                success: function (response, textStatus) {

                },
                error: function (request, status, error) {
                    console.log(status + ":" + error);
                    console.log("-->code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                }
            })
        });
        return false;
    });

</script>

</html>