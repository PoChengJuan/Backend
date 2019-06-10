package com.csi_system.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="UserDB")
public class User {

	public class java {

	}

	@Id
	@Column(name="auto_increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int auto_increment;
	
	@Column(name="name")
	private String name;
	
	@Column(name="password")
	private String password;
	
	@Column(name="permission")
	private int permission;
	
	@Column(name="shopname")
	private String shopname;

	@Column(name="branch")
	private String branch;
	
	@Column(name="lastupload")
	private String lastupload;
	
	
	public int getAuto_increment() {
		return auto_increment;
	}

	public void setAuto_increment(int auto_increment) {
		this.auto_increment = auto_increment;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getPermission() {
		return permission;
	}

	public void setPermission(int permission) {
		this.permission = permission;
	}

	public String getShopname() {
		return shopname;
	}

	public void setShopname(String shopname) {
		this.shopname = shopname;
	}

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getLastupload() {
		return lastupload;
	}

	public void setLastupload(String lastupload) {
		this.lastupload = lastupload;
	}



	
	
	
	
}
