<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>处罚基准库管理</title>
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
</head>
<body>
	<ul class="nav nav-tabs">
		<li><a href="${ctx}/base/punishLib/">处罚基准库列表</a></li>
		<li class="active"><a href="${ctx}/base/punishLib/form?id=${punishLib.id}">处罚基准库<shiro:hasPermission name="base:punishLib:edit">${not empty punishLib.id?'修改':'添加'}</shiro:hasPermission><shiro:lacksPermission name="base:punishLib:edit">查看</shiro:lacksPermission></a></li>
		<li><a href="${ctx}/base/punishLib/importTab">导入数据</a></li>
	</ul><br/>
	<form:form id="inputForm" modelAttribute="punishLib" action="${ctx}/base/punishLib/save" method="post" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>		
		<div class="control-group">
			<label class="control-label">编号：</label>
			<div class="controls">
				<form:input path="seq" htmlEscape="false" maxlength="32" class="input-xlarge "/>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">行为名称：</label>
			<div class="controls">
				<form:textarea path="behavior" htmlEscape="false"  style="width:800px;height:100px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">法律依据：</label>
			<div class="controls">
				<form:textarea path="lawBasis" htmlEscape="false"  style="width:800px;height:300px;" class="required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label">处罚种类：</label>
			<div class="controls">
				<form:input path="punishType" htmlEscape="false" maxlength="200" class="input-xxlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
			<div class="control-group">
				<label class="control-label">自由裁量权：</label>
				<div class="controls">
					<table id="contentTable" class="table table-striped table-bordered table-condensed">
						<thead>
							<tr>
								<th class="hide"></th>
								<th>情形描述</th>
								<th>裁量幅度</th>
								<shiro:hasPermission name="base:punishLib:edit"><th width="10">&nbsp;</th></shiro:hasPermission>
							</tr>
						</thead>
						<tbody id="punishLibRangeList">
						</tbody>
						<shiro:hasPermission name="base:punishLib:edit"><tfoot>
							<tr><td colspan="5"><a href="javascript:" onclick="addRow('#punishLibRangeList', punishLibRangeRowIdx, punishLibRangeTpl);punishLibRangeRowIdx = punishLibRangeRowIdx + 1;" class="btn">新增</a></td></tr>
						</tfoot></shiro:hasPermission>
					</table>
					<script type="text/template" id="punishLibRangeTpl">//<!--
						<tr id="punishLibRangeList{{idx}}">
							<td class="hide">
								<input id="punishLibRangeList{{idx}}_id" name="punishLibRangeList[{{idx}}].id" type="hidden" value="{{row.id}}"/>
								<input id="punishLibRangeList{{idx}}_delFlag" name="punishLibRangeList[{{idx}}].delFlag" type="hidden" value="0"/>
							</td>
							<td>
								<input id="punishLibRangeList{{idx}}_situation" name="punishLibRangeList[{{idx}}].situation" type="text" value="{{row.situation}}" maxlength="200" class="input-xxlarge required"/>
							</td>
							<td>
								<input id="punishLibRangeList{{idx}}_punishRange" name="punishLibRangeList[{{idx}}].punishRange" type="text" value="{{row.punishRange}}" maxlength="200" class="input-xxlarge required"/>
							</td>
							<shiro:hasPermission name="base:punishLib:edit"><td class="text-center" width="10">
								{{#delBtn}}<span class="close" onclick="delRow(this, '#punishLibRangeList{{idx}}')" title="删除">&times;</span>{{/delBtn}}
							</td></shiro:hasPermission>
						</tr>//-->
					</script>
					<script type="text/javascript">
						var punishLibRangeRowIdx = 0, punishLibRangeTpl = $("#punishLibRangeTpl").html().replace(/(\/\/\<!\-\-)|(\/\/\-\->)/g,"");
						$(document).ready(function() {
							var data = ${fns:toJson(punishLib.punishLibRangeList)};
							for (var i=0; i<data.length; i++){
								addRow('#punishLibRangeList', punishLibRangeRowIdx, punishLibRangeTpl, data[i]);
								punishLibRangeRowIdx = punishLibRangeRowIdx + 1;
							}
						});
					</script>
				</div>
			</div>
		<div class="form-actions">
			<shiro:hasPermission name="base:punishLib:edit"><input id="btnSubmit" class="btn btn-primary" type="submit" value="保 存"/>&nbsp;</shiro:hasPermission>
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
	</form:form>
</body>
</html>