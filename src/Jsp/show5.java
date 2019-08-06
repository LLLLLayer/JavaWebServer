package Jsp;
import java.io.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet;
public class show5 extends MyServlet {
public void doGet(MyRequest request, MyResponse resp){

PrintWriter out = new PrintWriter(resp.outputStream);
out.println("HTTP-1.0 200 OK\r\n");
out.println("");
out.println("<html>");
out.println("<head>");
out.println("<title>Testing for Filter</title>");
out.println("<body> <h1>Testing for Filter</h1> ");
out.println("<p>");
out.println("The site have been visited ");
out.println(course.AccessFilter.nNum);
out.println(" times.");
out.println("<p>");
out.println("</body> ");
out.println("");
out.println("</html>");
out.flush();
out.close();
}

protected void doPost(MyRequest request, MyResponse resp){}
}