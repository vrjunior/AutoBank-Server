<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<auto:admin-template>
    <jsp:body>
        <h1>Clientes</h1>
        <table class="mdl-data-table mdl-js-data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th class="mdl-data-table__cell--non-numeric">Nome</th>
                    <th class="mdl-data-table__cell--non-numeric">Email</th>
                    <th>CPF</th>
                    <th>Data de Nascimento</th>
                </tr>
                    <c:forEach items="${pager.records}" var="client">
                        <tr>
                            <td>${client.id}</td>
                            <td class="mdl-data-table__cell--non-numeric">${client.name}</td>
                            <td class="mdl-data-table__cell--non-numeric">${client.email}</td>
                            <td>${client.cpf}</td>
                            <td>${client.birthday}</td>
                        </tr>
                    </c:forEach>
                </tbody>
            </thead>
        </table>
        <!-- auto:paginator Exception: does not have the property 'lastPage'. -->
    </jsp:body>
</auto:admin-template>