<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>签名管理</title>
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

	</script>
</head>
<body>
    <h3>签名管理</h3>
    <les:caseSummary caseAttr="${tcase}"></les:caseSummary>
	<form:form id="inputForm" modelAttribute="caseProcess" action="${ctx}/tcase/caseProcess/updateSignatureTime" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">事项名称：</label>
			<div class="controls">
				${fns:getDictLabel(caseProcess.caseStage, 'case_stage', '')}
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">事项状态：</label>
			<div class="controls">
				${fns:getDictLabel(caseProcess.caseStageStatus, 'case_stage_status', '')}
			</div>
		</div>		
			<div class="control-group">
				<label class="control-label">签名列表：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th width="15%">任务名称</th>
								<th width="45%">审核意见</th>
								<th width="15%">签名</th>
								<th width="15%">签名时间</th>
								<shiro:hasPermission name="tcase:caseProcess:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="signatureList">
						</tbody>
					</table>
					<script type="text/template" id="signatureTpl">//<!--
						<tr id="signatureList{{idx}}">
							<td class="hide">
								<input id="signatures{{idx}}_id" name="signatures[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="signatures{{idx}}_delFlag" name="signatures[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="signatures{{idx}}_taskName" name="signatures[{{idx}}].taskName" type="text" value="{{row.taskName}}" maxlength="64" class="input-large " readonly="readonly"/>
							</td>
							<td>
                                <textarea id="signatures{{idx}}_approveOpinion" name="signatures[{{idx}}].approveOpinion" style="width:600px;height:80px;" readonly="readonly">{{row.approveOpinion}}</textarea>
							</td>
							<td>
                                <img style="height:100px; width:200px" src="{{row.data}}"/>
							</td>
							<td>
 				                <input id="signatures{{idx}}_createDate" name="signatures[{{idx}}].createDate" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					             value="{{row.createDateDisplay}}"
					             onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false});"/>                               
							</td>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var signatureRowIdx = 0, signatureTpl = $("#signatureTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(caseProcess.signatures)};
							for (var i=0; i<data.length; i++){
								addRow('#signatureList', signatureRowIdx, signatureTpl, data[i]);
								signatureRowIdx = signatureRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;
		</div>
	</form:form>
</body>
</html>