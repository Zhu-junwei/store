package com.store.web.servlet;

import com.store.domain.Cart;
import com.store.domain.CartItem;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.service.impl.OrderServiceImp;
import com.store.utils.PageModel;
import com.store.utils.UUIDUtils;
import com.store.web.base.BaseServlet;
import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/OrderServlet")
public class OrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = new OrderServiceImp();

	/**
	 *   保存订单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String saveOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//确认用户登录状态
		User user = (User)request.getSession().getAttribute("loginUser");
		if(null == user) {
			request.setAttribute("msg", "请登录后再下单");
			return "/jsp/order_info.jsp" ;
		}
		//获取购物车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//创建订单对象，为订单对象赋值
		Order order = new Order();
		order.setOid(UUIDUtils.getCode());
		order.setOrdertime(new Date());
		order.setTotal(cart.getTotal());
		order.setState(1);
		order.setUser(user);
		//遍历购物项的同时，创建订单项，为订单项赋值
		for (CartItem item: cart.getCartItems()) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(UUIDUtils.getCode());
			orderItem.setQuantity(item.getNum());
			orderItem.setTotal(item.getSubTotal());
			orderItem.setProduct(item.getProduct());
			
			orderItem.setOrder(order);
			order.getList().add(orderItem);
		}
		
		//调用service层，保存订单
		orderService.saveOrder(order);
		//清空购物车
		cart.clearCart();
		//将订单放入request
		request.setAttribute("order", order);
		//转发/jsp/order_info.jsp
		return "/jsp/order_info.jsp" ;
	}

	/**
	 * 以分页的形式返回订单信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String findMyOrdersWithPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用用户信息
		User user = (User)request.getSession().getAttribute("loginUser");
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用service层功能：查询当前用户信息，返回PageModel
		PageModel<Order> pm = orderService.findMyOrdersWithPage(user,curNum);
		//将PageModel放入request
		request.setAttribute("page", pm);
		//转发/jsp/order_list.jsp
		return "/jsp/order_list.jsp" ;
	}
	public String findOrderByOid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String oid = request.getParameter("oid");
		Order order = orderService.findOrderByOid(oid);
		request.setAttribute("order", order);
		//转发/jsp/order_info.jsp
		return "/jsp/order_info.jsp" ;
	}
}
