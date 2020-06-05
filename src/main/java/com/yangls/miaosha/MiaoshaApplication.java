package com.yangls.miaosha;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.yangls.miaosha.dao")
//extends SpringBootServletInitializer用于打war包
public class MiaoshaApplication/* extends SpringBootServletInitializer*/{

	public static void main(String[] args) {
		SpringApplication.run(MiaoshaApplication.class, args);
	}

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(MiaoshaApplication.class);
	}*/
}
