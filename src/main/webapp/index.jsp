<%@ page import="me.ulearn.DirectoryParser" %>
<%@ page import="me.ulearn.FileObject" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% DirectoryParser directory = (DirectoryParser) request.getAttribute("directory"); %>
<% String homeDirectory = (String) request.getAttribute("homeDirectory"); %>
<html>
<head>
    <title>FileExplorer</title>
    <link rel="stylesheet" href="static/css/style.css"/>
</head>
<body>
<div class="container">
    <div class="user_block">
        <div class="avatar">
            <img src="static/images/avatar.png" alt="" />
        </div>
        <div class="data">
            <div class="data_block">
                <div class="title">Your login</div>
                <div class="value file_name"><%=(String) request.getAttribute("login") %></div>
            </div>
        </div>
        <div class="exit_btn">
            <img src="static/images/exit.png" alt="" />
        </div>
    </div>
    <div class="files_main">
        <div class="title">
            <h1 data-path="<%=directory.getPath(homeDirectory)%>"><%=directory.getTitle(homeDirectory)%></h1>
            <% if( directory.getParentPath(homeDirectory) != null ){ %>
            <span class="go_to_parent" onclick="window.location.href='?path=<%=directory.getParentPath(homeDirectory)%>'">⇧</span>
            <% } %>
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
    <div class="generated_time"><%=new SimpleDateFormat("dd-MM-yyyy, HH:mm:ss").format(new Date())%></div>
</div>
<script>
    function openOrDownload(el, type){
        let currentPath = document.querySelector(".title h1").dataset.path;
        let fullPath = currentPath + "/" + el.querySelector(".body .file_name").innerHTML;
        window.location.href = "?path=" + fullPath + (type === 'File' ? "&download=true" : "");
    }

    document.querySelector(".exit_btn").addEventListener("click", async (e) => {
        const response = await fetch("/api/exit", {
            method: 'DELETE'
        }).then((data) => {
            window.location.href = "/login";
        });
    });
</script>
</body>
</html>
