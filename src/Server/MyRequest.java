package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyRequest {
	
	private String url;//获取需求的资源
	private String method;//获取GETorPOST
	public StringBuilder httpRequest;//读取输入流数据
	public StringBuffer strBuf;//提供主界面显示
	
	MyRequest(StringBuilder newhttpRequest) {
		httpRequest=newhttpRequest;
	}
	
	public MyRequest(InputStream inputStream,StringBuffer newstrBuf){//读取HTTP头
		strBuf=newstrBuf;
		httpRequest=new StringBuilder();
        byte[]httpRequestByte=new byte[1024];
        int length=0;
        try {
			if((length=inputStream.read(httpRequestByte))>0) {
			    httpRequest.append(new String(httpRequestByte,0,length));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
        //获取头中的url和method
        String httpHead=httpRequest.toString().split("\n")[0];
        	if(httpHead.indexOf(" ")!=-1) {
        		url=httpHead.split(" ")[1];
        		method=httpHead.split(" ")[0];
        		System.out.println(url+"\n"+method);
        }
	}
	
	public String getUrl() {//获取URL
        return url;
    }

    public void setUrl(String url) {//编辑URL
        this.url = url;
    }

    public String getMethod() {//获取METHOD
        return method;
    }
    
    public void setMethod(String method) {//编辑METHOND
        this.method = method;
    }

	public String getParameter(String name) throws IOException {//读取post得到某字符后的数据
		String str="";
		String res="";
		str=httpRequest.toString();
			if(str.indexOf(name+"=")>-1) {
				String temp=str.substring(str.indexOf(name+"=")+name.length()+1);
				if(temp.indexOf("&")!=-1) {
					int n=str.indexOf("&");
					res=str=str.substring(str.indexOf(name+"=")+name.length()+1,n);
				}
				else {
					res=str=str.substring(str.indexOf(name+"=")+name.length()+1);
				}
			}
		return str;
	}

	
	 private static Map<String,String> map=new HashMap<> ();//存储某些数据时使用
	 
	 public void setAttribute(String str1, String str2) {//map添加数据
	       map.put (str1,str2);
	 }
	    
	 public Object getAttribute(String name){//map得到name对应的数据 
		 return map.get (name);	    
	 }
	
	public Enumeration getHeaderNames() {//获取头中的信息
		String[] list=httpRequest.toString().split("\n");
		Vector<String> vec=new Vector<String>();
		for(String str:list) {
			if(str.length()<2) break;
			String Header=str.split(":")[0];
			String text=str.substring(str.indexOf(" ")+1);
			map.put (Header,text);
			vec.add(Header);
		}
		return vec.elements();
	}

	 public String getHeader(String t){//读取map中的数据
		 if(map.get (t)!=null)
			 return (String) map.get(t);
		 return null;
	 }

	public RequestDispatcher getRequestDispatcher(String string) {//请求转发和请求包含
		RequestDispatcher RD=new RequestDispatcher(string);
		return RD;
	}

	public MyRequest getSession() {
		return this;
	}
}
