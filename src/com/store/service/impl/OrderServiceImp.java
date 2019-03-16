package com.store.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.store.dao.OrderDao;
import com.store.dao.impl.OrderDaoImp;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.User;
import com.store.service.OrderService;
import com.store.utils.DBCPUtil;
import com.store.utils.PageModel;

public class OrderServiceImp implements OrderService{
	OrderDao orderDao = new OrderDaoImp();
	@Override
	public void saveOrder(Order order) {
		
//		try {
//			DBCPUtil.startTransaction();
//			OrderDao orderDao = new OrderDaoImp();
//			orderDao.saveOrder(order);
//			for (OrderItem item: order.getList()) {
//				orderDao.saveOrderItem(item);
//			}
//			DBCPUtil.commitAndClose();
//		} catch (SQLException e) {
//			DBCPUtil.rollbackAndClose();
//		}
		
		Connection conn = null ;
		try {
			//获取连接
			conn = DBCPUtil.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			
			orderDao.saveOrder(conn,order);
			//保存订单项
			for (OrderItem item : order.getList()) {
				orderDao.saveOrderItem(conn,item);
			}
			//提交
			conn.commit();
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} 
	}

	@Override
	public PageModel<Order> findMyOrdersWithPage(User user, int curNum) {
		//1、创建PageModel对象，目的：计算并且携带分页参数
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel<Order> pm = new PageModel<>(curNum, 3, totalRecords);
		//2、关联集合
		List<Order> list = orderDao.findMyOrdersWithPage(user,pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3、关联url
		pm.setUrl("OrderServlet?method=findMyOrdersWithPage");
		return pm;
	}

	@Override
	public Order findOrderByOid(String oid) {
		
		return orderDao.findOrderByOid(oid);
	}

}
