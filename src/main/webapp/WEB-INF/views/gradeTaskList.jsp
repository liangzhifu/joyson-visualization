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
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/Assets/css/jquery.searchableSelect.css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/klorofil-common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.searchableSelect.js"></script>
    <script type="text/javascript">
        var userId = <%=request.getAttribute("userId")%>;
        var tab;
        var width = 800;
        var contextPath = "${pageContext.request.contextPath}";
        function fixWidth(percent) {
            return $("#body").css("width") * percent;
        }

        function formatView(val, row) {
            if (val == "1") {
                if (row.taskSeq == "1") {
                    return "选择确认人";
                } else if (row.taskSeq == "2") {
                    return "选择承认人";
                } else {
                    return "未知";
                }
            } else if (val == "11") {
                if (row.taskSeq == "1") {
                    return "确认";
                } else if (row.taskSeq == "2") {
                    return "承认";
                } else {
                    return "未知";
                }
            } else {
                return "未知";
            }
        }

        function selectConfirmUser() {
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            } else if (ds.length > 1) {
                alert('只能选择一条记录！');
                return false;
            }
            var taskStatus = $("#dataTable").datagrid("getSelected").taskStatus;
            var taskSeq = $("#dataTable").datagrid("getSelected").taskSeq;
            if (!(taskSeq == "1" && taskStatus == "1")) {
                alert('只能选择(选择确认人)记录！');
                return false;
            }
            $("#DivConfirmUser").dialog('open').dialog('setTitle', '选择确认人');
        }

        // 选择确认人--提交
        function btnSelectConfirmUser() {
            var confirmUserId = $("#selectConfirmUser").val();
            if (confirmUserId == undefined || confirmUserId == null || confirmUserId == "") {
                alert('必须选择确认人！');
                return false;
            }
            $.post("<%=request.getContextPath()%>/gradeTask/confirmUser.do", {"userId": userId, "confirmUserId": confirmUserId}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    $("#DivConfirmUser").dialog('close');
                    search();
                } else {
                    alert('提交选择确认人错误！');
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "提交选择确认人错误！");
            });
        }

        function selectApproveUser() {
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            } else if (ds.length > 1) {
                alert('只能选择一条记录！');
                return false;
            }
            var taskStatus = $("#dataTable").datagrid("getSelected").taskStatus;
            var taskSeq = $("#dataTable").datagrid("getSelected").taskSeq;
            if (!(taskSeq == "2" && taskStatus == "1")) {
                alert('只能选择(选择承认人)记录！');
                return false;
            }
            $("#DivApproveUser").dialog('open').dialog('setTitle', '选择承认人');
        }

        // 选择承认人--提交
        function btnApproveUser() {
            var confirmUserId = $("#selectApproveUser").val();
            $.post("<%=request.getContextPath()%>/gradeTask/approveUser.do", {"userId": userId, "confirmUserId": confirmUserId}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    $("#DivApproveUser").dialog('close');
                    search();
                } else {
                    alert('提交选择承认人错误！');
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "提交选择承认人错误！");
            });
        }

        function confrimGrade() {
            var ids = "";
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            } else {
                for(var i = 0; i < ds.length; i++) {
                    var gradeObj = ds[i];
                    if (!(gradeObj.taskSeq == "1" && gradeObj.taskStatus == "11")) {
                        alert('只能选择(确认)记录！');
                        return false;
                    }
                    ids += "," + gradeObj.id;
                }
            }
            ids = ids.substring(1);
            $.post("<%=request.getContextPath()%>/gradeTask/confirmGradeList.do", {"userId": userId, "ids": ids}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    search();
                } else {
                    alert('确认评价错误！');
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "确认评价错误！");
            });
        }

        function approveGrade() {
            var ids = "";
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            } else {
                for(var i = 0; i < ds.length; i++) {
                    var gradeObj = ds[i];
                    if (!(gradeObj.taskSeq == "2" && gradeObj.taskStatus == "11")) {
                        alert('只能选择(承认)记录！');
                        return false;
                    }
                    ids += "," + gradeObj.id;
                }
            }
            ids = ids.substring(1);
            $.post("<%=request.getContextPath()%>/gradeTask/approveGradeList.do", {"userId": userId, "ids": ids}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    search();
                } else {
                    alert('承认评价错误！');
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "承认评价错误！");
            });
        }

        function generateDataGrid() {
            tab = $('#dataTable').datagrid({
                title: '待办任务列表',
                iconCls: 'icon-ok',
                url: '<%=request.getContextPath()%>/gradeTask/getPageList.do?userId=<%=request.getAttribute("userId")%>',
                striped: true,
                idField: "id",
                method: "post",
                toolbar: [{
                    text: '选择确认人',
                    iconCls: 'icon-edit',
                    handler: function () {
                        selectConfirmUser();
                    }
                }, {
                    text: '选择承认人',
                    iconCls: 'icon-edit',
                    handler: function () {
                        selectApproveUser();
                    }
                }, {
                    text: '确认',
                    iconCls: 'icon-edit',
                    handler: function () {
                        confrimGrade();
                    }
                },{
                    text: '承认',
                    iconCls: 'icon-edit',
                    handler: function () {
                        approveGrade();
                    }
                }],
                singleSelect: false,
                width: fixWidth(0.9),
                pagination: true,
                pageSize: 10,
                pageList: [5, 10, 15, 20, 100],
                fitColumns: true,
                rownumbers: true,//行号
                columns: [[
                    { field: 'deptName', title: '线体名称', width: 100 },
                    { field: 'shiftName', title: '班次', width: 100 },
                    { field: 'userName', title: '线长', width: 100 },
                    { field: 'month', title: '月份', width: 50},
                    { field: 'taskStatus', title: '代办', width: 100, formatter: formatView}
                ]],
                onBeforeLoad: function (param) {
                    var deptName =  $("#dept_name").val();
                    var gridOpts = $('#dataTable').datagrid('getPager').data("pagination").options;
                    param.page = gridOpts.pageNumber;
                    param.rows = gridOpts.pageSize;
                    param.deptName = deptName;
                }
            });
        }

        $(function () {
            generateDataGrid();
            $.post("<%=request.getContextPath()%>/userInfo/getList.do", null, function(rsp) {
                if(rsp.success){
                    var userList = rsp.userInfoList;
                    var selObj = $("#selectConfirmUser");
                    var selObj2 = $("#selectApproveUser");
                    selObj2.append("<option value=''>请选择</option>");
                    for(var i = 0; i < userList.length; i ++) {
                        var user = userList[i];
                        var id = user.id;
                        var userName = user.userName;
                        selObj.append("<option value="+id+">"+userName+"</option>");
                        selObj2.append("<option value="+id+">"+userName+"</option>");
                    }
                    $('#selectConfirmUser').searchableSelect();
                    $("#selectApproveUser").searchableSelect();
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "获取用户信息错误了！");
            });
        });

        //刷新
        function reflush() {
            tab.datagrid('clearSelections');
            tab.datagrid("reload");
        }

        function search() {
            reflush();
        }

        function exportRow() {
            var fileUrl = "";
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            }
            var id = $("#dataTable").datagrid("getSelected").id;

        }
    </script>
