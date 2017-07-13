<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<script type="text/javascript">
	
		// http://localhost:8080/ex01/user/loginPost
		
		// location.href="sboard/listPage"; 시에는
		// http://localhost:8080/ex01/user/sboard/listPage
		
		// location.href="/sboard/listPage"; 시에는
		// http://localhost:8080/sboard/listPage
		
		
		
		// http://localhost:8080/ex01/sboard/listPage
		location.href="${pageContext.request.contextPath}/sboard/listPage";
	</script>

</body>
</html>