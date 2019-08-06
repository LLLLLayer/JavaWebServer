package Jsp;
import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet;
public class test6 extends MyServlet {
public void doGet(MyRequest request, MyResponse resp){

PrintWriter out = new PrintWriter(resp.outputStream);
out.println("HTTP-1.0 200 OK\r\n");
out.println("<html> <body>");
out.println("<b>Login to System</B>");
out.println("");
out.println("Current user is:");
out.println(request.getSession().getAttribute("username"));
out.println("");
out.println(" </br>");
out.println(" </hr>");
out.println("");
out.println("<form  action=\"Login\" method=\"post\"> ");
out.println("");
out.println("   <h4> User Name: </h4>");
out.println("        <input type=\"text\"  name=\"username\"  size=\"10\">");
out.println("");
out.println("   <h4> Password: </h4>");
out.println("   <input type=\"text\"  name=\"password\"  size=\"10\">");
out.println("        <p>");
out.println("    <input type=\"submit\" value=\"Login\" >");
out.println("    </p></body>");
out.println("</form>");
out.flush();
out.close();
}

protected void doPost(MyRequest request, MyResponse resp){}
}