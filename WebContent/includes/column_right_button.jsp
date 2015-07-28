    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <aside id="sidebarB">
		<form method="post" action="<c:url value='/order/addItem'/>">
            <input type="hidden" name="ISBN" value="${product.ISBN}">
            <input type="image" src="<c:url value='/images/a.gif' />" 
                   width="150" alt="Add to Cart">
        </form>
	</aside>