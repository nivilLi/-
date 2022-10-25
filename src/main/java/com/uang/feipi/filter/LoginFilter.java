package com.uang.feipi.filter;

import com.alibaba.fastjson.JSON;
import com.uang.feipi.common.BaseContext;
import com.uang.feipi.common.R;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {

    {
        urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/user/sendMsg",
                "/user/login"};
    }

    private String[] urls;
    private static  final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String requestURI = request.getRequestURI();
        boolean checked = check(requestURI);
        if (checked == true){
            System.out.println("静态资源放行");
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
        Object employee = request.getSession().getAttribute("employee");
        Object user = request.getSession().getAttribute("user");
        if(employee == null && user == null){
            System.out.println("登录");
            servletResponse.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
            return;
        }
        if (employee != null)
        BaseContext.setCurrentId((Long)employee);
        if (user != null)
        BaseContext.setCurrentId((Long)user);
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public boolean check(String url){
        for (String url1 : urls) {
            boolean match = PATH_MATCHER.match(url1, url);
            if(match == true){
                return true;
            }
        }
        return false;
    }
}
