<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns:v="urn:schemas-microsoft-com:vml" xmlns:o="urn:schemas-microsoft-com:office:office"
      xmlns:w="urn:schemas-microsoft-com:office:word" xmlns:m="http://schemas.microsoft.com/office/2004/12/omml"
      xmlns="http://www.w3.org/TR/REC-html40">


<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>公示单编辑</title>
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap-ladda/ladda-themeless.min.css">
</head>
<body>
<div class="container">
    <div class="row">
        <h2><label for="editor">公示单编辑</label></h2>
        <div class="row" style="margin:20px 0px;">
            <div class="col-md-offset-3 col-md-2">
                <a href="javascript:void(0)" class="btn btn-success btn-sm btn-block ladda-button" data-style="zoom-out" onclick="save(this)"><span class="ladda-label">保存</span></a>
            </div>
            <div class="col-md-offset-2 col-md-2">
                <a href="javascript:void(0)" class="btn btn-success btn-sm btn-block" onclick="exportWord()">下载</a>
            </div>
        </div>
        <textarea id="editor" type="text/plain">${content}</textarea>
        <%--<h2 style="white-space: normal; text-align: center;"><span style="font-family: 黑体, SimHei;font-size: 28px;">&nbsp;</span></h2>--%>
        <%--<h2 style="white-space: normal; text-align: center;"><span style="font-family: 黑体, SimHei;font-size: 28px;">&nbsp;</span></h2>--%>
        <%--<h2 style="white-space: normal; text-align: center;"><span style="font-family: 黑体, SimHei;font-size: 28px;"><strong>年初预算土地款结算的公示</strong></span></h2>--%>
        <%--<p><span style="font-family: 宋体, SimSun; font-size: 12px;">&nbsp;</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun; font-size: 12px;">&nbsp;</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun; font-size: 12px;">&nbsp;</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun; font-size: 12px;">&nbsp;</span></p>--%>
        <%--<p><span style="font-family: 宋体, SimSun; font-size: 12px;">&nbsp;</span></p>--%>
        <%--<p style="text-indent: 28px;line-height:2.5em;"><span style="font-family: 仿宋, SimSun; font-size: 20px;">为体现公平、公正、公开和透明的办事原则，根据柯区民救[2017]8号文件精神，现将征地和拆迁补偿支出（越剑等）的经费予以公示。</span></p>--%>
        <%--<p style="text-indent: 28px;line-height:2.5em;"><span style="font-family: 仿宋, SimSun; font-size: 20px;">公示期限为：2019-03-22 00:00:00——2019-03-09 00:00:00，任何单位和个人对公示对象持有异议的，请在公示期内以书面形式向本镇反映。</span></p>--%>
        <%--<p style="text-indent: 42px;line-height:2.5em;"><span style="font-family: 仿宋, SimSun; font-size: 20px;">(具体名单详见文件)</span></p>--%>
        <%--<p><span style="font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;">&nbsp;</span></p>--%>
        <%--<p style="text-indent: 42px;"><span style="font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;">举报电话：85654157 &nbsp;85654330</span></p>--%>
        <%--<p><span style="font-family: 仿宋, SimSun; font-size: 20px;line-height:2.5em;">&nbsp;</span></p>--%>
        <%--<p><span style="font-family: 仿宋, SimSun; font-size: 20px;">&nbsp;</span></p>--%>
        <%--<p style="text-indent: 0em; text-align: right;"><span style="font-family: 仿宋, SimSun;font-size: 20px;line-height:2.5em;">绍兴市柯桥区齐贤街道办事处（盖章） &nbsp; &nbsp;</span></p>--%>
        <%--<p style="text-indent: 0em; text-align: right;"><span style="font-family: 仿宋, SimSun;font-size: 20px;line-height:2.5em;">二0一七年一月十三日 &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;</span></p>--%>
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
    var CBMUNITID = '${CBMUNITID}';
    var CBMUNITNAME = '${CBMUNITNAME}';
    var YEAR = '${YEAR}';
    var PNO = '${PNO}';
    var FILENO='${FILENO}';
    var editor = UE.getEditor("editor", {
        autoClearEmptyNode: false,
        tableDragable: false,
    });

    function save(that) {
        let btn = Ladda.create(that);
        btn.start();
        ajax.request({
            url: 'http://10.42.184.90:8080/CapitalAdmin/notice/save',
            type: 'POST',
            jsonp: 'callback',
            data: {
                CBMUNITID: CBMUNITID,
                CBMUNITNAME: CBMUNITNAME,
                YEAR: YEAR,
                PNO: PNO,
                BANNOUNCEMENTDOC: editor.getContent().replace(/&nbsp;/g, " ")
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
        // .replace(/&nbsp; &nbsp; &nbsp; &nbsp;/g, "&nbsp;")
        html += '</body></html>';
        saveAs(htmlDocx.asBlob(html, {orientation: "portrait"}), FILENO+"的公示单.docx");
    }

</script>
</body>
</html>


