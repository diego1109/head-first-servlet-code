<%@ page import="java.util.*, javax.servlet.http.HttpSession" %>
<html>

<body>
    <h1 align="center">Beer Recommendations</h1>
    <p>
        <%
				HttpSession session3 = request.getSession();
				String = (String)session3.getAttribute("advice");
				out.println("<br>try: " + it.next());		
			%>
    </p>
</body>

</html>