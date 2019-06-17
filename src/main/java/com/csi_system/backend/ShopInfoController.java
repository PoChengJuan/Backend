package com.csi_system.backend;

import java.util.Iterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@CrossOrigin
@RequestMapping("/ShopInfo")
public class ShopInfoController {

	@Autowired
	private ShopInfoRepository shopInfoRepository;
	

	
	@GetMapping(path="get")
	public @ResponseBody Iterable<ShopInfo> getAllInfo(@RequestParam String name) {
		// This returns a JSON or XML with the users
		return shopInfoRepository.aaa(name);
		//return shopInfoRepository.findAll();
	}
	@GetMapping(path="getMenu")
	public @ResponseBody String getMenu(@RequestParam String shopname, @RequestParam String branch) {
		return shopInfoRepository.getMenu(shopname, branch);
	}
	
	@GetMapping(path="getBranch")
	public @ResponseBody String getBranch(@RequestParam String shopname) {
		StringBuffer output = new StringBuffer();
		
		Iterator<?> Branch_Array = shopInfoRepository.getBranch(shopname).iterator();
		JSONObject Object = new JSONObject();
		JSONArray Array = new JSONArray();
		int i = 1;
		
		while(Branch_Array.hasNext()) {
			Object.accumulate("key", i);
			Object.accumulate("branch", Branch_Array.next().toString());
			Array.add(Object);
			Object.clear();
			i++;
		}
		
		/*output.append("<Menu>");
		Iterator iterator_Branch = shopInfoRepository.getBranch(shopname).iterator();
		Iterator iterator_Id = shopInfoRepository.getId(shopname).iterator();

		while(iterator_Id.hasNext()) {
			output.append("<Menu.Item key='");
			output.append(iterator_Id.next().toString());
			output.append("'>");
			output.append(iterator_Branch.next().toString());
			output.append("</ Menu.Item>");
		}
		output.append("</Menu>");*/

		//return shopInfoRepository.getBranch(shopname);
		return Array.toString();
	}
}
