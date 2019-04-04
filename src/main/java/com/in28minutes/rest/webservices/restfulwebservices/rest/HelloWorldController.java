package com.in28minutes.rest.webservices.restfulwebservices.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController
{

	@GetMapping("/hello")
	public HelloWorldBean sayHello()
	{
		return new HelloWorldBean("Hello world");
	}

	@GetMapping("/hello/{name}")
	public HelloWorldBean sayHello(@PathVariable String name)
	{
		return new HelloWorldBean("Hello world" + name);
	}
}
