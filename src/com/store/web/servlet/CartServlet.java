package com.store.web.servlet;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.service.impl.ProductServiceImp;
import com.store.web.base.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/CartServlet")
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	//添加购物项到购物车
	public String addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//从session获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if(null==cart) {
			//如果获取不到，创建购物车对象，放在session中
			cart = new Cart();
			request.getSession().setAttribute("cart", cart);
		}
		
		//如果获取到，使用即可
		//获取到商品id,数量
		String pid = request.getParameter("pid");
		int num = Integer.parseInt(request.getParameter("quantity"));
		//通过商品id查询商品对象
		ProductService productService = new ProductServiceImp();
		Product product = productService.findProductByPid(pid);
		//获取待购买的购物项
		CartItem cartItem = new CartItem();
		cartItem.setNum(num);
		cartItem.setProduct(product);
		
		//调用购物车上的方法
		cart.addCartItemToCar(cartItem);
		//重定向到/jsp/cart.jsp
		response.sendRedirect("/store/jsp/cart.jsp");
		return null;
	}
	
	//删除购物项
	public String removeCartItem(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取待删除商品pid
		String pid = request.getParameter("id");
		//获取到购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//调用购物车删除购物项方法
		cart.removeCartItem(pid);
		//重定向到/jsp/cart.jsp
		response.sendRedirect("/store/jsp/cart.jsp");
		return null ;
	}
	
	//清空购物车
	public String clearCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//清空购物车中的商品
		cart.clearCart();
		//重定向
		response.sendRedirect("/store/jsp/cart.jsp");
		return null ;
	}
}
