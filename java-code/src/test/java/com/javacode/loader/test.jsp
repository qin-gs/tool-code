<%@ page import="java.lang.*" %>
<%@ page import="java.io.*" %>
<%@ page import="com.javacode.loader.*" %>

<%
    FileInputStream is = new FileInputStream("E:\\A.class");
    byte[] b = new byte[is.available()];
    is.read(b);
    is.close();

    // out.println("<textarea style='width :1000 ; height=800'>");
    // out.println(JavaClassExecutor.execute(b) ) ;
    // out.println( "</textarea>" );

%>
