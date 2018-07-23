<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="processdefid" type="java.lang.String" required="true" description="processdefid"%>
<%@ attribute name="executionId" type="java.lang.String" required="true" description="executionId"%>

	<div class="container-fluid">
		<div class="row-fluid">
			<div class="span12">
				<iframe id="flowchart" name="flowchart" src="${ctx}/common/activiti/processTracking?processDefId=${processdefid}&executionId=${executionId}" 
				style="overflow: visible; height: 800px;" scrolling="yes" frameborder="no" width="100%" height="800px">
				</iframe>
			</div>

		</div>
	</div>