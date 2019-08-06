package Jsp;
import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet;
public class test2 extends MyServlet {
public void doGet(MyRequest request, MyResponse resp){

PrintWriter out = new PrintWriter(resp.outputStream);
out.println("HTTP-1.0 200 OK\r\n");
out.println("<b>Testing for first JSP</b>");
out.println("<br>");
out.println("<b> current time is: ");
out.println("    ");
out.println(  new java.util.Date() );
out.println(" ");
out.println("</b> ");
out.flush();
out.close();
}

protected void doPost(MyRequest request, MyResponse resp){}
}