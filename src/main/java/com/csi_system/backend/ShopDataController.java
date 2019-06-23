package com.csi_system.backend;

import java.sql.Date;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
	
	@Autowired
	private ShopInfoRepository shopInfoRepository;
	
	@GetMapping(path="get")
	public @ResponseBody Iterable<ShopData> getAllUsers() {
		// This returns a JSON or XML with the users
		return shopDataRepository.findAll();
	}
	
	@GetMapping(path="getLastStock")
	public @ResponseBody String getLast(@RequestParam String shop,@RequestParam String branch){
		JSONArray stock_array = JSONArray.fromObject(shopDataRepository.getLastData(shop, branch));
		JSONObject stock_object = new JSONObject();
		int num, mult;
		System.out.println(stock_array.toString());
		
		for(int i = 0;i < stock_array.size();i++) {
			stock_object = stock_array.getJSONObject(i);
			stock_array.remove(i);
			num = stock_object.getInt("stock");
			mult = stock_object.getInt("mult");
			stock_object.put("stock", num/mult);
			stock_array.add(i, stock_object);
		}
		return shopDataRepository.getLastData(shop, branch);
		//return stock_array.toString();
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
	@GetMapping(path="getRangeData")
	public @ResponseBody JSONArray getIncomeData(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end){
		//System.out.print(shopDataRepository.getIncomeData(shopname, branch, start, end));
        //Iterable<String> strings = shopDataRepository.getIncomeData(shopname, branch, start, end);

		//for(String s:strings) {
		//	System.out.print(s);
		//}
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		
		JSONArray Array_Expense = shopDataRepository.getExpenseData(shopname, branch, start, end);
		JSONArray Array_Expense_2 = new JSONArray();
		JSONObject Object_OrderExpense = new JSONObject();
		JSONObject Object_OtherExpense = new JSONObject();
		
		int i = 0;

		Iterator<?> iterator_Income = shopDataRepository.getIncomeData(shopname, branch, start, end).iterator();
		Iterator<?> iterator_Date = shopDataRepository.getRangeDate(shopname, branch, start, end).iterator();

		while(iterator_Income.hasNext()) {
			Object.put("key", i+1);
			Object.put("日期", iterator_Date.next().toString());
			Object.put("營業額", iterator_Income.next().toString());

			Array_Expense_2 = Array_Expense.getJSONArray(i);
			Object_OrderExpense = Array_Expense_2.getJSONObject(0);
			Object_OtherExpense = Array_Expense_2.getJSONObject(1);
			Object.put("進貨支出", Object_OrderExpense.get("cost"));
			Object.put("雜支", Object_OtherExpense.get("cost"));
			
			Array.add(Object);
			Object.clear();
			i++;
			//System.out.print(iterator_Income.next());
			//System.out.print(iterator_Date.next());
		}
		
		//return shopDataRepository.getIncomeData(shopname, branch, start, end);
		return Array;
	}
	/*@GetMapping(path="getExpenseData")
	public @ResponseBody String getExpenseData(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end){
		//System.out.print(shopDataRepository.getIncomeData(shopname, branch, start, end));
        //Iterable<String> strings = shopDataRepository.getIncomeData(shopname, branch, start, end);

		//for(String s:strings) {
		//	System.out.print(s);
		//}
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		
		
		JSONArray Array_Expense = shopDataRepository.getExpenseData(shopname, branch, start, end);
		JSONArray Array_Expense_2 = new JSONArray();
		JSONObject Object_OrderExpense = new JSONObject();
		JSONObject Object_OtherExpense = new JSONObject();

		Iterator iterator_Date = shopDataRepository.getRangeDate(shopname, branch, start, end).iterator();

		int i = 0;
		while(iterator_Date.hasNext()) {
			Object.put("日期", iterator_Date.next().toString());
			
			Array_Expense_2 = Array_Expense.getJSONArray(i);
			Object_OrderExpense = Array_Expense_2.getJSONObject(0);
			Object_OtherExpense = Array_Expense_2.getJSONObject(1);
			Object.put("進貨支出", Object_OrderExpense.get("cost"));
			Object.put("雜支", Object_OtherExpense.get("cost"));
			
			Array.add(Object);
			Object.clear();
			i++;
		}
		
		return Array.toString();
	}*/
	
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
		
		System.out.println(j.getString("stock"));
		
		//n.setStock(StockDataTrans(j.getString("stock")));
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
	@GetMapping(path="getStockItem")
	public @ResponseBody JSONArray getStockItem(@RequestParam String shopname,@RequestParam String branch) {
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		JSONArray item_Data = shopDataRepository.getStockItem(shopname, branch);
		JSONArray item_Array = new JSONArray();
		item_Array = item_Data.getJSONArray(0);
		Iterator<?> item_iterator = item_Array.iterator();
		JSONObject item_Object = new JSONObject();
		int i = 1;
		Object.accumulate("key", i);
		Object.accumulate("title", "-");
		Array.add(Object);
		Object.clear();
		i++;
		while(item_iterator.hasNext()) {
			
			item_Object = (JSONObject) item_iterator.next();
			Object.accumulate("key", i);
			Object.accumulate("title", item_Object.get("title"));
			Array.add(Object);
			Object.clear();
			i++;
		}
		return Array;
		//return shopDataRepository.getStockItem(shop, branch);
	}
	
	@GetMapping(path="getRangeStock")
	public @ResponseBody JSONArray getRangeStock(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end) {
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		
		JSONArray Array_Stock = shopDataRepository.getStockData(shopname, branch, start, end);
		JSONArray Array_Stock_2 = new JSONArray();
		JSONObject item_Object = new JSONObject();
		
		int i = 0;

		Iterator<?> iterator_Date = shopDataRepository.getRangeDate(shopname, branch, start, end).iterator();

		while(iterator_Date.hasNext()) {
			Object.put("key", i+1);
			Object.put("Date", iterator_Date.next().toString());
			Array_Stock_2 = Array_Stock.getJSONArray(i);
			Iterator<?> Stock_Data = Array_Stock_2.iterator();
			while(Stock_Data.hasNext()) {
				item_Object = (JSONObject) Stock_Data.next();
				Object.put(item_Object.get("title"), item_Object.get("stock"));
			}
			//item_Object = Array_Stock_2.getJSONObject(i);
			
			Array.add(Object);
			Object.clear();
			i++;
		}
		return Array;
	}
	
	@GetMapping(path="getAchieving")
	public @ResponseBody JSONArray getAchieving(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String month, @RequestParam String lastmonth) {
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		
		JSONArray Array_Last_Stock = shopDataRepository.getLastDayofLastMonth(shopname, branch, lastmonth);
		JSONArray Array_Stock = shopDataRepository.getAchieving(shopname, branch, month);
		JSONArray Array_Stock_2 = new JSONArray();
		JSONObject item_Object = new JSONObject();
		
		int i = 0;
		int j= 0;
		int order = 0;
		int sold = 0;
		int scrap = 0;
		
		
		for(i = 0;i < Array_Stock.size();i++) {
			Array_Stock_2 = Array_Stock.getJSONArray(i);
			for(j = 0;j < Array_Stock_2.size();j++) {
				if(i == 0) {
					order = Array_Last_Stock.getJSONArray(0).getJSONObject(j).getInt("stock");
				}else {
					order = Array.getJSONObject(j).getInt("總進貨");
					scrap = Array.getJSONObject(j).getInt("報廢");
					Array.remove(j);
				}
				item_Object = Array_Stock_2.getJSONObject(j);
				Object.put("title", item_Object.get("title"));
				order = order + item_Object.getInt("order");

				scrap = scrap + item_Object.getInt("scrap");

				if(i == Array_Stock.size()-1) {
					//總銷售 = order(上個月庫存+這個月叫貨) - 月底庫存 - 整個月總報廢
					sold = order - item_Object.getInt("stock") - scrap;
				}
				
				Object.put("總進貨", order);
				Object.put("總銷售", sold);
				Object.put("報廢", scrap);
				Array.add(j, Object);
				order = 0;
				sold = 0;
				scrap = 0;
			}
		}
		System.out.println(Array);
		/*Array.clear();
		System.out.println(Array_Last_Stock);
		for(i = 0;i < Array_Stock.size();i++) {
			Array_Stock_2 = Array_Stock.getJSONArray(i);
			for( j = 0;j < Array_Stock_2.size();j++) {
				if(j == i) {
				System.out.println(Array_Stock_2.getJSONObject(j));}
				//  get sold
				item_Object = Array_Stock_2.getJSONObject(j);
				Object.put("title", item_Object.get("title"));
				if(i == 0) {
					order = item_Object.getInt("stock");
					System.out.println(Array_Last_Stock.getJSONArray(0).getJSONObject(j).getInt("stock"));
					sold = Array_Last_Stock.getJSONArray(0).getJSONObject(j).getInt("stock") + item_Object.getInt("order") - item_Object.getInt("stock");
				}else {
					order = Array.getJSONObject(j).getInt("總進貨");
					sold = Array.getJSONObject(j).getInt("總銷售");

					order = order + item_Object.getInt("order");
					
					//總售量 + (昨天庫存 + 今天進貨 - 今天庫存
					sold = sold + ( Array_Stock.getJSONArray(i-1).getJSONObject(j).getInt("stock") + item_Object.getInt("order") - item_Object.getInt("stock") );

					Array.remove(j);
				}
				
				scrap = scrap + item_Object.getInt("scrap");
				
				Object.put("總進貨", order);
				Object.put("總銷售", sold);
				Object.put("報廢", scrap);
				//  get sold
				
				//  get scrap
				Array.add(j, Object);
				order = 0;
				sold = 0;
				scrap = 0;
			}
		}*/
		return Array;
	}
	
	@GetMapping(path="getTodayData")
	public @ResponseBody String getTodayData(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String today) {
		System.out.println(shopDataRepository.getTodayData(shopname, branch, today));
		return shopDataRepository.getTodayData(shopname, branch, today).toString();
	}
	
	
	public String StockDataTrans(String stock) {
		int num,mult;

		JSONArray stock_array = JSONArray.fromObject(stock);
		JSONObject stock_object = new JSONObject();
		System.out.println(stock_array.toString());
		for(int i=0;i < stock_array.size();i++) {
			stock_object = stock_array.getJSONObject(i);
			stock_array.remove(i);
			if(stock_object.getInt("stock") != 0) {
				num = stock_object.getInt("stock");
				mult = stock_object.getInt("mult");
				stock_object.put("stock", num*mult);
			}
			if(stock_object.getInt("order") != 0){
				num = stock_object.getInt("order");
				mult = stock_object.getInt("mult");
				stock_object.put("order", num*mult);
			}
			if(stock_object.getInt("scrap") != 0){
				num = stock_object.getInt("scrap");
				mult = stock_object.getInt("mult");
				stock_object.put("scrap", num*mult);
			}
			

			stock_array.add(i, stock_object);
		}
		return stock_array.toString();
	}
	
	@PutMapping(path="UpdateScrap/{id}")
	public @ResponseBody String UpdateScrap(@PathVariable int id, @RequestBody String stock) {
		JSONObject data = JSONObject.fromObject(stock);
		
		JSONArray newDataArray = data.getJSONArray("stock");
		JSONObject newDataObject = new JSONObject();
		JSONArray currentDataArray = new JSONArray();
		JSONObject currentDataObject = new JSONObject();
		System.out.println("id:"+id);
		System.out.println("stock:"+stock);
		

		Optional<ShopData> currentShopData = shopDataRepository.findById(id);
		System.out.println(currentShopData);

		ShopData shopdata = currentShopData.orElse(null);
		currentDataArray = JSONArray.fromObject(shopdata.getStock());
		for(int i=0;i < currentDataArray.size();i++) {
			currentDataObject = currentDataArray.getJSONObject(i);
			currentDataArray.remove(i);
			newDataObject = newDataArray.getJSONObject(i);
//			currentDataObject.put("scrap", newDataObject.getInt("scrap")*newDataObject.getInt("mult"));
			currentDataObject.put("scrap", newDataObject.getInt("scrap"));
			currentDataArray.add(i, currentDataObject);
		}
		
		System.out.println(currentDataArray.toString());
		shopdata.setName(data.getString("name"));
		shopdata.setTime(data.getString("time"));
		shopdata.setStock(currentDataArray.toString());
		shopDataRepository.save(shopdata);
		
		return "OK";
	}
	@PutMapping(path="UpdateStock/{id}")
	public @ResponseBody String UpdateStock(@PathVariable int id, @RequestBody String stock) {
		JSONObject j = JSONObject.fromObject(stock);

		System.out.println("id:"+id);
		System.out.println("stock:"+stock);
		return "OK";
	}
}
