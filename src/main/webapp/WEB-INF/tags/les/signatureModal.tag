<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/signature.jsp"%>

<!-- 本标签和signatureLoader同时使用，本标签在一个页面上只要放一个。 -->

<script type="text/javascript">
	
	function initial(){
		
		//alert("initial...");
	    //初始化
	    var dHeight = "320px";
		var dWidth = "810px" ; 
		
		var $sigdiv = $("#signatureBox");
		
		//alert($sigdiv);
		//alert($sigdiv==null);
		
		$sigdiv.jSignature({height:dHeight, width:dWidth, signatureLine:false});//初始化调整手写屏大小
		$sigdiv.jSignature("reset");
    }
	
	function loadModal(sigId){
		initial();
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

</script>

<input type="hidden" id="sigId" value=""/>

<!-- 模态框（Modal） -->
<div class="modal fade" id="sigModal" style="width:850px;height:450px;display:none;">
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
			<div class="modal-body" style="width: 820px;height: 340px;">
				<div id="signatureBox" style="width: 810px;height: 320px;border: 1px solid #ccc;margin: 10px 0px;"></div>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveSignature()" data-dismiss="modal">保存签名</button>
				<button type="button" class="btn btn-primary" onclick="resetSignature()">清除签名</button>	
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>		
			</div>
		</div><!-- /.modal-content -->
	</div><!-- /.modal -->
</div>

