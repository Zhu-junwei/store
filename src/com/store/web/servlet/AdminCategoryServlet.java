package com.store.web.servlet;

import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.service.impl.CategoryServiceImp;
import com.store.utils.UUIDUtils;
import com.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminCategoryServlet")
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		CategoryService categoryService = new CategoryServiceImp();
		List<Category> allCats = categoryService.getAllCats();
		request.setAttribute("allCats", allCats);
		return "/admin/category/list.jsp";
	}
	
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/admin/category/add.jsp";
	}
	
	public String addCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取分类名称
		String cname=request.getParameter("cname");
		//创建分类ID
		String id=UUIDUtils.getId();
		Category c=new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		CategoryService CategoryService=new CategoryServiceImp();
		CategoryService.addCategory(c);
		//重定向到查询全部分类信息
		response.sendRedirect("/store/AdminCategoryServlet?method=findAllCats");
		return null ;
	}
	
}
