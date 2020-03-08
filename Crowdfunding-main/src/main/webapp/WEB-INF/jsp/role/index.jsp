<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019\12\4 0004
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="sect" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/common/css.jsp"%>
    <link rel="stylesheet" href="${PATH}/static/css/main.css">
    <link rel="stylesheet" href="${PATH}/static/ztree/zTreeStyle.css">
    <style>
        .tree li {
            list-style-type: none;
            cursor:pointer;
        }
        table tbody tr:nth-child(odd){background:#F4F4F4;}
        table tbody td:nth-child(even){color:#C00;}
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
                    <h3 class="panel-title"><i class="glyphicon glyphicon-th"></i> 数据列表</h3>
                </div>
                <div class="panel-body">
                    <form class="form-inline" role="form" style="float:left;">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input id="condition" class="form-control has-success" type="text" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button id="queryBtn" type="button" class="btn btn-warning"><i class="glyphicon glyphicon-search"></i> 查询</button>
                    </form>
                    <button type="button" class="btn btn-danger" style="float:right;margin-left:10px;" id="deleteBatch"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <sect:authorize access="hasRole('PM - 项目经理')">
                    <button type="button" class="btn btn-primary" style="float:right;" id="addBtn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
                    </sect:authorize>
                    <br>
                    <hr style="clear:both;">
                    <div class="table-responsive">
                        <table class="table  table-bordered">
                            <thead>
                            <tr >
                                <th width="30">#</th>
                                <th width="30"><input id="selectAll" type="checkbox"></th>
                                <th>名称</th>
                                <th width="100">操作</th>
                            </tr>
                            </thead>
                            <tbody>

                            </tbody>
                            <tfoot>
                            <tr >
                                <td colspan="6" align="center">
                                    <ul class="pagination">

                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </dv>
</div>

<!--员工添加的模态框-->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="MyModalLabel">添加角色</h4>
            </div>
            <div class="modal-body">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">角色名称</label>
                        <div >
                            <input type="text" name="name" class="form-control" id="name" placeholder="请输入角色名称">
                        </div>
                    </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<!--员工修改的模态框-->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="ModalLabel">修改角色</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">角色名称</label>
                    <div >
                        <input type="text" name="name" class="form-control"  placeholder="请输入角色名称">
                        <input type="hidden" name="id">
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

<!--分配权限的模态框-->
<div class="modal fade" id="assignModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="ModalLabel1">给角色分配权限</h4>
            </div>
            <div class="zTreeDemoBackground left">
                <ul id="treeDemo" class="ztree"></ul>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="assignBtn">分配</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>


<%@include file="/WEB-INF/jsp/common/js.jsp"%>
<script src="${PATH}/static/script/docs.min.js"></script>
<script src="${PATH}/static/ztree/jquery.ztree.all-3.5.min.js"></script>
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

        initData(1);
    });

    //分页查询
    var json = {pn: 1, size: 2};

    function initData(pn) {

        json.pn=pn;
        var index=-1;

        $.ajax({
            type:"POST",
            url:"${PATH}/role/loadData",
            data:json,
            beforeSend:function () {
                index=layer.load(0,{time:1000*10});
                return true;
            },
            success:function (result) {
                console.log(result);
                layer.close(index);

                initShow(result);

                initNavg(result);
            }
        });
    }

    //显示数据
    function initShow(result) {
        $("tbody").empty();
        var list=result.list;

        var content='';
        $.each(list,function (index,e) {

            var tr=$("<tr></tr>");
            tr.append('<td>'+(index+1)+'</td>');
            tr.append('<td><input type="checkbox" roleId="'+e.id+'"></td>');
            tr.append(' <td>'+e.name+'</td>');

            var td=$("<td></td>");
            td.append('<button type="button" roleId="'+e.id+'" class="btn assignPermissionClass btn-success btn-xs"><i class=" glyphicon glyphicon-check"></i></button>');
            td.append('<button type="button" roleId="'+e.id+'" class="btn updateClass btn-primary btn-xs"><i class=" glyphicon glyphicon-pencil"></i></button>');
            td.append('<button type="button" roleId="'+e.id+'" class="btn deleteClass btn-danger btn-xs"><i class=" glyphicon glyphicon-remove"></i></button>');

            tr.append(td);

            tr.appendTo($('tbody'));

        });

    }

    //显示分页条
    function initNavg(result) {
        $(".pagination").empty();

        var ul = $(".pagination");
        var prePage='';
        var nextPage ='';

        if(result.isFirstPage){
             prePage = $("<li></li>").append($('<a href="#">上一页</a>')).addClass("disabled");
        }else {
             prePage = $("<li></li>").append($('<a onclick="initData('+result.prePage+')">上一页</a>'));
        }

        ul.append(prePage);

        $.each(result.navigatepageNums, function (i, e) {
            if (e == result.pageNum) {
                $("<li class='active'></li>").append($('<a href="#">' + e + '</a>')).appendTo(ul);
            } else {
                $("<li></li>").append($('<a onclick="initData('+e+')">' + e + '</a>')).appendTo(ul);
            }
        });

        if(result.isLastPage){
            nextPage = $("<li></li>").append($('<a href="#">下一页</a>')).addClass("disabled");
        }else {
            nextPage = $("<li></li>").append($('<a onclick="initData('+result.nextPage+')">下一页</a>'));
        }

        ul.append(nextPage);
    }

        //模糊查询
    $("#queryBtn").click(function () {
            var condition=$("#condition").val();
            json.condition=condition;
            initData(1);
        });

    //添加------------------------开始
    $("#addBtn").click(function () {
        $("#addModal").modal({
            show:true,
            backdrop:'static',
            keyboard:false
        });
    });

    $("#saveBtn").click(function () {
        var name=$("#addModal input[name='name']").val();
        console.log(name);
        $.ajax({
            type:"POST",
            url:"${PATH}/role/doAdd",
            data:{
                name:name
            },
            success:function (result) {
                if("ok"==result){
                    layer.msg("保存成功",{time:1000},function () {
                        $("#addModal").modal('hide');
                        $("#addModal input[name='name']").val("");
                        initData(9999999);
                    });
                }else if("403"==result){
                    layer.msg("你无权访问");
                    $("#addModal").modal('hide');
                }else {
                    layer.msg("保存失败");

                }
            }
        });
    });


    //修改------------------------开始
    $("tbody").on('click','.updateClass',function () {
        var roleId=$(this).attr('roleId');
        console.log(roleId);

        $.get("${PATH}/role/getRoleById",{id:roleId},function (result) {

            $("#updateModal").modal({
                backdrop:"static",
                keyboard:false
            });

            $("#updateModal input[name='name']").val(result.name);
            $("#updateModal input[name='id']").val(result.id);
        })
    });
    
    $("#updateBtn").click(function () {
        var name = $("#updateModal input[name='name']").val();
        var id = $("#updateModal input[name='id']").val();

        $.post("${PATH}/role/doUpdate",{name:name,id:id},function (result) {
            if("ok"==result){
                layer.msg("修改成功",{time:1000},function () {
                    $("#updateModal").modal("hide");
                    initData(json.pn);//修改后返回当前页
                });
            }else {
                layer.msg("修改失败");
            }
        });
    });

    //单个删除-----------------------------------------
    $("tbody").on("click",'.deleteClass',function () {
        var id=$(this).attr("roleId");

        layer.confirm("你确定要删除吗？",{btn:['确定','取消']},function (index) {

            $.post("${PATH}/role/doDelete",{id:id},function (result) {
                if("ok"==result){
                    layer.msg("删除成功",{time:1000});
                    initData(json.pn);
                }else {
                    layer.msg("删除失败");
                }
            });

            layer.close(index);
        },function (index) {
            layer.close(index);
        });

    });

    //实现全选操作
    $("#selectAll").click(function () {
        $("tbody input[type='checkbox']").prop("checked",$(this).prop("checked"));
    });

    //实现批量删除
    $("#deleteBatch").click(function () {
        var Listid=$("tbody input[type='checkbox']:checked");
        console.log(Listid);

        if(Listid.length==0){
            layer.msg('没有被勾选的记录！');
            return false;
        }
        var idArray=new Array();
        var idStr='';

        $.each(Listid,function (i,e) {
            var id=$(e).attr("roleId");
            idArray.push(id);
        });

        idStr=idArray.join(",");
        console.log(idStr);

        layer.confirm("你确定要删除吗？",{btn:['确定','取消']},function (index) {

            $.post("${PATH}/role/doDeleteBatch",{idStr:idStr},function (result) {
                if("ok"==result){
                    layer.msg("删除成功",{time:1000});
                    initData(json.pn);
                }else {
                    layer.msg("删除失败");
                }
            });

            layer.close(index);
        },function (index) {
            layer.close(index);
        });

    });


    var roleId='';
    //给角色分配权限
    $("tbody").on("click",".assignPermissionClass",function () {
        $("#assignModal").modal({
            backdrop:'static',
            keyboard:false

        });

        roleId=$(this).attr("roleId");
        initTree();

    });

    function initTree() {

        var setting = {
            check: {
                enable: true
            },
            data: {
                simpleData: {
                    enable: true,
                    pIdKey: "pid"
                },
                key:{
                    url:"xUrl",
                    name:"title"
                }
            },
            view: {
                addDiyDom: function (treeId, treeNode) {
                    $("#" + treeNode.tId + "_ico").removeClass();
                    $("#" + treeNode.tId + "_span").before("<span class='" + treeNode.icon + "'></span>")
                }
            }

        };

        //1.加载树的数据
        $.get("${PATH}/permission/loadTree", {}, function (result) {

           var treeObj= $.fn.zTree.init($("#treeDemo"), setting, result);

            treeObj.expandAll(true);

            //2.回显数据
            $.get("${PATH}/role/listPermissionIdByRoleId",{roleId:roleId}, function (result) {

               $.each(result,function (i,e) {
                   var permissionId=e;
                   var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
                   var node = treeObj.getNodeByParam("id", permissionId, null);
                   treeObj.checkNode(node, true, false,false);
               });


            });
        });
    }

    //点击分配按钮
    $("#assignBtn").click(function () {

        var treeObj = $.fn.zTree.getZTreeObj("treeDemo");
        var nodes = treeObj.getCheckedNodes(true);

        var idList='';
        var array=new Array();
        $.each(nodes,function (i,e) {
            var permissionId=e.id;
            console.log(permissionId);
            array.push(permissionId);
        });

        idList=array.join(",");

        console.log(idList);


        $.ajax({
            type:"POST",
            url:"${PATH}/role/doAssignPermissionToRole",
            data:{
                roleId:roleId,
                idList:idList
            },
            success:function (result) {
                if("ok"==result){
                    layer.msg("分配成功",{time:2000,icon:6},function () {
                        $("#assignModal").modal('hide');
                    });
                }else {
                    layer.msg("分配失败",{time:2000,icon:5});
                }

            }
        });
    });

</script>
</body>
</html>

