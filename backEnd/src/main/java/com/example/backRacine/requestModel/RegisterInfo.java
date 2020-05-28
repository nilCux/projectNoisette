package com.example.backRacine.requestModel;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterInfo {

	private final String mail;
	private final String password;
}
