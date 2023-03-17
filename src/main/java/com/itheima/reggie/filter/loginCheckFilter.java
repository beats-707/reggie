package com.itheima.reggie.filter;

import com.alibaba.fastjson.JSON;
import com.itheima.reggie.common.BaseContext;
import com.itheima.reggie.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
public class loginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        Long id = Thread.currentThread().getId();
        log.info("过滤器线程{}",id);


        HttpServletRequest req= (HttpServletRequest) request;
        HttpServletResponse res= (HttpServletResponse) response;

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**",
                "/common/**"

        };
        String requestURI = req.getRequestURI();

        if(check(urls,requestURI)){
            log.info("本次请求{}不需要处理",requestURI);
            chain.doFilter(request, response);
            return;
        }
        if(req.getSession().getAttribute("employee") != null){
            log.info("用户登录了");
            Long empolyee = (Long) req.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empolyee);
            chain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");
        log.info("本次拦截：",requestURI);


        res.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
        return;

    }

    /**
     * 路径检测
     * @param urls
     * @param requestURL
     * @return
     */
    public boolean check( String[] urls,String requestURL) {
        boolean match = false;
        for (String url : urls) {
            match = PATH_MATCHER.match(url, requestURL);
            if (match) {
                return match;
            }
        }
        return match;
    }
}
