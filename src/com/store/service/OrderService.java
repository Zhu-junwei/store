package com.store.service;

import java.util.List;

import com.store.domain.Order;
import com.store.domain.User;
import com.store.utils.PageModel;

public interface OrderService {

	void saveOrder(Order order);

	PageModel<Order> findMyOrdersWithPage(User user, int curNum);

	Order findOrderByOid(String oid);

	List<Order> findOrders();

	List<Order> findOrders(String state);

}
