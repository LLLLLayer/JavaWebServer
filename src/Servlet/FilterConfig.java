package Servlet;
import java.util.Enumeration;
import Server.*;

public interface FilterConfig {
	 public String getFilterName();
	 //public ServletContext getServletContext();
	 public String getInitParameter(String name);
	 public Enumeration<String> getInitParameterNames();
}
