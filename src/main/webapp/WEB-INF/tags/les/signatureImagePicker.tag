<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ include file="/WEB-INF/views/include/signature.jsp"%>

<!-- 本标签和signatureLoader同时使用，本标签在一个页面上只要放一个。 -->

<script type="text/javascript">

	var dHeight = 450;
	var dWidth = 800 ; 

    $(document).ready(function() {	
    	
    });
	
	function initial(){
    }
	
	function loadModal(loginName){
		
		$("#loginName").val(loginName);
		resetSignature();
	}
    
    function saveSignature(){
    	$("#signatureForm").submit();   	
    }

</script>

<!-- 模态框（Modal） -->
<div class="modal fade" id="sigModal" style="display:none;">
	<div class="modal-dialog">
	<form:form id="signatureForm" modelAttribute="signatureLib" action="${ctx}/base/signatureLib/saveImage" method="post" class="form-horizontal">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal">
					&times;
				</button>
				<h4 class="modal-title" >
					上传签名
				</h4>
			</div>
			<div class="modal-body" >
			    <form:hidden id="loginName" path="user.loginName"/>
			   
			    <form:hidden id="filepath" path="filepath" htmlEscape="false" maxlength="256" class="input-xlarge"/>
				<sys:ckfinder input="filepath" type="sigs" uploadPath="/signatureLib" selectMultiple="false" maxWidth="200" maxHeight="100"/>
				<strong>签名图片应小于50KB。</strong>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" onclick="saveSignature()" data-dismiss="modal">保存</button>
				<button type="button" class="btn btn-primary" data-dismiss="modal">关闭</button>		
			</div>
		</div><!-- /.modal-content -->
	</form:form>
	</div><!-- /.modal -->
</div>

