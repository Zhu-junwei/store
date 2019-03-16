package com.store.dao;

import com.store.domain.User;

public interface UserDao {

	int userRegist(User user);

	User userActive(String code);

	int updateUser(User user);

	User userLogin(User user);

}
