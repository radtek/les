<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="taskId" type="java.lang.String" required="true" description="任务Id"%>
<%@ attribute name="handleAction" type="java.lang.String" required="true" description="controller"%>
<%@ attribute name="availableHandlers" type="java.util.List" required="true" description="下一环节处理人"%>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false" description="是否会签"%>

    <script type="text/javascript">
    
    $(document).ready(function() {
    	$("#btnPass").click(function(){
    		if(!check()){
    			return;
    		}
    		top.$.jBox.confirm("确认要通过吗？","系统提示",function(v,h,f){
    			if(v=="ok"){
    				$("#approve").val("pass");
    				$("#approveForm").submit();
    			}
    		},{buttonsFocus:1});
    		top.$('.jbox-body .jbox-icon').css('top','55px');
    	});
    	
    	$("#btnReturn").click(function(){
    		if(!check()){
    			return;
    		}
    		top.$.jBox.confirm("确认要退回吗？","系统提示",function(v,h,f){
    			if(v=="ok"){
    				$("#approve").val("return");
    				$("#approveForm").submit();
    			}
    		},{buttonsFocus:1});
    		top.$('.jbox-body .jbox-icon').css('top','55px');
    	});
    	
    	$("#btnCancel").click(function(){
    		if(!check()){
    			return;
    		}
    		top.$.jBox.confirm("确认要导出用户数据吗？","系统提示",function(v,h,f){
    			if(v=="ok"){
    				$("#approve").val("cancel");
    				$("#approveForm").submit();
    			}
    		},{buttonsFocus:1});
    		top.$('.jbox-body .jbox-icon').css('top','55px');
    	});  
    	
    	function check(){
    		var rst = true;
    		var opinion = $("#approveOpinion").val();
    		if(opinion == ""){
    			alert("处理意见不能为空。");
    			rst = false;
    		}
    		return rst;
    	}

    	
    });

	</script>
<div class="borderedBox" style="width:90%; text-align:center; align:center;">
	<form:form id="approveForm" modelAttribute="actTask" action="${handleAction}" method="post" class="form-horizontal">

	    <input id="taskid" name="taskid" type="hidden" value="${taskId}"/>
	    <input id="approve" name="approve" type="hidden" value=""/>
	    
		<div class="control-group">
			<label class="control-label">处理意见：</label>
			<div class="controls">
				
                <form:textarea path="approveOpinion" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>&nbsp;&nbsp;&nbsp;&nbsp;			
                					
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">下一环节处理人：</label>
			<div class="controls">
                <c:choose>
                   <c:when test="${multiple}">
                     <form:checkboxes items="${availableHandlers}" path="nextHandlers" itemLabel="name" itemValue="loginName"/>
                   </c:when>
                   <c:otherwise>
                     <form:radiobuttons items="${availableHandlers}" path="nextHandlers" itemLabel="name" itemValue="loginName"/>
                   </c:otherwise>
                </c:choose>
                <c:if test="${empty availableHandlers}">
                                                                   无
                </c:if>
				
				&nbsp;&nbsp;
				<input id="btnPass" class="btn btn-primary" type="button" value="通 过"/>&nbsp;&nbsp;
				<input id="btnReturn" class="btn btn-info" type="button" value="退 回"/>&nbsp;&nbsp;	
				<input id="btnCancel" class="btn btn-danger" type="button" value="不通过"/>				
		
			</div>
		</div>		
	</form:form>	
</div>


