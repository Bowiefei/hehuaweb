<%@ page language="java" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/commons/taglibs.jsp"%>

<style>
body {
  padding-top: 40px;
  padding-bottom: 40px;
  background-color: #eee;
}

</style>

<div class="container">
  <div class="row">
    <div class="col-md-8 col-md-offset-2">
      <div class="alert alert-info" role="alert">
        <strong><span class="glyphicon glyphicon-user"></span>&nbsp;用户登录 </strong>
      </div>
      <strong>
        <form id="signup-form" name="signup-form" action="" method="post" class="form-horizontal"
          novalidate="novalidate">
          <div class="form-group row-space-top-2">
            <label for="email" class="col-sm-3 control-label">邮箱</label>
            <div class="col-sm-5">
              <input type="email" id="email" name="email" tabindex="1" class="form-control" placeholder="请输入邮箱地址">
            </div>
          </div>
          <div class="form-group">
            <label for="confirmPassword" class="col-sm-3 control-label">密码</label>
            <div class="col-sm-5">
              <input type="text" class="form-control" name="password" placeholder="请输入密码">
            </div>
          </div>
          <div class="form-group">
            <div class="col-sm-offset-3 col-sm-5">
              <button class="btn btn-lg btn-primary btn-block" type="submit">登录</button>
            </div>
          </div>
        </form>
      </strong>
    </div>
    <strong> </strong>
  </div>
  <strong> </strong>
</div>




