<%@ page import="me.ulearn.DirectoryParser" %>
<%@ page import="me.ulearn.FileObject" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% DirectoryParser directory = (DirectoryParser) request.getAttribute("directory"); %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="static/css/style.css"/>
</head>
<body>
<div class="container">
    <div class="files_main">
        <div class="title">
            <h1><%=directory.getPath()%></h1> <% if( directory.getParentPath() != null ){ %><span class="go_to_parent" onclick="window.location.href='/files?path=<%=directory.getParentPath()%>'">⇧</span><% } %>
        </div>
        <div class="files_body">
            <div class="files_list">
                <% for (FileObject fobj : directory.getContents()){ %>
                <div class="file_block" onclick="openOrDownload(this, '<%=fobj.getType()%>');">
                    <div class="icon">
                        <img src="static/images/<%=(fobj.getType() == FileObject.Type.Directory ? "folder" : "file")%>_icon.png" />
                    </div>
                    <div class="body">
                        <div class="data_block">
                            <div class="title">Name</div>
                            <div class="value file_name"><%=fobj.getName()%></div>
                        </div>
                        <div class="data_block">
                            <div class="title">Size</div>
                            <div class="value"><%=(fobj.getType() == FileObject.Type.Directory ? "–" : fobj.getSize())%></div>
                        </div>
                        <div class="data_block">
                            <div class="title">Creation Date</div>
                            <div class="value"><%=fobj.getCreationDate()%></div>
                        </div>
                    </div>
                </div>
                <% } %>
            </div>
        </div>
    </div>
</div>
<script>
    function openOrDownload(el, type){
        let currentPath = document.querySelector(".title h1").innerHTML;
        let fullPath = (currentPath === "/" ? "/" : currentPath + "/") + el.querySelector(".body .file_name").innerHTML;
        window.location.href = "/files?path=" + fullPath + (type === 'File' ? "&download=true" : "");
    }
</script>
</body>
</html>
