package com.store.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.OrderDao;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.domain.Product;
import com.store.domain.User;
import com.store.utils.CommonCRUDUtil;

public class OrderDaoImp implements OrderDao {

	@Override
	public void saveOrder(Order order) {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] args = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()}; 
		CommonCRUDUtil.update(sql, args);
	}

	@Override
	public void saveOrderItem(OrderItem item) {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] args = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()}; 
		CommonCRUDUtil.update(sql, args);
	}

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		Object[] args = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()}; 
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(conn, sql, args);
//		CommonCRUDUtil.update(sql, args);
		
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object[] args = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()}; 
		QueryRunner queryRunner = new QueryRunner();
		queryRunner.update(conn, sql, args);
//		CommonCRUDUtil.update(sql, args);
	}

	@Override
	public int getTotalRecords(User user) {
		String sql = "select count(*) from orders where uid=?";
		Long len = (Long)CommonCRUDUtil.query(sql, new ScalarHandler<>(), user.getUid());
		return len.intValue();
	}

	@Override
	public List<Order> findMyOrdersWithPage(User user, int startIndex, int pageSize){
		String sql = "select * from orders where uid=? limit ?,?";
		List<Order> list = CommonCRUDUtil.query(sql, new BeanListHandler<Order>(Order.class), user.getUid(),startIndex,pageSize);
		
		//遍历所有订单
		for (Order order : list) {
			//获取到每笔订单oid   查询每笔订单下的订单项以及订单项对应的商品信息
			String oid=order.getOid();
			sql="select * from orderItem o ,product p where o.pid=p.pid and oid=?";
			List<Map<String, Object>> list02 = CommonCRUDUtil.query(sql, new MapListHandler(), oid);
			
			//遍历list
			for (Map<String, Object> map : list02) {
				OrderItem orderItem=new OrderItem();
				Product product=new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
				
				try {
					//将map中属于orderItem的数据自动填充到orderItem对象上
					BeanUtils.populate(orderItem, map);
					//将map中属于product的数据自动填充到product对象上
					BeanUtils.populate(product, map);
				} catch (Exception e) {
					System.out.println("啊哦，出错了");
					e.printStackTrace();
				} 
				
				//让每个订单项和商品发生关联关系
				orderItem.setProduct(product);
				//将每个订单项存入订单下的集合中
				order.getList().add(orderItem);
				
			}
		}
		return list;
	}

	@Override
	public Order findOrderByOid(String oid) {
		String sql = "select * from orders where oid=?";
		Order order = CommonCRUDUtil.query(sql, new BeanHandler<Order>(Order.class), oid);
		
		sql = "select * from orderitem o , product p where o.pid=p.pid and oid=?";
		
		List<Map<String, Object>> list02 = CommonCRUDUtil.query(sql, new MapListHandler(), oid);
		//遍历list
		for (Map<String, Object> map : list02) {
			OrderItem orderItem=new OrderItem();
			Product product=new Product();
			// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
			// 1_创建时间类型的转换器
			DateConverter dt = new DateConverter();
			// 2_设置转换的格式
			dt.setPattern("yyyy-MM-dd");
			// 3_注册转换器
			ConvertUtils.register(dt, java.util.Date.class);
			
			try {
				//将map中属于orderItem的数据自动填充到orderItem对象上
				BeanUtils.populate(orderItem, map);
				//将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);
			} catch (Exception e) {
				System.out.println("啊哦，出错了");
				e.printStackTrace();
			} 
			
			//让每个订单项和商品发生关联关系
			orderItem.setProduct(product);
			//将每个订单项存入订单下的集合中
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public List<Order> findOrders() {
		String sql = "select * from orders";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Order>(Order.class));
	}

	@Override
	public List<Order> findOrders(String state) {
		String sql = "select * from orders where state=?";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Order>(Order.class),state);
	}

}
