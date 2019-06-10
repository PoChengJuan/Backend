package com.csi_system.backend;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

@Controller
@CrossOrigin
@RequestMapping("/ShopData")
public class ShopDataController {

	@Autowired
	private ShopDataRepository shopDataRepository;
	
	@GetMapping(path="get")
	public @ResponseBody Iterable<ShopData> getAllUsers() {
		// This returns a JSON or XML with the users
		return shopDataRepository.findAll();
	}
	
	@GetMapping(path="getLastStock")
	public @ResponseBody String getLast(@RequestParam String shop,@RequestParam String branch){
		return shopDataRepository.getLastData(shop, branch);
	}
	
	@GetMapping(path="getExpense")
	public @ResponseBody String getExpenseItem(@RequestParam String shop,@RequestParam String branch) {
		return shopDataRepository.getExpense(shop, branch);
	}
	@GetMapping(path="getIncome")
	public @ResponseBody Iterable<String> getStock(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end){
		return shopDataRepository.getIncomeData(shopname, branch, start, end);
	}
	
	@RequestMapping(path="add",method = RequestMethod.POST)
	public @ResponseBody String addStock(
			@RequestBody String name
			//@RequestBody String branch
			//@RequestBody String date,
			//@RequestBody String stock,
			//@RequestBody int expense,
			//@RequestBody int income
			) {
		ShopData n = new ShopData();
		JSONObject j = JSONObject.fromObject(name);
		System.out.print(j);
		n.setShopname(j.getString("shopname"));
		n.setBranch(j.getString("branch"));
		n.setName(j.getString("name"));
		n.setDate(j.getString("date"));
		n.setTime(j.getString("time"));
		n.setStock(j.getString("stock"));
		n.setExpense(j.getString("expense"));
		/*if(j.getString("expense").isEmpty()) {
			n.setExpense(0);
		}else {
			n.setExpense(j.getInt("expense"));
		}*/
		if(j.getString("income").isEmpty()) {
			n.setIncome(0);
		}else {
			n.setIncome(j.getInt("income"));
		}
		shopDataRepository.save(n);
		return "OK";
	}
	
}
