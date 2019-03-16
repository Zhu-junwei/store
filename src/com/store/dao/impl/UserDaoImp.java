package com.store.dao.impl;

import org.apache.commons.dbutils.handlers.BeanHandler;

import com.store.dao.UserDao;
import com.store.domain.User;
import com.store.utils.CommonCRUDUtil;

public class UserDaoImp implements UserDao {

	@Override
	public int userRegist(User user) {
		int flag = 0 ;
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		Object[] args = {
				user.getUid(),
				user.getUsername(),
				user.getPassword(),
				user.getName(),
				user.getEmail(),
				user.getTelephone(),
				user.getBirthday(),
				user.getSex(),
				user.getState(),
				user.getCode()
		};
		flag = CommonCRUDUtil.update(sql, args);
		return flag;
	}

	@Override
	public User userActive(String code) {
		String sql = "select * from user where code=? ";
		return CommonCRUDUtil.query(sql, new BeanHandler<User>(User.class), code);
	}

	@Override
	public int updateUser(User user) {
		String sql = "update user set state=? ,code = ? where uid=?";
		return CommonCRUDUtil.update(sql, user.getState(),user.getCode(),user.getUid());
	}

	@Override
	public User userLogin(User user) {
		String sql = "select * from user where username=? and password=? ";
		return CommonCRUDUtil.query(sql, new BeanHandler<User>(User.class), user.getUsername(),user.getPassword());
	}
}
