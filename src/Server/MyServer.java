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

	int port=80;//Ĭ�϶˿�
	public String fileHead="F:/Ecl/eclipse-workspace/WebServer/src/Res";//Ĭ�ϵ�ַ
	public StringBuffer strBuf;//�����ַ��������ӻ�������ʾ
	private Map<String, String>urlServletMap=new HashMap<>(16);//��xml��ȡ��ӳ��
	
    public MyServer(int port,StringBuffer newstrBuf) {//���캯��
        this.port=port;
        this.strBuf=newstrBuf;
    }
     
	public void run() {//�߳�
		try {
			Start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*����������*/
    public void Start() throws IOException, DocumentException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
    	initServletMapping();//��ȡxml
    	ServerSocket serverSocket=new ServerSocket(port);
        System.out.println("MyServer Start!");
        
        //�������
        String URL= "http://localhost/all.html";
        browserUrl(URL);
        
        while(true) {
            Socket socket=serverSocket.accept();
            InputStream inputStream=socket.getInputStream();//��ȡ������
            OutputStream outputStream=socket.getOutputStream();//��ȡ�����
            MyRequest request=new MyRequest(inputStream,strBuf);//��ȡrequest
            MyResponse response=new MyResponse(outputStream,strBuf);//��ȡrespone
            response.setFileHead(fileHead);//����·��
            dispatch(request,response);//������Ϣ
            socket.close();
        }
    }
    
    private void dispatch(MyRequest request,MyResponse response) throws IOException, ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {   	
    	//filter
    	if(request.getUrl().indexOf("show5.jsp")!=-1) {
    		System.out.println("Filter��");
    		FilterChain chain=new FilterChain();
    		chain.doFilter(request,response);
    		String nowFilter=chain.nowFilter();
    		System.out.println(nowFilter);
        	Class MyFilter=Class.forName(nowFilter);
            Filter jspSer=(Filter)MyFilter.newInstance();
        	jspSer.doFilter(request,response,chain);
        	System.out.println(nowFilter);
    	}
    	//jsp����
    	if(request.getUrl().indexOf(".jsp")!=-1) {
    		System.out.println("jsp������");
    		//System.out.println(request.httpRequest);
    		String path = response.fileHead+request.getUrl();
    		System.out.println("�����ļ���ַ��"+path);
    		
    		JspSupport jsp=new JspSupport(path,request.getMethod());//��ȡJSP�ļ���������
    		System.out.println("�����ࣺ "+jsp.getClassname());;		
    		
        	Class jspServlet=Class.forName(jsp.getClassname());//��������
            MyServlet jspSer=(MyServlet)jspServlet.newInstance();
        	jspSer.service(request, response);//��Ϣ����
    		return;	
    	}
    	//�����������
    	if(request.getUrl().indexOf(".")!=-1||request.getUrl().length()==1) {
            response.write(request.getUrl());//��Ϣ����
            return;
    	}
    	//servlet����
    	System.out.println("servlet��");
    	String className=urlServletMap.get(request.getUrl().substring(1));//�����
    	System.out.println("servlet:"+className);
    	
    	Class servletClass=Class.forName(className);//��������
        MyServlet myServlet=(MyServlet)servletClass.newInstance();
    	myServlet.service(request, response);//��Ϣ����	
    } 
    
    private void initServletMapping() throws DocumentException {//��ȡxml�ļ�
    	ServletMappingConfig servletMap=new ServletMappingConfig();
    	servletMap.setServletMappingConfig();
    	for(ServletMapping servletMapping:servletMap.servletMappings) {
            urlServletMap.put(servletMapping.getUrl(), servletMapping.getClassName());
        }
    }

    public void browserUrl(String url){//�������	 
        if (Desktop.isDesktopSupported()){//�ж��Ƿ�֧��Desktop��չ,���֧���������һ��
            try {
                URI uri = new URI(url);
                Desktop desktop = Desktop.getDesktop(); //����desktop����
                desktop.browse(uri);//����Ĭ���������ָ��URL
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
   }
