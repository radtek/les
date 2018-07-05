<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/signature.jsp"%>


<html> 
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"> 
<title>手写签名</title> 

<script type="text/javascript">
    $(function(){
        //初始化
        var dHeight = "300px";
		var dWidth = document.body.scrollWidth ; 
 
		$("#signature").jSignature({height:dHeight,width:dWidth, signatureLine:false});//初始化调整手写屏大小
 
    })
 
    //输出
    function jSignatureTest(){ 
        var $sigdiv = $("#signature");
//        $sigdiv.jSignature() // inits the jSignature widget.
        // after some doodling...
//        $sigdiv.jSignature("reset") // clears the canvas and rerenders the decor on it.
        var datapair = $sigdiv.jSignature("getData", "svgbase64") 
        console.log(datapair);
//          datapair = ["image/svg+xml;base64","PD94bWwgdmVyc2lvbj0iMS4wIi
//          BlbmNvZGluZz0iVVRGLTgiIHN0YW5kYWxvbmU9Im5vIj8+PCFET0NUWVBFIHN2Zy
//          BQVUJMSUMgIi0vL1czQy8vRFREIFNWRyAxLjEvL0VOIiAiaHR0cDovL3d3dy53My
//          5vcmcvR3JhcGhpY3MvU1ZHLzEuMS9EVEQvc3ZnMTEuZHRkIj48c3ZnIHhtbG5zPS
//          JodHRwOi8vd3d3LnczLm9yZy8yMDAwL3N2ZyIgdmVyc2lvbj0iMS4xIiB3aWR0aD
//          0iMzEiIGhlaWdodD0iMzQiPjxwYXRoIGZpbGw9Im5vbmUiIHN0cm9rZT0iIzAwMD
//          AwMCIgc3Ryb2tlLXdpZHRoPSIyIiBzdHJva2UtbGluZWNhcD0icm91bmQiIHN0cm
//          9rZS1saW5lam9pbj0icm91bmQiIGQ9Ik0gMSAxIGMgMC4xMiAwLjExIDUuMDEgMy
//          43NiA3IDYgYyAzLjI1IDMuNjUgNS43MSA4LjM1IDkgMTIgYyAyLjY0IDIuOTMgNi
//          4zNyA1LjE2IDkgOCBjIDEuNTggMS43IDQgNiA0IDYiLz48L3N2Zz4="]
        var i = new Image();
        i.src = "data:" + datapair[0] + "," + datapair[1] 
        $(i).appendTo($("#image")) // append the image (SVG) to DOM.
    }
 
    function reset(){
        var $sigdiv = $("#signature");
        $sigdiv.jSignature("reset");
		$("#image img").remove();
    }
</script>
</head>
<body>
<div id="signature" style="width: 400px;height: 300px;border: 1px solid #ccc;margin: 10px 0px;"></div>
<button type="button" onclick="jSignatureTest()">生成签名</button>
<button type="button" onclick="reset()">清除</button>
<div id="image" style="margin:20px"></div>
</body>
</html>
