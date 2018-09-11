<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>控制面板 - 人员定岗培训管理系统</title>
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/vendor/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/vendor/linearicons/style.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/themes/bootstrap/easyui.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/global.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/min.css">
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/app.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/klorofil-common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/app.js"></script>
    <script type="text/javascript">
        userId = <%=request.getAttribute("userId")%>;
    </script>
</head>
<body>
    <!-- WRAPPER -->
    <div id="wrapper">
        <!-- NAVBAR -->
        <nav class="navbar navbar-default navbar-fixed-top">
            <div class="brand">
                <a href="index.html">高田汽车配件</a>
            </div>
            <div class="container-fluid">
                <div class="navbar-btn">
                    <button type="button" class="btn-toggle-fullwidth"><i class="lnr lnr-arrow-left-circle"></i></button>
                </div>
                <h1>人员定岗培训<strong>系统</strong></h1>
                <div class="per">
                    您好，<%=request.getAttribute("userName")%>！&nbsp;&nbsp;<a id="lbChangePwd" href="http://10.234.11.22:8082/BaseData/ChangePassword.aspx">更改密码</a>&nbsp;&nbsp;<a id="lbLogout" href="http://10.234.11.22:8082/Login.aspx">退出</a>
                </div>
            </div>
        </nav>
        <!-- END NAVBAR -->
        <!-- LEFT SIDEBAR -->
        <div id="sidebar-nav" class="sidebar">
            <div class="tit">
                <h2>评价</h2>
                <ul>
                    <li><a href="<%=request.getContextPath()%>/gradeLine/getPageListDlg.do?userId=<%=request.getAttribute("userId")%>">员工评价</a></li>
                    <li><a href="<%=request.getContextPath()%>/gradeMonth/getPageListDlg.do?userId=<%=request.getAttribute("userId")%>">月度考核</a></li>
                    <li><a href="<%=request.getContextPath()%>/gradeTask/getPageListDlg.do?userId=<%=request.getAttribute("userId")%>">待办任务</a></li>
                </ul>
            </div>
            <div class="tit">
                <h2>人员定岗培训</h2>
                <ul>
                    <li><a href="http://10.234.11.22:8082/Default.aspx">人员定岗培训</a></li>
                </ul>
            </div>
        </div>
        <!-- END LEFT SIDEBAR -->
        <!-- MAIN -->
        <div class="main" style="min-height: 863px;">
            <!-- MAIN CONTENT -->
            <div class="main-content">

            </div>
            <!-- END MAIN CONTENT -->
        </div>
        <!-- END MAIN -->
        <div class="clearfix"></div>
    </div>
</body>
</html>