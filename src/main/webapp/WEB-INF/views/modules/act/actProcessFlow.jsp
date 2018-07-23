<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  
<iframe id="flowchart" name="flowchart" src="${pageContext.request.contextPath}/act/diagram-viewer?processDefinitionId=${procDefId}&processInstanceId=${procInsId}" 
style="overflow: visible; height: 500px;" scrolling="yes" frameborder="no" width="100%" height="500px">
</iframe>

<iframe id="flowchart2" name="flowchart2" src="${ctx}/act/task/histoicFlow?procInsId=${procInsId}&startAct=${startAct}&endAct=${endAct}&t="+new Date().getTime() 
style="overflow: visible; height: 500px;" scrolling="yes" frameborder="no" width="100%" height="500px">
</iframe>
</body>
</html>