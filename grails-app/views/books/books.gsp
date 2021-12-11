<!doctype html>
<html>
<head>
    <title><g:message code="books.title" default="Books" /></title>
    <meta name="layout" content="bootstrap">
</head>
<body>
<nav aria-label="breadcrumb">
    <ol class="breadcrumb">
        <li class="breadcrumb-item"><g:link controller="books">Home</g:link></li>
        <li class="breadcrumb-item active" aria-current="page">${tenantId}</li>
    </ol>
</nav>
<p><b>Total: ${count}</b></p>
<ul class="pagination">
    <g:each in="${pages}" var="page">
        <g:if test="${page.active}">
            <li class="page-item active" aria-current="page">
                <span class="page-link">${page.number}</span>
            </li>
        </g:if>
        <g:else>
            <li class="page-item">
                <g:link class="page-link" controller="books" action="${tenantId}" params="[page: page.number]">${page.number}</g:link>
            </li>
        </g:else>
    </g:each>
</ul>
<g:each in="${books}" var="book">
    <p>
        <b>${book.title}</b><br/>
        <asset:image src="${book.image}" width="150"/>
    </p>
</g:each>
</body>
</html>