package com.csi_system.backend;

import java.sql.Date;
import java.util.Iterator;

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

import net.sf.json.JSONArray;
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
	@GetMapping(path="getStock")
	public @ResponseBody String getLast(@RequestParam String shop,@RequestParam String branch,@RequestParam String date){
		//return shopDataRepository.getLastData(shop, branch);
		return shopDataRepository.getData(shop, branch, date);
	}
	
	@GetMapping(path="getLastExpense")
	public @ResponseBody String getLastExpense(@RequestParam String shop,@RequestParam String branch) {
		return shopDataRepository.getLastExpense(shop, branch);
	}
	@GetMapping(path="getExpense")
	public @ResponseBody String getExpense(@RequestParam String shop,@RequestParam String branch,@RequestParam String date) {
		return shopDataRepository.getExpense(shop, branch, date);
	}
	@GetMapping(path="getLastIncome")
	public @ResponseBody JSONArray getLastIncome(@RequestParam String shop,@RequestParam String branch) {
		JSONArray outputIncome = new JSONArray();
		JSONObject income = new JSONObject();
		income.accumulate("key", "1");
		income.accumulate("title", "營業額");
		income.accumulate("income", shopDataRepository.getLastIncome(shop, branch).toString());
		outputIncome.add(income);

		return outputIncome;
	}
	@GetMapping(path="getIncome")
	public @ResponseBody JSONArray getIncome(@RequestParam String shop,@RequestParam String branch, @RequestParam String date) {
		JSONArray outputIncome = new JSONArray();
		JSONObject income = new JSONObject();
		income.accumulate("key", "1");
		income.accumulate("title", "營業額");
		income.accumulate("income", shopDataRepository.getIncome(shop, branch,date).toString());
		outputIncome.add(income);

		return outputIncome;
	}
	
	@GetMapping(path="getLastUploadDate")
	public @ResponseBody Date getLastUploadDate(@RequestParam String shop,@RequestParam String branch) {
		return shopDataRepository.getLastUploadDate(shop, branch);
	}
	@GetMapping(path="getIncomeData")
	public @ResponseBody String getIncomeData(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end){
		//System.out.print(shopDataRepository.getIncomeData(shopname, branch, start, end));
        //Iterable<String> strings = shopDataRepository.getIncomeData(shopname, branch, start, end);

		//for(String s:strings) {
		//	System.out.print(s);
		//}
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		
		
		Iterator iterator_Income = shopDataRepository.getIncomeData(shopname, branch, start, end).iterator();
		Iterator iterator_Date = shopDataRepository.getIncomeDate(shopname, branch, start, end).iterator();

		while(iterator_Income.hasNext()) {
			Object.put("日期", iterator_Date.next().toString());
			Object.put("營業額", iterator_Income.next().toString());

			Array.add(Object);
			Object.clear();
			//System.out.print(iterator_Income.next());
			//System.out.print(iterator_Date.next());
		}
		
		//return shopDataRepository.getIncomeData(shopname, branch, start, end);
		return Array.toString();
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
		
		n.setShopname(j.getString("shopname"));
		n.setBranch(j.getString("branch"));
		n.setName(j.getString("name"));
		n.setDate(j.getString("date"));
		n.setTime(j.getString("time"));
		n.setStock(j.getString("stock"));
		System.out.print(j.getJSONArray("expense"));
		n.setExpense(j.getString("expense").toString());
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
