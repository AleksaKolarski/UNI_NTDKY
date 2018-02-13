package ntdky;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class JspFilter
 */
public class JspFilter implements Filter {
    public JspFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void  doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException{
	    HttpServletResponse res= (HttpServletResponse) response;
	    res.sendRedirect("./PocetnaServlet");
	  }

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
