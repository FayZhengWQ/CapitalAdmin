<%@ page language="java" import="java.util.*" contentType="text/html;charset=UTF-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>

<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>凭证套打</title>
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="<%=basePath%>js/bootstrap-ladda/ladda-themeless.min.css">
</head>
<body>
<script src="<%=basePath%>js/jquery/jquery.min.js"></script>
<script src="<%=basePath%>js/bootstrap/js/bootstrap.min.js"></script>
<script src="<%=basePath%>js/bootstrap-ladda/spin.min.js"></script>
<script src="<%=basePath%>js/bootstrap-ladda/ladda.min.js"></script>
<script type="text/javascript" src="<%=basePath%>js/lodop/LodopFuncs.js"></script>
<script src="<%=basePath%>js/ajax.js"></script>
<p><font size="3"><b>调整位置：</b></font></p>
<p>上下调整<input type="text" size="5" value="" id="up">左右调整<input type="text" size="5" value="" id="left">
<p>向上向左填负数</p>
<button>
    <a href="javascript:Preview1()">保存方案</a></p>
</button>


<p>2：选择如下打印机：<input type="radio" id="Radio1" name="RadioS1" checked="checked">默认打印机
    <input type="radio" id="Radio2" name="RadioS1" onclick="CreatePrinterList()">指定打印机:
    <select id="PrinterList" size="1"></select> </p>


<%--<p>&nbsp;&nbsp; 用上一步设置的动态纸张大小，打印方向是正(纵)向，见<a href="javascript:Preview2()">打印预览2</a>。</p>--%>


<%--<p>3：选择上一步所指定打印机的如下纸张类型：<input type="radio" id="Radio3" name="RadioS2" checked="checked">不指定纸张--%>
<%--<input type="radio" id="Radio4" name="RadioS2" onclick="CreatePagSizeList()">指定纸张:<select id="PagSizeList" size="1"></select> </p>--%>


<%--<p>&nbsp;&nbsp;打印方向不定，见<a href="javascript:Preview3()">打印预览3</a>。</p>--%>


<p>4：用宽<input type="text" size="5" value="1840" id="W4">(140mm)高<input type="text" size="4" value="940" id="H4">(90mm)进行
    <font size="3"><a href="javascript:Preview4()">打印预览4</a></font>的同时并建立<font size="3">名称为</font>LodopCustomPage的自定义类型。<br>
    &nbsp;这种方式可以适应某些不能动态设置纸张大小的打印机，新建的纸张可用SET_PRINT_MODE重命名。</p>


<%--<p><i>说明：<br>--%>
<%--&nbsp; </i>由于有些打印机对自定义的纸张不一定完全支持，所以同样纸张在不同打印机上预览结果可能不一样。<br>--%>
<%--&nbsp; Lodop的这种预览更接近实际打印输出，但在开发时要注意区分，建议安装一个虚拟打印机来辅助测试。</p>--%>


<%--<p><font size="3"><b>连续打印：</b></font></p>--%>


<p>5：用以上动态设置纸张的方法，循环执行<a href="javascript:Print5()">启动打印</a>，可以实现无间隔的连续打印输出。</p>



