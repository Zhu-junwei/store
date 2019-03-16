package com.store.service;

import com.store.domain.User;

public interface UserService {

	int userRegist(User user);

	boolean userActive(String code);

	User userLogin(User user);

}
