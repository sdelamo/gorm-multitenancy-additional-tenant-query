<!doctype html>
<html>
<head>
    <title><g:message code="books.title" default="Books" /></title>
    <meta name="layout" content="bootstrap">
</head>
<body>
<ol>
    <li><g:link controller="books" action="grails">Grails</g:link></li>
    <li><g:link controller="books" action="micronaut">Micronaut</g:link></li>
    <li><g:link controller="books" action="groovy">Groovy</g:link></li>
    <li><g:link controller="books" action="groovygrails">Groovy & Grails</g:link></li>
</ol>

<ol>
    <li><g:link controller="evict" action="grails">Evict Grails Cache</g:link></li>
    <li><g:link controller="evict" action="micronaut">Evict Micronaut Cache</g:link></li>
    <li><g:link controller="evict" action="groovy">Evict Groovy Cache</g:link></li>
</ol>
</body>
</html>