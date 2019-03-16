package com.store.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import com.store.domain.User;

/**
 * Servlet Filter implementation class PriviledgeFilter
 */
@WebFilter(
		{ "/jsp/order_list.jsp", 
		  "/jsp/order_info.jsp" ,
		  "/jsp/cart.jsp"})

/**
  *  权限过滤器，用来阻止用户在没有登录状态下访问某些网页
  *  如果没有登陆，跳转到提示页面，提示用户登录
  *  如果用户已经登录，则放行
 * @author zhujunwei
 * 2019年3月13日 下午8:28:20
 */
public class PriviledgeFilter implements Filter {
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest)request;
		User user = (User)httpServletRequest.getSession().getAttribute("loginUser");
		if(null != user) {
			//已登录，放行
			chain.doFilter(request, response);
		} else {
			//未登录，提示登录
			httpServletRequest.setAttribute("msg", "您还没有登录，请登录后访问");
			httpServletRequest.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
	}
}
