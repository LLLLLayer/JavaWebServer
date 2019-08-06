package Servlet;

import java.io.IOException;

import Server.MyRequest;
import Server.MyResponse;

public abstract class MyServlet {
    protected abstract void doGet(MyRequest request,MyResponse response);
    protected abstract void doPost(MyRequest request,MyResponse response);
    
    public void service(MyRequest request,MyResponse response) throws NoSuchMethodException, IOException {
        if(request.getMethod().equalsIgnoreCase("POST")) {
            doPost(request, response);
            System.out.println("service doPOST");
        }
        else if(request.getMethod().equalsIgnoreCase("GET")) {
            doGet(request, response);
            System.out.println("service doGET");
        }
        else {
            throw new NoSuchMethodException("Not support!");
        }
    }
}
