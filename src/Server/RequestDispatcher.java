package Server;

import java.io.IOException;

import Jsp.*;
import Servlet.MyServlet;

public class RequestDispatcher {
	
	String className;
	RequestDispatcher(String newclassName){
		this.className=newclassName;
	}
	
	public void forward(MyRequest req, MyResponse resp) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
		String path = resp.fileHead+"/"+className;
		System.out.println("RequestDispatcher"+path);
		JspSupport jsp=new JspSupport(path,req.getMethod());
		Class jspServlet=Class.forName(jsp.getClassname());//创建对象
        MyServlet jspSer=(MyServlet)jspServlet.newInstance();
    	jspSer.service(req, resp);	
	}
	
    public void include(MyRequest request, MyResponse response) {
    	
    }
}
