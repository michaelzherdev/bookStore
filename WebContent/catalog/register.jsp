<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left.jsp" />

	<!-- middle column  -->

<section>
	<h1>Download registration</h1>
	<p>Before you can download and read some chapters from selected book, 
  you must register with us by entering your name and email 
  address below.</p>
  	
  	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
	<form action="<c:url value='/catalog/product/${product.ISBN}/read'/>"
			method="post">
		<label>Email</label>
    	<input type="email" name="email" required="required"><br>
		<label>Username</label>
		<input type="text" name="j_username" required="required"><br>
		<label>Password</label>
		<input type="password" name="j_password" required="required"><br>
		
		<input type="submit" value="Login"><br>
	</form>
</section>
	<!-- end of middle column  -->

<jsp:include page="/includes/column_right_button.jsp" />
<jsp:include page="/includes/footer.jsp" />