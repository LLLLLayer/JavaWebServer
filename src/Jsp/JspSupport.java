package Jsp;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import Server.MyRequest;
import Server.MyResponse;
import Servlet.MyServlet;

//读取jsp文件，写出同包的其他类
public class JspSupport {
	String className="";//对象属于的类名
	String method="";
	String othermethod="";
	public JspSupport(String url,String newmethod) throws IOException {
		System.out.println(newmethod);
		
		if(newmethod.length()==4) {
			method="doPost";
			othermethod="doGet";
		}
		else {
			method="doGet";
			othermethod="doPost";
		}
		
		className="Jsp."+url.substring (url.lastIndexOf ("/")+1,url.lastIndexOf ("."));	
		FileReader reader = new FileReader(url);
        BufferedReader br = new BufferedReader(reader);
        String line;
        String file="";//存放所有文件内容       
        while ((line = br.readLine()) != null) {
        	file+=line+"\n";
        }
        file=file.replace("\"", "\\\"");	
        System.out.print("src/Jsp/"+url.substring (url.lastIndexOf ("/")+1,url.lastIndexOf ("."))+".java"); 
        FileWriter fileWriter = new FileWriter("src/Jsp/"+url.substring (url.lastIndexOf ("/")+1,url.lastIndexOf ("."))+".java");
        String exc="";       
        String head="package Jsp;\n" +
                "import java.io.*;\n\n" +
                "import java.io.File;\n" +
                "import java.io.FileInputStream;\n" +
                "import java.io.IOException;\n" +
                "import java.io.OutputStream;\n" +
                "import Server.MyRequest;\n" + 
                "import Server.MyResponse;\n"+
                "import Servlet.MyServlet;\n"+
                "public class "+url.substring (url.lastIndexOf ("/")+1,url.lastIndexOf ("."))+" extends MyServlet {\n"+
                "public void "+ 
                method+ 
                "(MyRequest request, MyResponse resp)"+
                "{\n";
        fileWriter.write(head+"\n");        
        fileWriter.write("PrintWriter out = new PrintWriter(resp.outputStream);\n");
        fileWriter.write("out.println(\"HTTP-1.0 200 OK\\r\\n\");\n");

        if(file.indexOf("<html>")!=-1) {        	
        	if((file.indexOf("<html>")-1)>0){
        		file=file.substring(file.indexOf("<html>")-1);
        	}
        	else {
        		file=file.substring(file.indexOf("<html>"));
        	}
        }
        //System.out.println(file);
        
        if(file.indexOf("<%=")!=-1) {
        	boolean writeTry=false;
			if(file.indexOf("getParameter")!=-1) {
	        	fileWriter.write("try {\n");
	        	writeTry=true;
			}
        	String[]list=file.split("\n");
        	for(String str:list) {
        		if(str.indexOf("<%=")==-1) {
        			fileWriter.write("out.println(\"" + str + "\"" + ");\n");
        		}
        		else {
        			str=str.replace("\\\"", "\"");
        			String currentStr=str;
        			while(currentStr.indexOf("<%=")!=-1) {
        				if(currentStr.indexOf("<%=")>0) {
        					fileWriter.write("out.println(\"" + currentStr.substring(0, currentStr.indexOf("<%=")) + "\"" + ");\n");
        					currentStr=currentStr.substring(currentStr.indexOf("<%=")+3);
        				}
        				fileWriter.write("out.println(" + currentStr.substring(0, currentStr.indexOf("%>")) + ");\n");
        				currentStr=currentStr.substring(currentStr.indexOf("%>")+2);
        			}
        			fileWriter.write("out.println(\"" + currentStr + "\"" + ");\n");
        		}
        	}
        	if(writeTry==true) {
        		fileWriter.write("} catch (IOException e) {\r\n" + 
        				"	e.printStackTrace();\r\n" + 
        				"}");
        	}
        }
        else {
			while (file.lastIndexOf("%>") != -1) {
				//i=file.indexOf ("<%");
				String temp = file.substring(0, file.indexOf("<%"));
				while (temp.indexOf("\n") != -1) {
					fileWriter.write("out.println(\"" + temp.substring(0, temp.indexOf("\n")) + "\"" + ");\n");
					temp = temp.substring(temp.indexOf("\n") + "\n".length());
				}
				String e = file.substring(file.indexOf("<%") + 2, file.indexOf("%>"));
				if (e.indexOf("=") == 0)
					fileWriter.write("out.println(" + e.substring(1) + ");\n");
				else {
					e = e.replace("\\\"", "\"");
					fileWriter.write(e + "\n");
				}
				file = file.substring(file.indexOf("%>") + 2);
			}
        
			if(!file.equals (null)){
				String temp=file;
				while (temp.indexOf ("\n")!=-1){
					fileWriter.write("out.println(\""+temp.substring(0,temp.indexOf("\n")-1)+"\""+");\n");
					temp=temp.substring(temp.indexOf("\n")+"\n".length());
				}
			}
        }
        fileWriter.write("out.flush();\nout.close();\n");
        
        fileWriter.write("}\n"+"\r\n" + 
        		"protected void "+ 
        		othermethod+ 
        		"(MyRequest request, MyResponse resp)"+
        		"{}\n"+"}");
        fileWriter.flush ();
        fileWriter.close ();
	}
	
	public String getClassname(){
		return className;
	}
	
	public static void main(String[] args) throws IOException {
		JspSupport jsp=new JspSupport("F:/Ecl/eclipse-workspace/WebServer/src/Res/test6.jsp","GET");
	}

}
