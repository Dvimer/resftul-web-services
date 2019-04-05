package com.in28minutes.rest.webservices.restfulwebservices.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
public class Post
{
	@Id
	@GeneratedValue
	private int id;
	private String description;
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnore
	private User user;

	public Post()
	{
	}

	public Post(String description, User user)
	{
		this.description = description;
		this.user = user;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public User getUser()
	{
		return user;
	}

	public void setUser(User user)
	{
		this.user = user;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("Post{");
		sb.append("id=").append(id);
		sb.append(", description='").append(description).append('\'');
		sb.append(", user=").append(user);
		sb.append('}');
		return sb.toString();
	}
}
