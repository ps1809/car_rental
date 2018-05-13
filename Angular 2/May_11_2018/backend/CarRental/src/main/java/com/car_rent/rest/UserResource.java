package com.car_rent.rest;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.car_rent.beans.User;
import com.car_rent.service.UserService;

@RestController
@RequestMapping("/user")
public class UserResource {
	private Logger logger=Logger.getLogger(getClass());
	@Autowired
	UserService service;
	
	@RequestMapping(value="/create",method=RequestMethod.POST,produces={"application/json"})
	public ResponseEntity<User> createUser(@RequestBody User user){
		logger.info("Creating User account for "+user.getName());
		User newUser=service.createUser(user);
		if(newUser==null){
			return new ResponseEntity<User>( HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(value="/getbyusername/{username}",method=RequestMethod.GET)
	public ResponseEntity<User> getUserByUsername(@PathVariable("username")String username){
		User newUser=service.getUserByUsername(username);
		if(newUser==null){
			return new ResponseEntity<User>( HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getbyid/{id}",method=RequestMethod.GET)
	public ResponseEntity<User> getUserById(@PathVariable("id")String username){
		User newUser=service.getUserById(username);
		if(newUser==null){
			return new ResponseEntity<User>( HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getbyemail",method=RequestMethod.GET,produces={"application/json"})
	public ResponseEntity<User> getUserByEmail(@RequestParam("email")String email){
		User newUser=service.getUserByEmail(email);
		if(newUser==null){
			return new ResponseEntity<User>( HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(newUser, HttpStatus.OK);
	}
	
	@RequestMapping(value="/getAll",method=RequestMethod.GET)
	public ResponseEntity<List<User>> getAllUsers() throws InterruptedException{
		List<User> userList=new ArrayList<>();
		userList=service.getAllUsers();
		Thread.sleep(3000);
		if(userList==null){
			return new ResponseEntity<List<User>>( HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<List<User>>(userList, HttpStatus.OK);
	}
	

}
