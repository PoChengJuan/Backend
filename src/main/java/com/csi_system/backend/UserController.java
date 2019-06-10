package com.csi_system.backend;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.sf.json.JSONObject;

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
	@PutMapping(path="updateUploaddate/{id}")
	public @ResponseBody String LastUpload(@PathVariable int id, @RequestBody String date) {
		JSONObject j = JSONObject.fromObject(date);

		Optional<User> currentUser = userRepository.findById(id);
		User user = currentUser.orElse(null);
		
		
		user.setLastupload(j.getString("date"));
		userRepository.save(user);

		return "OK";
	}
}
