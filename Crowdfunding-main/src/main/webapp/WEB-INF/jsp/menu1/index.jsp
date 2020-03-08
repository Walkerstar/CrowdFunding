<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019\12\6 0006
  Time: 11:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/common/css.jsp" %>
    <link rel="stylesheet" href="${PATH}/static/css/main.css">
    <link rel="stylesheet" href="${PATH}/static/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor: pointer;
        }

        table tbody tr:nth-child(odd) {
            background: #F4F4F4;
        }

        table tbody td:nth-child(even) {
            color: #C00;
        }
    </style>
</head>
<body>
<jsp:include page="/WEB-INF/jsp/common/top.jsp"/>

<div class="container-fluid">
    <div class="row">
        <jsp:include page="/WEB-INF/jsp/common/sidebar.jsp"/>
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 菜单列表</h3>
                </div>
                <div class="zTreeDemoBackground left">
                    <ul id="treeDemo" class="ztree"></ul>
                </div>
            </div>
        </div>
    </div>
</div>


<!--添加的模态框-->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="ModalLabel">添加菜单</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="pid">
                    <label class="col-sm-2 control-label">菜单名称</label>
                    <div>
                        <input type="text" name="name" id="name" class="form-control" placeholder="请输入菜单名称">
                    </div>

                    <label class="col-sm-2 control-label">菜单URL</label>
                    <div>
                        <input type="text" name="url" id="url" class="form-control" placeholder="请输入菜单URL">
                    </div>

                    <label class="col-sm-2 control-label">菜单图标</label>
                    <div>
                        <input type="text" name="icon" id="icon" class="form-control" placeholder="请输入菜单图标">

                        <%--  <option>glyphicon glyphicon-music</option>--%>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="addBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<!--修改的模态框-->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="MyModalLabel">添加菜单</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="id">
                    <label class="col-sm-2 control-label">菜单名称</label>
                    <div>
                        <input type="text" name="name" class="form-control" placeholder="请输入菜单名称">
                    </div>

                    <label class="col-sm-2 control-label">菜单URL</label>
                    <div>
                        <input type="text" name="url" class="form-control" placeholder="请输入菜单URL">
                    </div>

                    <label class="col-sm-2 control-label">菜单图标</label>
                    <div>
                        <input type="text" name="icon" class="form-control" placeholder="请输入菜单图标">

                        <%--  <option>glyphicon glyphicon-music</option>--%>

                    </div>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="updateBtn">修改</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<!-- 分配权限Modal -->
<div class="modal fade" id="permissionModal" tabindex="-1" role="dialog" aria-labelledby="Modal">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">给菜单分配权限</h4>
            </div>
            <div class="modal-body">
                <ul id="assignPermissionTree" class="ztree"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button id="assignPermission" type="button" class="btn btn-primary">分配</button>
            </div>
        </div>
    </div>
