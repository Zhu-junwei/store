package com.store.service.impl;

import java.util.List;

import com.store.dao.ProductDao;
import com.store.domain.Product;
import com.store.service.ProductService;
import com.store.utils.BeanFactory;
import com.store.utils.PageModel;

public class ProductServiceImp implements ProductService {

	ProductDao productdao = (ProductDao) BeanFactory.createObject("ProductDao");
	
	@Override
	public List<Product> findHots() {
		return productdao.findHots();
	}

	@Override
	public List<Product> findNews() {
		return productdao.findNews();
	}

	@Override
	public Product findProductByPid(String pid) {
		return productdao.finProductByPid(pid);
	}

	
	/**
	 * 将商品类别和当前页传递给dao层
	 * 将
	 */
	@Override
	public PageModel<Product> findProductByCidWithPage(String cid, int curNum) {
		//创建pageModel对象
		//取得所有的记录数
		int totalRecords = productdao.findTotalRecords(cid);
		PageModel<Product> pageModel = new PageModel<Product>(curNum, 12, totalRecords);
		//关联集合
		List<Product> list = productdao.findProductByCidWithPage(cid,pageModel.getStartIndex() , pageModel.getPageSize());
		pageModel.setList(list);
		//关联url
		pageModel.setUrl("ProductServlet?method=findProductByCidWithPage&cid="+cid);
		return pageModel;
	}

	@Override
	public PageModel<Product> findAllProductsWithPage(int curNum) {
		//1_创建对象
		int totalRecords=productdao.findTotalRecords();
		PageModel<Product> pm=new PageModel<>(curNum,5,totalRecords);
		//2_关联集合 select * from product limit ? , ?
		List<Product> list=productdao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setList(list);
		//3_关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) {
		productdao.saveProduct(product);
		
	}

}
