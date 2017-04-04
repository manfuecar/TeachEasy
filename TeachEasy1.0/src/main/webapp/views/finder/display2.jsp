<%--
 * display.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('STUDENT')">
<div class="col-md-12">
	<div class="row">
		<div class="col-md-12">
			<div class="panel panel-default">
				<div class="panel-body payment-form">
					<form:form	action="student/finder/course/edit2.do"	modelAttribute="finder"> 
					<div class="row">
						<div class="col-md-12">	
							<div class="row">
								<div class="col-md-3">
									<acme:textbox code="finder.city" path="city"/>		
								</div>
								<div class="col-md-3">
									<acme:textbox code="finder.matter" path="matter"/>		
								</div>
								<div class="col-md-2">		
									<acme:textbox code="finder.keyword" path="keyword"/>
								</div>
								<div class="col-md-2">
									<acme:textbox code="finder.minimumPrice" path="minimumPrice"/>		
								</div>
								<div class="col-md-2">
									<acme:textbox code="finder.maximumPrice" path="maximumPrice"/>	
								</div>
							</div>
						</div>
							</div>
							<div class="row">
								<div class="col-md-2">
									<acme:cancel url="student/finder/course/display2.do" code="finder.cancel"/>						
								</div>
								<div class="col-md-6">
								</div>
								<div class="col-md-4">
									<acme:submit name="save" code="finder.edit"/>
								</div>
							</div>
						</form:form>
					</div>
			</div>
		</div>
	</div>
	<div class="row">
		<div class="col-md-12">
		<c:forEach items="${courses}" var="course" >
			<div class="row">
				<div class="col-md-3 text-center">	
					<img src="${course.academy.picture}"  width="200" height="200" class="img-responsive">
				</div>
				<div class="col-md-9 ">
					<h1>${course.title}</h1>
					<h2>${course.academy.name}</h2>
					<div class="row">
						<div class="col-md-6">
							<h3><spring:message code="course.rate" />: ${course.rate}</h3>
						</div>
						<div class="col-md-6">
							<h3><spring:message code="course.avgStars" />: ${course.academy.avgStars}</h3>
						</div>
					</div>
					<div class="row text-right">
						<div class="col-md-12 mt-lg pr-xl">
							<security:authorize access="hasRole('TEACHER') || hasRole('ADMIN') || hasRole('STUDENT') || hasRole('ACADEMY')">
								<a class="btn btn-primary" href="academy/displayById.do?id=${course.academy.id}"><spring:message code="finder.view.academy" /></a>
							</security:authorize>
						</div>
					</div>
				</div>
			</div>
			<hr class="divider"/>
		</c:forEach>
		</div>
	</div>
</div>

</security:authorize>
