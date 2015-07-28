<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left.jsp" />
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	
	<!-- middle column  -->

<section>

<h1>Thanks for joining our email list</h1>
<p>Here is the information that you entered:</p>
	
		<label class="no_pad_top">Email</label>
		<span>${user.email}</span><br>
		<label class="no_pad_top">First Name</label>
		<span>${user.firstName}</span><br><br>
		<label class="no_pad_top">Last Name</label>
		<span>${user.lastName}</span><br><br>
	
</section>
	<!-- end of middle column  -->

<jsp:include page="/includes/column_right.jsp" />
<jsp:include page="/includes/footer.jsp" />