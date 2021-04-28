//package com.muyi.courage.auth.config;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@EnableWebSecurity
//@Configuration
//@Slf4j
//public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//
////	@Override
////	protected void configure(HttpSecurity http) throws Exception {
////		http.authorizeRequests()
////				.requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
////				.antMatchers("/auth/login").permitAll()
////				.anyRequest().authenticated();
////	}
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//
//
//	public static void main(String[] args) {
//		// 计算 BCryptPasswordEncoder 密文
//		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//		System.out.println(bCryptPasswordEncoder.encode("admin"));
////		System.out.println(bCryptPasswordEncoder.matches("12345678", "$2a$10$emzMKJ4MprB98HJcaf2beuZjIRkA/WQA.uk.jmoNkgot.HvVTd75O"));
//
//	}
//}
