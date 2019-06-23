package com.store.service.impl;

import java.util.List;

import com.store.dao.CategoryDao;
import com.store.domain.Category;
import com.store.service.CategoryService;
import com.store.utils.BeanFactory;
import com.store.utils.JedisUtil;

import redis.clients.jedis.Jedis;

public class CategoryServiceImp implements CategoryService {

    CategoryDao categoryDao = (CategoryDao)BeanFactory.createObject("CategoryDao");
    
	@Override
	public List<Category> getAllCats() {
		return categoryDao.getAllCats();
		
	}

	@Override
	public void addCategory(Category c) {
		categoryDao.addCategory(c);
		
		//更新redis缓存
		Jedis jedis = JedisUtil.getJedis();
		jedis.del("allCats");
		JedisUtil.close(jedis);
	}
	
}
