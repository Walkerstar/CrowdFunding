<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2019\12\29 0029
  Time: 18:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh_CN">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <%@include file="/WEB-INF/jsp/common/css.jsp"%>

    <link rel="stylesheet" href="${PATH}/static/css/main.css" >
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
                    <form class="form-inline" role="form" style="float:left;" action="${PATH}/cert/index" method="post" id="queryForm">
                        <div class="form-group has-feedback">
                            <div class="input-group">
                                <div class="input-group-addon">查询条件</div>
                                <input class="form-control has-success" type="text" name="condition" value="${param.condition}" placeholder="请输入查询条件">
                            </div>
                        </div>
                        <button type="button" class="btn btn-warning" onclick="$('#queryForm').submit()"><i class="glyphicon glyphicon-search"></i> 查询
                        </button>
                    </form>
                    <button type="button" id="deleteBatchBtn" class="btn btn-danger" style="float:right;margin-left:10px;"><i class=" glyphicon glyphicon-remove"></i> 删除</button>
                    <button type="button" class="btn btn-primary" style="float:right;" id="addBtn"><i class="glyphicon glyphicon-plus"></i> 新增</button>
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
                            <c:forEach items="${page.list}" var="cert" varStatus="status">
                                <tr>
                                    <td>${status.count}</td>
                                    <td><input type="checkbox" certId="${cert.id}"></td>
                                    <td>${cert.name}</td>
                                    <td>

                                        <button type="button" class="btn btn-primary btn-xs updateClass" certID="${cert.id}">
                                            <i class=" glyphicon glyphicon-pencil"></i></button>
                                        <button type="button" class="deleteBtnClass btn btn-danger btn-xs"  certID="${cert.id}">
                                            <i class=" glyphicon glyphicon-remove"></i></button>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                            <tfoot>
                            <tr>
                                <td colspan="6" align="center">
                                    <ul class="pagination">
                                        <c:if test="${page.isFirstPage}" >
                                            <li class="disabled"><a href="#">上一页</a></li>
                                        </c:if>
                                        <c:if test="${!page.isFirstPage}" >
                                            <li ><a href="${PATH}/cert/index?pn=${page.prePage}&condition=${param.condition}">上一页</a></li>
                                        </c:if>

                                        <c:forEach items="${page.navigatepageNums}" var="num">
                                            <c:if test="${page.pageNum==num}">
                                                <li class="active"><a href="#">${num}</a></li>
                                            </c:if>
                                            <c:if test="${page.pageNum!=num}">
                                                <li><a href="${PATH}/cert/index?pn=${num}&condition=${param.condition}">${num}</a></li>
                                            </c:if>

                                        </c:forEach>

                                        <c:if test="${page.isLastPage}" >
                                            <li class="disabled"><a href="#">上一页</a></li>
                                        </c:if>
                                        <c:if test="${!page.isLastPage}" >
                                            <li ><a href="${PATH}/cert/index?pn=${page.nextPage}&condition=${param.condition}">下一页</a></li>
                                        </c:if>
                                    </ul>
                                </td>
                            </tr>

                            </tfoot>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!--资质添加的模态框-->
<div class="modal fade" id="addModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="MyModalLabel">添加资质</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">资质名称</label>
                    <div >
                        <input type="text" name="name" class="form-control" id="name" placeholder="请输入资质名称">
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


<!--资质修改的模态框-->
<div class="modal fade" id="updateModal" tabindex="-1" role="dialog" aria-labelledby="gridSystemModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="ModalLabel">修改资质</h4>
            </div>
            <div class="modal-body">
                <div class="form-group">
                    <label class="col-sm-2 control-label">资质名称</label>
                    <div >
                        <input type="text" name="name" class="form-control"  placeholder="请输入资质名称">
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
<%@include file="/WEB-INF/jsp/common/js.jsp" %>
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

    //单个删除
    $(".deleteBtnClass").click(function () {
        var id=$(this).attr("certID");
        //询问框
        layer.confirm("你要删除吗?", {btn: ['确定', '取消']}, function (index) {
            window.location.href = "${PATH}/cert/doDelete?pn=${page.pageNum}&id="+id;
            layer.close(index);
        }, function (index) {
            layer.close(index);
        });
    });

    $("#selectAll").click(function(){
        $("tbody input[type='checkbox']").prop("checked",this.checked);
    });

    //批量删除
    $("#deleteBatchBtn").click(function () {
        var checkedBoxList= $("tbody input[type='checkbox']:checked");
        console.log(checkedBoxList);

        if(checkedBoxList.length==0){
            layer.msg('没有被勾选的记录！');
            return false;
        }

        var ids='';
        var array=new Array();
        $.each(checkedBoxList,function (index,e) {
            var certId=$(e).attr("certId");
            array.push(certId);
        });

        ids=array.join(",");

        console.log(ids);

        layer.confirm("你确定删除这些数据吗?",{btn:['确定','取消']},function (index) {
            window.location.href="${PATH}/cert/doDeleteBatch?pn=${page.pageNum}&ids="+ids;
            layer.close(index);
        },function (index) {
            layer.close(index);
        });

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
            url:"${PATH}/cert/doAdd",
            data:{
                name:name
            },
            success:function (result) {
                if("ok"==result){
                    layer.msg("保存成功",{time:1000},function () {
                        $("#addModal").modal('hide');
                        $("#addModal input[name='name']").val("");
                        location.reload(true);
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
        var certID=$(this).attr('certID');
        console.log(certID);

        $.get("${PATH}/cert/getCertById",{id:certID},function (result) {

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

        $.post("${PATH}/cert/doUpdate",{name:name,id:id},function (result) {
            if("ok"==result){
                layer.msg("修改成功",{time:1000},function () {
                    $("#updateModal").modal("hide");
                    location.reload(true);
                });
            }else {
                layer.msg("修改失败");
            }
        });
    });
</script>
</body>
</html>
