package com.csi_system.backend;

import java.sql.Date;
import java.util.Iterator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
		int num;
		System.out.println(stock_array.toString());
		
		for(int i = 0;i < stock_array.size();i++) {
			stock_object = stock_array.getJSONObject(i);
			stock_array.remove(i);
			num = stock_object.getInt("stock");
			//mult = stock_object.getInt("mult");
			stock_object.put("stock", num);
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
	@GetMapping(path="getIncomeData")
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
		
		n.setStock(calcuSold(j.getString("shopname"),j.getString("branch"),j.getJSONArray("stock")));
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
	private String calcuSold(String shop, String branch, JSONArray jsonArray) {
		// TODO Auto-generated method stub
		JSONArray last_array = JSONArray.fromObject(shopDataRepository.getLastData(shop, branch));
		int i = 0;
		int stock = 0;
		int order = 0;
		int sold = 0;
		int last_stock = 0;
		System.out.println(last_array.toString());
		System.out.println(jsonArray.toString());
		
		for(i = 0;i < jsonArray.size();i++) {
			stock = 0;
			order = 0;
			sold = 0;
			last_stock = 0;
			stock = jsonArray.getJSONObject(i).getInt("stock");
			order = jsonArray.getJSONObject(i).getInt("order");
			last_stock = last_array.getJSONObject(i).getInt("stock");
			sold = (last_stock + order) - stock;
			jsonArray.getJSONObject(i).remove("sold");
			jsonArray.getJSONObject(i).accumulate("sold", sold);
		}
		System.out.println(jsonArray.toString());
		return jsonArray.toString();
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
	
	@GetMapping(path="getRangeData")
	public @ResponseBody JSONArray getRangeData(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String start,@RequestParam String end) {
		JSONArray Array = new JSONArray();
		//Stock
		JSONArray Array_Sub1 = new JSONArray();
		JSONObject Object_1 = new JSONObject();
		//Scrap
		JSONObject Object_2 = new JSONObject();
		JSONArray Array_Sub2 = new JSONArray();
		//Sold
		JSONArray Array_Sub3 = new JSONArray();
		JSONObject Object_3 = new JSONObject();
		//Order
		JSONArray Array_Sub4 = new JSONArray();
		JSONObject Object_4 = new JSONObject();
		
		JSONArray Array_Stock = shopDataRepository.getStockData(shopname, branch, start, end);
		JSONArray Array_Stock_2 = new JSONArray();
		JSONObject item_Object = new JSONObject();
		String Date = new String();
		int i = 0;
		int j = 0;
		int sold = 0;
		JSONArray last_Stock = new JSONArray();

		Iterator<?> iterator_Date = shopDataRepository.getRangeDate(shopname, branch, start, end).iterator();

		while(iterator_Date.hasNext()) {
			Object_1.put("key", i+1);
			Date = iterator_Date.next().toString();
			Object_1.put("Date", Date);
			Object_2.put("key", i+1);
			Object_2.put("Date", Date);
			Object_3.put("key", i+1);
			Object_3.put("Date", Date);
			Object_4.put("key", i+1);
			Object_4.put("Date", Date);
			
			Array_Stock_2 = Array_Stock.getJSONArray(i);
			Iterator<?> Stock_Data = Array_Stock_2.iterator();
			while(Stock_Data.hasNext()) {
				item_Object = (JSONObject) Stock_Data.next();
				Object_1.put(item_Object.get("title"), item_Object.get("stock"));
				Object_2.put(item_Object.get("title"), item_Object.get("scrap"));
				Object_4.put(item_Object.get("title"), item_Object.get("order"));
				if(i != 0 ) {
					//sold (昨日庫存+今日叫貨) - 今日庫存 - 今日報廢
					sold = (last_Stock.getInt(j) + item_Object.getInt("order")) - item_Object.getInt("stock") - item_Object.getInt("scrap");
					Object_3.put(item_Object.get("title"), sold);
					last_Stock.remove(j);
					last_Stock.add(j, item_Object.getInt("stock"));
				}else {
					Object_3.put(item_Object.get("title"), 0);
					last_Stock.add(j, item_Object.getInt("stock"));
				}
				j++;
			}
			j = 0;
			//item_Object = Array_Stock_2.getJSONObject(i);
			
			Array_Sub1.add(Object_1);
			Array_Sub2.add(Object_2);
			Array_Sub3.add(Object_3);
			Array_Sub4.add(Object_4);
			Object_1.clear();
			Object_2.clear();
			Object_3.clear();
			Object_4.clear();
			i++;
		}
		Array.add(Array_Sub1);
		Array.add(Array_Sub2);
		Array.add(Array_Sub3);
		Array.add(Array_Sub4);
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
		int laststock = 0;
		int allstock = 0;
		
		for(i = 0;i < Array_Stock.size();i++) {
			Array_Stock_2 = Array_Stock.getJSONArray(i);
			for(j = 0;j < Array_Stock_2.size();j++) {
				if(i == 0) {
					laststock = Array_Last_Stock.getJSONArray(0).getJSONObject(j).getInt("stock");
					allstock = laststock;
				}else {
					laststock = Array.getJSONObject(j).getInt("上月庫存");
					allstock = Array.getJSONObject(j).getInt("總進貨") + laststock;
					order = Array.getJSONObject(j).getInt("總進貨");
					scrap = Array.getJSONObject(j).getInt("報廢");
					Array.remove(j);
				}
				item_Object = Array_Stock_2.getJSONObject(j);
				Object.put("title", item_Object.get("title"));
				allstock = allstock + item_Object.getInt("order");
				order = order + item_Object.getInt("order");

				scrap = scrap + item_Object.getInt("scrap");

				if(i == Array_Stock.size()-1) {
					//總銷售 = allstock(上個月庫存+這個月叫貨) - 月底庫存 - 整個月總報廢
					sold = allstock - item_Object.getInt("stock") - scrap;
				}
				Object.put("上月庫存", laststock);
				Object.put("總進貨", order);
				Object.put("總銷售", sold);
				Object.put("報廢", scrap);
				Array.add(j, Object);
				//laststock = 0;
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
	public @ResponseBody String UpdateStock(@PathVariable int id, @RequestBody String stock_data) {
		JSONObject data = JSONObject.fromObject(stock_data);

		JSONArray newDataArray = data.getJSONArray("stock");
		JSONObject newDataObject = new JSONObject();
		JSONArray currentDataArray = new JSONArray();
		JSONObject currentDataObject = new JSONObject();
		
		JSONArray last_array1 = new JSONArray();
		JSONArray last_array2 = new JSONArray();
		int stock,order,sold,scrap,last_stock;
		int old_stock,old_order,old_sold,old_scrap;
		int scrap_flag = 0;
		if(data.getString("type").equals("scrap")) {
			scrap_flag = 1;
		}else {
			scrap_flag = 0;
		}
		System.out.println("id:"+id);
		System.out.println("stock:"+stock_data);
		
		Optional<ShopData> currentShopData = shopDataRepository.findById(id);
		System.out.println(currentShopData);

		ShopData shopdata = currentShopData.orElse(null);
		currentDataArray = JSONArray.fromObject(shopdata.getStock());
		
		last_array1 = shopDataRepository.getLastDataForPut(shopdata.getShopname(), shopdata.getBranch());
		last_array2 = last_array1.getJSONArray(1);
		for(int i=0;i < currentDataArray.size();i++) {
			stock = 0;
			order = 0;
			sold = 0;
			scrap = 0;
			last_stock = 0;
			old_stock = 0;
			old_order = 0;
			old_sold = 0;
			old_scrap = 0;
			
			currentDataObject = currentDataArray.getJSONObject(i);
			old_stock = currentDataObject.getInt("stock");
			old_order = currentDataObject.getInt("order");
			old_sold = currentDataObject.getInt("sold");
			old_scrap = currentDataObject.getInt("scrap");
			currentDataArray.remove(i);
			newDataObject = newDataArray.getJSONObject(i);
//			currentDataObject.put("scrap", newDataObject.getInt("scrap")*newDataObject.getInt("mult"));
			
			
			scrap = newDataObject.getInt("scrap");
			if(scrap_flag == 0) {//update stock
				last_stock = last_array2.getJSONObject(i).getInt("stock");
				last_stock = last_stock - currentDataObject.getInt("scrap");
				//stock = newDataObject.getInt("stock") - currentDataObject.getInt("scrap");
				stock = newDataObject.getInt("stock");
				order = newDataObject.getInt("order");
				sold = (last_stock + order) - stock;
				currentDataObject.put("stock", stock);
				currentDataObject.put("order", order);
				currentDataObject.put("scrap", old_scrap);
			}else {//update scrap
				last_stock = last_array2.getJSONObject(i).getInt("stock");
				old_stock = old_stock + old_scrap;
				old_stock = old_stock - scrap;
				sold = (last_stock + old_order) - old_stock;
				currentDataObject.put("stock", old_stock);
				currentDataObject.put("order", old_order);
				currentDataObject.put("scrap", scrap);
			}
			currentDataObject.put("sold", sold);
			currentDataArray.add(i, currentDataObject);
		}
		
		System.out.println(currentDataArray.toString());
		shopdata.setName(data.getString("name"));
		shopdata.setTime(data.getString("time"));
		if(scrap_flag == 0) {
			shopdata.setExpense(data.getString("expense"));
			shopdata.setIncome(data.getInt("income"));
		}
		shopdata.setStock(currentDataArray.toString());
		shopDataRepository.save(shopdata);
		
		return "OK";
	}
	@GetMapping(path="getStatistics")
	public @ResponseBody JSONArray getStatistics(@RequestParam String shopname,@RequestParam String branch,
			@RequestParam String month) {
		JSONArray Array = new JSONArray();
		
		JSONArray Income_Data = JSONArray.fromObject(shopDataRepository.getMonthIncome(shopname, branch, month));
		JSONArray Array_Income = new JSONArray();
		JSONObject Object_Income = new JSONObject();
		
		JSONArray Expense_Data = JSONArray.fromObject(shopDataRepository.getMonthExpense(shopname, branch, month));
		JSONArray Array_Expense = new JSONArray();
		JSONObject Object_Expense = new JSONObject();
		
		JSONArray Order_Data = JSONArray.fromObject(shopDataRepository.getMonthsStock(shopname, branch, month));
		JSONArray Array_Order = new JSONArray();
		JSONObject Object_Order = new JSONObject();

		JSONArray Scrap_Data = JSONArray.fromObject(shopDataRepository.getMonthsStock(shopname, branch, month));
		JSONArray Array_Scrap = new JSONArray();
		JSONObject Object_Scrap = new JSONObject();

		
		JSONArray Item_Array = new JSONArray();
		JSONObject Item_Object = new JSONObject();
		//int i = 0;
		//*****Income*****
		int income = 0;
		Object_Income.put("key", 0);
		Object_Income.put("title", "月營收");
		for(int i = 0;i < Income_Data.size();i++) {
			income = income + Income_Data.getInt(i);
		}
		Object_Income.put("income",income);
		Array_Income.add(Object_Income);
		//*****Expense*****
		int num = 0;

		for(int i = 0;i < Expense_Data.size();i++) {
			Item_Array = Expense_Data.getJSONArray(i);
			for(int j=0;j < Item_Array.size();j++) {
				Item_Object = Item_Array.getJSONObject(j);
				if(i == 0) {
					Object_Expense.put("key", j);
					Object_Expense.put("title", Item_Object.get("title"));
					Object_Expense.put("cost", Item_Object.get("cost"));
				}else {
					num = Array_Expense.getJSONObject(j).getInt("cost");
					Array_Expense.remove(j);
					num = num + Item_Object.getInt("cost");
					Object_Expense.put("key", j);
					Object_Expense.put("title", Item_Object.get("title"));
					Object_Expense.put("cost", num);
				}
				Array_Expense.add(j,Object_Expense);
				Object_Expense.clear();
			}
		}
		Item_Array.clear();
		Item_Object.clear();
		//*****Order*****
		for(int i = 0;i < Order_Data.size();i++) {
			Item_Array = Order_Data.getJSONArray(i);
			for(int j=0;j < Item_Array.size();j++) {
				Item_Object = Item_Array.getJSONObject(j);
				if(i == 0) {
					Object_Order.put("key", j);
					Object_Order.put("title", Item_Object.get("title"));
					Object_Order.put("order", Item_Object.get("order"));
				}else {
					num = Array_Order.getJSONObject(j).getInt("order");
					Array_Order.remove(j);
					num = num + Item_Object.getInt("order");
					Object_Order.put("key", j);
					Object_Order.put("title", Item_Object.get("title"));
					Object_Order.put("order", num);
				}
				Array_Order.add(j,Object_Order);
				Object_Order.clear();
			}
		}
		Item_Array.clear();
		Item_Object.clear();
		//*****Scrap*****
		for(int i = 0;i < Scrap_Data.size();i++) {
			Item_Array = Scrap_Data.getJSONArray(i);
			for(int j=0;j < Item_Array.size();j++) {
				Item_Object = Item_Array.getJSONObject(j);
				if(i == 0) {
					Object_Scrap.put("key", j);
					Object_Scrap.put("title", Item_Object.get("title"));
					Object_Scrap.put("scrap", Item_Object.get("scrap"));
				}else {
					num = Array_Scrap.getJSONObject(j).getInt("scrap");
					Array_Scrap.remove(j);
					num = num + Item_Object.getInt("scrap");
					Object_Scrap.put("key", j);
					Object_Scrap.put("title", Item_Object.get("title"));
					Object_Scrap.put("scrap", num);
				}
				Array_Scrap.add(j,Object_Scrap);
				Object_Scrap.clear();
			}
		}
		Item_Array.clear();
		Item_Object.clear();
		
		
		Array.add(Array_Income);
		Array.add(Array_Expense);
		Array.add(Array_Order);
		Array.add(Array_Scrap);
		return Array;
	}
	@GetMapping(path="getWarning")
	public @ResponseBody String getWarning(@RequestParam String shop,@RequestParam String branch,@RequestParam String date,
			@RequestParam String page) {
		JSONArray Data_Array = JSONArray.fromObject(shopDataRepository.getData(shop, branch, date));
		JSONArray Info_Array = JSONArray.fromObject(shopInfoRepository.getStockItem(shop, branch));
		
		JSONArray Array = new JSONArray();
		JSONObject Object = new JSONObject();
		int x = 0;
		//JSONObject Data_Object = Data_Array.getJSONObject(0);
		//System.out.println(Data_Array.toString());
		if(page.equals("Info")) {
			for(int i=0;i < Data_Array.size();i++) {
				Object.clear();
				if(Data_Array.getJSONObject(i).getInt("stock") <= Info_Array.getJSONObject(i).getInt("warning")) {
					Object.put("key", x++);
					Object.put("title", Data_Array.getJSONObject(i).get("title"));
					Array.add(Object);
				}
				
			}
			if(x == 0) {
				return "OK";
			}else {
				return Array.toString();
			}
		}else if(page.equals("StockList")) {
			return "OK";
		}else {
			return "OK";
		}
	}
}
