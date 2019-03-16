package com.store.web.servlet;

import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImp;
import com.store.utils.PageModel;
import com.store.web.base.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminProductServlet")
public class AdminProductServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;

	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//取得当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用service层取得PageModel对象
		ProductService productService = new ProductServiceImp();
		PageModel<Product> pageModel = productService.findAllProductsWithPage(curNum);
		//将pageModel放入request中
		request.setAttribute("page", pageModel);
		//转发/admin/producr/list.jsp
		return "/admin/product/list.jsp" ;
	}
}
