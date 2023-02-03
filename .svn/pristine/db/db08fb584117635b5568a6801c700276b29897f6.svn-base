
		<%@ page import="java.util.Enumeration" %>
		<%
			out.println("<h2>Request Attributes</h2>");
			Enumeration e = request.getAttributeNames();
			while(e.hasMoreElements()) {
				String key = (String)e.nextElement();
				out.println("<hr /><i>" + key + "</i><br />");
				Object obj = request.getAttribute(key);
				if (null == obj) {
					out.println("null");
				} else {
					out.println(obj.toString());
				}
			}

			out.println("<h2>Session Attributes</h2>");
			e = session.getAttributeNames();
			while(e.hasMoreElements()) {
				String key = (String)e.nextElement();
				out.println("<hr /><i>" + key + "</i><br />");
				Object obj = session.getAttribute(key);
				if (null == obj) {
					out.println("null");
				} else {
					out.println(obj.toString());
				}
			}

		%>
