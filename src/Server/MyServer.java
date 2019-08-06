package Server;

import java.awt.Desktop;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.dom4j.DocumentException;

import Jsp.*;
import Server.MyRequest;
import Server.MyResponse;
import Servlet.Filter;
import Servlet.FilterChain;
import Servlet.MyServlet;
import Servlet.ServletMapping;
import Servlet.ServletMappingConfig;
import course.PetServlet;
import course.PetServlet2;

public class MyServer extends Thread{

	int port=80;//默认端口
	public String fileHead="F:/Ecl/eclipse-workspace/WebServer/src/Res";//默认地址
	public StringBuffer strBuf;//传子字符串到可视化界面显示
	private Map<String, String>urlServletMap=new HashMap<>(16);//由xml获取的映射
	
    public MyServer(int port,StringBuffer newstrBuf) {//构造函数
        this.port=port;
        this.strBuf=newstrBuf;
    }
     
	public void run() {//线程
		try {
			Start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*服务器启动*/
    public void Start() throws IOException, DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
    	initServletMapping();//读取xml
    	ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("MyServer Start!");
        
        //打开浏览器
        String URL= "http://localhost/all.html";
        browserUrl(URL);
        
        while(true) {
            Socket socket=serverSocket.accept();
            InputStream inputStream=socket.getInputStream();//获取输入流
            OutputStream outputStream=socket.getOutputStream();//获取输出流
            MyRequest request=new MyRequest(inputStream,strBuf);//获取request
            MyResponse response=new MyResponse(outputStream,strBuf);//获取respone
            response.setFileHead(fileHead);//配置路径
            dispatch(request,response);//处理消息
            socket.close();
        }
    }
    
    private void dispatch(MyRequest request,MyResponse response) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {   	
    	//filter
    	if(request.getUrl().indexOf("show5.jsp")!=-1) {
    		System.out.println("Filter！");
    		FilterChain chain=new FilterChain();
    		chain.doFilter(request,response);
    		String nowFilter=chain.nowFilter();
    		System.out.println(nowFilter);
        	Class MyFilter=Class.forName(nowFilter);
            Filter jspSer=(Filter)MyFilter.newInstance();
        	jspSer.doFilter(request,response,chain);
        	System.out.println(nowFilter);
    	}
    	//jsp操作
    	if(request.getUrl().indexOf(".jsp")!=-1) {
    		System.out.println("jsp操作！");
    		//System.out.println(request.httpRequest);
    		String path = response.fileHead+request.getUrl();
    		System.out.println("请求文件地址："+path);
    		
    		JspSupport jsp=new JspSupport(path,request.getMethod());//读取JSP文件，创建类
    		System.out.println("创建类： "+jsp.getClassname());;		
    		
        	Class jspServlet=Class.forName(jsp.getClassname());//创建对象
            MyServlet jspSer=(MyServlet)jspServlet.newInstance();
        	jspSer.service(request, response);//消息处理
    		return;	
    	}
    	//数据请求操作
    	if(request.getUrl().indexOf(".")!=-1||request.getUrl().length()==1) {
            response.write(request.getUrl());//消息处理
            return;
    	}
    	//servlet操作
    	System.out.println("servlet！");
    	String className=urlServletMap.get(request.getUrl().substring(1));//获得类
    	System.out.println("servlet:"+className);
    	
    	Class servletClass=Class.forName(className);//创建对象
        MyServlet myServlet=(MyServlet)servletClass.newInstance();
    	myServlet.service(request, response);//消息处理	
    } 
    
    private void initServletMapping() throws DocumentException {//读取xml文件
    	ServletMappingConfig servletMap=new ServletMappingConfig();
    	servletMap.setServletMappingConfig();
    	for(ServletMapping servletMapping:servletMap.servletMappings) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClassName());
        }
    }

    public void browserUrl(String url){//打开浏览器	 
        if (Desktop.isDesktopSupported()){//判断是否支持Desktop扩展,如果支持则进行下一步
            try {
                URI uri = new URI(url);
                Desktop desktop = Desktop.getDesktop(); //创建desktop对象
                desktop.browse(uri);//调用默认浏览器打开指定URL
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   }
