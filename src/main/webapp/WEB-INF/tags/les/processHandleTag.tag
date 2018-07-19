<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="taskId" type="java.lang.String" required="true" description="任务Id"%>
<%@ attribute name="handleAction" type="java.lang.String" required="true" description="controller"%>
<%@ attribute name="availableHandlers" type="java.util.List" required="true" description="下一环节处理人"%>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false" description="是否会签"%>

    <script type="text/javascript">
	    
	    function approvePass(){

			$("#approve").val("pass");
			$("#approveForm").submit();
	    	
	    }
	    
	    function approveReturn(){
	    	var opinion = $("#approveOpinion").val();
	    	
	    	if(opinion==""){
	    		alert("退回时，处理意见不能为空！");
	    		return;
	    	}

			$("#approve").val("return");
			$("#approveForm").submit();
	    	
	    }
	    
	    function approveCancel(){
	    	var opinion = $("#approveOpinion").val();
	    	
	    	if(opinion==""){
	    		alert("不通过时，处理意见不能为空！");
	    		return;
	    	}

			$("#approve").val("cancel");
			$("#approveForm").submit();
	    	
	    }	    

	</script>
<div class="borderedBox" style="width:80%; text-align:center; align:center;">
	<form:form id="approveForm" modelAttribute="actTask" action="${handleAction}" method="post" class="form-horizontal">

	    <input id="taskid" name="taskid" type="hidden" value="${taskId}"/>
	    <input id="approve" name="approve" type="hidden" value=""/>
	    
		<div class="control-group">
			<label class="control-label">处理意见：</label>
			<div class="controls">
				<form:input path="approveOpinion" class="input-xxlarge"/>&nbsp;&nbsp;

				<input id="btnPass" class="btn btn-primary" type="button" value="通 过" onclick="approvePass()"/>&nbsp;&nbsp;
				<input id="btnReturn" class="btn btn-info" type="button" value="退 回" onclick="approveReturn()"/>&nbsp;&nbsp;	
				<input id="btnCancel" class="btn btn-danger" type="button" value="不通过" onclick="approveCancel()"/>					
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
				
				&nbsp;&nbsp;
		
			</div>
		</div>		
	</form:form>	
</div>


