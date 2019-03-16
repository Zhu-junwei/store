package com.store.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.store.dao.CategoryDao;
import com.store.domain.Category;
import com.store.utils.CommonCRUDUtil;

public class CategoryDaoImp implements CategoryDao {

	//查询所有分类信息
	@Override
	public List<Category> getAllCats() {
		String sql = "select * from category" ;
		return CommonCRUDUtil.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category c) {
		String sql = "insert into category values(?,?)" ;
		CommonCRUDUtil.update(sql, c.getCid(),c.getCname());
	}

}