</head>
<body>
<form>
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
            <div class="main-content" class="white">
                <div id="body">
                    <!-- CONTENT -->
                    <div id="content" class="white">
                        <div class="bloc">
                            <div class="title">
                                待办任务查询
                            </div>
                            <div class="content">
                                <div class="divTable">
                                    <div class="tr">
                                        <div class="td" style="width: 10%; text-align: center; padding-top: 6px;">
                                            <label for="dept_name">线体名称</label>
                                        </div>
                                        <div class="td" style="width: 20%;">
                                            <div class="input">
                                                <input id="dept_name" name="dept_name" maxlength="15" class="txt" style="width: 150px;" type="text">
                                            </div>
                                        </div>
                                        <div class="td" style="width: 20%">
                                            <div class="submit">
                                                <input value="  查  询  " onclick="search();" style="font: inherit; font-weight: bold; height: 30px; margin-top: 4%" type="button">
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="bloc" style="margin-top: 20px;">
                            <div class="title">
                                待办任务列表
                            </div>
                            <div class="content">
                                <div style="clear: both;">
                                    <table id="dataTable"></table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- END MAIN CONTENT -->
        </div>
        <!-- END MAIN -->
        <div class="clearfix"></div>
    </div>
</form>

<div id="DivConfirmUser" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px"
     closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons'">
    <form id="formConfirmUser" method="post" novalidate="novalidate">
        <div class="content">
            <select id="selectConfirmUser">

            </select>
        </div>
        <div class="clearFix">
            <div style="text-align:right; padding-top:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnConfirmUser" iconcls="icon-ok" onclick="btnSelectConfirmUser()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivConfirmUser').dialog('close')">关闭</a>
            </div>
        </div>
    </form>
</div>

<div id="DivApproveUser" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px"
     closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons'">
    <form id="formApproveUser" method="post" novalidate="novalidate">
        <div class="content">
            <select id="selectApproveUser">

            </select>
        </div>
        <div class="clearFix">
            <div style="text-align:right; padding-top:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnApproveUser" iconcls="icon-ok" onclick="btnApproveUser()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivApproveUser').dialog('close')">关闭</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>