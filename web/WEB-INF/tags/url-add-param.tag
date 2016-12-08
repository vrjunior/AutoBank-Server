<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="paginator" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="name" required="true" type="java.lang.String" %>
<%@ attribute name="value" required="true" type="java.lang.String" %>

<c:url value="">
    <c:forEach items="${param}" var="query">
        <c:if test="${query.key != name}">
            <c:param name="${query.key}" value="${query.value}"/>
        </c:if>
    </c:forEach>
    <c:param name="${name}" value="${value}"/>
</c:url>