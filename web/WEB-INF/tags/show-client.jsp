<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<auto:admin-template>
    <jsp:body>
        <h1 class="title">Cliente #${client.id}</h1>
        <table class="mdl-data-table mdl-js-data-table">
            <tr>
                <td>Nome do Cliente</td>
                <td>${client.name}</td>
            </tr>
            <tr>
                <td>CPF do Cliente</td>
                <td>${client.cpf}</td>
            </tr>

        </table>
        <c:if test="${activeCard != null}">
            <table class="mdl-data-table mdl-js-data-table">
                <tr>
                    <th>
                        Abrir solicitação de cancelamento
                    </th>
                </tr>
                <tr>
                    <td>
                        <form method="post" action="../new-cancellation">
                            <input type="hidden" name="clientId" value="${client.id}"/>
                            <input type="hidden" name="cardId" value="${activeCard.id}"/>

                            <label for="reasonInput">Motivo</label>
                            <input id="reasonInput" type="text" name="reason" value=""/>

                            <button>Abrir solicitação</button>
                        </form>
                    </td>
                </tr>
            </table>
        </c:if>
    </jsp:body>
</auto:admin-template>