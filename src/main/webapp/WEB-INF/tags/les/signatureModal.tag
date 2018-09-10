<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/signature.jsp"%>

<!-- 本标签和signatureLoader同时使用，本标签在一个页面上只要放一个。 -->

<script type="text/javascript">

	var dHeight = 450;
	var dWidth = 800 ; 

    var bodyWidth = dWidth -30;
    var bodyHeight = dHeight *2/3;
    var boxWidth = bodyWidth - 10;
    var boxHeight = bodyHeight - 10; 

    $(document).ready(function() {
    	$('#sigModal').on('shown.bs.modal', function (e) {
            // 关键代码，如没将modal设置为 block，则$modala_dialog.height() 为零
            $(this).css('display', 'block');
            var modalTop = ($(window).height() - dHeight) / 2;
            var modalLeft = ($(window).width() - dWidth) / 2;
            if(modalTop<0){
            	modalTop = 0;
            	dHeight = $(window).height();
            };
            if(modalLeft<0){
            	modalLeft = 0;
            	dWidth = $(window).width();
            };
            alert("modalTop:"+modalTop+", modalLeft:"+modalLeft+",dHeight:"+dHeight+",dWidth:"+dWidth);
            //$(this).find('.modal-dialog').css({'margin-top': modalTop,'margin-left': modalLeft});
            $(this).css({'margin-top': modalTop,'margin-left': modalLeft, 'top': '0%', "left": '0%', 'width': dWidth,'height': dHeight});
            bodyWidth = dWidth -30;
            bodyHeight = dHeight *2/3;
            boxWidth = bodyWidth - 10;
            boxHeight = bodyHeight - 10;    
            
            alert("bodyWidth:"+bodyWidth+", bodyHeight:"+bodyHeight+", boxWidth:"+boxWidth+", boxHeight:"+boxHeight);
            
            $(this).find('.modal-body').css({'width': bodyWidth,'height': bodyHeight});
            $(this).find('#signatureBox').css({'width': boxWidth,'height': boxHeight});
            
            initial();
            
        });    	
    	
    });
	
	function initial(){
		
		//alert("initial...");
	    //初始化
	    var heightPx = (boxHeight-20) + "px";
		var widthPx = boxWidth + "px" ; 
		
		var $sigdiv = $("#signatureBox");
		
		//alert($sigdiv);
		//alert($sigdiv==null);
		
		$sigdiv.jSignature({height:heightPx, width:widthPx, signatureLine:false});//初始化调整手写屏大小
		$sigdiv.jSignature("reset");
    }
	
	function loadModal(sigId){
		
		$("#sigId").val(sigId);
		resetSignature();
	}
		
    function resetSignature(){
        var $sigdiv = $("#signatureBox");
        $sigdiv.jSignature("reset");
    }
    
    function saveSignature(){
    	var sigbox = $("#signatureBox");
    	var datapair = sigbox.jSignature("getData", "image");
    	
    	var json = {};
    	json["id"] = $("#sigId").val();
    	json["title"] = datapair[0];
    	json["signature"] = datapair[1];
    	
    	$.post("${ctx}/base/signature/save",
    		json,
    		function(a){
    		//刷新
    		
    		$("#imageSig"+json["id"]).attr("src","data:" + datapair[0] + "," + datapair[1]);

    	});    	
    }
    
    function loadSignature(sigId){
    	var signature = "${fns:getCache('CurrentUserSignatureContent', '')}";
    	var title = "${fns:getCache('CurrentUserSignatureTitle', '')}";
    	$("#imageSig"+sigId).attr("src","data:" + title + "," + signature);
    }
    
    function loadHistorySignature(sigId, loginName){

    	var json = {};
    	json["loginName"] = loginName;
    	
    	$.get("${ctx}//base/signature/getLatestSignatureByLoginName",
    		json,
    		function(a){
        	var signature = a["signature"];
        	var title = a["title"];
        	$("#imageSig"+sigId).attr("src","data:" + title + "," + signature);    		
    	    }
    	); 
   
    }

</script>

<input type="hidden" id="sigId" value=""/>

<!-- 模态框（Modal） -->
<div class="modal fade" id="sigModal" style="display:none;">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					&times;
				</button>
				<h4 class="modal-title" >
					签名框
				</h4>
			</div>
			<div class="modal-body" >
				<div id="signatureBox" style="border: 1px solid #ccc;margin: 10px 0px;"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveSignature()" data-dismiss="modal">保存签名</button>
				<button type="button" class="btn btn-primary" onclick="resetSignature()">清除签名</button>	
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>		
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

