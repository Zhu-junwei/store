package com.store.service;

import java.util.List;

import com.store.domain.Product;
import com.store.utils.PageModel;

public interface ProductService {

	List<Product> findHots();

	List<Product> findNews();

	Product findProductByPid(String pid);

	PageModel<Product> findProductByCidWithPage(String cid, int curNum);

	PageModel<Product> findAllProductsWithPage(int curNum);

}
