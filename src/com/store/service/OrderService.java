package com.store.service;

import com.store.domain.Order;
import com.store.domain.User;
import com.store.utils.PageModel;

public interface OrderService {

	void saveOrder(Order order);

	PageModel<Order> findMyOrdersWithPage(User user, int curNum);

	Order findOrderByOid(String oid);

}
