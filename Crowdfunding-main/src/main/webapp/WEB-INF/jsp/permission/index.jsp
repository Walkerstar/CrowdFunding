
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 权限列表</h3>
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
                <h4 class="modal-title" id="ModalLabel">添加权限</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="pid">
                    <label class="col-sm-2 control-label">权限名称</label>
                    <div>
                        <input type="text" name="title" id="title" class="form-control" placeholder="请输入权限名称">
                    </div>

                    <label class="col-sm-2 control-label">权限作用</label>
                    <div>
                        <input type="text" name="name" id="name" class="form-control" placeholder="请输入权限name 如:user:add">
                    </div>

                    <label class="col-sm-2 control-label">权限图标</label>
                    <div>
                        <input type="text" name="icon" id="icon" class="form-control" placeholder="请输入权限图标">

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
                <h4 class="modal-title" id="MyModalLabel">修改权限</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <input type="hidden" name="id">
                    <label class="col-sm-2 control-label">权限名称</label>
                    <div>
                        <input type="text" name="title" class="form-control" placeholder="请输入权限名称">
                    </div>

                    <label class="col-sm-2 control-label">权限作用</label>
                    <div>
                        <input type="text" name="name" class="form-control" placeholder="请输入权限name 如:user:add">
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
                },
                key:{
                    name:"title"
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
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="addBtn(' + treeNode.id + ')" >&nbsp;&nbsp;<i class="fa fa-fw fa-plus rbg "></i></a>';
                    } else if (treeNode.level == 1) {//叶子节点
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;"onclick="updateBtn(' + treeNode.id + ')" title="修改权限信息">&nbsp;&nbsp;<i class="fa fa-fw fa-edit rbg "></i></a>';
                        s += '<a class="btn btn-info dropdown-toggle btn-xs" style="margin-left:10px;padding-top:0px;" onclick="deleteBtn(' + treeNode.id + ')" >&nbsp;&nbsp;<i class="fa fa-fw fa-times rbg "></i></a>';
                    }

                    s += '</span>';
                    aObj.after(s);
                },
                removeHoverDom: function (treeId, treeNode) {
                    $("#btnGroup" + treeNode.tId).remove();
                }
            }

        };

        $.get("${PATH}/permission/loadTree", {}, function (result) {
            console.log(result);
            var zNodes = result;
            zNodes.push({title:"用户管理",id:0,icon:"glyphicon glyphicon-wrench"});
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
        var title = $("#addModal input[name='title']").val();
        var icon = $("#addModal input[name='icon']").val();

        console.log(pid, name, title, icon);

        $.ajax({
            type: "POST",
            url: "${PATH}/permission/doAdd",
            data: {
                name: name,
                pid: pid,
                title:title,
                icon: icon
            },
            success: function (result) {
                if ("ok" == result) {
                    layer.msg("保存成功", {time: 1000}, function () {
                        $("#addModal").modal('hide');
                        $("#addModal input[name='name']").val("");
                        $("#addModal input[name='title']").val("");
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
        $.get("${PATH}/permission/getMenuById", {id: id}, function (result) {

            console.log(result);

            $("#updateModal").modal({
                backdrop: 'static',
                keyboard: true
            });
            $("#updateModal input[name='id']").val(result.id);
            $("#updateModal input[name='name']").val(result.name);
            $("#updateModal input[name='title']").val(result.title);
            $("#updateModal input[name='icon']").val(result.icon);
        });

    }

    $("#updateBtn").click(function () {
        var id = $("#updateModal input[name='id']").val();
        var name = $("#updateModal input[name='name']").val();
        var title = $("#updateModal input[name='title']").val();
        var icon = $("#updateModal input[name='icon']").val();

        console.log(id, name, title, icon);

        $.ajax({
            type: "POST",
            url: "${PATH}/permission/doUpdate",
            data: {
                name: name,
                id: id,
               title:title,
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
            $.post("${PATH}/permission/doDelete", {id: id}, function (result) {
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
</script>
</body>
</html>
