<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<auto:admin-template>
    <jsp:body>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome do Cliente</th>
                    <th>CPF</th>
                    <th>Aberto em</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${pager.records}" var="reason">
                    <tr>
                        <td>${reason.id}</td>
                        <td>${reason.clientName}</td>
                        <td>${reason.clientCpf}</td>
                        <td>${reason.createdAt}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <auto:paginator/>
    </jsp:body>
</auto:admin-template>

