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
    <style>
        button, input, optgroup, select, textarea {
            color: black;
            font: inherit;
            margin: 0;
        }
    </style>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/locale/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/klorofil-common.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/app.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/Assets/js/jquery.searchableSelect.js"></script>
    <script type="text/javascript">
        var tab;
        var width = 800;
        var height = 400;

        var scoreData = [
            {"scoreId": "N/A", "scoreValue": "N/A"},
            {"scoreId": "良好", "scoreValue": "良好"},
            {"scoreId": "一般", "scoreValue": "一般"},
            {"scoreId": "不良", "scoreValue": "不良"}
        ];

        function formatView(val, row) {
            if (val == "1") {
                return "未评价";
            } else if (val == "2") {
                return "已评价";
            } else {
                return "";
            }
        }

        function fixWidth(percent) {
            return $("#body").css("width") * percent;
        }

        $(function () {
            $.post("<%=request.getContextPath()%>/userInfo/getList.do", null, function(rsp) {
                if(rsp.success){
                    var userList = rsp.userInfoList;
                    var gradeLineEditSelect = $("#gradeLineEditSelect");
                    for(var i = 0; i < userList.length; i ++) {
                        var user = userList[i];
                        var id = user.id;
                        var userName = user.userName;
                        gradeLineEditSelect.append("<option value="+id+">"+userName+"</option>");
                    }
                    $('#gradeLineEditSelect').searchableSelect();
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "获取用户信息错误了！");
            });
            $.post("<%=request.getContextPath()%>/lineInfo/getList.do", null, function(rsp) {
                if(rsp.success){
                    var lineList = rsp.lineinfoList;
                    var selObj = $("#lineSelect");

                    for(var i = 0; i < lineList.length; i ++) {
                        var line = lineList[i];
                        var id = line.id;
                        var deptName = line.deptname;
                        selObj.append("<option value="+id+">"+deptName+"</option>");
                    }
                    $('#lineSelect').searchableSelect();
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "获取生产线信息错误了！");
            });
            tab = $('#dataTable').datagrid({
                title: '评价列表',
                iconCls: 'icon-ok',
                url: '<%=request.getContextPath()%>/gradeLine/getPageList.do?userId=<%=request.getAttribute("userId")%>',
                striped: true,
                idField: "id",
                method: "post",
                toolbar: [{
                    text: '评价',
                    iconCls: 'icon-edit',
                    handler: function () {
                        editRow();
                    }
                }, {
                    text: '新增',
                    iconCls: 'icon-add',
                    handler: function () {
                        addRow();
                    }
                },  {
                    text: '编辑',
                    iconCls: 'icon-edit',
                    handler: function () {
                        gradeLineEdit();
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
                    { field: 'beginDate', title: '开始时间', width: 30},
                    { field: 'endDate', title: '结束时间', width: 30},
                    { field: 'gradeStatus', title: '状态', width: 30, formatter: formatView}
                ]],
                onBeforeLoad: function (param) {
                    var deptName =  $("#dept_name").val();
                    var gradeStatus =  $("#grade_status").val();
                    var gridOpts = $('#dataTable').datagrid('getPager').data("pagination").options;
                    param.page = gridOpts.pageNumber;
                    param.rows = gridOpts.pageSize;
                    param.deptName = deptName;
                    param.gradeStatus = gradeStatus;
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

        function gradeLineEdit() {
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            }
            if (ds.length > 1) {
                alert('请选择一条记录！');
                return false;
            }
            $("#gradeLineEditId").val($("#dataTable").datagrid("getSelected").id);
            $("#gradeLineEditLabelLine").html($("#dataTable").datagrid("getSelected").deptName);
            $("#gradeLineEditLabelShift").html($("#dataTable").datagrid("getSelected").shiftName);
            $("#gradeLineEditDiv").dialog('open').dialog('setTitle', '编辑');
        }

        function gradeLineEditOK() {
            var gradeLineEditSelect = $("#gradeLineEditSelect").val();
            if(gradeLineEditSelect == undefined || gradeLineEditSelect == null || gradeLineEditSelect == ""){
                alert("必须选择线长！");
                return false;
            }
            $.post("<%=request.getContextPath()%>/gradeLine/editUserId.do", {"id": $("#gradeLineEditId").val(), "userId": gradeLineEditSelect}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    $('#gradeLineEditDiv').dialog('close');
                    search();
                } else {
                    alert(rsp.message);
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "修改线长错误！");
            });
        }

        function addRow() {
            $("#DivAdd").dialog('open').dialog('setTitle', '新增');
        }

        function editRow() {
            var ds = $('#dataTable').datagrid('getSelections');
            if (ds.length == 0) {
                alert('请至少选择一条记录！');
                return false;
            }
            var id = $("#dataTable").datagrid("getSelected").id;
            var tab2 = $('#dataTable2').datagrid({
                title: '员工评价列表',
                iconCls: 'icon-ok',
                url: '<%=request.getContextPath()%>/gradeScore/getGradeScoreList.do',
                striped: true,
                idField: "id",
                method: "post",
                singleSelect: true,
                pagination: false,
                fitColumns: true,
                rownumbers: true,//行号
                onClickCell: onClickCell,
                onAfterEdit: onAfterEdit,
                columns: [[
                    { field: 'userName', title: '姓名', width: 100 },
                    { field: 'score', title: '评价', width: 100,
                        editor: {
                            type: 'combobox',
                            options: {
                                data: scoreData,
                                valueField: 'scoreId',
                                textField: 'scoreValue',
                                required: true
                            }
                        }
                    }
                ]],
                onBeforeLoad: function (param) {
                    param.gradeLineId = id;
                }
            });
            $.extend($.fn.datagrid.methods, {
                editCell: function (jq, param) {
                    return jq.each(function () {
                        var opts = $(this).datagrid('options');
                        var fields = $(this).datagrid('getColumnFields', true).concat($(this).datagrid('getColumnFields'));
                        for (var i = 0; i < fields.length; i++) {
                            var col = $(this).datagrid('getColumnOption', fields[i]);
                            col.editor1 = col.editor;
                            if (fields[i] != param.field) {
                                col.editor = null;
                            }
                        }
                        $(this).datagrid('beginEdit', param.index);
                        for (var i = 0; i < fields.length; i++) {
                            var col = $(this).datagrid('getColumnOption', fields[i]);
                            col.editor = col.editor1;
                        }
                    });
                }
            });
            $("#DivEdit").dialog('open').dialog('setTitle', '评价');
        }

        function onClickRow(rowIndex, rowData) {
            $("#dataTable2").datagrid('selectRow', rowIndex);
            $("#dataTable2").datagrid('beginEdit', rowIndex);//设置可编辑状态
        }

        function btnAddOK(){
            var shiftName = $("#shiftSelect").val();
            if(shiftName == undefined || shiftName == null || shiftName == ""){
                alert("必须选择班次！");
                return false;
            }
            var lineId = $("#lineSelect").val();
            if(lineId == undefined || lineId == null || lineId == ""){
                alert("必须选择线体！");
                return false;
            }
            $.post("<%=request.getContextPath()%>/gradeLine/add.do", {"lineId": lineId, "shiftName": shiftName}, function(rsp) {
                if (rsp.success) {
                    alert('提交成功！');
                    $('#DivAdd').dialog('close');
                    search();
                } else {
                    alert(rsp.message);
                    return false;
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "新增评价线体错误！");
            });
        }

        function btnEditOK(){
            var rows = $('#dataTable2').datagrid('getRows');
            for(i = 0;i < rows.length;i++)
            {
                var score = rows[i].score;
                if (score == undefined || score == null || score == "") {
                    var userName = rows[i].userName;
                    alert("员工（"+userName+"）必须评价！");
                    return;
                }
            }
            var gradeScoreRequest = new Object();
            gradeScoreRequest["gradeScoreList"] = JSON.stringify(rows);
            $.post("<%=request.getContextPath()%>/gradeScore/updateGradeScoreList.do", gradeScoreRequest, function(rsp) {
                if(rsp.status){
                    $.messager.alert("提示", "提交成功！");
                    $('#DivEdit').dialog('close');
                    search();
                }
            }, "JSON").error(function() {
                $.messager.alert("提示", "提交错误了！");
            });
        }

        //编辑完单元格之后触发的事件
        function onAfterEdit(index, row, changes) {

        }

        var editIndex = undefined;
        //判断是否编辑结束
        function endEditing() {
            if (editIndex == undefined) { return true }
            if ($('#dataTable2').datagrid('validateRow', editIndex)) {
                $('#dataTable2').datagrid('endEdit', editIndex);
                editIndex = undefined;
                return true;
            } else {
                return false;
            }
        }

        //点击单元格触发的事件
        function onClickCell(index, field) {

            if (endEditing()) {
                $('#dataTable2').datagrid('selectRow', index)
                    .datagrid('editCell', { index: index, field: field });
                editIndex = index;
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
                        评价信息查询
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
                                <div class="td" style="width: 10%; text-align: center; padding-top: 6px;">
                                    <label for="grade_status">状态</label>
                                </div>
                                <div class="td" style="width: 20%;">
                                    <div class="input">
                                        <select name="grade_status" id="grade_status" style="width:150px;">
                                            <option value="">请选择</option>
                                            <option value="1">未评价</option>
                                            <option value="2">已评价</option>
                                        </select>
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
                        评价列表
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

<div id="DivEdit" class="easyui-dialog" style="width:650px;height:250px;padding:10px 20px"
     closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons'">
    <form id="formEdit" method="post" novalidate="novalidate">
        <div class="content">
            <div style="clear: both;">
                <table id="dataTable2"></table>
            </div>
        </div>
        <div class="clearFix">
            <div style="text-align:right; padding-top:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnEditOK" iconcls="icon-ok" onclick="btnEditOK()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivEdit').dialog('close')">关闭</a>
            </div>
        </div>
    </form>
</div>

<div id="DivAdd" class="easyui-dialog" style="width:300px;height:200px;padding:10px 20px"
     closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons'">
    <form id="formAdd" method="post" novalidate="novalidate">
        <div class="content">
            <div class="form-group">
                <select id="shiftSelect" class="searchable-select">
                    <option value="">请选择</option>
                    <option value="A班">A班</option>
                    <option value="B班">B班</option>
                </select>
            </div>
            <div class="form-group">
                <select id="lineSelect">

                </select>
            </div>
        </div>
        <div class="clearFix">
            <div style="text-align:right; padding-top:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="btnAddOK" iconcls="icon-ok" onclick="btnAddOK()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#DivAdd').dialog('close')">关闭</a>
            </div>
        </div>
    </form>
</div>

<div id="gradeLineEditDiv" class="easyui-dialog" style="width:400px;height:300px;padding:10px 20px"
     closed="true" resizable="true" modal="true" data-options="iconCls: 'icon-add',buttons: '#dlg-buttons'">
    <form id="gradeLineEditForm" method="post" novalidate="novalidate">
        <div class="content">
            <table width="90%">
                <tr>
                    <td width="40%" height="40px">线体名称：</td>
                    <td><label id="gradeLineEditLabelLine"></label><input id="gradeLineEditId" type="hidden"></td>
                </tr>
                <tr>
                    <td width="40%" height="40px">班次：</td>
                    <td><label id="gradeLineEditLabelShift"></label></td>
                </tr>
                <tr>
                    <td width="40%" height="40px">线长：</td>
                    <td>
                        <select id="gradeLineEditSelect">

                        </select>
                    </td>
                </tr>
            </table>
        </div>
        <div class="clearFix">
            <div style="text-align:right; padding-top:10px">
                <a href="javascript:void(0)" class="easyui-linkbutton" id="gradeLineEditBtn" iconcls="icon-ok" onclick="gradeLineEditOK()">确定</a>
                <a href="javascript:void(0)" class="easyui-linkbutton" iconcls="icon-cancel" onclick="javascript:$('#gradeLineEditDiv').dialog('close')">关闭</a>
            </div>
        </div>
    </form>
</div>
</body>
</html>