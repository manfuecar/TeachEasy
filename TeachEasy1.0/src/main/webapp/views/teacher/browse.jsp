<%--
 * action-1.jsp
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

<div class="col-md-12">
		<c:forEach items="${teachers}" var="teacher" >
			<div class="row">
				<div class="col-md-3 text-center">	
					<img src="${teacher.picture}" class="img-responsive">
				</div>
				<div class="col-md-9 ">
					<h1>${teacher.name} ${teacher.surname}</h1>
					<h3>Fecha de nacimiento: ${teacher.date}</h3>
					<div class="row">
						<div class="col-md-6">
							<h3><spring:message code="teacher.city" />: ${teacher.city}</h3>
						</div>
						<div class="col-md-6">
							<h3><spring:message code="teacher.address" />: ${teacher.address}</h3>
						</div>
					</div>
					<h3><spring:message code="teacher.avgStars" />: ${teacher.avgStars}</h3>
					<security:authorize access="hasRole('TEACHER') || hasRole('ADMIN') || hasRole('STUDENT') || hasRole('ACADEMY')">
					<div class="row text-right">
						<div class="col-md-12 mt-lg pr-xl">
							<a class="btn btn-primary" href="teacher/displayById.do?id=${teacher.id}"><spring:message code="finder.view.teacher" /></a>
						</div>
					</div>
					</security:authorize>
				</div>
			</div>
			<hr class="divider"/>
		</c:forEach>
</div>

<display:table  name="teachers" id="teacherList" requestURI="${requestURI}" class="displaytag">
	
	<spring:message code="teacher.name" var="nameHeader"/>
	<display:column property="name" title="${nameHeader}"/>
	
	<spring:message code="teacher.surname" var="surnameHeader"/>
	<display:column property="surname" title="${surnameHeader}" sortable="true"/>
	
	
	<security:authorize access="isAuthenticated()">
	<display:column titleKey="teacher.view">
		<a href="teacher/displayById.do?id=${teacherList.id}">
		<spring:message code="teacher.view"></spring:message></a>	
	</display:column>
	</security:authorize>
	
</display:table>