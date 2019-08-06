package course;

import java.io.IOException;

import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet;

public class Login extends MyServlet {
	
	private static final long serialVersionUID = 1L;

    public Login() {
        super();
    }

	protected void doPost(MyRequest request, MyResponse response){
		String userName;
		try {
			userName = request.getParameter("username");

			String password=request.getParameter("password");
		
			System.out.println("userName:"+userName+"\t password:"+password);
			if("admin".equals(userName) && "admin".equals(password))
				  	request.getSession().setAttribute("username",userName);
			else
				request.getSession().setAttribute("username","Unknown User");
		
			System.out.println(request.getAttribute("username"));
			response.sendRedirect("test6.jsp",request,response);
		
		} catch (IOException e) {
			e.printStackTrace();
		}		
	//	doGet(request, response);
	}

	protected void doGet(MyRequest request, MyResponse response) {		
	}
}
