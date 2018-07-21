<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="caseAttr" type="org.wxjs.les.modules.tcase.entity.Tcase" required="true"%>

	<div class="form-horizontal borderedBox">
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span4">		
			<label class="control-label">事项序号：</label>
			<div class="controls controls-tight">
			    <input value="${caseAttr.caseSeq}" readonly="readonly" class="input-large">
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">事项类型：</label>
			<div class="controls controls-tight">
				<input value="${fns:getDictLabel(caseAttr.caseProcess.caseStage, 'case_stage', '')}" readonly="readonly" class="input-large">
			</div>
		        </div>
				<div class="span4">		
			<label class="control-label">当事人类型：</label>
			<div class="controls controls-tight">
				<input value="${caseAttr.partyType}" readonly="readonly" class="input-large">
			</div>
		        </div>		        
		    </div>
		</div>	

		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">

        <!-- org -->
        <c:if test="${caseAttr.partyType eq '单位'}">
				<div class="span6">		
			<label class="control-label">名称：</label>
			<div class="controls controls-tight">
				<input value="${caseAttr.orgName}" readonly="readonly" class="input-xlarge">
			</div>
		        </div>
        </c:if>
		
		<!-- individual -->
        <c:if test="${caseAttr.partyType eq '个人'}">

				<div class="span6">		
			<label class="control-label">姓名：</label>
			<div class="controls controls-tight">
				<input value="${caseAttr.psnName}" readonly="readonly" class="input-xlarge">
			</div>
		        </div>             
        </c:if>	
        
				<div class="span6">		
			<label class="control-label">案件所涉项目名称：</label>
			<div class="controls controls-tight">
				<input value="${caseAttr.projectName}" readonly="readonly" class="input-xlarge">
			</div>
		        </div>        		        		        
		    </div>
		</div>
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span12">		
			<label class="control-label">案由：</label>
			<div class="controls controls-tight">
				<input value="${caseAttr.caseCause}" readonly="readonly" class="input-huge">
			</div>
		        </div>	        
		    </div>
		</div>		

	</div>