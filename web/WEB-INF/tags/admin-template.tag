<%@tag description="Template Base admin" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Dashboard - AutoBank</title>
    <link rel="stylesheet" href="/AutoBank/assets/css/autobank-style.css"/>
</head>
    <body>
        <auto:menu-header/>

        <!-- Always shows a header, even in smaller screens. -->
        <div class="mdl-layout mdl-js-layout mdl-layout--fixed-header">
            <header class="mdl-layout__header">
                <div class="mdl-layout__header-row">
                    <!-- Title -->
                    <span class="mdl-layout-title">AutoBank</span>
                    <!-- Add spacer, to align navigation to the right -->
                    <div class="mdl-layout-spacer"></div>
                    <!-- Navigation. We hide it in small screens. -->
                    <nav class="mdl-navigation mdl-layout--large-screen-only">
                        <a class="mdl-navigation__link" href="/AutoBank/web/list-clients">Clientes</a>
                        <a class="mdl-navigation__link" href="/AutoBank/web/cancellations">Solicitações de Cancelamento</a>
                    </nav>
                </div>
            </header>
            <div class="mdl-layout__drawer">
                <span class="mdl-layout-title">AutoBank</span>
                <nav class="mdl-navigation">
                    <a class="mdl-navigation__link" href="/AutoBank/web/list-clients">Clientes</a>
                    <a class="mdl-navigation__link" href="/AutoBank/web/cancellations">Solicitações de Cancelamento</a>
                </nav>
            </div>
            <main class="mdl-layout__content">
                <div class="page-content"><!-- Your content goes here -->
                    <jsp:doBody/>
                </div>
            </main>
        </div>



        <!-- MDL -->
        <link rel="stylesheet" href="https://fonts.googleapis.com/icon?family=Material+Icons"/>
        <link rel="stylesheet" href="https://code.getmdl.io/1.2.1/material.indigo-blue.min.css" />
        <script defer src="https://code.getmdl.io/1.2.1/material.min.js"></script>
        <!-- JQUERY -->
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.0/jquery.min.js"></script>
        <script src="/AutoBank/assets/js/AdminFunctions.js"></script>
    </body>
</html>