package sg.ic.pg.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter
public class ApplicationFilter implements Filter {

    private List<String> unprotectedList;

    @Override
    public void init(FilterConfig config) throws ServletException {
        unprotectedList = new ArrayList<>();
        unprotectedList.add("/");
        unprotectedList.add("/mobile/verify");
    }

    private boolean isProtectedPath(String path) {
        boolean protectedPath = true;

        for (String pathUrl : unprotectedList) {
            if (path.equalsIgnoreCase(pathUrl)) {
                protectedPath = false;
                break;
            }
        }
        return protectedPath;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        boolean valid = true;

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String path = request.getRequestURI().substring(request.getContextPath().length());

        if (path.contains("/lib/") || path.contains("/js/") || path.contains("/css/") || path.contains("/images/")
                || path.contains(".txt") || path.contains(".json") || path.contains(".js") || path.contains("/asset/")
                || path.contains("/rest/")) {
            chain.doFilter(servletRequest, servletResponse);
        } else {
            if (isProtectedPath(path)) {
                HttpSession session = request.getSession(false);
                if (session == null) {
                    valid = false;
                }
            }

            if (valid) {
                chain.doFilter(servletRequest, servletResponse);
            }
        }
    }

}
