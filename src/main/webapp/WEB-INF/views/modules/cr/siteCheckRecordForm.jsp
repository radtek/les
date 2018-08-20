<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<html>
<head>
	<title>现场检查笔录管理</title>
	<meta name="decorator" content="default"/>
	
	<script type="text/javascript">
			 
	$(document).ready(function() {	
		
		$("#inputForm").validate({
			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
				closeLoading();
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
		
		 $('#btnSubmit').click(function() {
			$("#inputForm").attr("action","${ctx}/cr/siteCheckRecord/saveInfo");
			$("#inputForm").submit();		    	
	    });	
		 
		 $('#btnExportPdf').click(function() {
			$("#inputForm").attr("action","${ctx}/cr/siteCheckRecord/exportPDF");
			$("#inputForm").submit();		    	
		 });
	});
			function f1(value){	
				if(value == "个人"){
				   console.log(value);	
			       $("#people").show();
			       $("#unit").hide();
				}else{
		        	$("#unit").show();
		        	$("#people").hide();
		        }
			}
			
			$(function(){
				var value=$("#test").val();
				if(value=="个人"){
				    $("#gr").attr("checked", true);
					$("#people").show();
					$("#unit").hide();
				}
				else{
					$("#dw").attr("checked", true);
		        	$("#unit").show();
		        	$("#people").hide();
				}
				
			});
			
			
	</script>
</head>
<body>
	
	<h3>现场检查笔录</h3>
    
	
	
	<form:form id="inputForm" modelAttribute="siteCheckRecord" action="${ctx}/cr/siteCheckRecord/saveInfo" method="get" class="form-horizontal">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>	
		
		<!--隐藏域，获取对象的当事人类型，供页面加载函数使用 -->
		<div id="d1" hidden="true">
		<input id="test"  value="${siteCheckRecord.partyType}"/>
		</div>
		
		<div class="control-group container-fluid nopadding">
		<div class="control-group">
			<label class="control-label ">当事人类型：</label>
			<div class="controls ">
				<input id="dw" type="radio" name="partyType"  value="单位" onclick="f1(this.value)" />单位
				<input id="gr" type="radio" name="partyType"  value="个人" onclick="f1(this.value)"/>个人
				<!--<form:radiobuttons path="psnSex" items="${fns:getDictList('party_Type')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>-->
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</div>
		
		<div id="unit" >
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
				<div class="span7">		
				<label class="control-label ">名称：</label>
				<div class="controls ">
					<form:input path="orgName" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
		        </div>	
		        		
					<div class="span5">		
				<label class="control-label ">负责人：</label>
				<div class="controls ">
					<form:input path="orgResponsiblePerson" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>			
		    </div>
		</div>	
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
					<div class="span7">		
				<label class="control-label ">统一社会信用代码：</label>
				<div class="controls">
					<form:input path="orgCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>			
				</div>
			        </div>
			        
					<div class="span5">		
				<label class="control-label ">职务：</label>
				<div class="controls ">
					<form:input path="orgResponsiblePersonPost" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
		    </div>
		</div>	
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
					<div class="span7">		
				<label class="control-label">住址：</label>
				<div class="controls">
						<form:input path="orgAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
				</div>
		            </div>
		            
					<div class="span5">		
				<label class="control-label ">联系电话：</label>
				<div class="controls ">
					<form:input path="orgPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
		       	   </div>
		    </div>
		</div>
		</div>
		<div id="people" >
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
					<div class="span7">		
				<label class="control-label ">姓名：</label>
				<div class="controls ">
					<form:input path="psnName" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
			        
						<div class="span5">		
							<label class="control-label">性别：</label>
					<div class="controls ">
						<form:radiobuttons path="psnSex" items="${fns:getDictList('sex')}" itemLabel="label" itemValue="value" htmlEscape="false" class="required"/>
					   <span class="help-inline"><font color="red">*</font> </span>
					</div>
		      			</div>
			</div>
		</div>
		
			        
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
						<div class="span7">		
					<label class="control-label ">出身年月：</label>
					<div class="controls ">
					<input name="psnBirthday" type="text" readonly="readonly" maxlength="20" class="input-medium Wdate "
						value="<fmt:formatDate value="${siteCheckRecord.psnBirthday}" pattern="yyyy-MM"/>"
						onclick="WdatePicker({dateFmt:'yyyy-MM',isShowClear:false});"/>
					</div>
						</div>
						<div class="span5">		
					<label class="control-label ">身份证：</label>
					<div class="controls ">
						<form:input path="psnCode" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
					</div>
						</div>
			</div>
		</div>
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
					<div class="span7">		
						<label class="control-label ">住址：</label>
				<div class="controls ">
						<form:input path="psnAddress" htmlEscape="false" maxlength="100" class="input-xxlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
				</div>
			        </div>
		        
					<div class="span5">		
						<label class="control-label ">联系电话：</label>
				<div class="controls ">
						<form:input path="psnPhone" htmlEscape="false" maxlength="32" class="input-xlarge required"/>
						<span class="help-inline"><font color="red">*</font> </span>
				</div>
		        	</div>
		    </div>
		</div>
		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">
					<div class="span7">		
				<label class="control-label">工作单位：</label>
				<div class="controls ">
					<form:input path="psnOrganization" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
					<span class="help-inline"><font color="red">*</font> </span>
				</div>
		       	    </div>
		       	    
				<div class="span5">		
			<label class="control-label ">职务：</label>
			<div class="controls">
				<form:input path="psnPost" htmlEscape="false" maxlength="100" class="input-xlarge required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		        </div>
		    </div>
		</div>		
		</div>
		
		<div class="control-group container-fluid nopadding">					
		<div class="control-group">
			<label class="control-label ">现场检查记录情况：</label>
			<div class="controls ">
				<form:textarea  path="siteSituation" htmlEscape="false"   rows="2" maxlength="255" style="width:800px;height:100px;" class=" required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
			
			
		</div>
		</div>
		<div class="control-group">
			<label class="control-label ">现场勘察示意图：</label>
			<div class="controls ">
				<form:hidden id="nameImage" path="sitePicture" htmlEscape="false" maxlength="255" class="input-xxlarge required"/>
				<sys:ckfinder input="nameImage" type="images" uploadPath="/test/test" selectMultiple="true"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		
		<div class="control-group container-fluid nopadding">
		<div class="control-group">
			<label class="control-label ">示意图说明：</label>
			<div class="controls ">
				<form:textarea  path="sitePictureMemo" htmlEscape="false"   rows="2" maxlength="255" style="width:800px;height:100px;" class=" required"/>
				<span class="help-inline"><font color="red">*</font> </span>
			</div>
		</div>
		</div>
		
		
		<div class="form-actions">
			<shiro:hasPermission name="cr:siteCheckRecord:edit">
				<input id="btnSubmit" class="btn btn-primary" type="button" value="保 存"/>&nbsp;
				<input id="btnExportPdf" class="btn btn-primary" type="button" value="导出PDF " />
			</shiro:hasPermission>
	
			<input id="btnCancel" class="btn" type="button" value="返 回" onclick="history.go(-1)"/>
		</div>
		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">	
					<div class="span6">		
			<label class="control-label controls-tight">当事人签名：</label>
			<div class="controls controls-tight"> 
			    <les:signatureLoader sig="${siteCheckRecord.partySig}" path="partySig.id" hideLoadButton="hide"></les:signatureLoader>
			</div>
		        </div>
					
					
				<div class="span6">		
			<label class="control-label controls-tight">见证人签名：</label>
			<div class="controls controls-tight">
				<les:signatureLoader sig="${siteCheckRecord.witnessSig}" path="witnessSig.id" hideLoadButton="hide"></les:signatureLoader>
				<les:signatureModal></les:signatureModal>
			</div>
		        </div> 
		    </div>
		</div>		
		
		<div class="control-group container-fluid nopadding">
			<div class="row-fluid">	
					<div class="span6">		
			<label class="control-label controls-tight">勘查人签名：</label>
			<div class="controls controls-tight">
			    <les:signatureLoader sig="${siteCheckRecord.checkerSig}" path="checkerSig.id" hideLoadButton="hide"></les:signatureLoader>
			</div>
		        </div>
					
				<div class="span6">		
			<label class="control-label controls-tight">记录人签名：</label>
			<div class="controls controls-tight">
				<les:signatureLoader sig="${siteCheckRecord.recorderSig}" path="recorderSig.id" hideLoadButton="hide"></les:signatureLoader>
			</div>
		        </div> 
		    </div>
		</div>		
	</form:form>
</body>
</html>