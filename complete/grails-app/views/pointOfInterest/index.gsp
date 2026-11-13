<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'pointOfInterest.label', default: 'Point of Interest')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<div id="content" role="main">
    <div class="container">
        <section class="row">
            <a href="#list-pointOfInterest" class="visually-hidden-focusable" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
            <nav class="navbar navbar-expand-lg bg-body-tertiary">
                <ul class="navbar-nav container-fluid">
                    <li class="nav-item"><a class="nav-link btn" aria-label="Home" href="${createLink(uri: '/')}">
                        <i class="bi-house"></i> <g:message code="default.home.label"/></a>
                    </li>
                    <li class="nav-item me-lg-auto">
                        <g:link class="nav-link btn" aria-label="New" action="create"><i class="bi-database-add"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
                    </li>
                </ul>
            </nav>
        </section>
        <section class="row">
            <div id="list-pointOfInterest" class="col-12 content scaffold-list" role="main">
                <h1><g:message code="default.list.label" args="[entityName]" /></h1>
                <g:flashMessages />
                <f:table class="scaffold table table-striped table-sm" collection="${pointOfInterestList}" properties="['name']"/>
                <g:if test="${pointOfInterestCount > params.int('max')}">
                    <div class="btn-toolbar mb-3" role="toolbar">
                        <g:paginate activeClass="active" class="btn" total="${pointOfInterestCount ?: 0}" />
                    </div>
                </g:if>
            </div>
        </section>
    </div>
</div>
</body>
</html>
