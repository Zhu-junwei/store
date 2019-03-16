package com.store.service.impl;

import com.store.dao.UserDao;
import com.store.dao.impl.UserDaoImp;
import com.store.domain.User;
import com.store.service.UserService;

public class UserServiceImp implements UserService {

	@Override
	public User userLogin(User user) {
		UserDao userDao = new UserDaoImp();
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
		UserDao userDao = new UserDaoImp();
		return userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) {
		boolean flag = false ;
		UserDao userDao = new UserDaoImp();
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
