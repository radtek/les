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
		.control-label100{
		    width:100px;
		}
	</style>
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/qa/questionanswer/">询问笔录列表</a></li>
		<li class="active"><a href="${ctx}/qa/questionanswer/form?id=${questionanswer.id}">询问笔录<shiro:hasPermission name="qa:questionanswer:edit">${not empty questionanswer.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="qa:questionanswer:edit">查看</shiro:lacksPermission></a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="questionanswer" action="${ctx}/qa/questionanswer/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">案由：</label>
			<div class="controls">
				<form:input path="caseCause" htmlEscape="false" maxlength="200" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">询问时间：</label>
			<div class="controls">
				<input name="fromDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${questionanswer.fromDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>到
				<input name="toDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${questionanswer.toDate}" pattern="yyyy-MM-dd HH:mm"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>					
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">询问地点：</label>
			<div class="controls">
				<form:input path="location" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
			<label class="control-label">调查询问人：</label>
			<div class="controls">
				<form:input path="quizzer" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		        </div>
				<div class="span5">		
			<label class="control-label">记录人：</label>
			<div class="controls">
				<form:input path="recorder" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		        </div>
		    </div>
		</div>					
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label">被询问人：</label>
			<div class="controls">
				<form:input path="answerer" htmlEscape="false" maxlength="32" class="input-middle "/>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label">性别：</label>
			<div class="controls">
				<form:input path="answererSex" htmlEscape="false" maxlength="8" class="input-small required"/>
			</div>
		        </div>		        
		        <div class="span5">		
			<label class="control-label">身份证：</label>
			<div class="controls">
				<form:input path="answererCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>	
		        </div>
		    </div>
		</div>		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">工作单位：</label>
			<div class="controls">
				<form:input path="answererOrganization" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label">职务：</label>
			<div class="controls">
				<form:input path="answererPost" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label">出生年月：</label>
			<div class="controls">
				<input name="answererBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
					value="<fmt:formatDate value="${questionanswer.answererBirthday}" pattern="yyyy-MM"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">住址：</label>
			<div class="controls">
				<form:input path="answererAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
				<div class="span3">		
			<label class="control-label">联系电话：</label>
			<div class="controls">
				<form:input path="answererPhone" htmlEscape="false" maxlength="32" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>		        
		        <div class="span3">		
			<label class="control-label">邮政编码：</label>
			<div class="controls">
				<form:input path="zipCode" htmlEscape="false" maxlength="32" class="input-large required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		<div class="control-group">
			<label class="control-label">备注信息：</label>
			<div class="controls">
				<form:textarea path="remarks" htmlEscape="false" rows="4" maxlength="64" class="input-xxlarge "/>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">询问笔录项目：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>问答</th>
								<th>备注信息</th>
								<shiro:hasPermission name="qa:questionanswer:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="questionanswerItemList">
						</tbody>
						<shiro:hasPermission name="qa:questionanswer:edit"><tfoot>
							<tr><td colspan="4"><a href="javascript:" onclick="addRow('#questionanswerItemList', questionanswerItemRowIdx, questionanswerItemTpl);questionanswerItemRowIdx = questionanswerItemRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="questionanswerItemTpl">//<!--
						<tr id="questionanswerItemList{{idx}}">
							<td class="hide">
								<input id="questionanswerItemList{{idx}}_id" name="questionanswerItemList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="questionanswerItemList{{idx}}_delFlag" name="questionanswerItemList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="questionanswerItemList{{idx}}_qaContent" name="questionanswerItemList[{{idx}}].qaContent" type="text" value="{{row.qaContent}}" maxlength="500" class="input-small required"/>
							</td>
							<td>
								<textarea id="questionanswerItemList{{idx}}_remarks" name="questionanswerItemList[{{idx}}].remarks" rows="4" maxlength="64" class="input-small ">{{row.remarks}}</textarea>
							</td>
							<shiro:hasPermission name="qa:questionanswer:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#questionanswerItemList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var questionanswerItemRowIdx = 0, questionanswerItemTpl = $("#questionanswerItemTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(questionanswer.questionanswerItemList)};
							for (var i=0; i<data.length; i++){
								addRow('#questionanswerItemList', questionanswerItemRowIdx, questionanswerItemTpl, data[i]);
								questionanswerItemRowIdx = questionanswerItemRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="qa:questionanswer:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>