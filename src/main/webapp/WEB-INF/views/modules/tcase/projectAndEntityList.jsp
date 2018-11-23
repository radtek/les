<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<script type="text/javascript">
var projectAndEntityList = "${projectAndEntityList}";
</script>

<table class="table table-striped table-bordered table-condensed" style="width:100%">
	<tr>
		<th width="10%">项目编号</th>
		<th width="20%">项目名称</th>
		<th width="10%">单位类型</th>
		<th width="10%">单位代码</th>
		<th width="20%">单位名称</th>
		<th width="20%">单位地址</th>
		<th width="10%">法定代表人</th>
		<th></th>
	</tr>
	<c:forEach items="${projectAndEntityList}" var="entity">
		<tr>
			<td>${entity.prjNum}</td>
			<td>${entity.prjName}</td>
			<td>${entity.tenderType}</td>
			<td>${entity.orgCode}</td>
			<td>${entity.orgName}</td>
			<td>${entity.orgAddress}</td>
			<td>${entity.orgAgent}</td>
			<td><input type="button" onclick="m_selectItem('${entity.jsonString}')" value="选中"/></td>
		</tr>
	</c:forEach>
</table>