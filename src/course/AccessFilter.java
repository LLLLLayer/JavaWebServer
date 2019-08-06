package course;

import Server.*;
import Servlet.*;


public class AccessFilter extends Filter {
	public static int nNum=0; 

    public AccessFilter() {
    }

	public void destroy() {
	}

	public void doFilter(MyRequest request, MyResponse response, FilterChain chain){
		AccessFilter.nNum++;
		//System.out.println(AccessFilter.nNum);
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig){
	}

}
