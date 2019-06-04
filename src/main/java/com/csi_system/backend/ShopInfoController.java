package com.csi_system.backend;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
	
	
}
