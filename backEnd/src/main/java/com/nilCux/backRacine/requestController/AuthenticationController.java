package com.nilCux.backRacine.requestController;

import com.nilCux.backRacine.modules.enumeration.ResultCode;
import com.nilCux.backRacine.modules.output.ApiResult;
import com.nilCux.backRacine.modules.services.UserDBService;
import com.nilCux.backRacine.modules.dto.RegistrationInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class AuthenticationController {


	//private static final String template = "Hello, %s!";
	//private final AtomicLong counter = new AtomicLong();
	@Autowired
	private UserDBService userDBService;

	@RequestMapping("/registry")
	public ApiResult registry(@RequestBody RegistrationInfo information) {
		String fakeName = information.getFakeName();
		String mail = information.getMail();
		String password = information.getPassword();
		if (!userDBService.userExist(mail)) {
			log.info("Trying to register" + fakeName);
			if (userDBService.registerUser(fakeName, mail, password))
				return ApiResult.ok();
			else return ApiResult.fail("Operation_Failure");
		} else {
			return ApiResult.fail(ResultCode.UNCHANGED.getCode(),"User_Exist");
		}
	}

	@RequestMapping("/isAuthenticated")
	public ApiResult isAuthenticated() {
		return ApiResult.ok();
	}

}