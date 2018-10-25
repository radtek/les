<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/taglib.jsp"%>
<%@ attribute name="content" type="java.lang.String" required="true" description="消息内容"%>
<c:if test="${not empty content}">
    <c:set var="ctype" value="${fn:indexOf(content,'失败') eq -1?'success':'error'}"/>
    <c:choose>
      <c:when test="${ctype eq 'error'}">
        <div id="messageBox" style="font-weight:bold;color:red;">${content}</div> 
      </c:when>
      <c:otherwise>
        <div id="messageBox" style="font-weight:bold;color:blue;">${content}</div> 
      </c:otherwise>
    </c:choose>
</c:if>