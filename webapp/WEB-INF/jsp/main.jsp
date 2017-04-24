<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <!-- title, character encoding, Metadata, style, script, external file(for page rendering image etc..)-->
    <title>ESTsecurity pilot project</title>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1"/>


    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/jstree.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jstree/3.2.1/themes/default/style.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/main.css"/>

    <link rel="icon" href="${pageContext.request.contextPath}/assets/img/al.jpg">
</head>

<body>
<header id="parentbox">
    <img id="logo" src="${pageContext.request.contextPath}/assets/img/ESTsecurity_CI_green.png">
    <h1>Remote File Explorer</h1>
</header>

<div>
    <nav id="dir-nav">
        <h4>Directories</h4>
        <div id="jstree">
            <ul>
                <li>C:\
                    <ul>
                        <li id="child_node_1">Child node 1<ul><li></li></ul></li>
                        <li id="child_node_2">Child node 2<ul><li></li></ul></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>
    <section id="main-contents">
        <div class="table-responsive">
            <h4 id="currentDir">${currentDirectory}</h4>
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
                    <td ondblclick="onClickDirEntry(null, 'dir')">..</td>
                    <td></td>
                    <td>dir</td>
                    <td></td>
                </tr>
                <c:forEach items="${dirEntryList }" var="FileMetaInfoVo" varStatus="status">
                    <tr>
                        <%--<td rel="popover" data-popover-content="#op-menu" ondblclick="onClickDirEntry('${FileMetaInfoVo.name}', '${FileMetaInfoVo.fileType}')" >${FileMetaInfoVo.name}</td>--%>
                        <td ondblclick="onClickDirEntry('${FileMetaInfoVo.name}', '${FileMetaInfoVo.fileType}')" >${FileMetaInfoVo.name}</td>
                        <td>${FileMetaInfoVo.time}</td>
                        <td>${FileMetaInfoVo.fileType}</td>
                        <td>${FileMetaInfoVo.size}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <div>
            <form id="fileup-form" method="post" enctype="multipart/form-data" action="${pageContext.request.contextPath}/file-upload">
                <div>
                    <input type="file" id="targfile" name="targfile">
                    <input type="hidden" name="savePath" value="${currentDirectory}">
                </div>
                <br>
                <div>
                    <button id="btn-submit" type="button" class="btn btn-link">upload</button>
                </div>
            </form>
        </div>
    </section>
</div>

<%--menu popover--%>
<div id="op-menu" class="list-group hide">
    <a href="#" class="list-group-item copy-menu">Copy</a>
    <a href="#" class="list-group-item move-menu" data-toggle="modal" data-target="#moveModal" data-targpath="" data-curdir="${currentDirectory}">Move</a>
    <a href="#" class="list-group-item rename-menu" data-toggle="modal" data-target="#renameModal" data-targname="" data-curdir="${currentDirectory}">Rename</a>
</div>

<%--rename modal--%>
<div class="modal fade" id="renameModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="renameModalLabel">Rename </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form-rename" method="post" action="${pageContext.request.contextPath}/rename">
                    <div class="form-group">
                        <label for="new-name" class="form-control-label">New Name:</label>
                        <input type="text" class="form-control" name='new-name' id="new-name">
                        <input type="hidden" class="form-control" name='old-name' id="old-name">
                        <input type="hidden" class="form-control" name="cur-dir" id="cur-dir" value="">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="btn-rename" type="button" class="btn btn-primary">Modify</button>
            </div>
        </div>
    </div>
</div>

<%--move modal--%>
<div class="modal fade" id="moveModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="moveModalLabel">Move to </h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <div class="modal-body">
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                <button id="btn-move" type="button" class="btn btn-primary">Modify</button>
            </div>
        </div>
    </div>
</div>

</body>

<script>
    $(function () {
        $('#jstree').jstree({
            'core' : {
                'data' : {
                    "url" : "${pageContext.request.contextPath}/ajaxdir",
                    "data" : function (node) {
                        return { "id" : node.id };
                    }
                }
            }
        });
        // 8 interact with the tree - either way is OK
        $('button').on('click', function () {
//            $('#jstree').jstree(true).select_node('child_node_1');
//            $('#jstree').jstree('select_node', 'child_node_1');
            //$.jstree.reference('#jstree').select_node('child_node_1');
        });
    });

</script>
<script type="text/javascript">

    //file upload request submit
    $(document).ready(function () {
        $("#btn-submit").click(function () {
            $("#fileup-form").submit();
        });
    });
    //rename request submit
    $(document).ready(function () {
        $("#btn-rename").click(function () {
            var renameForm = $("#form-rename");
            renameForm.submit();
        });
    });

    //menu popover script
    $(function(){
        $('[rel="popover"]').popover({
            container: 'body',
            html: true,
            content: function () {
                var clone = $($(this).data('popover-content')).clone(true).removeClass('hide');
                var filename = $(this).html();
                clone.find('.rename-menu').attr('data-targname', filename);
                return clone;
            }
        });
    });

    //rename modal script
    $('#renameModal').on('show.bs.modal', function (event) {
        var li = $(event.relatedTarget); // Button that triggered the modal
        var curdir = li.data('curdir'); // Extract info from data-* attributes
        var targname = li.data('targname');
        // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
        // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
        var modal = $(this);
        modal.find('.modal-title').text('Rename ' + curdir + targname);
        modal.find('#new-name').val('');
        modal.find('#old-name').val(targname);
        modal.find('#cur-dir').val( curdir);
    });

    //move modal script
    $('#moveModal').on('show.bs.modal', function (event) {
        var li = $(event.relatedTarget);
        var curdir = li.data('curdir');
        var targpath = li.data('targpath');
        var modal = $(this);
        modal.find('.modal-body').html('<ul><li>hi1</li><li>hi2</li></ul>');
    });

    var onClickDirEntry = function (targName, fileType) {
        var actionType = null;
        if (fileType === 'dir')
            actionType = 'dir';
        else
            actionType = 'file-download'

        var formTag = document.createElement("form");
        formTag.method = "post";
        formTag.action = "${pageContext.request.contextPath}/" + actionType;

        var currentDir = document.getElementById("currentDir").innerHTML;
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = "path";
        if(targName === null)
            input.value = currentDir.substring(0, currentDir.lastIndexOf('\\'));
        else
            input.value = currentDir + '\\' + targName;
        formTag.insertBefore(input, null);

        document.body.insertBefore(formTag, null);
        formTag.submit();

    }

    var getDirEntry = function (targName) {
        var formTag = document.createElement("form");
        formTag.method = "post";
        formTag.action = "${pageContext.request.contextPath}/dir";

        var currentDir = document.getElementById("currentDir").innerHTML;
        var input = document.createElement("input");
        input.type = "hidden";
        input.name = "path";
        input.value = currentDir + '\\' + targName;
        formTag.insertBefore(input, null);

        document.body.insertBefore(formTag, null);
        formTag.submit();
    }
</script>
</html>