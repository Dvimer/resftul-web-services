package com.in28minutes.rest.webservices.restfulwebservices.rest;

import com.in28minutes.rest.webservices.restfulwebservices.dao.PostRepository;
import com.in28minutes.rest.webservices.restfulwebservices.dao.UserRepository;
import com.in28minutes.rest.webservices.restfulwebservices.entity.Post;
import com.in28minutes.rest.webservices.restfulwebservices.entity.User;
import com.in28minutes.rest.webservices.restfulwebservices.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RequestMapping("/jpa")
@RestController
public class UserJPAResource
{

	@Autowired
	private MessageSource messageSource;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private PostRepository postRepository;

	@GetMapping("/users")
	public List<User> retrieveAllUsers()
	{
		return userRepository.findAll();
	}

	@GetMapping("/users/{id}")
	public Resource<User> retrievUser(@PathVariable int id)
	{
		User user = userRepository.getOne(id);
		Resource<User> resource = new Resource<>(user);
		ControllerLinkBuilder linkTo = ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass(), retrieveAllUsers()));
		resource.add(linkTo.withRel("all-users"));
		return resource;
	}

	@PostMapping("/users")
	public ResponseEntity createUser(@Valid @RequestBody User user)
	{
		User saveUser = userRepository.save(user);

		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(saveUser.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@DeleteMapping("/users/{id}")
	public User deleteUser(@PathVariable int id)
	{
		User deletedUser = userRepository.getOne(id);
		if (deletedUser == null)
		{
			throw new UserNotFoundException("id = " + id);
		}
		userRepository.delete(deletedUser);
		return deletedUser;
	}

	@GetMapping("/users/{id}/posts")
	public List<Post> getPosts(@PathVariable int id)
	{
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent())
		{
			throw new UserNotFoundException("id = " + id);
		}

		return optionalUser.get().getPosts();
	}

	@PostMapping("/users/{id}/posts")
	public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post)
	{
		Optional<User> optionalUser = userRepository.findById(id);
		if (!optionalUser.isPresent())
		{
			throw new UserNotFoundException("id = " + id);
		}
		User user = optionalUser.get();
		post.setUser(user);
		postRepository.save(post);

		URI uri = ServletUriComponentsBuilder
			.fromCurrentRequest()
			.path("/{id}")
			.buildAndExpand(post.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}


	@GetMapping("/hello-world-internalized")
	public String helloWorldIntrenalized()
	{
		return messageSource.getMessage("good.morning.message", null, LocaleContextHolder.getLocale());
	}
}
