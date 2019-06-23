package com.store.dao;

import java.util.List;

import com.store.domain.Product;

public interface ProductDao {

	List<Product> findHots();

	List<Product> findNews();

	Product finProductByPid(String pid);

	int findTotalRecords(String cid);

	List<Product> findProductByCidWithPage(String cid, int startIndex, int pageSize);

	int findTotalRecords();

	List<Product> findAllProductsWithPage(int startIndex, int pageSize);

	void saveProduct(Product product);



}
