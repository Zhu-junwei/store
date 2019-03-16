package com.store.web.servlet;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImp;
import com.store.web.base.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/IndexServlet")
public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//调用业务层功能：获取全部分类信息，返回集合
//		CategoryService categoryService = new CategoryServiceImp();
//		List<Category> list = categoryService.getAllCats();
		//返回的集合放入request
//		request.setAttribute("allCats", list);
		//转发到真实的首页
		
		//调用业务层查询最新商品，最热商品，返回两个集合
		ProductService productService = new ProductServiceImp();
		List<Product> list1 = productService.findHots();
		List<Product> list2 = productService.findNews();
		//将两个集合放到request
		request.setAttribute("hots", list1);
		request.setAttribute("news", list2);
		//跳转到真实的首页
		return "/jsp/index.jsp";
	}
}
