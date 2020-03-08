<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019\12\7 0007
  Time: 14:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/common/css.jsp"%>
    <link rel="stylesheet" href="${PATH}/static/css/main.css">
    <link rel="stylesheet" href="${PATH}/static/css/doc.min.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
    </style>
</head>

<body>

<jsp:include page="/WEB-INF/jsp/common/top.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <ol class="breadcrumb">
                <li><a href="#">首页</a></li>
                <li><a href="#">数据列表</a></li>
                <li class="active">分配角色</li>
            </ol>
            <div class="panel panel-default">
                <div class="panel-body">
                    <form role="form" class="form-inline">
                        <div class="form-group">
                            <label for="exampleInputPassword1">未分配角色列表</label><br>
                            <select id="leftRoleList" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">

                                <c:forEach items="${unAssignList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>

                            </select>
                        </div>
                        <div class="form-group">
                            <ul>
                                <li id="leftToRightBtn" class="btn btn-default glyphicon glyphicon-chevron-right"></li>
                                <br>
                                <li id="rightToLeftBtn" class="btn btn-default glyphicon glyphicon-chevron-left" style="margin-top:20px;"></li>
                            </ul>
                        </div>
                        <div class="form-group" style="margin-left:40px;">
                            <label for="exampleInputPassword1">已分配角色列表</label><br>
                            <select id="rightRoleList" class="form-control" multiple size="10" style="width:200px;overflow-y:auto;">
                                <c:forEach items="${assignList}" var="role">
                                    <option value="${role.id}">${role.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/jsp/common/js.jsp"%>
<script src="${PATH}/static/script/docs.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function(){
            if ( $(this).find("ul") ) {
                $(this).toggleClass("tree-closed");
                if ( $(this).hasClass("tree-closed") ) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });
    });

    //分配角色
    $("#leftToRightBtn").click(function () {
        var leftSelectedRoleList=$("#leftRoleList option:selected");

        if(leftSelectedRoleList.length==0){
            layer.msg("请选择角色再分配",{time:2000,icon:5});
            return false;
        }

        var str='';

        $.each(leftSelectedRoleList,function (i,e) {
           var roleId= e.value;
           str+='roleId='+roleId+'&';
        });

        str+='adminId=${param.id}';

        $.ajax({
            type:"POST",
            url:"${PATH}/admin/doAssign",
            data:str,
            success:function (result) {
                if("ok"==result){
                    layer.msg("分配成功",{time:1000,icon:6},function () {
                        $("#rightRoleList").append(leftSelectedRoleList.clone());
                        leftSelectedRoleList.remove();
                    });
                }else {
                    layer.msg("分配失败",{time:1000,icon:5});
                }
            }
        });
    });

    //取消分配角色
    $("#rightToLeftBtn").click(function () {
        var rightSelectedRoleList=$("#rightRoleList option:selected");


        if(rightSelectedRoleList.length==0){
            layer.msg("请选择已分配角色再取消分配",{time:2000,icon:5});
            return false;
        }

        var str='';

        $.each(rightSelectedRoleList,function (i,e) {
            var roleId= e.value;
            str+='roleId='+roleId+'&';
        });

        str+='adminId=${param.id}';

        $.ajax({
            type:"POST",
            url:"${PATH}/admin/doUnAssign",
            data:str,
            success:function (result) {
                if("ok"==result){
                    layer.msg("取消分配成功",{time:1000,icon:6},function () {
                        $("#leftRoleList").append(rightSelectedRoleList.clone());
                        rightSelectedRoleList.remove();
                    });
                }else {
                    layer.msg("取消分配失败",{time:1000,icon:5});
                }
            }
        });

    });
</script>
</body>
</html>

