package com.csi_system.backend;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ShopData")
public class ShopData {

	@Id
	@Column(name="auto_increment")
    @GeneratedValue(strategy=GenerationType.AUTO)
	private int auto_increment;
	
	@Column(name="name")
	private String name;
	
	@Column(name="branch")
	private String branch;
	
	@Column(name="date")
	private String date;
	
	@Column(name="stock")
	private String stock;
	
	@Column(name="expense")
	private int expense;
	
	@Column(name="income")
	private int income;

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

	public String getBranch() {
		return branch;
	}

	public void setBranch(String branch) {
		this.branch = branch;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public int getExpense() {
		return expense;
	}

	public void setExpense(int expense) {
		this.expense = expense;
	}

	public int getIncome() {
		return income;
	}

	public void setIncome(int income) {
		this.income = income;
	}

	
	
	
}
