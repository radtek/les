<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="sig" type="org.wxjs.les.modules.base.entity.Signature" required="true"%>

<%@ attribute name="path" type="java.lang.String" required="true"%>

<%@ attribute name="hideLoadButton" type="java.lang.String" required="false" description="set 'hide' to hide load button"%>

<!-- 本标签和signatureModal同时使用，本标签在一个页面上可以有多个，signatureModal只要放一个。 -->

<div style="margin:20px">

	<img id="imageSig${sig.id}"  style="height:100px; width:180px" src="${sig.data}"/><BR>
	<!--  
	时间：<div id="sigDate${sig.id}">${sig.createDateDisplay}</div><BR>
	-->
	<form:hidden path="${path}"/>
	
	<c:if test="${hideLoadButton ne 'hide'}">
	<button type="button" class="btn btn-primary" onclick="loadSignature('${sig.id}')">读取签名</button> &nbsp;&nbsp;
	</c:if>
	
	<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#sigModal" onclick="loadModal('${sig.id}')">签名</button>

</div>

