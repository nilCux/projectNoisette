package com.nilCux.backRacine.requestController;

import com.nilCux.backRacine.modules.output.ApiResult;
import com.nilCux.backRacine.modules.services.UserDBService;
import com.nilCux.backRacine.modules.dto.RegistrationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class RegistryController {


	//private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	private UserDBService userDBService;

	@RequestMapping("/registry")
	public ApiResult registry(@RequestBody RegistrationInfo information) {
		log.info("Registry controller visited");
		String mail = information.getMail();
		String password = information.getPassword();
		if (!userDBService.userExist(mail)) {
			if (userDBService.registerUser(mail, password))
				return ApiResult.ok();
			else return ApiResult.fail("Operation_Failure");
		} else {
			return ApiResult.fail("User_Exist");
		}
	}

	@RequestMapping("/blank")
	public ApiResult blank() {
		return ApiResult.ok();
	}
}
