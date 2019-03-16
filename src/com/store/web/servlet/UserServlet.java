package com.store.web.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.store.domain.User;
import com.store.service.UserService;
import com.store.service.impl.UserServiceImp;
import com.store.utils.MailUtils2;
import com.store.utils.MyBeanUtils;
import com.store.utils.UUIDUtils;
import com.store.web.base.BaseServlet;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {

	private static final long serialVersionUID = 1L;

	// 跳转到注册界面
	public String registUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/register.jsp";
	}

	// 跳转到登录界面
	public String loginUI(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/jsp/login.jsp";
	}

	// 接收表单数据、进行对象的封装、设置跳转界面
	public String userRegist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 接收表单数据
		Map<String, String[]> map = request.getParameterMap();
		// 封装成对象
		User user = new User();
		MyBeanUtils.populate(user, map);

		user.setState(0);
		user.setUid(UUIDUtils.getId());
		user.setCode(UUIDUtils.getCode());

		System.out.println(user);
		UserService userService = new UserServiceImp();
		int flag = userService.userRegist(user);
		// 注册成功
		if (flag == 1) {
			try {
				// 发送邮件
//				MailUtils.sendMail(user.getEmail(), user.getCode());
				MailUtils2.sendMail(user.getEmail(), "网上商城用户激活",
						"http://localhost:8080/store/UserServlet?method=active&code=" + user.getCode());
				System.out.println("发件成功");
			} catch (Exception e) {
				e.printStackTrace();
			}
			request.setAttribute("msg", "恭喜你，注册成功。");
		} else {
			// 注册失败
			request.setAttribute("msg", "注册失败，请重新注册。");
		}
		// 设置跳转的页面
		return "/jsp/info.jsp";
	}

	// 激活用户
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		String code = request.getParameter("code");
		UserService userService = new UserServiceImp();
		boolean flag = userService.userActive(code);
		if (flag == true) {
			request.setAttribute("msg", "恭喜您，激活成功，请登录。");
			path = "/jsp/login.jsp";
		} else {
			request.setAttribute("msg", "恭喜您，激活成功，请登录。");
			path = "/jsp/login.jsp";
		}
		return path;
	}

	// 激活用户
	public String userLogin(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = "";
		// 接收表单数据
		Map<String, String[]> map = request.getParameterMap();
		// 封装成对象
		User user = new User();
		MyBeanUtils.populate(user, map);
		
		UserService userService = new UserServiceImp();
		User user2 = null;
		try {
			//登录成功
			user2 = userService.userLogin(user);
			if(null!=user2) {
				request.getSession().setAttribute("loginUser", user2);
				response.sendRedirect("/store/index.jsp");
//				path = "/index.jsp" ;
				return null ;
			}
		} catch (Exception e) {
			//登录失败
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			path = "/jsp/login.jsp";
		} 
		return path;
	}

	//退出登录
	public String logOut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//清除session
		request.getSession().invalidate();
		//重新定向到首页
		response.sendRedirect("/store/index.jsp");
		return null ;
	}
}