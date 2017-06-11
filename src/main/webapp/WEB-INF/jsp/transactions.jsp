<!DOCTYPE html>

<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html lang="en">

<body>
	<div>
		${lastBlock}
	</div>
	<p>==============================</p>
	<c:forEach var="transaction" items="${transactions}">
		<div>${transaction}</div>
	</c:forEach>
</body>

</html>
