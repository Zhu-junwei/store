package com.store.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.User;

public interface OrderDao {

	void saveOrder(Order order);

	void saveOrderItem(OrderItem item);

	void saveOrder(Connection conn, Order order) throws SQLException;

	void saveOrderItem(Connection conn, OrderItem item) throws SQLException;

	int getTotalRecords(User user);

	List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize);

	Order findOrderByOid(String oid);

	List<Order> findOrders();

	List<Order> findOrders(String state); 

}
