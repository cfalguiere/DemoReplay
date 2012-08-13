
<%@ page import="org.example.BookFinder" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'bookFinder.label', default: 'BookFinder')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
        </div>
        <div class="body">
            <h1><g:message code="default.list.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div>
               <g:form action="search" >
                    <div class="dialog">
                        <table>
                            <tbody>

                                <tr class="prop">
                                    <td valign="top" class="name">
                                        <label for="searchPhrase"><g:message code="bookFinder.searchPhrase.label" default="Search Phrase" /></label>
                                    </td>
                                    <td valign="top" class="value ${hasErrors(bean: bookFinderInstance, field: 'searchPhrase', 'errors')}">
                                        <g:textField name="searchPhrase" value="${bookFinderInstance?.searchPhrase}" />
                                    </td>
                                 <tr>
                             </tbody>
                        </table>
                        <div class="buttons" >
                            <span class="button"><g:submitButton name="search" class="save" value="${message(code: 'default.button.search.label', default: 'Search')}" /></span>
                        </div>
                    </div>
                </g:form>

            </div>
            <g:if test="${booksFoundTotal != 0}">
             <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                            <g:sortableColumn property="id" title="${message(code: 'book.id.label', default: 'Id')}" />
                        
                            <g:sortableColumn property="author" title="${message(code: 'book.author.label', default: 'Author')}" />
                            <g:sortableColumn property="title" title="${message(code: 'book.title.label', default: 'Title')}" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${booksFound}" status="i" var="book">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        
                            <td><g:link action="show" controller="book" id="${book.id}">${fieldValue(bean: book, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: book, field: "author")}</td>
                            <td>${fieldValue(bean: book, field: "title")}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${booksFoundTotal}" />
            </div>
            </g:if>
        </div>
    </body>
</html>
