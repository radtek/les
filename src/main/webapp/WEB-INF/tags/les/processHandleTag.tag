<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>	

<%@ attribute name="handleAction" type="java.lang.String" required="true" description="controller"%>
<%@ attribute name="availableHandlers" type="java.util.List" required="true" description="下一环节处理人"%>
<%@ attribute name="multiple" type="java.lang.Boolean" required="false" description="是否会签"%>

<%@ attribute name="caseStageName" type="java.lang.String" required="true" description="case stage name"%>

<%@ attribute name="actTaskAttr" type="org.wxjs.les.modules.base.entity.ActTask" required="true" description="ActTask"%>

    <script type="text/javascript">
    
    $(document).ready(function() {
    	$("#btnPass").click(function(){
    		if(!check()){
    			return;
    		}
    		
    		//check nextHandlers
    		var availableHandlersEmptyFlag = $("#availableHandlersEmptyFlag").val();
    		var nextHandlers = $("#nextHandlers").val();
    		if(!availableHandlersEmptyFlag && nextHandlers.length==0){
    			alert("请选择下一环节处理人！");
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
	    
	    var obj_lis = document.getElementById("opinionTemplates").getElementsByTagName("li");
	    for(i=0;i<obj_lis.length;i++){
	        obj_lis[i].onclick = function(){
	            $("#approveOpinion").val(this.innerHTML);
	        }
	    }

    	
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

	</script>
<div class="borderedBox" style="width:90%; text-align:left; align:left;">
          事项类型：<label style="font-weight:bold;color:red">${caseStageName}</label>&nbsp;&nbsp;&nbsp;任务名称：<label style="font-weight:bold;color:red">${actTaskAttr.taskName}</label>
	<form:form id="approveForm" modelAttribute="actTask" action="${handleAction}" method="post" class="form-horizontal">

	    <input id="taskId" name="taskId" type="hidden" value="${actTaskAttr.taskId}"/>
	    <input id="procInsId" name="procInsId" type="hidden" value="${actTaskAttr.procInsId}"/>
	    <input id="approve" name="approve" type="hidden" value=""/>
	    <input id="businesskey" name="businesskey" type="hidden" value="${actTaskAttr.businesskey}"/>
	    
	    <input id="taskName" name="taskName" type="hidden" value="${actTaskAttr.taskName}"/>
	              
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label" style="width: 15px; font-size: 15px; word-wrap: break-word;">处理意见：</label>
			<div class="controls" style="margin-left:30px">
				<form:textarea path="approveOpinion" htmlEscape="false"  style="width:520px;height:150px;" class="required"/>
			</div>
		        </div>			
				<div class="span5">		
			<label class="control-label" style="width: 15px; font-size: 15px; word-wrap: break-word;"><a href="${ctx}/base/opinionTemplate/list">常用批语：</a></label>
			<div class="controls" style="margin-left:30px">
			    
				<ul id="opinionTemplates">
				    <c:forEach items="${fns:getOpinionTemplates()}" var="temp">
				       <li style="cursor: pointer;margin: 3px 0px 3px 0px">${temp.opinion}</li>
				    </c:forEach>
				</ul>
			</div>
		        </div>
		    </div>
		</div>	    
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<c:if test="${not empty availableHandlers}">
			<label class="control-label">下一环节处理人：</label>
			<div class="controls controls-tight">
                <c:choose>
                   <c:when test="${multiple}">
                     <form:checkboxes items="${availableHandlers}" path="nextHandlers" itemLabel="name" itemValue="loginName" class="required"/>
                     <span class="help-inline"><font color="red">*</font> </span>
                   </c:when>
                   <c:otherwise>
                     <form:radiobuttons items="${availableHandlers}" path="nextHandlers" itemLabel="name" itemValue="loginName" class="required"/>
                     <span class="help-inline"><font color="red">*</font> </span>
                   </c:otherwise>
                </c:choose>
			</div>
			</c:if>
			<input id="availableHandlersEmptyFlag" type="hidden" value="${empty availableHandlers}">
		        </div>			
				<div class="span5">		
			<label class="control-label"></label>
			<div class="controls controls-tight">
			    <c:if test="${actTask.needPassButton}">
			       <input id="btnPass" class="btn btn-primary" type="button" value="通 过"/>&nbsp;&nbsp;
			    </c:if>
				<c:if test="${actTask.needReturnButton}">
				   <input id="btnReturn" class="btn btn-info" type="button" value="退 回"/>&nbsp;&nbsp;	
				</c:if>
				<c:if test="${actTask.needCancelButton}">
				   <input id="btnCancel" class="btn btn-danger" type="button" value="不通过"/>
				</c:if>
			</div>
		        </div>

		    </div>
		</div>		
	</form:form>	
</div>


