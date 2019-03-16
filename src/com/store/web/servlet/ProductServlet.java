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

@WebServlet("/ProductServlet")
public class ProductServlet extends BaseServlet {
	
	private static final long serialVersionUID = 1L;
	
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pid = request.getParameter("pid");
		ProductService productService = new ProductServiceImp();
		Product product = productService.findProductByPid(pid);
		request.setAttribute("product", product);
		return "/jsp/product_info.jsp";
	}
	
	/**
	 * 	获得商品类别id(cid)和要请求的页数,
	 * 	请求service层，返回个pageMedel对象，将其放到request属性中
	 * @param request
	 * @param response
	 * @return 返回到商品列表页面
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findProductByCidWithPage(HttpServletRequest request , HttpServletResponse response) throws ServletException ,IOException {
		String cid = request.getParameter("cid");
		int curNum = Integer.parseInt(request.getParameter("num"));
		
		ProductService productService = new ProductServiceImp();
		PageModel<Product> pageModel = productService.findProductByCidWithPage(cid,curNum);
		request.setAttribute("page", pageModel);
		return "/jsp/product_list.jsp" ;
	}
	
}
