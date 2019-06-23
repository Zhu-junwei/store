package com.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.store.dao.ProductDao;
import com.store.domain.Product;
import com.store.utils.CommonCRUDUtil;

public class ProductDaoImp implements ProductDao {

	@Override
	public List<Product> findHots() {
		String sql = "SELECT * FROM product WHERE pflag=0 AND is_hot=1 ORDER BY pdate DESC LIMIT 0,9";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public List<Product> findNews() {
		String sql = "SELECT * FROM product WHERE pflag=0 ORDER BY pdate DESC LIMIT 0,9";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public Product finProductByPid(String pid) {
		String sql = "Select * from product where pid=?";
		return CommonCRUDUtil.query(sql, new BeanHandler<Product>(Product.class), pid);
	}

	@Override
	public int findTotalRecords(String cid) {
		String sql = "select count(*) from product where cid=?";
		Long len = (long)CommonCRUDUtil.query(sql, new ScalarHandler<>(), cid); 
		return len.intValue();
	}
	
	@Override
	public int findTotalRecords() {
		String sql = "select count(*) from product";
		Long len = (long)CommonCRUDUtil.query(sql, new ScalarHandler<>()); 
		return len.intValue();
	}

	@Override
	public List<Product> findProductByCidWithPage(String cid, int startIndex, int pageSize) {
		String sql = "select * from product where cid=? limit ? , ?";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Product>(Product.class), cid,startIndex,pageSize);
	}

	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) {
		String sql = "select * from product order by pdate desc limit ? , ?";
		return CommonCRUDUtil.query(sql, new BeanListHandler<Product>(Product.class), startIndex,pageSize);
	}

	@Override
	public void saveProduct(Product product) {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		Object[] args = {
				product.getPid(),
				product.getPname(),
				product.getMarket_price(),
				product.getShop_price(),
				product.getPimage(),
				product.getPdate(),
				product.getIs_hot(),
				product.getPdesc(),
				product.getPflag(),
				product.getCid()
		};
		CommonCRUDUtil.update(sql, args);
	}

}
