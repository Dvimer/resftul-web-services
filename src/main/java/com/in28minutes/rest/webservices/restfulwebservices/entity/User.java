package com.in28minutes.rest.webservices.restfulwebservices.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;
@ApiModel(description = "All details about tge user.")
@Entity
public class User
{
	@Id
	@GeneratedValue
	private Integer id;
	@Size(min = 2, message = "Name should have atleast 2 charackters")
	private String name;
	@Past
	@ApiModelProperty(notes = "Bitrh date shoud be in the past")
	private Date birthDate;

	public User()
	{
	}

	public User(Integer id, String name, Date birthDate)
	{
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
	}

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Date getBirthDate()
	{
		return birthDate;
	}

	public void setBirthDate(Date birthDate)
	{
		this.birthDate = birthDate;
	}

	@Override
	public String toString()
	{
		final StringBuilder sb = new StringBuilder("User{");
		sb.append("id=").append(id);
		sb.append(", name='").append(name).append('\'');
		sb.append(", birthDate=").append(birthDate);
		sb.append('}');
		return sb.toString();
	}
}
