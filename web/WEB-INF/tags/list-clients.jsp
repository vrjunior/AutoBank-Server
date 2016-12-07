<%@ taglib prefix="auto" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="cons" value="us.guihouse.autobank.servlets.collaborator.Constants"/>
<auto:admin-template>
    <jsp:body>
        <form action="/AutoBank/web/list-clients" method="GET" id="formListClient">
            <h1 class="title">Clientes</h1>
            <div class="mdl-textfield mdl-js-textfield mdl-textfield--expandable" id="searchSection">
                <label class="mdl-button mdl-js-button mdl-button--icon" for="search-input">
                    <i class="material-icons">search</i>
                </label>
                <div class="mdl-textfield__expandable-holder">
                    <input class="mdl-textfield__input" type="text" value="${search}" id="search-input" name="search">
                    <label class="mdl-textfield__label" for="search-input">Buscar</label>
                </div>
            </div>
            <div id="ordenationSection">
                <select name="ordenation" id="comboOrdenation">
                    <option value="" selected disabled>Ordenar por</option>
                    <option value="0">Nome</option>
                    <option value="1">CPF</option>
                    <option value="2">Aniversário</option>
                </select>

                <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-1">
                    <input type="radio" id="option-1" class="mdl-radio__button" name="direction" value="0">
                    <span class="mdl-radio__label">Crescente</span>
                </label>
                <label class="mdl-radio mdl-js-radio mdl-js-ripple-effect" for="option-2">
                    <input type="radio" id="option-2" class="mdl-radio__button" name="direction" value="1">
                    <span class="mdl-radio__label">Decrescente</span>
                </label>
            </div>
            <input id="btnSendForm" type="submit"/>
        </form>
        <table class="mdl-data-table mdl-js-data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Nome</th>
                    <th>Email</th>
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
        <auto:paginator/>
    </jsp:body>
</auto:admin-template>