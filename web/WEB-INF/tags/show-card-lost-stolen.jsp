<%@taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"  %>

<auto:admin-template>
    <jsp:body>
        <h1 class="title">Solicitação de cancelamento #${reason.id}</h1>
        <table class="mdl-data-table mdl-js-data-table">
            <tr>
                <td>Nome do Cliente</td>
                <td>${reason.clientName}</td>
            </tr>
            <tr>
                <td>CPF do Cliente</td>
                <td>${reason.clientCpf}</td>
            </tr>
            <tr>
                <td>4 Ultimos digitos</td>
                <td>${reason.lastCardDigits}</td>
            </tr>
            <tr>
                <td>Data de emissão do cartão</td>
                <td><fmt:formatDate value="${reason.cardEmission}" type="DATE" dateStyle="MEDIUM"/></td>
            </tr>
            <tr>
                <td>Data de expiração do cartão</td>
                <td><fmt:formatDate value="${reason.cardExpiration}" type="DATE" dateStyle="MEDIUM"/></td>
            </tr>
            <tr>
                <td>Comentário do cliente</td>
                <td>${reason.comment}</td>
            </tr>
            <tr>
                <td>Aberto em</td>
                <td><fmt:formatDate value="${reason.createdAt}" type="DATE" dateStyle="MEDIUM"/></td>
            </tr>
            <tr>
                <td>
                    <form method="post">
                        <input type="hidden" name="accepted" value="1"/>
                        <button>Gerar novo plástico</button>
                    </form>
                </td>
                <td>
                    <form method="post">
                        <input type="hidden" name="accepted" value="0"/>
                        <button>Finalizar contrato</button>
                    </form>
                </td>
            </tr>
        </table>
    </jsp:body>
</auto:admin-template>

