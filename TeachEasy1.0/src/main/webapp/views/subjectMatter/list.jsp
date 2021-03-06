<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<div class="col-md-12 mb-md">
	<security:authorize access="hasRole('TEACHER') || hasRole('ACADEMY')">
		<div>
			<a class="btn btn-primary" href="javascript: window.location.replace('subjectMatter/edit.do')"><spring:message code="subjectMatter.create" /></a>
		</div>
	</security:authorize>
</div>

<div class="col-md-12">
	<div class="row">
		<security:authorize access="hasRole('ADMIN') || hasRole('TEACHER') || hasRole('ACADEMY')">
		<div class="col-md-12">
		<div class="table-responsive">
		<table class="table table-striped" id="table-subjectMatter">
			<thead>
		      <tr>
		        <th><spring:message code="subjectMatter.name"/></th>
		        <th><spring:message code="subjectMatter.description"/></th>
		        <th></th>
		      </tr>
		    </thead>
		    <tbody>
			<c:forEach items="${subjectMatters}" var="subjectMatter" >
		      <tr>
		        <td>${subjectMatter.name}</td>
		        <td>${subjectMatter.description}</td>
		        <td>
		        <security:authorize access="hasRole('ADMIN')">
							<jstl:if test="${!subjectMatter.validated}">
								<a class="btn btn-success" href="javascript: window.location.replace('subjectMatter/administrator/accept.do?subjectMatterId=${subjectMatter.id}')"><spring:message code="subjectMatter.accept" /></a>
								
								
								<a class="btn btn-danger" href="javascript: window.location.replace('subjectMatter/administrator/deny.do?subjectMatterId=${subjectMatter.id}')"><spring:message code="subjectMatter.deny" /></a>
								
							</jstl:if>
					</security:authorize>
		        </td>
		      </tr>
		    </c:forEach>
		    
		    <jstl:if test="${msgSaved!=null}"><b><spring:message code="subjectMatter.msgSaved"/></b></jstl:if>
		    </tbody>
		</table>
		<div class='pagination-container' >
			<nav>
			  <ul class="pagination" id="pagination"></ul>
			</nav>
		</div>
		</div>
		</div>
		</security:authorize>
	</div>
	
</div>
<script>
$(document).ready(function() {
	getPagination('#table-subjectMatter', 10);
} );
</script>

