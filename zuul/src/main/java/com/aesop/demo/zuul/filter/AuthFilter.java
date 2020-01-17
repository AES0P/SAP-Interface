package com.aesop.demo.zuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.micrometer.core.instrument.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 为【网关路由】添加自定义过滤器
 * <p>
 * zuul过滤器有四种类型分别是：pre 路由前,route 路由时,post 路由完毕,error 发生错误时。
 */
public class AuthFilter extends ZuulFilter {

    @Override
    public String filterType() {
        return "pre"; // 在请求被路由之前调用
    }

    @Override
    public int filterOrder() {
        return 0; // filter执行顺序，通过数字指定 ,优先级为0，数字越大，优先级越低
    }

    @Override
    public boolean shouldFilter() {
        return true; // 是否执行该过滤器，此处为true，说明需要过滤
    }

    @Override
    public Object run() throws ZuulException {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        String token = request.getParameter("token");// 获取请求的参数

        // 如果有token参数并且token值为miniooc，才进行路由
        if (StringUtils.isNotBlank(token) && token.equals("aesop")) {
            ctx.setSendZuulResponse(true); //对请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("code", 1);
        } else {
            ctx.setSendZuulResponse(false); //不对其进行路由
            ctx.setResponseStatusCode(401);
            HttpServletResponse response = ctx.getResponse();
            response.setHeader("content-type", "text/html;charset=utf8");
            ctx.setResponseBody("网关认证失败，停止路由");
            ctx.set("code", 0);
        }
        return null;
    }
}
