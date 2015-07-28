<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<!-- middle column  -->

<section>

	<h1> Join our email List</h1>
	<p>If you do, we'll send you announcements about new releases and special offers.</p>
	
	<p><i>${message}</i></p>
	
	<form action="<c:url value='/user/subscribeToEmail'/>" method="post">
		<label>Email</label>
		<input type="email" name="email" required="required"><br>
		<label>First Name</label>
		<input type="text" name="firstName" required="required"><br>
		<label>Last Name</label>
		<input type="text" name="lastName" required="required"><br>
		
		<input type="submit" value="Join Us" id="submit">
	</form>
	
	
</section>
	<!-- end of middle column  -->

<jsp:include page="/includes/column_right.jsp" />
<jsp:include page="/includes/footer.jsp" />