<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<h2>Create a Bus Trip</h2>

<sf:form method="post" modelAttribute="bus">


	<div class="error">
		<p><sf:errors path="date"></sf:errors></p>
		<p><sf:errors path="leaveFrom"></sf:errors></p>
		<p><sf:errors path="goingTo"></sf:errors></p>
	</div>

	<table class="form-horizontal" role="form">
		<tr>
			<td class="control-label col-sm-2">Leave Date:</td>
			<td><sf:input class="col-sm-10" path="date" name="date"
					type="text" /><br /></td>
		<tr>
		<tr>
			<td class="control-label col-sm-2">Leave From:</td>
			<td><sf:input class="col-sm-10" path="leaveFrom"
					name="leaveFrom" type="text" /><br />
		<tr>
		<tr>
			<td class="control-label col-sm-2">Going To:</td>
			<td><sf:input class="col-sm-10" path="goingTo" name="goingTo"
					type="text" /><br />
		<tr>
			<td>
			<td>
			<td><input class="btn-primary pull-right" value="Create Trip"
				type="submit" /></td>
		</tr>
	</table>


</sf:form>