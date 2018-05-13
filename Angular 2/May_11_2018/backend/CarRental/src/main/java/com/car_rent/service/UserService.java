package com.car_rent.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.car_rent.beans.User;

@Component
public interface UserService {
	
	public User createUser(User user);

	public User getUserByUsername(String username);

	public User getUserById(String username);

	public User getUserByEmail(String email);

	public List<User> getAllUsers();

	public User doLogin(User user);

}
