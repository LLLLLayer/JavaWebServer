package Servlet;
import Server.*;

public abstract  class Filter {
	public abstract void init(FilterConfig filterConfig);
	public abstract void doFilter(MyRequest request, MyResponse response,
            FilterChain chain);
	public abstract void destroy();
}
