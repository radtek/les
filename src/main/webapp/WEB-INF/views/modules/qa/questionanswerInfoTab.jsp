<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>询问笔录管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			//$("#name").focus();
			$("#inputForm").validate({
				submitHandler: function(form){
					loading('正在提交，请稍等...');
					form.submit();
				},
				errorContainer: "#messageBox",
				errorPlacement: function(error, element) {
					$("#messageBox").text("输入有误，请先更正。");
					if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
						error.appendTo(element.parent().parent());
					} else {
						error.insertAfter(element);
					}
				}
			});
		});
		function addRow(list, idx, tpl, row){
			$(list).append(Mustache.render(tpl, {
				idx: idx, delBtn: true, row: row
			}));
			$(list+idx).find("select").each(function(){
				$(this).val($(this).attr("data-value"));
			});
			$(list+idx).find("input[type='checkbox'], input[type='radio']").each(function(){
				var ss = $(this).attr("data-value").split(',');
				for (var i=0; i<ss.length; i++){
					if($(this).val() == ss[i]){
						$(this).attr("checked","checked");
					}
				}
			});
		}
		function delRow(obj, prefix){
			var id = $(prefix+"_id");
			var delFlag = $(prefix+"_delFlag");
			if (id.val() == ""){
				$(obj).parent().parent().remove();
			}else if(delFlag.val() == "0"){
				delFlag.val("1");
				$(obj).html("&divide;").attr("title", "撤销删除");
				$(obj).parent().parent().addClass("error");
			}else if(delFlag.val() == "1"){
				delFlag.val("0");
				$(obj).html("&times;").attr("title", "删除");
				$(obj).parent().parent().removeClass("error");
			}
		}
	</script>
	
	<style type="text/css">
		.nopadding {
		    padding-left: 0px;
		    padding-right: 0px;
		}			
	</style>
</head>
<body>
	<les:questionanswer tab="info" qaId="${questionanswer.id }" isNew="${questionanswer.isNewRecord }"></les:questionanswer>
	<form:form id="inputForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/saveInfo" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label control-tight">案由：</label>
			<div class="controls controls-tight">
				<form:input path="caseCause" htmlEscape="false" maxlength="200" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label control-tight">询问时间：</label>
			<div class="controls controls-tight">
				<input name="fromDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.fromDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>到
				<input name="toDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>	
				<span class="help-inline"><font color="red">*</font> </span>				
			</div>
		</div>
		<div class="control-group">
			<label class="control-label control-tight">询问地点：</label>
			<div class="controls controls-tight">
				<form:input path="location" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label control-tight">调查询问人：</label>
			<div class="controls controls-tight">
				<form:checkboxes path="quizzer" items="${fns:getUserByOffice('')}" itemLabel="name" itemValue="id" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label control-tight">记录人：</label>
			<div class="controls controls-tight">
				<form:input path="recorder" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		        </div>
		    </div>
		</div>					
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label control-tight">被询问人：</label>
			<div class="controls controls-tight">
				<form:input path="answerer" htmlEscape="false" maxlength="32" class="input-middle "/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">性别：</label>
			<div class="controls controls-tight">
				<form:radiobuttons path="answererSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span5">		
			<label class="control-label control-tight">身份证：</label>
			<div class="controls controls-tight">
				<form:input path="answererCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>	
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label control-tight">工作单位：</label>
			<div class="controls controls-tight">
				<form:input path="answererOrganization" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">职务：</label>
			<div class="controls controls-tight">
				<form:input path="answererPost" htmlEscape="false" maxlength="100" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label control-tight">出生年月：</label>
			<div class="controls controls-tight">
				<input name="answererBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate required"
					value="<fmt:formatDate value="${questionanswer.answererBirthday}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label control-tight">住址：</label>
			<div class="controls controls-tight">
				<form:input path="answererAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label control-tight">联系电话：</label>
			<div class="controls controls-tight">
				<form:input path="answererPhone" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label control-tight">邮政编码：</label>
			<div class="controls controls-tight">
				<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input-medium required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="control-group">
			<label class="control-label control-tight">备注信息：</label>
			<div class="controls controls-tight">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
		<div class="form-actions">
			<shiro:hasPermission name="qa:questionanswer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
		</div>
	</form:form>
</body>
</html>