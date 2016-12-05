<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<auto:admin-template>
    <jsp:body>
        <h1 class="title">Solicitações de cancelamento</h1>
        <table class="mdl-data-table mdl-js-data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th class="mdl-data-table__cell--non-numeric">Nome do Cliente</th>
                    <th>CPF</th>
                    <th>Aberto em</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pager.records}" var="reason">
                    <tr>
                        <td>${reason.id}</td>
                        <td class="mdl-data-table__cell--non-numeric">${reason.clientName}</td>
                        <td>${reason.clientCpf}</td>
                        <td>${reason.createdAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <auto:paginator/>
    </jsp:body>
</auto:admin-template>

