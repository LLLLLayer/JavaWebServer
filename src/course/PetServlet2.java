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
import Server.RequestDispatcher;
import Servlet.MyServlet; 

public class PetServlet2 extends MyServlet { 

    private String recommendedPet(int weight, int legs) { 
        if (legs ==0) return "a goldfish"; 
        if (legs ==4) { 
           if (weight<20) return "a cat"; 
           if (weight<100) return "a dog"; 
        } 
        return "a house plant"; 
    } 

    public void doPost(MyRequest req, MyResponse resp ){  
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
          petWeight=petLegs=-1; // indicates that we got an invalid number
        } 

        String pet = recommendedPet(petWeight, petLegs); 
        req.setAttribute("pet",pet);
        
    	RequestDispatcher requestDispatcher = req.getRequestDispatcher("show3.jsp");
		try {
			try {
				requestDispatcher.forward(req, resp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

    }

	@Override
	protected void doGet(MyRequest request, MyResponse response) {
	} 
} 
