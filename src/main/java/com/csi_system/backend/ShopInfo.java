package com.csi_system.backend;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ShopInfo")
public class ShopInfo {

	@Id
	@Column(name="auto_increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int auto_increment;
	
	@Column(name="owner")
	private String owner;
	
	@Column(name="shopName")
	private String shopName;
	
	@Column(name="branch")
	private String brach;
	
	@Column(name="menu")
	private String menu;

	public int getAuto_increment() {
		return auto_increment;
	}

	public void setAuto_increment(int auto_increment) {
		this.auto_increment = auto_increment;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getBrach() {
		return brach;
	}

	public void setBrach(String brach) {
		this.brach = brach;
	}

	public String getMenu() {
		return menu;
	}

	public void setMenu(String menu) {
		this.menu = menu;
	}
	
	
	
}
