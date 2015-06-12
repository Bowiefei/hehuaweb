<!DOCTYPE HTML><%@ page language="java" pageEncoding="UTF-8"%><%@ include file="/WEB-INF/commons/taglibs.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title><d:title default="hehuaweb.com" /></title>
<meta name="description" content="${meta}" />

<link rel="stylesheet" href="<c:url value="/libs/bootstrap/css/bootstrap.min.css?v=20140307" />" />
<!-- Documentation extras -->
<link rel="stylesheet" href="<c:url value="/libs/bootstrap/docs.min.css?v=20140307" />" />
<link rel="stylesheet" href="<c:url value="/libs/bootstrap/pygments-manni.css?v=20140307" />" />
<!--[if lt IE 9]><script src="<c:url value="/libs/bootstrap/ie8-responsive-file-warning.js?v=20140307" />"></script><![endif]-->
<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
<!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
  <script src="https://oss.maxcdn.com/libs/respond.js/1.4.2/respond.min.js"></script>
<![endif]-->
<script src="<c:url value="/libs/jquery/jquery-1.11.0.min.js?v=20140307" />"></script>
<script src="<c:url value="/libs/jquery/jquery.validate.min.js?v=20140307" />"></script>
<script src="<c:url value="/libs/jquery/jquery.form.min.js?v=20140307" />"></script>
<script src="<c:url value="/libs/bootstrap/js/bootstrap.min.js?v=20140307" />"></script>
<d:head />
</head>
<body>
  <%@ include file="/WEB-INF/commons/header_admin.jsp"%>
  <c:if test="${not _blank}">
    <d:body />
  </c:if>
  <%@ include file="/WEB-INF/commons/footer.jsp"%>
</body>
</html>