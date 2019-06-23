package com.store.service.impl;

import com.store.dao.UserDao;
import com.store.domain.User;
import com.store.service.UserService;
import com.store.utils.BeanFactory;

public class UserServiceImp implements UserService {

	UserDao userDao = (UserDao) BeanFactory.createObject("UserDao");
	
	@Override
	public User userLogin(User user) {
		User userReturn = userDao.userLogin(user);
		if(null == userReturn) {
			throw new RuntimeException("用户名或密码错误");
		}else if(userReturn.getState()==0){
			throw new RuntimeException("用户名未激活");
		}
		return userReturn ;
	}

	@Override
	public int userRegist(User user) {
		return userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) {
		boolean flag = false ;
		User user = userDao.userActive(code);
		//有此用户
		if(null != user) {
			user.setState(1);
			user.setCode(null);
			int len = userDao.updateUser(user);
			if(len == 1) {
				flag = true;
			}
			
		}
		//无此用户,不进行操作
		return flag;
	}



}
