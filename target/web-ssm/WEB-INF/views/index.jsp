<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="content-type" content="txt/html; charset=utf-8" />
    <base href="<%=basePath%>">
    <script type="text/javascript" src="jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="jquery/jquery.js"></script>
    <script type="text/javascript" src="jquery/jquery.form.js"></script>

    <script type="text/javascript">
        $(function () {
            $.ajax({
                url: "/index.do",
                type: "post",
                dataType: "json",
                success: function(data){
                    /*这个方法里是ajax发送请求成功之后执行的代码*/
                    var str = "";
                    $.each(data,function (i,n) {
                        str = "<tr><td>" + n.id + "</td><td>" + n.username + "</td><td>"+n.password+"</td><td>"+n.enable+"</td></tr>";
                        $("#tab").append(str);
                    })
                }
            });
        })


        //JS校验form表单信息
        function checkData(){
            var fileDir = $("#upfile").val();
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            console.log('fileDir'+fileDir);
            console.log('fileDir'+suffix);

            if("" == fileDir){
                alert("选择需要导入的Excel文件！");
                return false;
            }
            if(".xls" != suffix && ".xlsx" != suffix ){
                alert("选择Excel格式的文件导入！");
                return false;
            }
            return true;
        }


        //JS校验form表单信息
        function checkData(){
            var fileDir = $("#upfile").val();
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            console.log('fileDir'+fileDir);
            console.log('fileDir'+suffix);

            if("" == fileDir){
                alert("选择需要导入的Excel文件！");
                return false;
            }
            if(".xls" != suffix && ".xlsx" != suffix ){
                alert("选择Excel格式的文件导入！");
                return false;
            }
            return true;
        }
        //JS校验form表单信息
        function checkData3(){
            var fileDir = $("#upfile3").val();
            var suffix = fileDir.substr(fileDir.lastIndexOf("."));
            console.log('fileDir'+fileDir);
            console.log('fileDir'+suffix);

            if("" == fileDir){
                alert("选择需要导入的Excel文件！");
                return false;
            }
            if(".xls" != suffix && ".xlsx" != suffix ){
                alert("选择Excel格式的文件导入！");
                return false;
            }
            return true;
        }

        $(function() {
            //导出excel表
            $('#consumesOutExcel').on('click',function(){
               if (confirm("真的要导出到这张表中吗?")) {
                   $.messager.progress({
                       title : '处理中',
                       msg : '请稍后',
                   });
                   $.messager.progress('close');
                    location.href="/exportExcel.do";
               }
            });
        });


    </script>
</head>
<body>

    <form method="POST"  enctype="multipart/form-data" id="form1" action="/CapitalAdmin/report/income/upload">
        <table>
            <tr>
                <td>上传文件: </td>
                <td> <input id="upfile" type="file" name="upfile"></td>
            </tr>
            <tr>
                <td><input type="submit" value="导入到数据库" onclick="return checkData()"></td>
            </tr>
        </table>
    </form>
    <br/>
    <br/>
    <form method="POST"  enctype="multipart/form-data" id="form3" action="/CapitalAdmin/fileno/uplode">
        <table>
            <tr>
                <td>上传word文件: </td>
                <td> <input id="upfile2" type="file" name="upfile"></td>
            </tr>
            <tr>
                <td><input type="submit" value="上传word" onclick="return checkData()"></td>
            </tr>
        </table>
    </form>
</body>
<br/><br/>
<form method="POST"  enctype="multipart/form-data" id="form2" action="recbank/import">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile3" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入RECNAMK" onclick="return checkData3()"></td>
        </tr>
    </table>
</form>

<br/><br/>

<form method="POST"  enctype="multipart/form-data" id="form4" action="/CapitalAdmin/paybank/import">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile4" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入PAYBANK" onclick="return checkData()"></td>
        </tr>
    </table>
</form>
<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form5" action="/CapitalAdmin/project/dealings">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile5" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入PROJECT" onclick="return checkData()"></td>
        </tr>
    </table>
</form>
<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form6" action="/CapitalAdmin/purpose/import">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile6" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入PURPOSE用途" onclick="return checkData()"></td>
        </tr>
    </table>
</form>
<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form7" action="/CapitalAdmin/pay/customisze/import">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile7" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入拨款单模板" onclick="return checkData()"></td>
        </tr>
    </table>
</form>

<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form8" action="/CapitalAdmin/project/dealings">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile8" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入往来资金模板" onclick="return checkData()"></td>
        </tr>
    </table>
</form>

<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form9" action="/CapitalAdmin/subsidiary/import">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile9" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="导入银行存款明细表/现金日记账" onclick="return checkData()"></td>
        </tr>
    </table>
</form>

<br/>
<br/>
<form method="POST"  enctype="multipart/form-data" id="form10" action="/CapitalAdmin/project/uplode">
    <table>
        <tr>
            <td>上传文件: </td>
            <td> <input id="upfile10" type="file" name="upfile"></td>
        </tr>
        <tr>
            <td><input type="submit" value="附件上传" onclick="return checkData()"></td>
        </tr>
    </table>
</form>



</html>