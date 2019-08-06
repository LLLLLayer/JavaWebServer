package Servlet;
import java.awt.print.Printable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import Jsp.JspSupport;
import Server.*;

public class FilterChain {

	public HashMap<String,String>FilterMapping=new HashMap<String,String>();
	Vector<String> filterClass=new Vector<String>();
	int allnum=0;
	int num=0;
	
	public String nowFilter() {
		if(num<allnum) {
			num++;
			return filterClass.get(num-1);
		}
		return " ";
	}
	
	public void doFilter(MyRequest request, MyResponse response) {
		SAXReader saxreader=new SAXReader();
		Document doc;
		try {
			doc = saxreader.read("src/web.xml");
			Element root=doc.getRootElement();
			List<Element> child=root.elements();
			for(Element element:child){
				if("filter".equals(element.getName())){
					Element filtername=element.element("filter-name");
					System.out.println(filtername.getText());
					Element filterclass=element.element("filter-class");
					System.out.println(filterclass.getText());
					FilterMapping.put(filtername.getText(),filterclass.getText());
					filterClass.add(filterclass.getText());
					allnum++;
					System.out.println(allnum);
					}
				}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}
}
