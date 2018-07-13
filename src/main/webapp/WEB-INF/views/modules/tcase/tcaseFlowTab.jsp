<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>案件管理</title>
	<meta name="decorator" content="default"/>
	<script type="text/javascript">
		$(document).ready(function() {

		});
	</script>
</head>
<body>
	<h3>案件管理</h3>
    <div style="text-align:right;margin-right:30px;">
        <input class="btn btn-primary" type="button" value="返回 " onclick="window.location.href='${ctx}/case/tcase/'"/>
    </div>
    <les:caseSummary></les:caseSummary>
    <les:caseTab tab="flow" id="${tcase.id}"></les:caseTab>		
	<table id="contentTable" class="table table-striped table-bordered table-condensed">
		<thead>
			<tr>
			    <th>流程环节</th>
				<th>状态</th>
				<th>查看</th>
				<th>修改办理时间</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td>案件来源及受理</td>			
				<td>已办结</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>
			<tr>
				<td>立案</td>			
				<td>已办结</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>
			<tr>
				<td>案件审理</td>			
				<td>已办结</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>
			<tr>
				<td>发告知书</td>			
				<td>已办结</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>
			<tr>
				<td>发决定书</td>			
				<td>流转中</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>	
			<tr>
				<td>结案书</td>			
				<td>未启动</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>
			<tr>
				<td>案件结束</td>			
				<td>未启动</td>
				<td><a href="">查看</a></td>
				<td><a href="">修改办理时间</a></td>
			</tr>																	
		</tbody>
	</table>
</body>
</html>