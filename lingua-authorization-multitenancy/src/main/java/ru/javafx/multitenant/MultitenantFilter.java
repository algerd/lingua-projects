
package ru.javafx.multitenant;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MultitenantFilter implements Filter {
    
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {      
        MultitenantContext.setMultitenantFlag(true);
        chain.doFilter(request, response);
        
        logger.info("RequestFilter path: " + ((HttpServletRequest)request).getServletPath());
    }

    @Override
    public void destroy() {
    }
    

}
