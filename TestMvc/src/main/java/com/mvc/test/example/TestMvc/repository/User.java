package com.mvc.test.example.TestMvc.repository;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user")
public class User {

	@Id
	private Integer ID;
	private String name;
	private Integer salary;
	private String teamName;
	
	public User() {}
	
	public User(Integer iD, String name, Integer salary, String teamName) {
		ID = iD;
		this.name = name;
		this.salary = salary;
		this.teamName = teamName;
	}

	public Integer getID() {
		return ID;
	}

	public void setID(Integer iD) {
		ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
}
