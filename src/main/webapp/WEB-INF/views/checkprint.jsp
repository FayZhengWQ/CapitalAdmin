<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html>
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
      xmlns:w="urn:schemas-microsoft-com:office:word" xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
      xmlns="http://www.w3.org/TR/REC-html40">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>抽查巡查单编辑</title>
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap-ladda/ladda-themeless.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <h2 style="text-align: center;"><label for="editor">抽查巡查单编辑</label></h2>
        <textarea id="editor" type="text/plain">${content}</textarea>
        <%--<h2 style="white-space: normal; text-align: center;">--%>
        <%--<span style="font-family: 宋体, SimSun;">乡镇财政资金监管巡查签证单<br/></span>--%>
        <%--</h2>--%>
        <%--<p style="white-space: normal; text-align: right;"><span style="font-family: 宋体, SimSun;font-size:14px;">单号：</span></p>--%>
        <%--<p style="white-space: normal;"><span style="font-family: 宋体, SimSun;font-size:14px;">被查单位：绍兴市柯桥区齐贤街道办事处</span></p>--%>
        <%--<table>--%>
        <%--<tbody>--%>
        <%--<tr class="firstRow" style="height:71px;">--%>
        <%--<td width="1133" valign="top" style="word-break: break-all;">--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;">巡查的资金项目名称：人民调解工作专项经费<br/></span></p>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr style="height:154px;">--%>
        <%--<td width="1133" valign="top" style="word-break: break-all;">--%>
        <%--<p><span style="font-family:宋体, SimSun;font-size:14px;">资金的使用情况：</span></p>--%>
        <%--<p style="text-indent: 28px;"><span style="font-family:宋体, SimSun;font-size:14px;">补助内容</span></p>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr style="height:129px;">--%>
        <%--<td width="1133" valign="top" style="word-break: break-all;">--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;">巡查的主要资料内容：</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td width="1133" valign="top" style="word-break: break-all;">--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;">巡查结论：</span></p>--%>
        <%--<p style="text-indent: 28px;"><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p style="text-indent: 28px;"><span style="font-family: 宋体, SimSun;font-size: 22px;">符合规定</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--<tr>--%>
        <%--<td width="1133" valign="top" style="word-break: break-all;">--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;">被检查单位相关人员签证</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p style="text-indent: 28px;"><span style="font-family: 宋体, SimSun; font-size: 22px;">情况属实</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p style="text-indent: 266px;"><span style="font-family: 宋体, SimSun;font-size:14px;">经办人员或主管人员签字：</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--</td>--%>
        <%--</tr>--%>
        <%--</tbody>--%>
        <%--</table>--%>
        <%--<p style="text-align: left;"><span style="font-family: 宋体, SimSun;font-size:14px;">巡查组制单人：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;巡查组组长：&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;日期：</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun;font-size:14px;"><br/></span></p>--%>
        <%--<p style="text-indent: 28px;"><span style="font-family: 宋体, SimSun;"><strong>注：</strong>1、此工作签证单需一事一单，并摘录巡查事项发生的日期、内容等。</span></p>--%>
        <%--<p style="text-indent: 56px;"><span style="font-family: 宋体, SimSun;">2、被巡查单位相关人员签证需认定巡查工作签证单摘录的事项是否真实，如属实，签“情况属实”；如有不同意见，应说明理由，并附相关证据材料。</span></p>--%>
    </div>
    <div class="row" style="margin:20px 0px;">
        <div class="col-md-offset-3 col-md-2">
            <a href="javascript:void(0)" class="btn btn-success btn-sm btn-block ladda-button" data-style="zoom-out" onclick="save(this)"><span class="ladda-label">保存</span></a>
        </div>
        <div class="col-md-offset-2 col-md-2">
            <a href="javascript:void(0)" class="btn btn-success btn-sm btn-block ladda-button" data-style="zoom-out" onclick="exportWord(this)"><span class="ladda-label">下载</span></a>
        </div>
    </div>
</div>
<script src="<%=basePath%>js/jquery/jquery.min.js"></script>
<script src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/bootstrap-ladda/spin.min.js"></script>
<script src="<%=basePath%>js/bootstrap-ladda/ladda.min.js"></script>
<script src="<%=basePath%>js/ueditor/ueditor.config.js"></script>
<script src="<%=basePath%>js/ueditor/ueditor.all.min.js"></script>
<script src="<%=basePath%>js/ueditor/zh-cn.js"></script>
<script src="<%=basePath%>js/FileSaver.js"></script>
<script src="<%=basePath%>js/html-docx.js"></script>
<script src="<%=basePath%>js/ajax.js"></script>
<script>
    var PNO = '${PNO}'
    var editor = UE.getEditor("editor", {
        autoClearEmptyNode: false,
        tableDragable: false,
    });

    function save(that) {
        let btn = Ladda.create(that);
        btn.start();
        ajax.request({
            url: 'http://10.42.184.90:8088/check/save',
            type: 'POST',
            jsonp: 'callback',
            data: {
                PNO: PNO,
                BCHECKDOC: editor.getContent().replace(/&nbsp;/g, " ")
            },
            callback: function (data) {
                btn.stop();
            }
        });
    }

    function exportWord() {
        var html = '<html><head><meta charset="utf-8" />' +
            '<style type="text/css">' +
            'table,td {border: 1px solid #000;} table{border-collapse:collapse;} p{margin:0px;}' +
            '</style>' +
            '</head><body>';
        html += editor.getContent();
        // .replace(/&nbsp; /g, "")
        html += '</body></html>';
        saveAs(htmlDocx.asBlob(html, {orientation: "portrait"}), "抽查巡查单.docx");
    }
</script>
</body>
</html>


