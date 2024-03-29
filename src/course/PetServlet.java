package course;

//   The Servlet library is needed to compile this code.
//   That is NOT included in the JDK download.

//   For servlets, you need to download J2EE (currently 1.4)
//   from http://java.sun.com/j2ee/1.4/download.html#sdk
//      or from http://jakarta.apache.org

// compile with javac -Djava.ext.dirs=%TOMCAT_HOME%\common\lib PetServlet.java 
// or make sure the servlet-api.jar is in the CLASSPATH

import java.io.*; 
import java.text.*; 
import java.util.*;

import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet; 

public class PetServlet extends MyServlet { 

	public PetServlet(){
		System.out.println("PetServlet Created!");
	}
	
    private String recommendedPet(int weight, int legs) { 
        if (legs ==0) return "a goldfish"; 
        if (legs ==4) { 
           if (weight<20) return "a cat"; 
           if (weight<100) return "a dog"; 
        } 
        return "a house plant"; 
    } 

	protected void doGet(MyRequest request, MyResponse response) {
		
	}

	protected void doPost(MyRequest req, MyResponse resp){
        int petWeight = 0, petLegs = 0; 
        try { 
          try {
			petWeight = Integer.parseInt(req.getParameter("weight"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
          try {
			petLegs = Integer.parseInt(req.getParameter("legs"));
		} catch (IOException e) {
			e.printStackTrace();
		} 
        } catch (NumberFormatException nfe) { 
          petWeight=petLegs=-1;
        } 
        DataOutputStream out = new DataOutputStream(resp.outputStream);
        try {
			out.writeBytes("HTTP-1.0 200 OK\r\n");
        out.writeBytes("Content-type:text/html\r\n\r\n");
        //resp.setContentType("text/html"); 
        //PrintWriter out = resp.getWriter();     
        out.writeBytes(" <html> <body> <h1>Recommended Pet - Testing for Servlet</h1> <p>"); 
        out.writeBytes("You want a " + petLegs + "-legged pet weighing " + petWeight + "lbs."); 
        String pet = recommendedPet(petWeight, petLegs); 
        out.writeBytes("<P> We recommend getting <b>" + pet ); 
        out.writeBytes("</b> <hr> </body> </html> "); 
		out.flush();
		out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	} 
} 
