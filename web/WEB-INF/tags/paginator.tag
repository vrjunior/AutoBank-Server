<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<ul>
    <c:if test="${pager.canGoFirst()}">
        <li><a href="?page=1">Primeira</a></li>
    </c:if>

    <c:if test="${pager.canGoBack()}">
        <li><a href="?page=${pager.previous}">Anterior</a></li>
    </c:if>

    <c:forEach begin="${pager.pageLoopStart()}" end="${pager.pageLoopEnd()}" varStatus="status">
        <c:if test="${status.index != pager.currentPage}">
            <li><a href="?page=${status.index}">${status.index}</a></li>
        </c:if>
    </c:forEach>

    <c:if test="${pager.canGoNext()}">
        <li><a href="?page=${pager.next}">Próxima</a></li>
    </c:if>

    <c:if test="${pager.canGoLast()}">
        <li><a href="?page=${pager.lastPage}">Última</a></li>
    </c:if>
</ul>