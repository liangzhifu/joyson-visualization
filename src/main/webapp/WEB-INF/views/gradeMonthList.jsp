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
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/datagrid-detailview.js"></script>
    <script type="text/javascript">
        var tab;
        var width = 800;
        var contextPath = "${pageContext.request.contextPath}";
        function fixWidth(percent) {
            return $("#body").css("width") * percent;
        }

        function formatView(val, row) {
            if (val == "0") {
                return "已完成";
            } else if (val == "1") {
                return "选择确认人";
            } else if (val == "2") {
                return "选择审核人";
            } else if (val == "3") {
                return "待确认";
            } else if (val == "4") {
                return "待审核";
            } else {
                return "已完成";
            }
        }

        $(function () {
            tab = $('#dataTable').datagrid({
                title: '月度考核列表',
                iconCls: 'icon-ok',
                url: '<%=request.getContextPath()%>/gradeMonth/getPageList.do?userId=<%=request.getAttribute("userId")%>',
                striped: true,
                idField: "id",
                method: "post",
                toolbar: [{
                    text: '导出',
                    iconCls: 'icon-edit',
                    handler: function () {
                        exportRow();
                    }
                }],
                singleSelect: true,
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
                    { field: 'month', title: '月份', width: 30},
                    { field: 'monthStatus', title: '状态', width: 100, formatter: formatView},
                    { field: 'taskUserName', title: '代办人', width: 100}
                ]],
                onBeforeLoad: function (param) {
                    var deptName =  $("#dept_name").val();
                    var gridOpts = $('#dataTable').datagrid('getPager').data("pagination").options;
                    param.page = gridOpts.pageNumber;
                    param.rows = gridOpts.pageSize;
                    param.deptName = deptName;
                }
            });
        });

        //刷新
        function reflush() {
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
            $.post("<%=request.getContextPath()%>/gradeMonth/doExportPDF.do?id="+id, null, function(rsp) {
                if(rsp.success){
                    fileUrl = "<%=request.getContextPath()%>/" + rsp.path;
                    window.open(fileUrl);
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "生成PDF错误了！");
            });
            if(fileUrl != ""){

            }
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
                                月度考核查询
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
                                月度考核列表
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

</body>
</html>