    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <aside id="sidebarB">
		<form method="post" action="<c:url value='/order/addItem'/>">
            <input type="hidden" name="ISBN" value="${product.ISBN}">
            <input type="image" src="<c:url value='/images/addtocart.png' />" 
                   width="150" alt="Add to Cart">
        </form>
        
        <a href="<c:url value='/catalog/product/${product.ISBN}/read' />">
            <img src="<c:url value='/images/download.png'/>" 
                 width="150" alt="Download cover" class="top_margin">
        </a>
	</aside>