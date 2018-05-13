package com.car_rent.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.car_rent.beans.User;
import com.car_rent.service.UserService;

@RestController
@RequestMapping("/login")
public class LoginResource {
	
	@Autowired
	UserService service;
	
	@RequestMapping(value="/dologin",method=RequestMethod.POST,produces={"application/json"})
	public User doLigin(@RequestBody User user){
		if(StringUtils.isEmpty(user.getUsername())||StringUtils.isEmpty(user.getPassword())){
			return null;
		}
		
		user=service.doLogin(user);
		
		if(user!=null){
			return user;
		}
		
		return null;
	}

}
