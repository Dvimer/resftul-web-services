package com.in28minutes.rest.webservices.restfulwebservices.dao;

import com.in28minutes.rest.webservices.restfulwebservices.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UserDAO
{
	private List<User> users = new ArrayList<>();

	private static int userCount = 5;

	@PostConstruct
	public void userInit()
	{
		users.add(new User(1, "Lexa", new Date()));
		users.add(new User(2, "Eva", new Date()));
		users.add(new User(3, "Leon", new Date()));
		users.add(new User(4, "Dimon", new Date()));
		users.add(new User(5, "Vasya", new Date()));
	}

	public List<User> findAll()
	{
		return users;
	}

	public User save(User user)
	{
		if (user.getId() == null)
		{
			user.setId(++userCount);
		}
		users.add(user);
		return user;
	}

	public User findOne(int userId)
	{
		for (User user : users)
		{
			if (user.getId() == userId)
			{
				return user;
			}
		}
		return null;
	}

	public User deleteById(int userId)
	{
		for (User user : users)
		{
			if (user.getId() == userId) ;
			users.remove(user);
			return user;
		}
		return null;
	}
}
