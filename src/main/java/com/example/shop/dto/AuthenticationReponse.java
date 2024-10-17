package com.example.shop.dto;

import java.io.Serializable;

public class AuthenticationReponse implements Serializable{

	private static final long serialVersionUID = 1L;


	private String jwttoken = "";

	public AuthenticationReponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}
