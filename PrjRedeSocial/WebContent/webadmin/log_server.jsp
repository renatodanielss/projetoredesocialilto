<%@ page language="java" contentType="text/html; charset=ISO-8859-1" import="java.io.*"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
LOG SERVER...<br><br>
	<%
	
		String str = "";
	    File file = new File(System.getProperty("catalina.base") + "/logs/", "SpringMVC.log");
	    InputStream is = new FileInputStream(file);
	    try {
	        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
	        if (is != null) {                            
	            while ((str = reader.readLine()) != null) { 
	            	out.print(str + "<BR>");        
	            }                
	        }
	    } catch(Exception e){
			e.printStackTrace();
		}
	    
	%>
</body>
</html>