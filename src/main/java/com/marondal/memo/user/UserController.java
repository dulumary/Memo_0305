package com.marondal.memo.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
	
	@GetMapping("/user/join-view")
	public String inputJoin() {
		return "user/join";
	}

}
