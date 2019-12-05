<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="zh-CN">
  <head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="keys" content="">
    <meta name="author" content="">
	<%@include file="/WEB-INF/jsp/common/css.jsp"%>
      <link rel="stylesheet" href="${PATH }/static/css/login.css">
	<style>

	</style>
  </head>
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
      <div class="container">
        <div class="navbar-header">
          <div><a class="navbar-brand" href="index.html" style="font-size:32px;">尚筹网-创意产品众筹平台</a></div>
        </div>
      </div>
    </nav>

    <div class="container">
      <form id="loginForm" action="${PATH }/doLogin" method="POST" class="form-signin" role="form">

        <h2 class="form-signin-heading"><i class="glyphicon glyphicon-log-in"></i> 用户登录</h2>
            <c:if test="${message != null}">
                <div class="form-group has-success has-feedback">
                        ${message}
                </div>
            </c:if>
		  <div class="form-group has-success has-feedback">
			<input type="text" class="form-control" id="floginacct" name="loginacct" value="superadmin" placeholder="请输入登录账号" autofocus>
			<span class="glyphicon glyphicon-user form-control-feedback"></span>
		  </div>
		  <div class="form-group has-success has-feedback">
			<input type="password" class="form-control" id="fuserpswd" name="userpswd" value="123456" placeholder="请输入登录密码" style="margin-top:10px;">
			<span class="glyphicon glyphicon-lock form-control-feedback"></span>
		  </div>

        <div class="checkbox">
          <label>
            <input type="checkbox" value="remember-me"> 记住我
          </label>
          <br>
          <label>
            忘记密码
          </label>
          <label style="float:right">
            <a href="reg.html">我要注册</a>
          </label>
        </div>
        <a class="btn btn-lg btn-success btn-block" onclick="dologin()" > 登录</a>
      </form>
    </div>
   <%@include file="/WEB-INF/jsp/common/js.jsp"%>
    <script>
    function dologin() {
       /* var flgoinacct=$("#floginacct");
        var fuserpswd=$("#fuserpswd");
        var ftype=$("#ftype");

        //对于表单数据而言不能用null进行判断,如果文本框什么都不输入,获取的值是""
        if($.trim(flgoinacct)==""){
            alert("用户账号不能为空，请重新输入!");
            flgoinacct.val("");
            flgoinacct.focus();
            return false;
        }else if($.trim(fuserpswd)==""){
            alert("用户账号不能为空，请重新输入!");
            fuserpswd.val("");
            fuserpswd.focus();
            return false;
        }

        $.ajax({
            type:"POST",
            data:{
                loginacct:flgoinacct.val(),
                userpswd:fuserpswd.val(),
                type:ftype.val()
            },
            url:"/doLogin",
            beforeSend:function () {
                //一般做表单数据校验.
                return true;
            },
            success:function (result) {
                if(result.success){
                    window.location.href="/main";
                }else {
                    alert("not ok");
                }
            }
        });*/

       if($.trim($("#floginacct").val())==""){
           layer.msg("用户名不能为空",{time:2000,icon:5});
           return false;
       }
    	$("#loginForm").submit();

    }
    </script>
  </body>
</html>