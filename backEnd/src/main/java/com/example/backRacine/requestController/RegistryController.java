package com.example.backRacine.requestController;

import com.example.backRacine.dataServices.UserOperatorService;
import com.example.backRacine.repositories.UserRepository;
import com.example.backRacine.requestModel.RegisterInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class RegistryController {


	//private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	private UserOperatorService userOperatorService;
	@RequestMapping("/registry")
	public String registry(@RequestBody RegisterInfo information) {
		String mail = information.getMail();
		String password = information.getPassword();
		if (userOperatorService.registerUser(mail, password))
			return "Success";
		else return "Failure";
	}

	@RequestMapping("/loginUnsecure")
	public String logIn(@RequestBody RegisterInfo information) {
		String mail = information.getMail();
		String password = information.getPassword();
		if (userOperatorService.loggingIn(mail, password))
			return "Success";
		else return "Failure";
	}
}