</div>
<%@include file="/WEB-INF/jsp/common/js.jsp" %>
<script src="${PATH}/static/script/docs.min.js"></script>
<script src="${PATH}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
<script type="text/javascript">
    $(function () {
        $(".list-group-item").click(function () {
            if ($(this).find("ul")) {
                $(this).toggleClass("tree-closed");
                if ($(this).hasClass("tree-closed")) {
                    $("ul", this).hide("fast");
                } else {
                    $("ul", this).show("fast");
                }
            }
        });

        initTree();

    });

    function initTree() {

        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid"
                }
            },
            view: {
                addDiyDom: function (treeId, treeNode) {
                    $("#" + treeNode.tId + "_ico").removeClass();
                    $("#" + treeNode.tId + "_span").before("<span class='" + treeNode.icon + "'></span>")
                },
                addHoverDom: function (treeId, treeNode) {
                    var aObj = $("#" + treeNode.tId + "_a"); // tId = permissionTree_1, ==> $("#permissionTree_1_a")
                    aObj.attr("href", "javascript:;");
                    if (treeNode.editNameFlag || $("#btnGroup" + treeNode.tId).length > 0) return;
                    var s = '<span id="btnGroup' + treeNode.tId + '">';
                    if (treeNode.level == 0) {//根节点
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn(' + treeNode.id + ')" title="添加菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if (treeNode.level == 1) {//分支节点
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="updateBtn(' + treeNode.id + ')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        if (treeNode.children.length == 0) {
                            s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"onclick="deleteBtn(' + treeNode.id + ')" title="删除菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                        }
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn(' + treeNode.id + ')" title="添加菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="assignBtn('+treeNode.id+')" title="分配权限">&nbsp;&nbsp;<i class="fa fa-fw fa-anchor rbg "></i></a>';
                    } else if (treeNode.level == 2) {//叶子节点
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"onclick="updateBtn(' + treeNode.id + ')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn(' + treeNode.id + ')" title="删除菜单">&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="assignBtn('+treeNode.id+')" title="分配权限">&nbsp;&nbsp;<i class="fa fa-fw fa-anchor rbg "></i></a>';
                    }

                    s += '</span>';
                    aObj.after(s);
                },
                removeHoverDom: function (treeId, treeNode) {
                    $("#btnGroup" + treeNode.tId).remove();
                }
            }

        };

        $.get("${PATH}/menu/loadTree", {}, function (result) {
            var zNodes = result;
            zNodes.push({id: 0, name: "系统菜单", icon: "glyphicon glyphicon-th"});
            $.fn.zTree.init($("#treeDemo"), setting, zNodes);

            var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
            treeObj.expandAll(true);
        });
    }

    //添加------------------------------
    function addBtn(id) {
        $("#addModal").modal({
            backdrop: 'static',
            keyboard: true
        });
        $("#addModal input[name='pid']").val(id);
    }

    $("#addBtn").click(function () {
        var pid = $("#addModal input[name='pid']").val();
        var name = $("#addModal input[name='name']").val();
        var url = $("#addModal input[name='url']").val();
        var icon = $("#addModal input[name='icon']").val();

        console.log(pid, name, url, icon);

        $.ajax({
            type: "POST",
            url: "${PATH}/menu/doAdd",
            data: {
                name: name,
                pid: pid,
                url: url,
                icon: icon
            },
            success: function (result) {
                if ("ok" == result) {
                    layer.msg("保存成功", {time: 1000}, function () {
                        $("#addModal").modal('hide');
                        $("#addModal input[name='name']").val("");
                        $("#addModal input[name='url']").val("");
                        $("#addModal input[name='icon']").val("");
                        initTree();
                    });
                } else {
                    layer.msg("保存失败");
                }

            }
        });

    });

    //修改------------------------------
    function updateBtn(id) {
        $.get("${PATH}/menu/getMenuById", {id: id}, function (result) {

            console.log(result);

            $("#updateModal").modal({
                backdrop: 'static',
                keyboard: true
            });
            $("#updateModal input[name='id']").val(result.id);
            $("#updateModal input[name='name']").val(result.name);
            $("#updateModal input[name='url']").val(result.url);
            $("#updateModal input[name='icon']").val(result.icon);
        });

    }

    $("#updateBtn").click(function () {
        var id = $("#updateModal input[name='id']").val();
        var name = $("#updateModal input[name='name']").val();
        var url = $("#updateModal input[name='url']").val();
        var icon = $("#updateModal input[name='icon']").val();

        console.log(id, name, url, icon);

        $.ajax({
            type: "POST",
            url: "${PATH}/menu/doUpdate",
            data: {
                name: name,
                id: id,
                url: url,
                icon: icon
            },
            success: function (result) {
                if ("ok" == result) {
                    layer.msg("修改成功", {time: 1000}, function () {
                        $("#updateModal").modal('hide');
                        initTree();
                    });
                } else {
                    layer.msg("修改失败");
                }
            }
        });

    });


    //删除------------------------------
    function deleteBtn(id) {
        layer.confirm("你确定要删除吗？", {btn: ['确定', '取消']}, function (index) {
            $.post("${PATH}/menu/doDelete", {id: id}, function (result) {
                if ("ok" == result) {
                    layer.msg("删除成功", {time: 1000}, function () {
                        initTree();
                    });
                } else {
                    layer.msg("删除失败");
                }
            });


            layer.close(index);
        }, function (index) {
            layer.close(index);
        });
    }

    //为菜单分配权限做准备，加载权限树
    var tempMenuid = '';

    function assignBtn(menuid) {
        tempMenuid = menuid;
        //1.初始化权限树，带复选框
        initPermissioinToMenuTree();
        //2.显示模态框，展示权限树
        $("#permissionModal").modal({
            show: true,
            backdrop: "static"});
        //3.回显权限树（之前分配过的权限应该被勾选）
        showMenuPermissions(menuid);
    }

    function initPermissioinToMenuTree() {
        var setting = {
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid"
                },
                key: {
                    url: "xUrl",
                    name: "title"
                }
            },
            check: {
                enable: true
            },
            view: {
                addDiyDom: addDiyDom
            }
        };
        //1.加载数据
        $.get("${PATH}/permission/loadTree", function (data) {
            //data.push({"id":0,"title":"系统权限","icon":"glyphicon glyphicon-asterisk"});
            var tree = $.fn.zTree.init($("#assignPermissionTree"), setting, data);
            var treeObj = $.fn.zTree.getZTreeObj("assignPermissionTree");
            treeObj.expandAll(true);
        });
    }

    function addDiyDom(treeId, treeNode) {
        $("#" + treeNode.tId + "_ico").removeClass();
        $("#" + treeNode.tId + "_span").before('<span class="' + treeNode.icon + '"></span>');
    }

    //回显权限树
    function showMenuPermissions(menuid) {
        $.get("${PATH}/menu/menu_permission?menuid=" + menuid, function (data) {
            //1、遍历每一个权限，在ztree中选中对应的节点
            $.each(data, function () {
                console.log(this);
                var treeObj = $.fn.zTree.getZTreeObj("assignPermissionTree");
                var node = treeObj.getNodeByParam("id", this.id, 0); //根据指定的节点id搜索节点，null表示搜索整个树
                treeObj.checkNode(node, true, false);//需要回显的节点，是否勾选复选框，父子节点勾选是否联动（例如：勾选父节点，要不要把它的所有子节点都勾上，取消父节点勾选，要不要把它的所有子节点也都取消勾选）
            });
        });
    }

    //分配权限功能
    $("#assignPermission").click(function () {
        //1、获取到已经选中的所有权限的id
        var treeObj = $.fn.zTree.getZTreeObj("assignPermissionTree");
        var ids = new Array();
        $.each(treeObj.getCheckedNodes(true), function () {
            ids.push(this.id);
        });
        var idsStr = ids.join();

        //2、组装给后台提交的数据
        var data = {mid: tempMenuid, perIds: idsStr};
        console.log(data);

        //3、发请求，完成权限分配功能
        $.post("${PATH}/permission/assignPermissionToMenu", data, function () {
            layer.msg("权限分配完成...")
            $("#permissionModal").modal('hide');
        })
    });
</script>
</body>
</html>
