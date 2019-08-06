package Server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;

import Servlet.MyServlet;

public class MyResponse {

    public OutputStream outputStream;//��Ϣ���
    public String fileHead="F:/Ecl/eclipse-workspace/WebServer/src/Res";//Ĭ�ϵ����ݴ洢��ַ
    
    public StringBuffer strBuf;//���������
    public String filelist="";
    
    public MyResponse(OutputStream outputStream,StringBuffer newstrBuf) {//���캯��
        this.outputStream=outputStream;
        this.strBuf=newstrBuf;
    }
    
    public OutputStream getOutputStream() {//��ȡ�����
    	return this.outputStream;
    }
    
    public void write(String fname)throws IOException{//�������Դд����Ŀ¼д��
    	DataOutputStream out = new DataOutputStream(outputStream);
    	out.writeBytes("HTTP-1.0 200 OK\r\n");
    	String where=fileHead+fname;
		System.out.println("looking for "+where);
		strBuf.append("looking for "+where+"\n");	
		/*
		if(fname.endsWith("favicon.ico")) {
			return;
		}
		*/
		if(fname.length()==1) {//����Ŀ¼
			//System.out.println("����Ŀ¼��");
			File f = new File(where);
			method(f);
			out.writeBytes("Content-type:text/html\r\n\r\n");
			out.writeBytes(filelist+"</body><html>");
			out.flush();
			out.close();
			return;
		}
		//�����ļ�
		String ContentType="";
		if(fname.endsWith("html")) {
			ContentType="Content-Type:text/html\r\n\r\n";
		}
		else if(fname.endsWith("jpg")) {
			ContentType="Content-Type:image/jpeg\r\n\r\n";
		}
		else if(fname.endsWith("png")) {
			ContentType="Content-Type:image/png\r\n\r\n";
		}
		else if(fname.endsWith("gif")) {
			ContentType="Content-Type:image/gif\r\n\r\n";
		}
		else if(fname.endsWith("css")) {
			ContentType="Content-Type:text/css\r\n\r\n";
		}
		else if(fname.endsWith("ico")) {
			ContentType="Content-Type:image/x-icon\r\n\r\n";
		}
		File f=new File(where);
		DataInputStream din=new DataInputStream(new FileInputStream(f));
		int len=(int)f.length();
		byte[]buf=new byte[len];
		din.readFully(buf);
		out.writeBytes("Content-Length:"+len+"\r\n");
		out.writeBytes(ContentType);
		out.write(buf);
		out.flush();
		out.close();
    }
    
	public void setFileHead(String str) {//�����ļ�·��
		this.fileHead=str;
	}
	
	public void method(File file) {//�ļ�Ŀ¼��ȡ�����
		if(file.isDirectory()) {
			File[] files = file.listFiles();
			System.out.println("��Դ�ļ�Ŀ¼��");
			for (File f : files) {
				if(f.isFile()) {
					System.out.println(f.getName());
					filelist=filelist+f.getName()+"<br/>";
				}
				else if(f.isDirectory()){
					method(f);
				}
			}
		}
	}
	
	public void sendRedirect(String PageName,MyRequest Req,MyResponse Res){
		String ClassName="Jsp."+PageName.substring(0,PageName.indexOf("."));
    	Class newClass;
		try {
			newClass = Class.forName(ClassName);
	        MyServlet Myclass=(MyServlet)newClass.newInstance();
	        Req.setMethod("GET");
	        System.out.println("sendRedirect:"+Req.getAttribute("username"));
	        Myclass.service(Req,Res);
		} catch (ClassNotFoundException | NoSuchMethodException | IOException | InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}

        
	}
}
