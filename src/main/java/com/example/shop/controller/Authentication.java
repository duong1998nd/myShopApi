package com.example.shop.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.shop.dto.AuthenticationRequest;
import com.example.shop.securityConfig.JwtUtils;
import com.example.shop.service.UserDetailsServiceImple;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/auth")
public class Authentication {
	@Autowired
	com.example.shop.service.AccountService accService;
	
	@Autowired
	UserDetailsServiceImple detailsService;

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private AuthenticationManager authenticationManager;


	@PostMapping("/login")
	public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest req, HttpServletResponse response) throws IOException {
		authenticationManager.authenticate( new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		final UserDetails user = detailsService.loadUserByUsername(req.getUsername());
		// UserEntity _user = accService.find(user.getUsername());
		// System.out.println(_user.getFullname());
		if(user != null) {
////			response.addHeader("Authorization", "Bearer "+jwtUtils.generateToken(user));
			Cookie cookie = new Cookie("jwt", jwtUtils.generateToken(user));
			cookie.setHttpOnly(true);
			cookie.setSecure(true);
			cookie.setMaxAge(1000 * 60 * 60 * 10);
			response.addCookie(cookie);
			
			return ResponseEntity.ok(jwtUtils.generateToken(user));
		}
		return ResponseEntity.status(400).body("some error has occurred");
	}

	public String requestHeaders(String jwt) throws IOException {
		String jwtToken = jwt;
		String url = "http://localhost:8080/api/auth/login";
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Authorization", "Bearer " + jwtToken);

		int responseCode = con.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			// xử lý phản hồi từ server nếu yêu cầu thành công
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuilder response = new StringBuilder();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			System.out.println(response.toString());
			return response.toString();
		} else {
			// xử lý lỗi nếu yêu cầu không thành công
			System.out.println("Lỗi: " + responseCode);
			return "Lỗi: " + responseCode;
		}
	}

}