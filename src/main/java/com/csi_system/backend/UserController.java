package com.csi_system.backend;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/User")
public class UserController {

	@Autowired
    private UserRepository userRepository;


	@GetMapping(path="/all")
	public @ResponseBody Iterable<User> getAllUsers() {
		// This returns a JSON or XML with the users
		return userRepository.findAll();
	}
	
	@GetMapping(path="getMember")
	public @ResponseBody Iterable<User> getMem(@RequestParam String name, @RequestParam String password){
		return userRepository.getMember(name, password);
	}
}
