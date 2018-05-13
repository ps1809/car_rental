package com.car_rent.daoimpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.car_rent.beans.Address;
import com.car_rent.beans.User;
import com.car_rent.dao.UserDao;
import com.car_rent.rowmapper.UserRowMapper;
import com.car_rent.util.AppUtil;

@Repository
@PropertySource(value = { "classpath:db_queries.properties" })
public class UserDaoImpl implements UserDao {
	private Logger logger = Logger.getLogger(getClass());
	@Autowired
	Environment env;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public User createUser(User user) {
		User newUser = null;
		user.setCreatedOn(AppUtil.getDate());
		user.setUpdatedOn(AppUtil.getDate());
		logger.info(user);
		int i = jdbcTemplate.update(env.getProperty("QUERY.USER.CREATE_USER"), new Object[] { user.getName(),
				user.getUsername(), user.getEmail(), user.getPassword(), user.getCreatedOn(), user.getUpdatedOn() });
		if (i > 0) {
			newUser = jdbcTemplate.queryForObject(env.getProperty("QUERY.USER.GET_USER_BY_USERNAME"),
					new Object[] { user.getUsername() }, new UserRowMapper());
			user.setId(newUser.getId());
			boolean flag = addUserAddress(user);
			if (flag) {
				return newUser;
			} else {
				deleteUserById(newUser.getId());
				newUser = null;
			}

		}
		return newUser;
	}

	private String deleteUserById(String id) {
		Map<String, Object> map = new HashMap<>();
		map.put("userId", id);
		try {
			map.put("status", "unable to delete User");
			int i = jdbcTemplate.update(env.getProperty("QUERY.USER.DELETE_USER"), new Object[] { id });
			if (i > 0) {
				map.put("status", "User deleted successfully");
			}
		} catch (Exception e) {
			map.put("error", e.getMessage());
			logger.error(e.getMessage());
		}
		return AppUtil.getJsonString(map);
	}

	private boolean addUserAddress(User user) {
		int i = -1;
		try {
			i = jdbcTemplate.update(env.getProperty("QUERY.ADDRESS.ADD_ADDRESS"),
					new Object[] { user.getAddress().getLine1(), user.getAddress().getLine2(),
							user.getAddress().getPincode(), user.getAddress().getCity(), user.getAddress().getState(),
							user.getAddress().getCountry(), user.getId() });
		} catch (Exception e) {
			logger.error(e.getMessage());
			i = -1;
		}

		if (i > 0) {
			return true;
		}

		return false;
	}

	@Override
	public User getUserByUsername(String username) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(env.getProperty("QUERY.USER.GET_USER_BY_USERNAME"),
					new Object[] { username }, new UserRowMapper());
		} catch (Exception e) {
			logger.error(e);
		}
		return user;
	}

	@Override
	public User getUserById(String id) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(env.getProperty("QUERY.USER.GET_USER_BY_ID"), new Object[] { id },
					new UserRowMapper());
		} catch (Exception e) {
			logger.error(e);
		}

		return user;
	}

	@Override
	public User getUserByEmail(String email) {
		User user = null;
		try {
			user = jdbcTemplate.queryForObject(env.getProperty("QUERY.USER.GET_USER_BY_EMAIL"), new Object[] { email },
					new UserRowMapper());

		} catch (Exception e) {
			logger.error(e);
		}
		return user;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> userList = null;
		try {
			userList = jdbcTemplate.query(env.getProperty("QUERY.USER.GET_ALL_USERS"), new UserRowMapper());
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return userList;
	}

	public Address getAddressByUserId(String id) {
		Address address = null;
		try {
			address = jdbcTemplate.queryForObject(env.getProperty("QUERY.ADDRESS.GET_ADDRESS"), new Object[] { id },
					new BeanPropertyRowMapper<>(Address.class));
		} catch (Exception e) {
			logger.info(e.getMessage());
		}
		return address;
	}

	@Override
	public User doLogin(User user) {
		User userCheck=null;
		try{
			userCheck=jdbcTemplate.queryForObject(env.getProperty("QUERY.USER.USER_LOGIN"), 
					new Object[]{user.getUsername(),user.getPassword()},new UserRowMapper());
		}catch(Exception e){
			logger.error(e.getMessage());
		}
		return userCheck;
	}

}
