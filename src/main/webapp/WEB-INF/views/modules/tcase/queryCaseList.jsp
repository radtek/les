<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {
			
			$("#btnExport").click(function(){
				top.$.jBox.confirm("确认要导出数据吗？","系统提示",function(v,h,f){
					if(v=="ok"){
						$("#searchForm").attr("action","${ctx}/case/tcase/exportExcel");
						$("#searchForm").submit();
					}
				},{buttonsFocus:1});
				top.$('.jbox-body .jbox-icon').css('top','55px');
			});	
			
			$("#btnSubmit").click(function(){
				$("#searchForm").attr("action","${ctx}/case/tcase/query");
				$("#searchForm").submit();
			});	
			
			$("#btnUpload").click(function(){
				
				var caseId = $("input[name='case_id']");
			    var flag = false;
			    var temp = "";
			    for(var i=0;i<caseId.length;i++)
			    {
			        if(caseId[i].checked)
			        {
			        	temp += ","+caseId[i].value;
			        	flag =true;
			        }
			    }
				
				if(!flag){
					alert("至少勾选一个项目。");
					return;
				}	
				
				$("#caseIds").val(temp.substring(1));
				
				$("#searchForm").attr("action","${ctx}/case/tcase/upload");
				$("#searchForm").submit();
			});
			
		});
		function page(n,s){
			$("#pageNo").val(n);
			$("#pageSize").val(s);
			$("#searchForm").submit();
        	return false;
        }
	</script>
</head>
<body>
    <h3>案件列表</h3>
	
	<form:form id="searchForm" modelAttribute="tcase" action="${ctx}/case/tcase/query" method="post" class="form-horizontal">
		<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
		<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
		
		<input id="caseIds" name="caseIds" type="hidden" value=""/>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案卷年度：</label>
			<div class="controls controls-tight">
				<form:select path="docYear" class="input-xlarge ">
				    <form:option value="" label="全部"/>
					<form:options items="${yearList}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>			    
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">当事人类型：</label>
			<div class="controls controls-tight">
				<form:select path="partyType" class="input-xlarge ">
				    <form:option value="" label="全部"/>
					<form:options items="${fns:getDictList('party_type')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>				
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">当事人名称：</label>
			<div class="controls controls-tight">
			    <form:input path="party" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">项目名称：</label>
			<div class="controls controls-tight">
				<form:input path="projectName" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案由：</label>
			<div class="controls controls-tight">
			    <form:input path="caseCause" htmlEscape="false" maxlength="100" class="input-xlarge"/>
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">处罚决定书编号：</label>
			<div class="controls controls-tight">
				<form:input path="caseDecision.year" htmlEscape="false" maxlength="100" class="input-small"/>年<form:input path="caseDecision.seq" htmlEscape="false" maxlength="100" class="input-small"/>号
			</div>
		        </div>
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">立案日期：</label>
			<div class="controls controls-tight">
				<input name="initialDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.initialDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>到
				<input name="initialDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.initialDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>			
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label">结案日期：</label>
			<div class="controls controls-tight">
				<input name="settleDateFrom" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.settleDateFrom}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>到
				<input name="settleDateTo" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate"
					value="<fmt:formatDate value="${tcase.settleDateTo}" pattern="yyyy-MM-dd"/>"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:false});"/>
			</div>
		        </div>
		    </div>
		</div>						
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span6">		
			<label class="control-label">案件状态：</label>
			<div class="controls controls-tight">
				<form:select path="status" class="input-xlarge ">
					<form:option value="all" label="全部"/>
					<form:options items="${fns:getDictList('case_status')}" itemLabel="label" itemValue="value" htmlEscape="false"/>
				</form:select>                			
			</div>
		        </div>
				<div class="span6">		
			<label class="control-label"></label>
			<div class="controls controls-tight">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="查询"/>&nbsp;&nbsp;&nbsp;
				<input id="btnExport" class="btn btn-primary" type="button" value="导出"/>&nbsp;&nbsp;&nbsp;
				<input id="btnUpload" class="btn btn-primary" type="button" value="数据申报"/>
			</div>
		        </div>
		    </div>
		</div>
	</form:form>
	<sys:message content="${message}"/>
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th></th>
			    <th>编号</th>
				<th>案卷编号</th>
				<th>当事人</th>
				<th>项目名称</th>
				<th width="15%">案由</th>
				<th>处罚决定书编号</th>
				<th>立案日期</th>
				<th>结案日期</th>
				<th>经办人</th>
				<th>案件状态</th>
				<th>生成附件进度</th>
				<th width="15%">本系统附件详情</th>
				<th>上报状态</th>
				<th>上报附件进度</th>
				<th width="15%">前置机附件详情</th>
				<th>上报四库状态</th>
				<th>&nbsp;</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.list}" var="tcase" varStatus="status">
			<tr>
			    <td>
			    <input type="checkbox" name="case_id" value="${tcase.id}" />
			    </td>
			    <td>${status.index+1}</td>
				<td>${tcase.caseDecision.docNumber}</td>
				<td>${tcase.partyDisplay}</td>
				<td>${tcase.projectName}</td>
				<td>${tcase.caseCause}</td>
				<td>${tcase.caseDecision.fullDecisionNumber}</td>								
				<td><fmt:formatDate value="${tcase.initialDate}" pattern="yyyy-MM-dd"/></td>
				<td><fmt:formatDate value="${tcase.settleDate}" pattern="yyyy-MM-dd"/></td>
				<td>${tcase.initialHandler}</td>
				<td>${tcase.statusLabel}</td>
				<td>${tcase.attachLocalProgress}</td>
				<td>${tcase.attachLocalDetail}</td>
				<td>${fns:getDictLabel(tcase.uploadStatus, 'upload_status', '未上传')}</td>
				<td>${tcase.attachUploadProgress}</td>
				<td>${tcase.attachUploadDetail}</td>
				<td>${fns:getDictLabel(tcase.uploadStatusLib4, 'yes_no', '否')}</td>
				<td><a href="${ctx}/case/tcase/infoTab?businesskey=${tcase.id}" target="_blank">详情</a></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<div class="pagination">${page}</div>
</body>
</html>