<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left.jsp" />

	<!-- middle column  -->
	
	<%@ page isErrorPage="true" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<section>
	<h1>Database Error</h1>
	 <p>You have encountered an error with the database. .</p>

    <h2>Details</h2>
    <code>
    	${pageContext.errorData.servletName} threw a ${pageContext.exception}
    	<c:forEach var="line" items="${pageContext.errorData.throwable.stackTrace}">
    		&nbsp;&nbsp;&nbsp;&nbsp;at ${line}<br>
    	</c:forEach>
    </code>
</section>
	
	<!-- end of middle column  -->

<jsp:include page="/includes/footer.jsp" />