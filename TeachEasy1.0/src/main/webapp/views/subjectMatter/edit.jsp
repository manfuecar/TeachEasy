<%--
 * edit.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<style>
.info{
    float: left;
	}
</style>

<security:authorize access="hasRole('TEACHER') || hasRole('ACADEMY')">
<div class="col-md-3"></div>
<div class="col-md-6">
	<form:form	action="subjectMatter/edit.do"	modelAttribute="subjectMatter"> 
			
			<acme:textbox code="subjectMatter.name" path="name"/>
			<acme:textbox code="subjectMatter.description" path="description"/>

			<div class="row">
				<div class="col-md-3"><acme:cancel code="subjectMatter.cancel" url="subjectMatter/list.do"/></div>
				<div class="col-md-3"></div>
				<div class="col-md-6"><acme:submit name="save" code="subjectMatter.save"/></div>
						
			
			</div>

		
	</form:form>
</div>
<div class="col-md-3"></div>
</security:authorize>

