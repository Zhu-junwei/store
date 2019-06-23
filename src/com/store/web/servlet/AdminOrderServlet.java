package com.store.web.servlet;

import com.store.domain.Order;
import com.store.service.OrderService;
import com.store.service.impl.OrderServiceImp;
import com.store.web.base.BaseServlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AdminOrderServlet")
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	OrderService orderService = new OrderServiceImp();

	public String findOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String state = request.getParameter("state");
		List<Order> list = null ;
		if(null==state|"".equals(state)) {
			//查询全部订单
			list = orderService.findOrders();
		} else {
			//查询各类别订单
			list = orderService.findOrders(state);
		}
		request.setAttribute("allOrders", list);
		return "/admin/order/list.jsp" ;
	}
}
