<%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TITLE</title>
</head>
<body>
	<%@ include file="../layout/header.jsp"%>
	<center>
		<h1>
			<font color='green'><%=request.getParameter("id").toString()%></font>님
			환영합니다.
		</h1>
	</center>
	<%@ include file="../layout/footer.jsp"%>
</body>
</html>