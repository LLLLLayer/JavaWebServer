package Server;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class MyRequest {
	
	private String url;//��ȡ�������Դ
	private String method;//��ȡGETorPOST
	public StringBuilder httpRequest;//��ȡ����������
	public StringBuffer strBuf;//�ṩ��������ʾ
	
	MyRequest(StringBuilder newhttpRequest) {
		httpRequest=newhttpRequest;
	}
	
	public MyRequest(InputStream inputStream,StringBuffer newstrBuf){//��ȡHTTPͷ
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
        //��ȡͷ�е�url��method
        String httpHead=httpRequest.toString().split("\n")[0];
        	if(httpHead.indexOf(" ")!=-1) {
        		url=httpHead.split(" ")[1];
        		method=httpHead.split(" ")[0];
        		System.out.println(url+"\n"+method);
        }
	}
	
	public String getUrl() {//��ȡURL
        return url;
    }

    public void setUrl(String url) {//�༭URL
        this.url = url;
    }

    public String getMethod() {//��ȡMETHOD
        return method;
    }
    
    public void setMethod(String method) {//�༭METHOND
        this.method = method;
    }

	public String getParameter(String name) throws IOException {//��ȡpost�õ�ĳ�ַ��������
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

	
	 private static Map<String,String> map=new HashMap<> ();//�洢ĳЩ����ʱʹ��
	 
	 public void setAttribute(String str1, String str2) {//map�������
	       map.put (str1,str2);
	 }
	    
	 public Object getAttribute(String name){//map�õ�name��Ӧ������ 
		 return map.get (name);	    
	 }
	
	public Enumeration getHeaderNames() {//��ȡͷ�е���Ϣ
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

	 public String getHeader(String t){//��ȡmap�е�����
		 if(map.get (t)!=null)
			 return (String) map.get(t);
		 return null;
	 }

	public RequestDispatcher getRequestDispatcher(String string) {//����ת�����������
		RequestDispatcher RD=new RequestDispatcher(string);
		return RD;
	}

	public MyRequest getSession() {
		return this;
	}
}
