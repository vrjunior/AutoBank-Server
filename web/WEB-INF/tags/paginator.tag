<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@tag description="paginator" pageEncoding="UTF-8" %>
<ul class="paginator">
    <c:if test="${pager.canGoFirst()}">
        <li><a href="<auto:url-add-param name="page" value="1"/>">Primeira</a></li>
    </c:if>

    <c:if test="${pager.canGoBack()}">
        <li><a href="<auto:url-add-param name="page" value="${pager.previous}"/>">Anterior</a></li>
    </c:if>

    <c:forEach begin="${pager.pageLoopStart()}" end="${pager.pageLoopEnd()}" varStatus="status">
        <c:if test="${status.index != pager.currentPage}">
            <li><a href="<auto:url-add-param name="page" value="${status.index}"/>">${status.index}</a></li>
        </c:if>
    </c:forEach>

    <c:if test="${pager.canGoNext()}">
        <li><a href="<auto:url-add-param name="page" value="${pager.next}"/>">Próxima</a></li>
    </c:if>

    <c:if test="${pager.canGoLast()}">
        <li><a href="<auto:url-add-param name="page" value="${pager.lastPage}"/>">Última</a></li>
    </c:if>
</ul>