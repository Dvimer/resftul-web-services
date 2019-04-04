package com.in28minutes.rest.webservices.restfulwebservices.rest;

import com.in28minutes.rest.webservices.restfulwebservices.dao.UserDAO;
import com.in28minutes.rest.webservices.restfulwebservices.entity.User;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleContextResolver;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Locale;

@RestController
public class UserResource
{

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserDAO userDAO;

	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
		return userDAO.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource<User> retrievUser(@PathVariable int id)
	{
		User user = userDAO.findOne(id);
		if (user == null)
		{
			throw new UserNotFoundException("id = " + id);
		}
		Resource<User> resource = new Resource<>(user);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass(), retrieveAllUsers()));
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	@PostMapping("/users")
	public ResponseEntity createUser(@Valid @RequestBody User user)
	{
		User saveUser = userDAO.save(user);

		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(saveUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping(("/users/{id}"))
	public User deleteUser(@PathVariable int id)
	{
		User deletedUser = userDAO.deleteById(id);
		if (deletedUser == null)
		{
			throw new UserNotFoundException("id = " + id);
		}
		return deletedUser;
	}


	@GetMapping("/hello-world-internalized")
	public String helloWorldIntrenalized()
	{
		return messageSource.getMessage("good.morning.message", null,LocaleContextHolder.getLocale());
	}
}
