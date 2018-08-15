<%@ tag language="java" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/views/include/taglib.jsp"%>

<%@ attribute name="sig" type="org.wxjs.les.modules.base.entity.Signature" required="true"%>

<%@ attribute name="path" type="java.lang.String" required="true"%>

<!-- 本标签和signatureModal同时使用，本标签在一个页面上可以有多个，signatureModal只要放一个。 -->

<div style="margin:20px">
	<c:if test="${not empty sig.data}">
	<img id="imageSig${sig.id}"  style="height:100px; width:200px" src="${sig.data}"/><BR>
	时间：<fmt:formatDate value="${sig.updateDate}" pattern="yyyy-MM-dd HH:mm:ss"/><BR>
	</c:if>
	<form:hidden path="${path}"/>
	<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#sigModal" onclick="loadModal('${sig.id}')">
	签名
    </button>&nbsp;&nbsp;&nbsp;&nbsp;
    <c:if test="${not empty fns:getCache('CurrentUserSignatureTitle', '')}">
    <button type="button" class="btn btn-primary" onclick="loadSignature('${sig.id}')">读取上次签名</button> 
    
    </c:if>
	   
	
</div>

