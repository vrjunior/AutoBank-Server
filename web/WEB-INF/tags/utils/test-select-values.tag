<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@tag description="paginator" pageEncoding="UTF-8" %>
<%@ attribute name="valueOne" required="true" type="java.lang.String" %>
<%@ attribute name="valueTwo" required="true" type="java.lang.String" %>
<%@ attribute name="out" required="true" type="java.lang.String" %>

<c:if test="${valueOne == valueTwo}">
    <c:out value="${out}"/>
</c:if>