<script language="javascript" type="text/javascript">
    var LODOP; //声明为全局变量
    var data=${data};
    var CBMUNITID=${CBMUNITID};
    var LODOPLEFT=${LODOPLEFT};
    var LODOPUP=${LODOPUP};
    $("#up").attr("value",LODOPUP);
    $("#left").attr("value",LODOPLEFT);
    function Preview1() {
        ajax.request({
            url: 'http://10.42.184.90:8080/CapitalAdmin/voucher/add/info',
            type: 'POST',
            jsonp: 'callback',
            data: {
                CBMUNITID: CBMUNITID,
                LODOPUP: document.getElementById('up').value,
                LODOPLEFT: document.getElementById('left').value
            },
            callback: function (data) {
                alert("保存成功");
            }
        });
    };

    function Print5() {

        LODOP=getLodop();
        LODOP.SET_LICENSES("","0ED3CB1FB79D3AB72D528E7A0D9559AC","C94CEE276DB2187AE6B65D56B3FC2848","");
        LODOP.PRINT_INITA(document.getElementById('up').value, document.getElementById('left').value, "", "", "");
        LODOP.SET_PRINT_PAGESIZE(3,document.getElementById('W4').value,document.getElementById('H4').value,"A3");
        LODOP.SET_PRINTER_INDEX(getSelectedPrintIndex());
        AddPrintContent();
        LODOP.PRINT();

        print();

    };

    function print(){
        ajax.request({
            url: 'http://10.42.184.90:8080/CapitalAdmin/pay/customisze/print',
            type: 'POST',
            jsonp: 'callback',
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            data: {
                list: JSON.stringify(data)
            },
            callback: function (data) {

                alert("凭证已打印");

            }
        });
    }
    
    function AddPrintContent() {
        // ADD_PRINT_TEXT(Top,Left,Width,Height,strContent)
        if(CBMUNITID==627201 ||CBMUNITID==627005){
            console.log('现在在柯开委');
            for (let index = 0; index < data.length; index++) {
                const element = data[index];
                LODOP.ADD_PRINT_TEXT(47 + 355 * index, 255, 33, 20, new Date(element.PAYDATE).getFullYear());
                LODOP.ADD_PRINT_TEXT(47 + 355 * index, 304, 25, 20, (new Date(element.PAYDATE).getMonth()+1));
                LODOP.ADD_PRINT_TEXT(47 + 355 * index, 342, 36, 20,  new Date(element.PAYDATE).getDate());
                LODOP.ADD_PRINT_TEXT(42 + 355 * index, 510, 100, 20, element.CODE);
                if(element.ENAME.length>13){
                    LODOP.ADD_PRINT_TEXT(74 + 355 * index, 163, 240, 15, element.ENAME);
                    LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
                }else{
                    LODOP.ADD_PRINT_TEXT(74 + 355 * index, 163, 240, 15, element.ENAME);
                }
                LODOP.ADD_PRINT_TEXT(95 + 355 * index, 163, 177, 14, element.PAYBANKNO);
                LODOP.ADD_PRINT_TEXT(118 + 355 * index, 163, 250, 15, element.PAYBANKNAME);
                if(element.RECNAME.length>17){
                    LODOP.ADD_PRINT_TEXT(76 + 355 * index, 429, 350, 16, element.RECNAME);
                    LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
                }else{
                    LODOP.ADD_PRINT_TEXT(76 + 355 * index, 429, 350, 16, element.RECNAME);
                }
                LODOP.ADD_PRINT_TEXT(97 + 355 * index, 428, 159, 15, element.RECBANKNO);
                if(element.RECBANKNAME.length>15) {
                    LODOP.ADD_PRINT_TEXT(120 + 355 * index, 428, 250, 30, element.RECBANKNAME);
                }else{
                    LODOP.ADD_PRINT_TEXT(120 + 355 * index, 428, 300, 14, element.RECBANKNAME);
                }
                LODOP.ADD_PRINT_TEXT(147 + 355 * index, 161, 300, 26, element.CHINESE_IMONEY);
                LODOP.ADD_PRINT_TEXT(174 + 355 * index, 519, 100, 18, "￥"+element.IMONEY);
                if(element.PURPOSE.length>15){
                    LODOP.ADD_PRINT_TEXT(194 + 355 * index, 104, 300, 20, element.PURPOSE);
                    LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
                }else{
                    LODOP.ADD_PRINT_TEXT(194 + 355 * index, 104, 300, 20, element.PURPOSE);
                }

                LODOP.ADD_PRINT_TEXT(194 + 355 * index, 349, 82, 43, element.CFUNCTIONNAME1 );
                LODOP.ADD_PRINT_TEXT(195 + 355 * index, 445, 83, 46, element.CFUNCTIONNAME2);
                LODOP.ADD_PRINT_TEXT(195 + 355 * index, 535, 81, 52, element.CFUNCTIONNAME3);

            }

        }else{

        for (let index = 0; index < data.length; index++) {
            const element = data[index];
            LODOP.ADD_PRINT_TEXT(47 + 350 * index, 255, 33, 20, new Date(element.PAYDATE).getFullYear());
            LODOP.ADD_PRINT_TEXT(47 + 350 * index, 304, 25, 20, (new Date(element.PAYDATE).getMonth()+1));
            LODOP.ADD_PRINT_TEXT(47 + 350 * index, 342, 36, 20,  new Date(element.PAYDATE).getDate());
            LODOP.ADD_PRINT_TEXT(42 + 350 * index, 510, 100, 20, element.CODE);
            if(element.ENAME.length>13){
                LODOP.ADD_PRINT_TEXT(74 + 350 * index, 163, 240, 15, element.ENAME);
                LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
            }else{
                LODOP.ADD_PRINT_TEXT(74 + 350 * index, 163,240, 15, element.ENAME);
            }
            LODOP.ADD_PRINT_TEXT(95 + 350 * index, 163, 177, 14, element.PAYBANKNO);
            LODOP.ADD_PRINT_TEXT(118 + 350 * index, 163, 250, 15, element.PAYBANKNAME);
            if(element.RECNAME.length>17){
                LODOP.ADD_PRINT_TEXT(76 + 350 * index, 429, 350, 16, element.RECNAME);
                LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
            }else{
                LODOP.ADD_PRINT_TEXT(76 + 350 * index, 429, 350, 16, element.RECNAME);
            }
            LODOP.ADD_PRINT_TEXT(97 + 350 * index, 428, 159, 15, element.RECBANKNO);
            if(element.RECBANKNAME.length>15) {
                LODOP.ADD_PRINT_TEXT(120 + 350 * index, 428, 250, 30, element.RECBANKNAME);
            }else{
                LODOP.ADD_PRINT_TEXT(120 + 350 * index, 428, 300, 14, element.RECBANKNAME);
            }
            LODOP.ADD_PRINT_TEXT(147 + 350 * index, 161, 300, 26, element.CHINESE_IMONEY);
            LODOP.ADD_PRINT_TEXT(174 + 350 * index, 519, 100, 18, "￥"+element.IMONEY);
            if(element.PURPOSE.length>15){
                LODOP.ADD_PRINT_TEXT(194 + 350 * index, 104, 300, 20, element.PURPOSE);
                LODOP.SET_PRINT_STYLEA(0, "FontSize",7);
            }else{
                LODOP.ADD_PRINT_TEXT(194 + 350 * index, 104, 300, 20, element.PURPOSE);
            }

            LODOP.ADD_PRINT_TEXT(194 + 350 * index, 349, 82, 43, element.CFUNCTIONNAME1 );
            LODOP.ADD_PRINT_TEXT(195 + 350 * index, 445, 83, 46, element.CFUNCTIONNAME2);
            LODOP.ADD_PRINT_TEXT(195 + 350 * index, 535, 81, 52, element.CFUNCTIONNAME3);

        }
        }
    };

    function getSelectedPrintIndex(){
        if (document.getElementById("Radio2").checked)
            return document.getElementById("PrinterList").value;
        else return -1;
    };
    function getSelectedPageSize(){
        if (document.getElementById("Radio4").checked)
            return document.getElementById("PagSizeList").value;
        else return "";
    };
    function CreatePrinterList(){
        if (document.getElementById('PrinterList').innerHTML!="") return;
        LODOP=getLodop();
        var iPrinterCount=LODOP.GET_PRINTER_COUNT();
        for(var i=0;i<iPrinterCount;i++){

            var option=document.createElement('option');
            option.innerHTML=LODOP.GET_PRINTER_NAME(i);
            option.value=i;
            document.getElementById('PrinterList').appendChild(option);
        };
    };
    function clearPageListChild(){
        var PagSizeList =document.getElementById('PagSizeList');
        while(PagSizeList.childNodes.length>0){
            var children = PagSizeList.childNodes;
            for(i=0;i<children.length;i++){
                PagSizeList.removeChild(children[i]);
            };
        };
    }
    function CreatePagSizeList() {
        LODOP = getLodop();
        clearPageListChild();
        var strPageSizeList = LODOP.GET_PAGESIZES_LIST(getSelectedPrintIndex(), "\n");
        var Options = new Array();
        Options = strPageSizeList.split("\n");
        for (i in Options) {
            var option = document.createElement('option');
            option.innerHTML = Options[i];
            option.value = Options[i];
            document.getElementById('PagSizeList').appendChild(option);
        }
    };

</script>

</body>
</html>