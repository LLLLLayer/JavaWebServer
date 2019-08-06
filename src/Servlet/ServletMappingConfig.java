package Servlet;

import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class ServletMappingConfig {
	public List<ServletMapping>servletMappings=new ArrayList<>(16);
	
	public void setServletMappingConfig() throws DocumentException {
			SAXReader saxreader=new SAXReader();
			Document doc =saxreader.read("src/web.xml");		
			Element root=doc.getRootElement();
			//System.out.println(root.getName());
			//System.out.println(root.attributeValue("version"));
			List<Element> child=root.elements();
			for(Element element:child){
				if("servlet".equals(element.getName())){
					Element displayname=element.element("display-name");
					//System.out.println(displayname.getText());
					Element servletname=element.element("servlet-name");
					//System.out.println(servletname.getText());
					Element servletclass=element.element("servlet-class");
					//System.out.println(servletclass.getText());
					servletMappings.add(new ServletMapping(displayname.getText(),
							servletname.getText(),servletclass.getText()));
				}
			}
	}
}

