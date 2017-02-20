package lt.itakademija.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by lenovo on 2/20/2017.
 */
public class MyLoggerFilter extends HttpFilter {

    @Autowired
    RequestFormatter requestFormatter;

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        Logger logger = LoggerFactory.getLogger(MyLoggerFilter.class);
        logger.info(requestFormatter.formatForLog(request));
        chain.doFilter(request, response);
    }
}
