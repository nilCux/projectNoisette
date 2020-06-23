package com.nilCux.backRacine.modules.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationInfo {
	private final  String fakeName;
	private final String mail;
	private final String password;
}
