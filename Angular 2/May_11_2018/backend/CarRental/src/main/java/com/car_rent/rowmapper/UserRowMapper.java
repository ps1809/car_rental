package com.car_rent.rowmapper;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.car_rent.beans.Address;
import com.car_rent.beans.User;

public class UserRowMapper implements RowMapper<User>{

	@Override
	public User mapRow(ResultSet rs, int colCount) throws SQLException {
		User user=new User();
		user.setId(rs.getString("id"));
		user.setCreatedOn(rs.getString("created_on"));
		user.setEmail(rs.getString("email"));
		user.setName(rs.getString("name"));
		user.setUsername(rs.getString("username"));
		user.setUpdatedOn(rs.getString("updated_on"));
		Address address=new Address();
		address.setAddressId(rs.getString("address_id"));
		address.setCity(rs.getString("city"));
		address.setCountry(rs.getString("country"));
		address.setLine1(rs.getString("line1"));
		address.setLine2(rs.getString("line2"));
		address.setState(rs.getString("state"));
		address.setPincode(rs.getInt("pincode"));
		user.setAddress(address);
		
		return user;
	}

}
